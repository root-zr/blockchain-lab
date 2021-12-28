package com.scut.soul.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegisterDto implements Serializable{
    @NotBlank(message = "电话号码不能为空")
    private String userId;
    @NotBlank(message = "昵称不能为空")
    private String username;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "密码不能为空")
    private String password;

    private String checkid;

    private String checkPass;
}
