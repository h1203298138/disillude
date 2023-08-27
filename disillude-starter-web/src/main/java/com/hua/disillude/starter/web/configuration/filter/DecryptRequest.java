/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： DecryptRequest.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration.filter;

import com.hua.disillude.biz.access.AccessUser;
import com.hua.disillude.mini.utils.encrypt.AESUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hedh
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
public class DecryptRequest extends RequestBodyAdviceAdapter {
  /**
   * 加密策略：
   * 0||null：不加密
   * 1：请求&响应都加密
   * 2：请求加密
   * 3：响应加密
   */
  private static final List<String> SUPPORT_CRYPTS = Arrays.asList("1", "2");
  @Autowired
  private AccessUser accessUser;

  @Override
  public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    return StringUtils.isNotBlank(this.accessUser.getUserId())
        && StringUtils.isNotBlank(this.accessUser.getTenant());
  }

  @Override
  public HttpInputMessage beforeBodyRead(
      final HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
    String crypt = inputMessage.getHeaders().getFirst("crypt");
    String timestamp = inputMessage.getHeaders().getFirst("timestamp");
    if (SUPPORT_CRYPTS.contains(crypt) && timestamp != null) {
      try {
        String bodyStr = new BufferedReader(new InputStreamReader(inputMessage.getBody()))
            .lines().collect(Collectors.joining(System.lineSeparator()));
        if (StringUtils.isNotBlank(bodyStr) && "{}".equals(bodyStr)) {
          return new DecryptHttpInputMessage(bodyStr.getBytes(StandardCharsets.UTF_8), inputMessage);
        }
        String plainText = AESUtil.decryptWithMd5Params(bodyStr, this.accessUser.getUserId(), timestamp);
        return new DecryptHttpInputMessage(plainText.getBytes(StandardCharsets.UTF_8), inputMessage);
      } catch (Exception e) {
        throw new RuntimeException("decrypt body failed!");
      }
    }
    return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
  }

  @Data
  public static class DecryptHttpInputMessage implements HttpInputMessage {
    private final ByteArrayInputStream bais;
    private final HttpInputMessage inputMessage;

    public DecryptHttpInputMessage(byte[] decrypt, HttpInputMessage inputMessage) {
      this.bais = new ByteArrayInputStream(decrypt);
      this.inputMessage = inputMessage;
    }

    @Override
    public InputStream getBody() {
      return this.bais;
    }

    @Override
    public HttpHeaders getHeaders() {
      return this.inputMessage.getHeaders();
    }
  }
}
