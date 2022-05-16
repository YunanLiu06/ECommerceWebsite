package com.example.mall.config;

import com.example.mall.config.handler.TokenToUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

public class MallWebMvcConfigurer extends WebMvcConfigurationSupport {

    @Autowired
    private TokenToUserMethodArgumentResolver tokenToUserMethodArgumentResolver;

    public void addArgumentResolver(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenToUserMethodArgumentResolver);
    }
}
