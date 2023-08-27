/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： NameGenerator.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.generator;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author Hedh
 * @since 1.0
 */
public class NameGenerator {
  /** 获取长度为1~10的名称 */
  public static String nextName() {
    return nextName(1, 10);
  }

  /** 获取指定长度的名称 */
  public static String nextName(final int length) {
    return nextName(length, length);
  }

  /** 获取指定长度范围的名称 */
  public static String nextName(final int minLen, final int maxLen) {
    Random randomLen = new Random();
    int len = randomLen.nextInt(maxLen - minLen + 1) + minLen;
    StringBuilder ret = new StringBuilder();
    for (int i = 0; i < len; i++) {
      int highPos, lowPos;
      Random random = new Random();
      highPos = (176 + Math.abs(random.nextInt(39)));
      lowPos = (161 + Math.abs(random.nextInt(93)));
      byte[] b = new byte[2];
      b[0] = (Integer.valueOf(highPos)).byteValue();
      b[1] = (Integer.valueOf(lowPos)).byteValue();
      try {
        ret.append(new String(b, "GBK"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return ret.toString();
  }

  public static void main(String[] args) {
    System.out.println(nextName(1, 2));
    System.out.println(nextName(10));
    System.out.println(nextName());
  }
}