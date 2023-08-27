/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： JsonUtil.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hua.aroma.commons.mini.lang.StringUtil;
import com.hua.disillude.mini.utils.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * json工具类
 *
 * @author Hedh
 * @since 1.0
 */
public class JsonUtil {

  public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private static ThreadLocal<ObjectMapper> threadLocal = ThreadLocal.withInitial(() -> {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setTimeZone(TimeZone.getDefault());
    mapper.setDateFormat(new DateTimeFormat(JsonUtil.DATE_FORMAT));
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  });

  public static String objectToJson(Object value) throws RuntimeException {
    if (value == null) {
      return null;
    }

    try {
      return JsonUtil.threadLocal.get().writeValueAsString(value);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static <T> T jsonToObject(String json, Class<T> valueType) throws RuntimeException {
    if (StringUtil.isNullOrBlank(json)) {
      return null;
    }

    try {
      return JsonUtil.threadLocal.get().readValue(json, valueType);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /** 对含有泛型的返回体反序列化的支持 */
  public static <T> T mapToObject(Map source, Class<T> valueType) throws RuntimeException {
    if (source == null) {
      return null;
    }

    try {
      return JsonUtil.threadLocal.get().readValue(JsonUtil.objectToJson(source), valueType);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static <T> T convertValue(Map object, Class<T> valueType) throws RuntimeException {
    if (object == null) {
      return null;
    }

    try {
      return JsonUtil.threadLocal.get().convertValue(object, valueType);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /** 对含有泛型的返回体反序列化的支持 */
  public static <T> T jsonToObject(String json, TypeReference<T> valueType) throws RuntimeException {
    if (StringUtil.isNullOrBlank(json)) {
      return null;
    }

    try {
      return JsonUtil.threadLocal.get().readValue(json, valueType);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public static <T> List<T> jsonToArrayList(String json, Class<T> elementType)
      throws RuntimeException {
    if (StringUtil.isNullOrBlank(json)) {
      return new ArrayList<T>();
    }

    try {
      return JsonUtil.threadLocal.get().readValue(json, JsonUtil.threadLocal.get().getTypeFactory().constructParametricType(ArrayList.class, elementType));
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * 下划线转驼峰法
   *
   * @param json
   *     源字符串
   * @return 转换后的字符串
   */
  public static String underline2Camel(String json) {
    if (StringUtil.isNullOrBlank(json)) {
      return null;
    }

    Map<String, Object> o = JsonUtil.jsonToObject(json, Map.class);
    o = JsonUtil.underline2Camel(o);
    return JsonUtil.objectToJson(o);
  }

  private static Map<String, Object> underline2Camel(Map<String, Object> o) {
    Map<String, Object> target = new HashMap<>();
    for (String key : o.keySet()) {
      Object value = o.get(key);
      if (value instanceof Map) {
        value = JsonUtil.underline2Camel((Map<String, Object>) value);
      } else if (value instanceof List) {
        List list = (List) value;
        for (int i = 0; i < list.size(); i++) {
          Object v = list.get(i);
          if (v instanceof Map) {
            v = JsonUtil.underline2Camel((Map<String, Object>) v);
            list.set(i, v);
          }
        }
      }

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < key.length(); i++) {
        char c = key.charAt(i);
        if (c == '_') {
          i++;
          c = key.charAt(i);
          sb.append(String.valueOf(c).toUpperCase());
        } else {
          sb.append(c);
        }
      }
      target.put(sb.toString(), value);
    }
    return target;
  }

  /**
   * 驼峰法转下划线
   *
   * @param json
   *     源字符串
   * @return 转换后的字符串
   */
  public static String camel2Underline(String json) {
    if (StringUtil.isNullOrBlank(json)) {
      return null;
    }

    Map<String, Object> o = JsonUtil.jsonToObject(json, Map.class);
    o = JsonUtil.camel2Underline(o);
    return JsonUtil.objectToJson(o);
  }

  private static Map<String, Object> camel2Underline(Map<String, Object> o) {
    if (o == null) {
      return null;
    }

    Map<String, Object> target = new HashMap<>();
    for (String key : o.keySet()) {
      Object value = o.get(key);
      if (value instanceof Map) {
        value = JsonUtil.camel2Underline((Map<String, Object>) value);
      } else if (value instanceof List) {
        List list = (List) value;
        for (int i = 0; i < list.size(); i++) {
          Object v = list.get(i);
          if (v instanceof Map) {
            v = JsonUtil.underline2Camel((Map<String, Object>) v);
            list.set(i, v);
          }
        }
      }

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < key.length(); i++) {
        char c = key.charAt(i);
        if (c >= 'A' && c <= 'Z') {
          sb.append("_").append(String.valueOf(c).toLowerCase());
        } else {
          sb.append(c);
        }
      }
      target.put(sb.toString(), value);
    }
    return target;
  }
}
