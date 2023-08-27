/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： Base64Util.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.encrypt;

/**
 * 加密工具类 - Base64加密算法
 *
 * @author Hedh
 * @since 1.0
 */
public class Base64Util {

  /**
   * Base64加密算法加密数据
   *
   * @param data
   *     待解密数据
   * @return 解密后的数据
   */
  public static String encrypt(String data) {
    return java.util.Base64.getEncoder().encodeToString(data.getBytes());
  }

  /**
   * Base64加密算法解密数据
   *
   * @param data
   *     待解密数据（加密后的密钥）
   * @return 解密后的数据
   */
  public static String decrypt(String data) {
    return new String(java.util.Base64.getDecoder().decode(data));
  }

  public static void main(String[] args) throws Exception {
    String encryptByBase64 = encrypt("12345678");
    String decryptByBase64 = decrypt(encryptByBase64);
    System.out.println("encryptByBase64 = " + encryptByBase64);
    System.out.println("decryptByBase64 = " + decryptByBase64);
  }
}