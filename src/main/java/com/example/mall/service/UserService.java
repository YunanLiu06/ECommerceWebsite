package com.example.mall.service;

import com.example.mall.api.param.UserUpdateParam;

public interface UserService {

    String register(String loginName, String password);
    String login(String loginName, String password);
    Boolean logout(Long userId);
    Boolean updateUserInfo(UserUpdateParam userUpdateParam, Long userId);
}
