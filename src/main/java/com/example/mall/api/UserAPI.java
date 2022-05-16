package com.example.mall.api;

import com.example.mall.api.param.UserRegisterParam;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.service.UserService;
import com.example.mall.util.NumberUtil;
import com.example.mall.util.Result;
import com.example.mall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    @Resource
    private UserService userService;

    @PostMapping("/user/register")
    public Result register(@RequestBody @Valid UserRegisterParam userRegisterParam) {
        if (NumberUtil.isNotPhone(userRegisterParam.getLoginName())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }

        String registerResult = userService.register(userRegisterParam.getLoginName(), userRegisterParam.getPassword());
        log.info("register api, loginName = {}, logResult = {}", userRegisterParam.getLoginName(), registerResult);

        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }

        return ResultGenerator.genFailResult(registerResult);
    }
}
