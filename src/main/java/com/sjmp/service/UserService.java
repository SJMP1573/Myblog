package com.sjmp.service;

import com.sjmp.po.User;


public interface UserService {
    User checkUser(String username, String password);
}
