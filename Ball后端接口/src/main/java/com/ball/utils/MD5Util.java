package com.ball.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    private static final String HASH_ALGORITHM_NAME = "MD5";

    public static String getMD5Pwd(String pwd, String uid) {
        try {
            byte[] salt = uid.getBytes(StandardCharsets.UTF_8);
            MessageDigest m = MessageDigest.getInstance(HASH_ALGORITHM_NAME);
            m.update(salt);
            m.update(pwd.getBytes(StandardCharsets.UTF_8));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (byte b : s) {
                result.append(Integer.toHexString((0x000000ff & b) | 0xffffff00)
                        .substring(6));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pwd;
    }

}
