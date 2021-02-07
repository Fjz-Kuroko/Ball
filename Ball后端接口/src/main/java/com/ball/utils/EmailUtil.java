package com.ball.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailUtil {

    private final static String USER_NAME = "2497965937@qq.com";
    private final static String PASSWORD = "qolkthpngjvdebbj";

    /**
     * 发送邮件验证码
     * @param emailAddress 发送的邮件地址（收件人地址）
     * @param code 邮件验证码
     * @return boolean 是否发送成功
     */
    public static boolean sendEmail(String emailAddress, String code) {
        //创建一个HtmlEmail实例对象
        HtmlEmail htmlEmail = new HtmlEmail();
        // 设置邮箱的SMTP服务器
        htmlEmail.setHostName("smtp.qq.com");
        // 设置发送的字符类型
        htmlEmail.setCharset("utf-8");
        try {
            // 设置接收人
            htmlEmail.addTo(emailAddress);
            // 设置发送人
            htmlEmail.setFrom("2497965937@qq.com", "Kuroko");
            // 设置邮箱地址和客户端授权码
            htmlEmail.setAuthentication(USER_NAME, PASSWORD);
            // 设置邮件主题
            htmlEmail.setSubject("球球网邮箱验证");
            // 发送的内容
            htmlEmail.setMsg("亲爱的<b>"+ emailAddress +"</b>，您的邮件验证码为： <b>" + code + "</b>");
            // 发送
            htmlEmail.send();
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
