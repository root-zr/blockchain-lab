package com.scut.soul.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "账号不能为空")
    private String userId;

    @NotBlank(message = "密码不能为空")
    private String password;


}
