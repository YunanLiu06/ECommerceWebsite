package com.example.mall.api;

import com.example.mall.api.param.UserLoginParam;
import com.example.mall.api.param.UserRegisterParam;
import com.example.mall.api.param.UserUpdateParam;
import com.example.mall.api.vo.UserVO;
import com.example.mall.common.Constants;
import com.example.mall.common.ServiceResultEnum;
import com.example.mall.config.annotation.TokenToUser;
import com.example.mall.entity.User;
import com.example.mall.service.UserService;
import com.example.mall.util.NumberUtil;
import com.example.mall.util.Result;
import com.example.mall.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/login")
    public Result<String> login(@RequestBody UserLoginParam userLoginParam) {
        if (NumberUtil.isNotPhone(userLoginParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }

        String loginResult = userService.login(userLoginParam.getLoginName(), userLoginParam.getPassword());

        log.info("login api,loginName={},loginResult={}", userLoginParam.getLoginName(), loginResult);

        if (StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);

            return result;
        }

        return ResultGenerator.genFailResult(loginResult);
    }

    @PostMapping("/user/logout")
    public Result<String> logout (@TokenToUser User user) {
        boolean logoutResult = userService.logout(user.getUserId());
        log.info("logout api,loginMallUser={}", user.getUserId());

        if (logoutResult) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("logout error");
    }

    @GetMapping("/user/info")
    public Result<UserVO> getUserDetail(@TokenToUser User user){
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        return ResultGenerator.genSuccessResult(userVO);

    }

    @PutMapping("/user/info")
    public Result updateInfo(@RequestBody UserUpdateParam userUpdateParam, @TokenToUser User user) {
        Boolean flag = userService.updateUserInfo(userUpdateParam, user.getUserId());
        if (flag == true) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("Fail to update");
    }

}
