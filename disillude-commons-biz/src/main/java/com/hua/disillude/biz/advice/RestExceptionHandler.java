/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： RestExceptionHandler.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.biz.advice;

import com.hua.disillude.mini.exception.BizServiceException;
import com.hua.disillude.mini.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hua.disillude.mini.http.ResponseStatus.FILE_UPLOAD_EXCEPTION;
import static com.hua.disillude.mini.http.ResponseStatus.PARAMETER_INVALID;
import static com.hua.disillude.mini.http.ResponseStatus.REQUEST_FAILURE;
import static org.springframework.http.HttpStatus.OK;

/**
 * 全局异常处理
 *
 * @author Hedh
 * @since 1.0
 */
@Slf4j
@SuppressWarnings("all")
public class RestExceptionHandler {
  /** 默认情况下值为null的表示字符 */
  public static final char DEFAULT_NULL = '~';
  /** 映射表与字符串转换时，默认情况下的转义字符。 */
  public static final char DEFAULT_MAP_ESCAPE = '\\';
  public static final String SEPARATOR_COMMA = ",";

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Response<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error(e.getMessage());
    Set<String> errorList = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toSet());
    return Response.fail(String.join(SEPARATOR_COMMA, errorList));
  }

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(OK)
  public Response restExceptionHandler(Exception ex) {
    return buildResponse(ex);
  }

  public static Response buildResponse(Throwable e) {
    log.error("请求响应异常", e);
    Response response = new Response();
    response.setSuccess(false);
    if (e instanceof BizServiceException) {
      response.setCode(((BizServiceException) e).getCode());
      response.setMessage(e.getMessage());
    } else if (e instanceof IllegalArgumentException) {
      response.setCode(PARAMETER_INVALID.getCode());
      response.setMessage(e.getMessage());
    } else if (e instanceof MethodArgumentNotValidException) {
      response.setCode(PARAMETER_INVALID.getCode());
      Map<String, String> errors = new HashMap<>();
      BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
      bindingResult.getFieldErrors().forEach(k -> errors.put(k.getField(), k.getDefaultMessage()));
      response.setMessage(RestExceptionHandler.mapToString(errors, ';', ':'));
      response.setData(errors);
    } else if (e instanceof MaxUploadSizeExceededException) {
      response.setCode(FILE_UPLOAD_EXCEPTION.getCode());
      response.setMessage(FILE_UPLOAD_EXCEPTION.getMessage());
    } else {
      response.setCode(REQUEST_FAILURE.getCode());
      response.setMessage(REQUEST_FAILURE.getMessage());
    }
    response.setDetailMessage(e.getMessage());
    return response;
  }

  public static String mapToString(Map map, char entrySeparator, char keyValueSeparator) {
    if (map == null) {
      return null;
    }
    StringBuffer sb = new StringBuffer();
    map.keySet().forEach(key -> {
      if (sb.length() > 0) {
        sb.append(entrySeparator);
      }
      if (key == null) {
        sb.append(DEFAULT_NULL);
      } else {
        encode(sb, key.toString(), DEFAULT_MAP_ESCAPE, entrySeparator, keyValueSeparator);
      }
      sb.append(keyValueSeparator);
      Object value = map.get(key);
      if (value == null) {
        sb.append(DEFAULT_NULL);
      } else {
        encode(sb, value.toString(), DEFAULT_MAP_ESCAPE, entrySeparator, keyValueSeparator);
      }
    });
    return sb.toString();
  }

  private static void encode(StringBuffer sb, String input, char escape, char... reservedChars) {
    assert sb != null;
    assert input != null;
    for (char c : input.toCharArray()) {
      if (c == escape) {
        sb.append(escape);
      } else {
        boolean reserved = false;
        for (char rc : reservedChars) {
          if (rc == c) {
            reserved = true;
            break;
          }
        }
        if (reserved) {
          sb.append(escape);
        }
      }
      sb.append(c);
    }
  }
}
