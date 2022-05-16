package com.example.mall.config.handler;

import com.example.mall.common.MallException;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.config.annotation.TokenToUser;
import com.example.mall.dao.UserMapper;
import com.example.mall.dao.UserTokenMapper;
import com.example.mall.entity.User;
import com.example.mall.entity.UserToken;
import com.example.mall.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TokenToUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserTokenMapper userTokenMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.getParameterAnnotation(TokenToUser.class) instanceof TokenToUser) {
            String token = webRequest.getHeader("token");
            if (tokenUtil.isValidToken(token)) {
                UserToken userToken = userTokenMapper.findUserTokenByToken(token);
                if (userToken == null) {
                    MallException.fail(ServiceResultEnum.TOKEN_EXPIRE_ERROR.getResult());
                }
                if (tokenUtil.isExpiredUserToken(userToken)) {
                    MallException.fail(ServiceResultEnum.TOKEN_EXPIRE_ERROR.getResult());
                }
                User user = userMapper.findUserByUserId(userToken.getUserId());
                if (user == null) {
                    MallException.fail(ServiceResultEnum.USER_NULL_ERROR.getResult());
                }
                return user;
            } else {
                MallException.fail(ServiceResultEnum.NOT_LOGIN_ERROR.getResult());
            }
        }
        return null;
    }
}
