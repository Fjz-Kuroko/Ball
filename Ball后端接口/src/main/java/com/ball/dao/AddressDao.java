package com.ball.dao;

import com.ball.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("addressDao")
@Mapper
public interface AddressDao {
    int addAddress(Address address);
    int delAddress(int aid);
    int update(Address address);
    Address selectByAid(int aid);
    List<Address> selectByEmail(String email);
}
