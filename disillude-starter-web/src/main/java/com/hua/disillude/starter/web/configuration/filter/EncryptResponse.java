/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： EncryptResponse.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration.filter;

import com.hua.disillude.biz.access.AccessUser;
import com.hua.disillude.mini.utils.encrypt.AESUtil;
import com.hua.disillude.mini.utils.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hedh
 * @since 1.0
 */
@Slf4j
@ControllerAdvice
public class EncryptResponse implements ResponseBodyAdvice<Object> {

  private static final List<String> SUPPORT_CRYPTS = Arrays.asList("1", "3");

  @Autowired
  private AccessUser accessUser;

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return StringUtils.isNotBlank(this.accessUser.getUserId())
        && StringUtils.isNotBlank(this.accessUser.getTenant());
  }

  @Override
  public Object beforeBodyWrite(
      Object body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {
    String crypt = request.getHeaders().getFirst("crypt");
    String timestamp = request.getHeaders().getFirst("timestamp");
    if (SUPPORT_CRYPTS.contains(crypt) && timestamp != null) {
      String json = JsonUtil.objectToJson(body);
      String chipText = AESUtil.encryptWithMd5Params(json, this.accessUser.getUserId(), timestamp);
      Map<String, Object> map = new HashMap<>();
      map.put("chipText", chipText);
      response.getHeaders().set("crypt", "1");
      response.getHeaders().set("timestamp", timestamp);
      response.getHeaders().set("userid", this.accessUser.getUserId());
      return map;
    }
    return body;
  }

  public static void main(String[] args) {
    String chipText = "RnwIxkWbr/L/A9GxNPujntT71NuTnjHphAltKmTAOCzFQa1tOyOL9LVR5kXGuu9ompOrdqc3bYhZb4obL4kH8uMDczc3Zl8mPQd88p6yIRwLXkUIwJSFPX6cSzTPJcBhYNFwJ11uDuBOD//0v8eMaz3P/vOooUtBc5t6ca2GUHQXYhuVfG5c8BRBnuClr8SAHB9nqdgJr1bO0BoJp8c4QRrJIpynZnpSJu7g7OvXwUdtxG8O6QAOPF4fq3jmK7G/vBd0nKaZIe0t6YjQToEOF/XRc3Bz05mxc3l1RUjKWo7Mxjm3zU2ceH9PgTuNb1dpyiZoMMZ5Lm6oLA5bdC8Zq2nRO6rfxDMTRP58PL9qIz7SWPH7l6JQ6b07JXbDVyvXYsvB7YD2vcCyCirochg7pWyXqXRO1HF1oJz+ZP5YC0t0BN2rwoduwBoH++rWMnVexUitL1RR9ihoJTmt292f6UUAtYOvGja6Erw5O20b3aC9luBpeKA5pqLHH3qCMFnjlhREWM+UBtaKbwLcOrfm4MWj7h4ZdOCqTkAlQ8BiktVqIBFDJ5vvkaQ1XcSJG82tc1VSin2gK0gwvPis8jG4bwNn1G0YBd7kFMSRLcsM/6Ub23G2J/4zobbXVK+dKFsIlTzII0NmxNvit8PC9EAvtA==";
    String userid = "184239011662839808";
    String timestamp = "1677827185351";
    System.out.println(AESUtil.decryptWithMd5Params(chipText, userid, timestamp));

  }
}
