package com.vico.license.dao.test;

import com.vico.license.dao.UserDao;
import com.vico.license.pojo.User;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) // = extends SpringJUnit4ClassRunner
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class UserDaoTest {

    private static final Logger logger = Logger.getLogger(UserDaoTest.class);
    @Autowired
    UserDao uDao;

    @Test
    public void testGetTotal() {
        System.out.println("获取到的总数是" + uDao.getTotal());
    }

    @Test
    public void testgetUserByPage() {
        int i = 1;
        List<User> list = new ArrayList<User>();
        list = uDao.getUserByPage(8, 5);
        for (User user : list) {
            System.out.println("jieguo:  " + user.getUsername() + "  " + i++);
        }
    }

    //@Test
    public void testModifyUser() {
        int i = 1;
        User user = new User();
        user.setUserID(2);
        user.setUsername("tstette");
        user.setPassword("12321321");
        i = uDao.modifyUserByID(user);
        System.out.println(">>>>>>>>>" + i);
    }
}
