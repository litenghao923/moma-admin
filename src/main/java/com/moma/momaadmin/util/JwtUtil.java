package com.moma.momaadmin.util;

import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {

    public static final String FRONT_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String getJwtToken(String name){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("moma-member")
                .setIssuedAt(new Date())
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS256, FRONT_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtil.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(FRONT_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtil.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(FRONT_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtil.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(FRONT_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return claims.get("id").toString();
    }

    /**
     * 根据token获取用户名
     * @param request
     * @return
     */
    public static String getUsernameByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtil.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(FRONT_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return String.valueOf(claims.get("name").toString());
    }

}
