package com.scut.soul.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scut.soul.common.dto.LoginDto;
import com.scut.soul.common.dto.RegisterDto;
import com.scut.soul.common.lang.Result;
import com.scut.soul.entity.User;
import com.scut.soul.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result register(@Validated @RequestBody RegisterDto registerDto)
    {

        User user = userService.getOne(new QueryWrapper<User>().eq("id", registerDto.getUserId()));

        if(user != null ){
            return Result.fail("该手机号已存在！");
        }

        User new_user = new User();
        new_user.setUserId(registerDto.getUserId());
        new_user.setUsername(registerDto.getUsername());
        new_user.setPassword(registerDto.getPassword());
        new_user.setEmail(registerDto.getEmail());
        new_user.setStatus(0);

        userService.saveOrUpdate(new_user);


        return Result.succ(null);

    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {


        User user = userService.getOne(new QueryWrapper<User>().eq("id", loginDto.getUserId()));
        Assert.notNull(user, "用户不存在");

//        if(!SecureUtil.md5(loginDto.getPassword()).equals(user.getPassword())){
//            return Result.fail("密码不正确");
//        }

        if(!user.getPassword().equals(loginDto.getPassword())){
            return Result.fail("密码不正确");
        }


        return Result.succ(MapUtil.builder()
                .put("id", user.getUserId())
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
