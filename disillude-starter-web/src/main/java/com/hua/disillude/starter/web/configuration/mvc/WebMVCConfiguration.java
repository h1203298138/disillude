/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： WebMVCConfiguration.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration.mvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hua.disillude.mini.utils.format.DateTimeFormat;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hedh
 * @since 1.0
 */
@Configuration
public class WebMVCConfiguration {

  /**
   * 跨域配置
   */
  // @Bean
  @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedHeaders("*")
            .allowedMethods("GET", "POST")
            .allowedOrigins("*").allowCredentials(false);
      }
    };
  }

  @Bean
  public ObjectMapper objectMapper() {
    //设置日期格式
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleDateFormat smt = new DateTimeFormat("yyyy-MM-dd HH:mm:ss");
    objectMapper.setDateFormat(smt);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//反序列化时，解析不存在参数不报错
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper;
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    jsonConverter.setObjectMapper(this.objectMapper());
    jsonConverter.setPrettyPrint(true);
    return jsonConverter;
  }

  @Bean
  public StringHttpMessageConverter stringHttpMessageConverter() {
    StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
    converter.setSupportedMediaTypes(
        Arrays.asList(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
    return converter;
  }

  @Bean
  public RestTemplate restTemplate()
      throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    // 设置可以信任访问https资源
    TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
        .loadTrustMaterial(null, acceptingTrustStrategy).build();
    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

    requestFactory.setHttpClient(httpClient);
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    for (int i = messageConverters.size() - 1; i >= 0; i--) {
      HttpMessageConverter<?> converter = messageConverters.get(i);
      if (converter instanceof StringHttpMessageConverter) {
        messageConverters.remove(i);
      } else if (converter instanceof MappingJackson2HttpMessageConverter) {
        messageConverters.remove(i);
      }
    }
    messageConverters.add(this.stringHttpMessageConverter());
    messageConverters.add(this.mappingJackson2HttpMessageConverter());
    return restTemplate;
  }
}
