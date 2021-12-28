package com.scut.soul.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SendToChain {

    private String nameFrom;

    private String nameTo;

    private String dateToString;

    private String goodsId;
}
