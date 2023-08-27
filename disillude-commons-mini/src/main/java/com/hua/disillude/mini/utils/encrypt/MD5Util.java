/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： MD5Util.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.encrypt;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 加密工具类 - MD5加密算法
 *
 * @author Hedh
 * @since 1.0
 */
public class MD5Util {
  // 获取随机盐
  public static String getSalt() {
    StringBuilder saltStr = new StringBuilder();
    List<Character> allChars = new ArrayList<>();
    Random random = new Random();
    for (int i = 33; i < 126; i++) {
      allChars.add((char) i);
    }
    Collections.shuffle(allChars);
    for (int i = 0; i < random.nextInt(6) + 6; i++) {
      saltStr.append(allChars.get(random.nextInt(allChars.size())));
    }
    return saltStr.reverse().toString();
  }

  public static String encode(String salt, String key) {
    return DigestUtils.md5Hex(salt + key);
  }

  public static String encode(String bytes) {
    return encode(bytes.getBytes(StandardCharsets.UTF_8));
  }

  public static String encode(byte[] bytes) {
    try {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.reset();
      digest.update(bytes);
      byte[] byteArr = digest.digest();
      StringBuilder builder = new StringBuilder();
      for (byte b : byteArr) {
        if (Integer.toHexString(0xFF & b).length() == 1) {
          builder.append("0").append(Integer.toHexString(0xFF & b));
        } else {
          builder.append(Integer.toHexString(0xFF & b));
        }
      }
      return builder.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    String salt = getSalt();
    String encode = encode(salt, "123456");
    System.out.println("salt = " + salt);
    System.out.println("encode = " + encode);
  }
}