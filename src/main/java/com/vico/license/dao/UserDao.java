package com.vico.license.dao;

import com.vico.license.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {

    List<User> selectAllUsers();

    int addUser(User user);

    List<User> getUserByPage(@Param("length") Integer length, @Param("start") Integer start);

    Integer getTotal();

    int modifyUserByID(User user);

    User getUserByName(String username);
}
