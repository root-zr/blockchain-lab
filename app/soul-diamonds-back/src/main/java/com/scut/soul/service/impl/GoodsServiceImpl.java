package com.scut.soul.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scut.soul.entity.Goods;
import com.scut.soul.entity.Reviews;
import com.scut.soul.mapper.GoodsMapper;
import com.scut.soul.mapper.ReviewsMapper;
import com.scut.soul.service.GoodsService;
import org.springframework.stereotype.Service;


@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}
