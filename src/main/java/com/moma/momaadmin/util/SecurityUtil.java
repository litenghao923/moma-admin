package com.moma.momaadmin.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {

    /**
     * 生成BCryptPasswordEncoder密码
     * @param password 明文密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    /**
     * 判断密码是否一致
     * @param rawPassword 对照明文密码
     * @param encodedPassword 加密字符串
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword,String encodedPassword){
        return new BCryptPasswordEncoder().matches(rawPassword,encodedPassword);
    }
}
