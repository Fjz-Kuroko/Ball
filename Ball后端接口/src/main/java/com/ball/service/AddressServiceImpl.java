package com.ball.service;

import com.ball.dao.AddressDao;
import com.ball.dao.UserDao;
import com.ball.entity.Address;
import com.ball.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private UserDao userDao;

    @Override
    public int addAddress(Address address) {
        User user = userDao.selectUserByEmail(address.getEmail());
        if (user == null) {
            return -1;
        }
        return addressDao.addAddress(address);
    }

    @Override
    public int delAddress(int aid) {
        return addressDao.delAddress(aid);
    }

    @Override
    public int update(Address address) {
        Address selectByAid = addressDao.selectByAid(address.getAid());
        if (selectByAid == null) {
            return -1;
        }
        return addressDao.update(address);
    }

    @Override
    public Address selectByAid(int aid) {
        return addressDao.selectByAid(aid);
    }

    @Override
    public List<Address> selectByEmail(String email) {
        return addressDao.selectByEmail(email);
    }
}
