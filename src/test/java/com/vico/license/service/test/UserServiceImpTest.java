package com.vico.license.service.test;

import com.alibaba.fastjson.JSON;
import com.vico.license.pojo.DataTableRequest;
import com.vico.license.pojo.User;
import com.vico.license.pojo.UserByPage;
import com.vico.license.service.UserService;
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

public class UserServiceImpTest {

    private static final Logger logger = Logger.getLogger(LicenseServiceImpTest.class);
    @Autowired
    private UserService usi;

    //@Test
    public void testSelectAllUsers() {

        List<User> users = new ArrayList<User>();
        users = usi.SelectAllUsers();

        for (User user : users) {
            System.out.println("*****" + user.getUsername());
        }
    }

    //@Test
    public void testAdd() {
        User user = new User();
        user.setUsername("卧槽");
        int i = usi.addUser(user);
        System.out.println("插入结果是" + i);
    }


    @Test
    public void testgetUserByPage() {
        DataTableRequest request = new DataTableRequest();
        request.setDraw(1);
        request.setLength(10);
        request.setStart(0);
        UserByPage uPage = usi.getUserByPage(request);
        System.out.println(JSON.toJSONString(uPage));

    }


}