/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： NumberConst.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.constant;

/**
 * 数值精度常量定义
 *
 * @author Hedh
 * @since 1.0
 */
public abstract class NumberConst {
  /** 数字有效位数 */
  public static final int PRECISION = 24;
  /** 金额小数位数 */
  public static final int SCALE_MONEY = 2;
  /** 价格小数位数 */
  public static final int SCALE_PRICE = 4;
  /** 税率小数位数 */
  public static final int SCALE_RATE = 4;
}
