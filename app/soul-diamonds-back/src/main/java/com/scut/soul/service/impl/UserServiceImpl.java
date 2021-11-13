package com.scut.soul.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scut.soul.entity.User;
import com.scut.soul.mapper.UserMapper;
import com.scut.soul.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
