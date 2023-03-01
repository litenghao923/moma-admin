package com.moma.momaadmin.entity;

import lombok.Data;

@Data
public class LoginBody extends SysUser{
    //验证码
    private String code;
    //唯一
    private String uuid;
}
