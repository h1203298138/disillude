/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： BizServiceException.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.exception;

import io.swagger.annotations.ApiModelProperty;

/**
 * 业务异常
 *
 * @author Hedh
 * @since 1.0
 */
public class BizServiceException extends Exception implements HasErrorCode {
  @ApiModelProperty(value = "错误码")
  private int code = 50002;

  public BizServiceException() {
    super();
  }

  public BizServiceException(String message) {
    super(message);
  }

  public BizServiceException(int code, String message) {
    super(message);
    this.code = code;
  }

  public BizServiceException(Throwable cause) {
    super(cause);
  }

  public BizServiceException code(int code) {
    this.setCode(code);
    return this;
  }

  @Override
  public int getCode() {
    return this.code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
