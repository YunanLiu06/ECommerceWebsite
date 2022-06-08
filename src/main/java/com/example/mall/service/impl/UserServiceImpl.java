package com.example.mall.service.impl;

import com.example.mall.api.param.UserUpdateParam;
import com.example.mall.common.MallException;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.dao.UserTokenMapper;
import com.example.mall.dao.UserMapper;
import com.example.mall.entity.User;
import com.example.mall.entity.UserToken;
import com.example.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Override
    public String register(String loginName, String password) {
        if (userMapper.findUserByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }

        User user = new User();
        user.setLoginName(loginName);
        user.setNickName(loginName);
        user.setIntroduceSign("hello");
        user.setPassword(password);

        if (userMapper.save(user) != null) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String password) {

        User user = userMapper.findUserByLoginNameAndPassword(loginName, password);
        if (user != null) {
            String token = "12345678901234567890123456789012";
            UserToken userToken = userTokenMapper.findUserTokenByUserId(user.getUserId());

            Date now = new Date();
            Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);

            if (userToken == null) {
                userToken = new UserToken();
                userToken.setUserId(user.getUserId());
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);

                if (userTokenMapper.save(userToken) != null) {
                    return token;
                }
            } else {
                userToken.setToken(token);
                userToken.setUpdateTime(now);
                userToken.setExpireTime(expireTime);

                if (userTokenMapper.save(userToken) != null) {
                    return token;
                }
            }
        }

        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public Boolean logout(Long userId) {
        userTokenMapper.deleteById(userId);
        return true;
    }

    @Override
    public Boolean updateUserInfo(UserUpdateParam userUpdateParam, Long userId) {
        User user = userMapper.findUserByUserId(userId);
        if (user == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        user.setNickName(userUpdateParam.getNickname());
        user.setPassword(userUpdateParam.getPassWord());
        user.setIntroduceSign(userUpdateParam.getIntroduceSign());

        if (userMapper.save(user) != null) {
            return true;
        }
        return false;
    }
}
