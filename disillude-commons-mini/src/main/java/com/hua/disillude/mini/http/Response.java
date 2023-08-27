/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： Response.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.http;

import com.hua.disillude.mini.exception.BizServiceException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应信息
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@NoArgsConstructor
@ApiModel(value = "响应信息")
public class Response<T> implements Serializable {
  private static final long serialVersionUID = -5220780165470826402L;

  public static final int CODE_OK = 200;
  public static final int CODE_SERVER_ERROR = 50001;

  @ApiModelProperty(value = "请求是否成功")
  private boolean success = true;
  @ApiModelProperty(value = "返回状态码。请参考msg说明")
  private int code;
  @ApiModelProperty(value = "状态码说明")
  private String message;
  @ApiModelProperty(value = "错误信息")
  private String detailMessage;
  @ApiModelProperty(value = "响应数据")
  private T data;

  public static Response success() {
    return success(null);
  }

  public static <T> Response success(T data) {
    return success(data, 0, null);
  }

  public static <T> Response success(T data, int code) {
    return success(data, code, null);
  }

  public static <T> Response success(T data, int code, String message) {
    Response response = new Response();
    response.setSuccess(true);
    response.setData(data);
    response.setCode(code);
    response.setMessage(message);
    return response;
  }

  public static Response fail(String message) {
    return fail(CODE_SERVER_ERROR, message);
  }

  public static Response fail(int code, String message) {
    Response response = new Response();
    response.setSuccess(false);
    response.setCode(code);
    response.setMessage(message);
    return response;
  }

  public Response setCode(int code) {
    this.code = code;
    return this;
  }

  public Response setMessage(String message) {
    this.message = message;
    return this;
  }

  public Response setData(T data) {
    this.data = data;
    return this;
  }

  public T safeGetData() throws BizServiceException {
    if (this.success == false) {
      throw new BizServiceException(this.message);
    }
    return this.getData();
  }
}
