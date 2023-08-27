/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： VerificationConfig.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 万能验证码配置
 *
 * @author Hedh
 * @since 1.0
 */
@Data
public class VerificationConfig {
  /** 万能验证码配置项前缀 */
  public static final String PREFIX = "auth-verification";
  @ApiModelProperty(value = "验证码过期时间，单位：分钟")
  private int validCodeExpireTimes = 10;
  @ApiModelProperty(value = "万能验证码")
  private String universalCode;
}
