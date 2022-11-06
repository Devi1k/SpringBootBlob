package com.example.springbootblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
    public static Boolean validKeyword(String keyword) {
        String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(keyword);
        return matcher.matches();
    }
}
