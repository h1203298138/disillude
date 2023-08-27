/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： JwtUtil.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.encrypt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * jwt令牌工具类
 *
 * @author Hedh
 * @since 1.0
 */
@Component
public class JwtUtil {
  public static final String JWT_ID = UUID.randomUUID().toString();
  public static final String JWT_SECRET = "c2hpeWFubG91MjAyMTAzMDc=";
  public static final int EXPIRE_TIME = 4 * 60 * 60 * 1000;// 过期时间1小时

  protected static SecretKey generalSecretKey() {
    byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
    return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
  }

  /**
   * 创建JWT
   *
   * @param audience
   *     jwt接收者
   * @param subject
   *     用户信息，可将多个关键信息转为JSON进行存放
   * @return JWT字符串
   */
  public static String getJwt(String audience, String subject) {
    // 设置签名算法
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    Map<String, Object> claims = new HashMap<>(2);
    claims.put("username", "admin");
    claims.put("password", "123456");
    long nowTime = System.currentTimeMillis();
    Date issuedAt = new Date(nowTime);
    SecretKey key = generalSecretKey();
    JwtBuilder builder = Jwts.builder()
        .setClaims(claims)
        .setId(JWT_ID)
        .setIssuedAt(issuedAt)
        .setIssuer("admin")
        .setSubject(subject)
        .signWith(signatureAlgorithm, key);
    // 设置过期时间
    long exp = nowTime + (long) EXPIRE_TIME;
    builder.setExpiration(new Date(exp));
    builder.setAudience(audience);
    return builder.compact();
  }

  /**
   * 解析JWT
   */
  public static Claims parseJwt(String jwt) {
    SecretKey key = generalSecretKey();
    return Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(jwt)
        .getBody();
  }

}