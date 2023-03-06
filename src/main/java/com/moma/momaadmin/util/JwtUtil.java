package com.moma.momaadmin.util;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.moma.momaadmin.common.security.JwtConstant;
import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtil {


    /**
     * 签发JWT
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey =generalKey();
        JwtBuilder builder=Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("moma_litenghao")
                .setIssuedAt(now)
                .signWith(signatureAlgorithm,secretKey);
        if (ttlMillis>=0){
            long expMillis=nowMillis+ttlMillis;
            Date expDate=new Date(expMillis);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 生成jwt token
     * @param username
     * @return
     */
    public static String genJwtToken(String username){
        return createJWT(username,username,60*60*1000);
    }

    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJWT(String jwtStr){
        CheckResult checkResult=new CheckResult();
        Claims claims=null;
        try {
            claims=parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        }catch (NullPointerException e){
            checkResult.setErrCode(JwtConstant.JWT_ERR_CODE_NULL);
            checkResult.setSuccess(false);
        }
        catch (SignatureVerificationException e){
            checkResult.setErrCode(JwtConstant.JWT_ERR_CODE_FAIL);
            checkResult.setSuccess(false);
        }catch (TokenExpiredException e){
            checkResult.setErrCode(JwtConstant.JWT_ERR_CODE_EXPIRE);
            checkResult.setSuccess(false);
        }catch (AlgorithmMismatchException e){
            checkResult.setErrCode(JwtConstant.JWT_ERR_CODE_ALGORITHM);
            checkResult.setSuccess(false);
        }catch (JWTDecodeException e){
            checkResult.setErrCode(JwtConstant.JWT_ERR_CODE_DECODE);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 生成加密key
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(JwtConstant.JWT_SECRET_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析JWT字符串
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt){
        SecretKey secretKey=generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }
}
