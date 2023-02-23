package com.moma.momaadmin.common;

/**
 * jwt系统静态变量
 */
public class JwtConstant {

    //token不存在
    public static final int JWT_ERRCODE_NULL = 4000;
    //token过期
    public static final int JWT_ERRCODE_EXPIRE = 4001;
    //token验证未通过
    public static final int JWT_ERRCODE_FAIL = 4002;

    //JWT密钥
    public static final String JWT_SECERT = "WZ5mA5alSmwMqA0i1w2NDOgivYBD7h5C";

    //token有效时间
    public static final long JWT_TTL = 24 * 60 * 60 * 1000;


}
