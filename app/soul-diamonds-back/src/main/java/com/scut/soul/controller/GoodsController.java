package com.scut.soul.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.soul.common.lang.Result;
import com.scut.soul.entity.Goods;
import com.scut.soul.entity.Reviews;
import com.scut.soul.service.GoodsService;
import com.scut.soul.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @GetMapping("/goods/getInfo")
    public Result list() {

        List<Goods> goodsList = goodsService.list();
//        System.out.println(goodsList.toArray());
        return Result.succ(goodsList);
    }


}


