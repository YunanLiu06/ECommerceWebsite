package com.example.mall.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
    private NumberUtil() {
    }

    public static boolean isPhone (String phone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-8])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isNotPhone (String phone) {
        return !isPhone(phone);
    }
}
