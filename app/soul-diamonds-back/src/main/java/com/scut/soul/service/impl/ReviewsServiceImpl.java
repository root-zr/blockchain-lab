package com.scut.soul.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scut.soul.entity.Reviews;
import com.scut.soul.mapper.ReviewsMapper;
import com.scut.soul.service.ReviewsService;
import org.springframework.stereotype.Service;


@Service
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements ReviewsService {

}
