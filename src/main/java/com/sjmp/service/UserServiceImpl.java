package com.sjmp.service;

import com.sjmp.dao.UserRepository;
import com.sjmp.po.User;
import com.sjmp.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: sjmp1573
 * @date: 2020/9/29 6:44
 * @description:
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
