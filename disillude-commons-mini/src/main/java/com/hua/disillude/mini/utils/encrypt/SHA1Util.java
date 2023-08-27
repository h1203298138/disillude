/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： SHA1Util.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密工具类 - SHA1加密算法
 *
 * @author Hedh
 * @since 1.0
 */
public class SHA1Util {
  /**
   * @param content
   *     待加密的字符串
   * @return 加密字符串
   */
  public static String encode(String content) {
    char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    try {
      byte[] btInput = content.getBytes(StandardCharsets.UTF_8);
      // 获得MD5摘要算法的 MessageDigest 对象
      MessageDigest mdInst = MessageDigest.getInstance("sha-1");
      // 使用指定的字节更新摘要
      mdInst.update(btInput);
      // 获得密文
      byte[] md = mdInst.digest();
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
      System.err.println("Sha1 error: " + e);
      throw new RuntimeException(e);
    }
  }
}