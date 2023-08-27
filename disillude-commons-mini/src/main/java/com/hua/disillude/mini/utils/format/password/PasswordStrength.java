/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： PasswordStrength.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.format.password;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hedh
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@ApiModel(value = "密码强度枚举")
public enum PasswordStrength {
  /** 简单 */
  WEAK(PasswordStrategy.WEAK),
  /** 普通 */
  NORMAL(PasswordStrategy.NORMAL),
  /** 复杂 */
  COMPLEX(PasswordStrategy.COMPLEX),
  ;
  @ApiModelProperty(value = "正则表达式")
  private final String regular;

  public interface PasswordStrategy {
    /** 简单：长度8-16位，至少包含2种字符格式 */
    String WEAK = "^(?!^(\\d+|[a-zA-Z]+|[~!@#$%',+\\-;:._/<>(){}=\\[\\]|^&*?\"]+)$)^[\\w~!@#$%',+\\-;:._/<>(){}=\\[\\]|^&*?\"]{8,16}$";
    /** 普通：长度8-16位，至少包含3种字符格式 */
    String NORMAL = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\\\W_!@#$%^&*`~()\\-;:'|,.?/+={}<>\"\\[\\]]+$)(?![a-z0-9]+$)(?![a-z\\\\W_!@#$%^&*`~()\\-;:'|,.?/+={}<>\"\\[\\]]+$)(?![0-9\\\\W_!@#$%^&*`~()\\-;:'|,.?/+={}<>\"\\[\\]]+$)[a-zA-Z0-9\\\\W_!@#$%^&*`~()\\-;:'|,.?/+={}<>\"\\[\\]]{8,16}$";
    /** 复杂：长度8-16位，必须包含所有字符格式 */
    String COMPLEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!~#$%^&@*+;,:'\"\\\\|/?_=.(){}<>\\-\\[\\]])[\\da-zA-Z!~#$%^&@*+;,:'\"\\\\|/?_=.(){}<>\\-\\[\\]]{8,16}$";
  }
}
