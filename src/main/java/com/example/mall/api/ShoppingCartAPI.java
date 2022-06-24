package com.example.mall.api;

import com.example.mall.api.param.SaveCartItemParam;
import com.example.mall.config.annotation.TokenToUser;
import com.example.mall.entity.User;
import com.example.mall.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartAPI {

    @PostMapping("/shopping-cart")
    public Result saveShoppingCartItem(@RequestBody SaveCartItemParam saveCartItemParam,
                                       @TokenToUser User user) {
        String saveResult = "success";


        return null;
    }
}
