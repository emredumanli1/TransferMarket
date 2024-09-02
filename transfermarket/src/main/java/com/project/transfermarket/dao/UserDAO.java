package com.project.transfermarket.dao;

import java.util.List;

import com.project.transfermarket.entity.User;

public interface UserDAO {

    void save(User user);

    User findUserByName(String name);

    void update(User user);

    void deleteUserByName(String name);

    List<User> findAll();

}
