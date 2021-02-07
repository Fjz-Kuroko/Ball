package com.ball.service;

import com.ball.entity.Address;

import java.util.List;

public interface AddressService {
    // 新增地址
    int addAddress(Address address);
    // 根据aid删除地址
    int delAddress(int aid);
    // 更新地址
    int update(Address address);
    // 根据aid查询地址
    Address selectByAid(int aid);
    // 根据email查询用户所有地址
    List<Address> selectByEmail(String email);
}
