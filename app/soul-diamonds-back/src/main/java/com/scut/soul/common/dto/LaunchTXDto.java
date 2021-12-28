package com.scut.soul.common.dto;

import lombok.Data;
import java.util.Date;

@Data
public class LaunchTXDto {
    
    private String nameFrom;

    private String nameTo;

    private Date date;

    private String dateToString;

    private String goodsId;

}
