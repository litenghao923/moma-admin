package com.moma.momaadmin.common.security;

/**
 * jwt系统静态变量
 */
public class JwtConstant {

    //token不存在
    public static final int JWT_ERR_CODE_NULL = 4000;
    //token过期
    public static final int JWT_ERR_CODE_EXPIRE = 4001;
    //token验证未通过
    public static final int JWT_ERR_CODE_FAIL = 4002;
    //加密算法异常
    public static final int JWT_ERR_CODE_ALGORITHM = 4003;
    //token解密异常
    public static final int JWT_ERR_CODE_DECODE = 4004;

    //JWT密钥
    public static final String JWT_SECRET_KEY = "WZ5mA5alSmwMqA0i1w2NDOgivYBD7h5C";

    //token有效时间
    public static final long JWT_TTL = 24 * 60 * 60 * 1000;


}
