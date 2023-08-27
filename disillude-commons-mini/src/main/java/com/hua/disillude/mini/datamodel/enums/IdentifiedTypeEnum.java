/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： IdentifiedTypeEnum.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 认证方式枚举
 *
 * @author Hedh
 * @since 1.0
 */
@Getter
@ApiModel(value = "认证方式")
public enum IdentifiedTypeEnum {
  /** 用户名 */
  @ApiModelProperty(value = "用户名", notes = "用户名+密码验证")
  USERNAME,
  /** 手机号 */
  @ApiModelProperty(value = "手机号", notes = "手机号+密码验证")
  PHONE,
  /** 邮箱 */
  @ApiModelProperty(value = "电子邮箱", notes = "电子邮箱+密码验证")
  EMAIL,
  /** 邮箱验证码 */
  @ApiModelProperty(value = "电子邮箱验证码", notes = "电子邮箱+验证码")
  EMAIL_VERIFY_CODE,
  /** 手机验证码 */
  @ApiModelProperty(value = "手机验证码", notes = "手机+验证码")
  PHONE_VERIFY_CODE,
}
