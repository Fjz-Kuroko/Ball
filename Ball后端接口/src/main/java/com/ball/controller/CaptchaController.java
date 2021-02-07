package com.ball.controller;

import com.ball.dto.Captcha;
import com.ball.utils.EhcacheUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @RequestMapping("/image")
    public void imageCode(HttpServletResponse response) {
        // 获取验证码以及验证码图片
        Map<String, Object> map = Captcha.getImageCode(90, 37);
        // 获取随机的uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 把验证码存进缓存
        EhcacheUtil.insertCaptchaCode(uuid, map.get("code").toString().toLowerCase());
        System.out.println("=========本次uuid为：" + uuid);
        System.out.println("=========本次验证码为：" + map.get("code").toString().toLowerCase());
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, uuid, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        // 禁用缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("uuid", uuid);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Access-Control-Expose-Headers", "uuid");
//        response.addHeader("uuid", uuid);
        // 这里要指定响应的类型
        response.setContentType("image/jpeg");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write((BufferedImage) map.get("image"), "jpg", outputStream);
        } catch (IOException e) {
            System.out.println("CaptchaController.imageCode:图片验证码输出流出现异常：" + e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println("CaptchaController.imageCode:图片验证码输出流关闭异常：" + e.getMessage());
                }
            }
        }
    }

}
