package com.ball.controller;

import com.ball.dto.ResponseData;
import com.ball.utils.EhcacheUtil;
import com.ball.utils.EmailUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/email")
public class EmailController {

    @PostMapping("/code")
    public ResponseData code(String email) {
        String code = getRandomCode();
        if (EmailUtil.sendEmail(email, code)) {
            EhcacheUtil.insertEmailCode(email, code);
            return ResponseData.ok();
        }
        return ResponseData.serverInternalError();
    }

    public static String getRandomCode() {
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i = 0; i < 4; i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

}
