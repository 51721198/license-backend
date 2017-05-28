package com.vico.license.controller;

import com.alibaba.fastjson.JSON;
import com.vico.license.pojo.DataTableRequest;
import com.vico.license.pojo.ProcessResult;
import com.vico.license.pojo.User;
import com.vico.license.pojo.UserByPage;
import com.vico.license.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("user")
public class UserController {
    public static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "showAllUsers", method = RequestMethod.GET)
    public List<User> showAllUsers() {

        List<User> users = new ArrayList<User>();
        try {
            users = userService.SelectAllUsers();
        } catch (Exception e) {
            logger.error(e);
        }
        return users;
    }

    @RequestMapping(value = "getUserByPage", method = RequestMethod.POST)
    public UserByPage getUserByPage(HttpServletRequest request) {
        DataTableRequest dataTableRequest = new DataTableRequest();
        UserByPage result = new UserByPage();
        try {
            dataTableRequest.setDraw(Integer.parseInt(request.getParameter("draw")));
            dataTableRequest.setLength(Integer.parseInt(request.getParameter("length")));
            dataTableRequest.setStart(Integer.parseInt(request.getParameter("start")));
            dataTableRequest.setObject(null);
            if (dataTableRequest != null) result = userService.getUserByPage(dataTableRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(result));
        return result;
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        int res = 0;
        System.out.println("+++++" + user.getUsername());
        try {
            res = userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @RequestMapping(value = "modifyUser", method = RequestMethod.POST)
    public int modifyUser(@RequestBody User user) {
        int res = 0;
        try {
            res = userService.modifyUserByID(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping(value = "login" ,method = RequestMethod.POST)
    public ProcessResult userLogin(@RequestBody User user) {
        ProcessResult result = new ProcessResult();
        int usergroup = -1;
        try {
            if (user != null) {
                usergroup = userService.userLogin(user);
                result.setResultcode(usergroup);
                result.setResultdesc(userService.createToken(user.getUsername()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

