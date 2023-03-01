package com.moma.momaadmin.util;

import com.moma.momaadmin.common.JwtConstant;
import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtil {

    /**
     * 签发JWT
     *
     * @param id
     * @param subject   可以是JSON数据 即可能少
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)  //主题
                .setIssuer("litenghao")  //签发者
                .setIssuedAt(now)    //签发时间
                .signWith(signatureAlgorithm, secretKey); //签名算法以及密钥
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            //过期时间
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 生成jwt token
     *
     * @param username
     * @return
     */
    public static String genJwtToken(String username) {
        return createJWT(username, username, 60 * 60 * 1000);
    }

    /**
     * 验证JWT
     *
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJWT(String jwtStr) {
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(JwtConstant.JWT_ERR_CODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(JwtConstant.JWT_ERR_CODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(JwtConstant.JWT_ERR_CODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.decode(JwtConstant.JWT_SECRET_KEY);
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) throws InterruptedException {
        //设置20秒失效
        String sc = createJWT("1", "李腾昊", 1000);
        System.out.println(sc);
        System.out.println(validateJWT(sc).getErrCode());
        System.out.println(validateJWT(sc).getClaims().getId());
        System.out.println(validateJWT(sc).getClaims().getSubject());
//        Thread.sleep(3000);
        System.out.println(validateJWT(sc).getClaims());
        Claims claims = validateJWT(sc).getClaims();
        String sc2 = createJWT(claims.getId(), claims.getSubject(), JwtConstant.JWT_TTL);
        System.out.println(sc2);
    }

}
