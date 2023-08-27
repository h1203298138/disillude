/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： AESUtil.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.encrypt;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加密工具类 - AES对称加密算法
 *
 * @author Hedh
 * @since 1.0
 */
@Slf4j
public class AESUtil {
  /**
   * 是否初始化
   * https://www.cjavapy.com/article/697/
   */
  public static boolean initialized = false;

  /** BouncyCastle作为安全提供，防止我们加密解密时候因为jdk内置的不支持改模式运行报错。 **/
  public static void initialize() {
    if (AESUtil.initialized) {
      return;
    }
    AESUtil.initialized = true;
  }

  /**
   * 加密，对秘钥和iv做MD5处理
   * 考虑到 jdk 的关系，取MD5的16位长度
   */
  public static String encryptWithMd5Params(String plainText, String secretKey, String iv) {
    return encrypt(plainText, MD5Util.encode(secretKey).substring(0, 16), MD5Util.encode(iv).substring(0, 16));
  }

  /**
   * 解密，对秘钥和iv做MD5处理
   * 考虑到 jdk 的关系，取MD5的16位长度
   */
  public static String decryptWithMd5Params(String cipherText, String secretKey, String iv) {
    return decrypt(cipherText, MD5Util.encode(secretKey).substring(0, 16), MD5Util.encode(iv).substring(0, 16));
  }

  // 加密
  public static String encrypt(String src, String secretKey, String iv) {
    return AESUtil.encrypt(src, secretKey.getBytes(), iv.getBytes());
  }

  public static String encrypt(String plainText, byte[] secretKey, byte[] iv) {
    try {
      AESUtil.initialize();
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      SecretKeySpec skeySpec = new SecretKeySpec(secretKey, "AES");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
      byte[] plainBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(plainBytes);
      // https://www.cjavapy.com//article/697
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public static String decrypt(String cipherText, String secretKey, String iv) {
    return AESUtil.decrypt(cipherText, secretKey.getBytes(), iv.getBytes());
  }

  public static String decrypt(String cipherText, byte[] secretKey, byte[] iv) {
    try {
      AESUtil.initialize();
      SecretKeySpec skeySpec = new SecretKeySpec(secretKey, "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      // https://www.cjavapy.com//article/697
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
      // parseHexStr2Byte(sSrc);//先用base64解密
      byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
      byte[] original = cipher.doFinal(cipherBytes);
      return new String(original, StandardCharsets.UTF_8);
    } catch (Exception ex) {
      log.error(ex.getMessage());
      return null;
    }
  }

  /**
   * 将二进制转换成十六进制
   */
  public static String parseByte2HexStr(byte[] buf) {
    StringBuilder sb = new StringBuilder();
    for (byte b : buf) {
      String hex = Integer.toHexString(b & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex.toUpperCase());
    }
    return sb.toString();
  }

  /**
   * 将十六进制转换为二进制
   */
  public static byte[] parseHexStr2Byte(String hexStr) {
    if (hexStr.length() < 1) {
      return null;
    } else {
      byte[] result = new byte[hexStr.length() / 2];
      for (int i = 0; i < hexStr.length() / 2; i++) {
        int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
        int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
        result[i] = (byte) (high * 16 + low);
      }
      return result;
    }
  }
}