package com.scut.soul.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.soul.common.lang.Result;
import com.scut.soul.entity.Reviews;
import com.scut.soul.entity.User;
import com.scut.soul.service.ReviewsService;
import com.scut.soul.service.UserService;
import com.scut.soul.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;


@RestController
@RequestMapping
public class ReviewsController {

    @Autowired
    ReviewsService reviewsService;

//    @RequiresAuthentication
    @GetMapping("/reviews")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);

        QueryWrapper<Reviews> reviewsQueryWrapper = new QueryWrapper<Reviews>().orderByDesc("created");

        IPage pageData = reviewsService.page(page,reviewsQueryWrapper );

//        System.out.println(pageData.getRecords());

        return Result.succ(pageData);
    }

    @PostMapping("/reviews/edit")
    public Result edit(@Validated @RequestBody Reviews reviews) {

        if(reviews == null || reviews.getId() == null) {
           return Result.fail(null);

        }

        Reviews temp = new Reviews();
        temp.setId(reviews.getId());
        temp.setTitle(reviews.getTitle());
        temp.setContent(reviews.getContent());
        temp.setCreated(LocalDateTime.now());

        reviewsService.saveOrUpdate(temp);

        return Result.succ(null);
    }


}


