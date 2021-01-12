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

//    由于要使用 DAO 中的方法，所以将 UserRepository 注入过来！
    @Autowired
    private UserRepository userRepository;

//   后台登录时，检查用户名和密码是否正确，正确则返回user，否则返回空！
    @Override
    public User checkUser(String username, String password) {
//        调用 DAO 层，传入用户名、密码校验！
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
