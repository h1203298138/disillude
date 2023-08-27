/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： IdGenerator.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.generator;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author Hedh
 * @since 1.0
 */
public class IdGenerator {
  /**
   * 根据入参生成uuid并取其mostSignificantBits
   *
   * @param keys
   *     应当是不包含","的字符串数组
   */
  public static String newId(String... keys) {
    return String.valueOf(UUID.nameUUIDFromBytes(StringUtils.join(keys, ',').getBytes()).getMostSignificantBits() & Long.MAX_VALUE);
  }

  /** 获取UUID */
  public static String nextIid() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  public static void main(String[] args) {
    System.out.println(IdGenerator.newId("test", "96998135361363968", "852732512263616280"));
    System.out.println(nextIid());
  }
}