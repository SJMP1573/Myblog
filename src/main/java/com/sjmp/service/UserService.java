package com.sjmp.service;

import com.sjmp.po.User;


public interface UserService {
    //    接口 ，根据用户名和密码
    User checkUser(String username, String password);
}
