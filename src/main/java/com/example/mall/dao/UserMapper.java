package com.example.mall.dao;

import com.example.mall.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserMapper extends CrudRepository<User, Long> {
    User findUserByLoginName(String loginName);
    User save(User user);
    User findUserByLoginNameAndPassword(String loginName, String password);
}
