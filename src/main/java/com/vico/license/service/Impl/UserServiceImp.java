package com.vico.license.service.Impl;

import com.alibaba.fastjson.JSON;
import com.vico.license.aop.TokenManager;
import com.vico.license.dao.UserDao;
import com.vico.license.pojo.DataTableRequest;
import com.vico.license.pojo.User;
import com.vico.license.pojo.UserByPage;
import com.vico.license.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImp implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImp.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RedisTemplate<String,User> redisTemplate;

    @Autowired
    private RedisCacheManager manager;

    @Override
    public List<User> SelectAllUsers() {

        List<User> users = new ArrayList<User>();

        users = userDao.selectAllUsers();

        return users;
    }

    /**
     * 新加的用户同时会被写进缓存当中
     * 可以把这个@cacheput注解加在接口上,这样所有实现类都可以用,但是不推荐这样搞
     * @param user
     * @return
     */
    @Override
    @CachePut("license")
    public int addUser(User user) {
        int res = 0;
        try {
            res = userDao.addUser(user);
        }catch (Exception e){
            logger.error("add user exception:"+user.toString()+e);
        }
        return res;
    }

    @Override
    public UserByPage getUserByPage(DataTableRequest request) {
        Integer length = 0;
        Integer start = 0;
        Integer draw = 0;
        Integer recordsTotal = 0;
        Integer filterRecordsTotal = 0;
        List<User> users = new ArrayList<>();

        length = request.getLength();
        start = request.getStart();
        draw = request.getDraw();
        try {
            users = userDao.getUserByPage(length, start);
            recordsTotal = userDao.getTotal();
            filterRecordsTotal = recordsTotal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserByPage result = new UserByPage();
        result.setDraw(draw);
        result.setData(users);
        result.setRecordsTotal(recordsTotal);
        result.setRecordsFiltered(filterRecordsTotal);
        System.out.println("service>>>>>>>>>>" + JSON.toJSONString(result));
        return result;
    }

    @Override
    public int modifyUserByID(User user) {
        // TODO Auto-generated method stub
        int res = 0;

        try {
            res = userDao.modifyUserByID(user);
            //测试spring事务:
            //userDao.addUser(user);  //添加该user会失败,因为ID和原来的一样,抛出异常应该回滚,上一条sql不应该执行

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();   //关键语句,只有抛出这个运行时异常,事务才会发生回滚
        }
        return res;
    }

    /**
     * cacheable注解:基于切面,请求会先到缓存中查请求参数即user对象,查到直接
     * 返回方法需要返回的值:这里为group而不是user对象
     * 如果没有查到,会放请求下去,查完数据库后会把user对象写进缓存
     * @param user
     * @return
     */
    @Override
    @Cacheable("license")
    public int userLogin(User user) {
        User userIncache = redisTemplate.opsForValue().get("licenseUser:"+user.getUsername()+"");

        //template手动操作缓存
//        if (userIncache != null) {
//            System.out.println("缓存有此结果,查缓存进行比较");
//            if (!user.getPassword().equals(userIncache.getPassword())) return -1;
//        }else {
//            System.out.println("缓存查无结果,查数据库如果合法则放进缓存");
//        }

        System.out.println(String.valueOf(userIncache));
        Set<String> set = redisTemplate.keys("*");
        Collection<String> set2 = manager.getCacheNames();
        set.forEach(System.out::println);
        set2.forEach(System.out::println);
        // TODO Auto-generated method stub
        int usergroup = -1;
        User user2 = userDao.getUserByName(user.getUsername());

        try {
            if (user2 != null) {
                if (user2.getPassword().equals(user.getPassword())) {
                    usergroup = user2.getUsergroup();
//                    redisTemplate.opsForValue().set("licenseUser:"+user.getUsername()+"",user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usergroup;
    }

    @Override
    public String createToken(String username) {
        String token = "";
        token = tokenManager.createToken(username);
        return token;
    }

}
