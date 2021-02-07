package com.ball.dao;

import com.alibaba.fastjson.JSONObject;
import com.ball.entity.Message;
import com.ball.utils.SequenceUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageDao {

    // 格式化
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 创建MongoDB 客户端
    static MongoClient mongoClient = new MongoClient("localhost", 27017);
    // 连接数据库
    static MongoDatabase database = mongoClient.getDatabase("balldb");
    static {
        // 创建collection，相当于表
        if (database.getCollection("message") == null) {
            database.createCollection("message");
        }
    }
    // 连接collection
    static MongoCollection<Document> collection = database.getCollection("message");

    public static void insertMessage(String email, String msg) {
        // 新增记录
        Document document = new Document();
        document.put("mid", SequenceUtil.getLocalTrmSeq());
        document.put("email", email);
        document.put("msg", msg);
        document.put("time", simpleDateFormat.format(new Date()));
        collection.insertOne(document);
    }

    public static List<Message> getAllMessage() {
        FindIterable<Document> findIterable = collection.find();
        List<Message> messages = new ArrayList<>();
        for (Document document : findIterable) {
            JSONObject jsonObject = JSONObject.parseObject(document.toJson());
            Message message = jsonObject.toJavaObject(Message.class);
            messages.add(0, message);
        }
        return messages;
    }

//    public static void printAll(MongoCollection<Document> collection) {
//        FindIterable<Document> findIterable = collection.find();
//
//        List<Message> messages = new ArrayList<>();
//        for (Document document : findIterable) {
//            JSONObject jsonObject = JSONObject.parseObject(document.toJson());
//            Message message = jsonObject.toJavaObject(Message.class);
//            messages.add(message);
//            System.out.println(document.toJson());
//        }
//        System.out.println("==================");
//        System.out.println(messages);
//    }

//    public static void main(String[] args) {
////        insertMessage("2497965937@qq.com", "很棒的网站！");
//        System.out.println(getAllMessage());
//    }

}