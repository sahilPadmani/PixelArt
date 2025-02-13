package com.fsd.art.service;

import com.fsd.art.model.req.UserReq;
import com.fsd.art.model.res.UserRes;
import com.fsd.art.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // later throw error
    public UserRes getUser(Integer id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null)
            return null;
        return userMapper.forUserRes(user);
    }

    public UserRes saveUser(UserReq userReq){
        var user = userMapper.toUser(userReq);
        var saveUser = userRepository.save(user);
        return userMapper.forUserRes(saveUser);
    }
}
