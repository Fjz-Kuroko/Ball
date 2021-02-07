package com.ball.controller;

import com.ball.dao.MessageDao;
import com.ball.dto.ResponseData;
import com.ball.entity.Message;
import com.ball.utils.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @PostMapping("/addMessage")
    public ResponseData addMessage(String email, String msg, HttpServletRequest request) {
        // 验证添加地址的邮箱和当前请求的邮箱是否一致
        if (!JWTUtil.verifyId(email, request)) {
            ResponseData responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        if (msg == null || "".equals(msg)) {
            ResponseData responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "留言不能为空！");
            return responseData;
        }
        MessageDao.insertMessage(email, msg);
        return ResponseData.ok();
    }

    @RequestMapping("/getAllMessage")
    public ResponseData getAllMessage(String adminName, HttpServletRequest request) {
        ResponseData responseData;
        // 验证请求头的token中的adminName和操作的adminName是否一致
        if (!JWTUtil.verifyId(adminName, request)) {
            responseData = ResponseData.unauthorized();
            responseData.putDataValue("msg", "没有权限！");
            return responseData;
        }
        List<Message> messages = MessageDao.getAllMessage();
        responseData = ResponseData.ok();
        responseData.putDataValue("messages", messages);
        return responseData;
    }

}
