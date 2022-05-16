package com.example.mall.service.impl;

import com.example.mall.common.ServiceResultEnum;
import com.example.mall.dao.UserMapper;
import com.example.mall.entity.User;
import com.example.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String register(String loginName, String password) {
        if (userMapper.findUserByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }

        User user = new User();
        user.setLoginName(loginName);
        user.setNickName(loginName);
        user.setIntroduceSign("hello");
        user.setPasswordMd5(password);

        if (userMapper.save(user) != null) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
}
