package com.example.springbootblog.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class SystemUtil {

    public static String genToken(String src) {
        if (src == null || "".equals(src)) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            return null;
        }
    }
}
