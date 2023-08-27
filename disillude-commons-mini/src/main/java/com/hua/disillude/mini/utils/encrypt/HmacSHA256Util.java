package com.hua.disillude.mini.utils.encrypt;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * HmacSHA256 计算工具类
 */
@Slf4j
public class HmacSHA256Util {

  /**
   * HmacSHA256 加密
   *
   * @param s
   *     待加密的字符串
   * @return 加密字符串
   */
  public static String encode(String key, String s) {
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    try {
      byte[] btKey = key.getBytes(StandardCharsets.UTF_8);
      byte[] btInput = s.getBytes(StandardCharsets.UTF_8);
      Mac hmacSha256 = Mac.getInstance("HmacSHA256");
      hmacSha256.init(new SecretKeySpec(btKey, "HmacSHA256"));
      // 获得密文
      byte[] md = hmacSha256.doFinal(btInput);
      // 把密文转换成十六进制的字符串形式
      int j = md.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (byte byte0 : md) {
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      log.error("HmacSHA256 error", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * HmacSHA256 加密，返回Base64编码后的字符串
   * 注意：不将密文转换成16进制后再进行base64编码，而是直接将密文进行base64编码
   *
   * @param s
   *     待加密的字符串
   * @return 加密字符串
   */
  public static String encodeReturnBase64(String key, String s) {
    try {
      byte[] btKey = key.getBytes(StandardCharsets.UTF_8);
      byte[] btInput = s.getBytes(StandardCharsets.UTF_8);
      Mac hmacSha256 = Mac.getInstance("HmacSHA256");
      hmacSha256.init(new SecretKeySpec(btKey, "HmacSHA256"));
      // 获得密文
      byte[] md = hmacSha256.doFinal(btInput);
      return Base64.getEncoder().encodeToString(md);
    } catch (Exception e) {
      log.error("HmacSHA256 error", e);
      throw new RuntimeException(e);
    }
  }
}
