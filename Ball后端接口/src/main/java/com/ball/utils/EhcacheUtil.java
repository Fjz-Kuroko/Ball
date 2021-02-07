package com.ball.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.UUID;

public class EhcacheUtil {

    // 使用Ehcache默认配置获取单例的CacheManager实例
    // Ehcache会自动加载classpath根目录下名为ehcache.xml文件。
    final static CacheManager cacheManager = CacheManager.create();
    // 根据配置文件分别创建一个CacheManager实例 emailCodeCache
    final static Cache emailCodeCache = cacheManager.getCache("emailCodeCache");
    // 创建一个存储验证码缓存的实例
    final static Cache captchaCodeCache = cacheManager.getCache("captchaCodeCache");

    /**
     * 存入email验证码
     * @param email email
     * @param code 验证码
     */
    public static void insertEmailCode(String email, String code) {
        emailCodeCache.put(new Element(email, code));
    }

    /**
     * 根据email 获取对应的验证码
     * @param email email
     * @return 获取对应的验证码，如果过期了返回null
     */
    public static String getEmailCode(String email) {
        Element element = emailCodeCache.get(email);
        if (element == null) {
            return null;
        }
        return element.getObjectValue().toString();
    }

    /**
     * 存入图片验证码
     * @param uuid uuid
     * @param code 验证码
     */
    public static void insertCaptchaCode(String uuid, String code) {
        captchaCodeCache.put(new Element(uuid, code));
    }

    /**
     * 根据uuid获取验证码
     * @param uuid uuid
     * @return 对应的验证码，如果过期了会返回null
     */
    public static String getCaptchaCode(String uuid) {
        Element element = captchaCodeCache.get(uuid);
        if (element == null) {
            return null;
        }
        return element.getObjectValue().toString();
    }

//    public static void main(String[] args) {
//        String email = "2497965937@qq.com";
//        String code = "123456";
//        insertEmailCode(email, code);
//        try {
//            Thread.sleep(25000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(getEmailCode(email));
//        System.out.println(UUID.randomUUID().toString());
//    }

}
