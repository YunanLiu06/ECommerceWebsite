package com.example.mall.util;

import com.example.mall.common.Constants;
import com.example.mall.entity.UserToken;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    public boolean isValidToken (String token) {
        if (null != token && !"".equals(token) && token.length() == Constants.TOKEN_LENGTH) {
             return true;
        }
        return false;
    }

    public boolean isExpiredUserToken (UserToken userToken) {
        return userToken.getExpireTime().getTime() <= System.currentTimeMillis();
    }
    public boolean isNotExpiredUserToken (UserToken userToken) {
        return !isExpiredUserToken(userToken);
    }

}
