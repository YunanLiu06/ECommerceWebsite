package com.example.mall.service;

import com.example.mall.api.param.SaveCartItemParam;

public interface ShoppingCartService {
    String saveCartItem(SaveCartItemParam saveCartItemParam, Long userId);

}
