/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： DESUtil.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 加密工具类 - DES对称加密算法
 *
 * @author Hedh
 * @since 1.0
 */
public class DESUtil {
  // 算法名称：对称加密算法
  protected static final String KEY_ALGORITHM = "DES";
  // 算法名称/加密模式/填充方式
  // DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
  protected static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";

  /**
   * 生成密钥key对象
   *
   * @param keyStr
   *     密钥字符串
   * @return 密钥对象
   * @throws Exception
   *     抛异常
   */
  protected static SecretKey keyGenerator(String keyStr) throws Exception {
    DESKeySpec desKey = new DESKeySpec(HexString2Bytes(keyStr));
    // 创建一个密匙工厂，然后用它把DESKeySpec转换成
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESUtil.KEY_ALGORITHM);
    return keyFactory.generateSecret(desKey);
  }

  protected static int parse(char c) {
    if (c >= 'a' && c <= 'f') {
      return (c - 'a' + 10) & 0x0f;
    } else if (c >= 'A' && c <= 'F') {
      return (c - 'A' + 10) & 0x0f;
    }
    return (c - '0') & 0x0f;
  }

  // 从十六进制字符串到字节数组转换
  protected static byte[] HexString2Bytes(String hexStr) {
    byte[] b = new byte[hexStr.length() / 2];
    int j = 0;
    for (int i = 0; i < b.length; i++) {
      char c0 = hexStr.charAt(j++);
      char c1 = hexStr.charAt(j++);
      b[i] = (byte) ((parse(c0) << 4) | parse(c1));
    }
    return b;
  }

  /**
   * DES加密算法加密数据
   *
   * @param data
   *     待加密数据（长度是8的倍数）
   * @param key
   *     16位的密钥
   * @return 加密后的数据
   */
  public static String encrypt(String data, String key) throws Exception {
    Key keyGenerator = DESUtil.keyGenerator(key);
    // 实例化Cipher对象，它用于完成实际的加密操作
    Cipher cipher = Cipher.getInstance(DESUtil.CIPHER_ALGORITHM);
    SecureRandom random = new SecureRandom();
    // 初始化Cipher对象，设置为加密模式
    cipher.init(Cipher.ENCRYPT_MODE, keyGenerator, random);
    byte[] results = cipher.doFinal(data.getBytes());
    // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
    // 结果可以与加解密在线测试网站（http://tripledes.online-domain-tools.com/）的十六进制结果进行核对
    return Base64.encodeBase64String(results);
  }

  /**
   * DES加密算法解密数据
   *
   * @param data
   *     待解密数据
   * @param key
   *     密钥
   * @return 解密后的数据
   */
  public static String decrypt(String data, String key) throws Exception {
    Key deskey = keyGenerator(key);
    Cipher cipher = Cipher.getInstance(DESUtil.CIPHER_ALGORITHM);
    // 初始化Cipher对象，设置为解密模式
    cipher.init(Cipher.DECRYPT_MODE, deskey);
    // 执行解密操作
    return new String(cipher.doFinal(Base64.decodeBase64(data)));
  }

  public static void main(String[] args) throws Exception {
    String encrypt = encrypt("12345678", "12345678910111213");
    String decrypt = decrypt(encrypt, "12345678910111213");
    System.out.println("encrypt = " + encrypt);
    System.out.println("decrypt = " + decrypt);
  }
}