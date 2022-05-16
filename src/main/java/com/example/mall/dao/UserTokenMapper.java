package com.example.mall.dao;

import com.example.mall.entity.UserToken;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenMapper extends CrudRepository<UserToken, Long> {
    UserToken findUserTokenByUserId(Long userId);
    UserToken save(UserToken userToken);
    UserToken findUserTokenByToken(String token);
    void deleteById(Long userId);
}
