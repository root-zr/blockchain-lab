package com.scut.soul.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("reviews")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "reviews_id")
    private Long reviewsId;

    private String id;

    private String title;

    private String content;

    private Integer status;

    private LocalDateTime created;


}
