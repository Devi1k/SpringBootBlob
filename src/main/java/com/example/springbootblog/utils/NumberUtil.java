package com.example.springbootblog.utils;

public class NumberUtil {
    public static int getRandomNum(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) random += 0.1;
        for (int i = 0; i < length; ++i) {
            num *= 10;
        }
        return (int) ((random * num));
    }
}
