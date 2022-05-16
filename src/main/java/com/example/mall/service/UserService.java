package com.example.mall.service;

public interface UserService {

    String register(String loginName, String password);
    String login(String loginName, String password);
    Boolean logout(Long userId);
}
