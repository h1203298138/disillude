/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： QueryDefinitionBuilder.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.biz.jdbc.utils;

import com.hua.aroma.commons.biz.query.AndCondition;
import com.hua.aroma.commons.biz.query.Condition;
import com.hua.aroma.commons.biz.query.Cop;
import com.hua.aroma.commons.biz.query.OrCondition;
import com.hua.aroma.commons.biz.query.QueryCondition;
import com.hua.aroma.commons.biz.query.QueryDefinition;
import com.hua.aroma.commons.biz.query.QueryField;
import com.hua.aroma.commons.biz.query.QueryOrderDirection;
import com.hua.aroma.commons.mini.lang.Assert;
import com.hua.disillude.mini.http.request.FilterParam;
import com.hua.disillude.mini.http.request.QueryRequest;
import com.hua.disillude.mini.http.request.SortParam;
import com.hua.disillude.mini.utils.StringUtil;
import com.hua.disillude.mini.utils.format.DateTimeFormat;
import com.hua.disillude.mini.utils.range.DateRange;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Hedh
 * @since 1.0
 */
@Slf4j
public class QueryDefinitionBuilder {
  public static final String CT_TIME_BTW = "TimeBtw";

  private Class queryClass;
  private Map<String, ParamConverter> converters = new HashMap<>();

  public QueryDefinitionBuilder(Class queryClass) {
    this.queryClass = queryClass;
  }

  /**
   * 将界面条件转换成QueryDefinition
   *
   * @param query
   *     界面传入的查询条件
   * @return qd
   */
  public QueryDefinition build(QueryRequest query) {
    return this.build(query, null);
  }

  public QueryDefinition build(QueryRequest query, FilterParamProcessor filterParamProcessor) {
    QueryDefinition qd = new QueryDefinition();
    if (query == null) {
      return qd;
    }
    Assert.notNull(this.queryClass, "queryClass");

    qd.setPage(query.getPage());
    qd.setPageSize(query.getPageSize());

    // 过滤条件
    try {
      if (query.getFilters() != null) {
        qd.and(this.buildConditions(query.getFilters(), filterParamProcessor));
      }
    } catch (Exception e) {
      log.error("转换查询条件失败", e);
      throw new IllegalArgumentException("转换查询条件失败: " + e.getMessage());
    }
    // 排序条件
    try {
      this.decodeSort(qd, query);
    } catch (Exception e) {
      log.error("转换排序条件失败", e);
      throw new IllegalArgumentException("转换排序条件失败: " + e.getMessage());
    }
    return qd;
  }

  public Condition[] buildConditions(Object filterList, FilterParamProcessor filterParamProcessor)
      throws IllegalAccessException, InstantiationException {
    if (filterList instanceof List == false) {
      throw new IllegalAccessException("参数不正确");
    }
    List<Condition> conditions = new ArrayList<>();
    List filterParams = (List) filterList;
    if (CollectionUtils.isEmpty(filterParams)) {
      return conditions.toArray(new Condition[]{});
    }

    for (Object param : filterParams) {
      String property = null;
      Object value = null;
      if (param instanceof FilterParam) {
        property = ((FilterParam) param).getProperty();
        value = ((FilterParam) param).getValue();
      } else if (param instanceof Map) {
        property = (String) ((Map) param).get("property");
        value = ((Map) param).get("value");
      }

      Condition condition = null;
      if (StringUtils.isBlank(property)) {
        continue;
      } else if ("or".equals(property)) {
        OrCondition or = new OrCondition(this.buildConditions(value, filterParamProcessor));
        if (or.getConditions().isEmpty() == false) {
          condition = or;
        }
      } else if ("and".equals(property)) {
        AndCondition and = new AndCondition(this.buildConditions(value, filterParamProcessor));
        if (and.getConditions().isEmpty() == false) {
          condition = and;
        }
      } else {
        condition = this.buildCondition(property, value, filterParamProcessor);
      }
      if (condition != null) {
        conditions.add(condition);
      }
    }
    return conditions.toArray(new Condition[]{});
  }

  public Condition buildCondition(String property, Object value, FilterParamProcessor filterParamProcessor)
      throws InstantiationException, IllegalAccessException {
    String[] s = property.split(":");
    if (s.length == 2) {
      String operator = this.getOperator(s[1]);
      if ((value == null || StringUtils.isBlank(value.toString()))
          && !(Cop.IS_NULL.equals(operator) || Cop.not(Cop.IS_NULL).equals(operator))) {
        // 参数为空的不处理
        return null;
      }
      if (value instanceof Collection && CollectionUtils.isEmpty((Collection) value)) {
        return null;
      }
      if (filterParamProcessor != null) {
        Condition condition = filterParamProcessor.process(new FilterParam(property, value));
        if (condition != null) {
          return condition;
        }
      }

      // 如果是操作符是 in 或者 !in，且操作数只有一个的。那自动转换为等于操作
      // if(Cop.IN.equals(operator) || Cop.not(Cop.IN).equals(operator))){
      //   if ( value instanceof Collection && ((Collection<?>) value).size() == 1) {
      //
      //   }
      // }

      return this.buildCondition(s, property, operator, value);
    } else {
      throw new RuntimeException("不支持的查询条件");
    }
  }

  public Condition buildCondition(String[] s, String property, String operator, Object value)
      throws InstantiationException, IllegalAccessException {
    // QueryField
    Field field = this.getField(this.queryClass, s[0]);
    if (field == null) {
      // QueryOperation
      field = this.getField(this.queryClass, s[0], s[1]);
      if (field == null) {
        throw new RuntimeException("不支持的查询条件" + Arrays.toString(s));
      }
      String fieldKey = (String) field.get(null);
      Object[] params = this.paramsConverter(property, operator, value, field);
      return QueryCondition.createByOperation(fieldKey, params);
    } else {
      String fieldKey = (String) field.get(null);
      Object[] params = this.paramsConverter(property, operator, value, field);
      if (operator.equals("~")) {
        if (params.length == 1 && params[0] instanceof Date) {
          operator = "[,]";
          params = new Object[]{params[0], DateRange.endOfTheDate((Date) params[0])};
        } else {
          throw new RuntimeException("无法解析 property=" + property + ", operator=" + operator + ", value=" + value);
        }

      }
      return QueryCondition.createByField(fieldKey, operator, params);
    }
  }

  public Object[] paramsConverter(String property, String operator, Object value, Field field) {
    ParamConverter converter = this.converters.get(property);
    if (converter != null) {
      return converter.converter(property, value);
    }
    // 枚举处理

    QueryField queryField = field.getAnnotation(QueryField.class);

    if (queryField != null) {
      Class fieldType = queryField.fieldType();
      if (fieldType.isEnum()) {
        if (value instanceof String) {
          value = StringUtil.toEnum((String) value, fieldType);
        } else if (value instanceof Collection) {
          value = ((Collection) value).stream()
              .map(k -> StringUtil.toEnum((String) k, fieldType))
              .collect(Collectors.toList());
        }
      } else if (fieldType == Date.class) {
        value = this.parseAsDate(value);
      } else if (fieldType == Boolean.class) {
        value = this.parseAsBoolean(value);
      }
    }
    if (value instanceof Collection) {
      value = ((Collection<?>) value).toArray();
    }
    // if (value instanceof Object[]) {
    //   return (Object[]) value;
    // }
    // if (Cop.IN.equals(operator) || "notIn".equals(operator) || "!in".equals(operator)) {
    //   try {
    //     return new StringArrayConverter().converter(property, value);
    //   } catch (Exception e) {
    //     return new Object[]{value};
    //   }
    // }
    return value instanceof Object[] ? (Object[]) value : new Object[]{value};
  }

  private void decodeSort(QueryDefinition qd, QueryRequest query) throws IllegalAccessException, InstantiationException {
    if (query.getSorters() == null) {
      return;
    }

    for (SortParam sp : query.getSorters()) {
      String property = sp.getProperty();
      if (StringUtils.isBlank(property)) {
        continue;
      }
      // QueryField
      Field field = this.getField(this.queryClass, property, null);
      String fieldKey = null;
      if (field != null) {
        fieldKey = (String) field.get(null);
      } else {
        if (property.toLowerCase().contains("sum(")
            || property.toLowerCase().contains("avg(")
            || property.toLowerCase().contains("count(")) {
          fieldKey = property;
        }
        if (fieldKey == null) {
          continue;
        }
      }
      qd.addOrder(fieldKey, QueryOrderDirection.valueOf(sp.getDirection().toLowerCase()));
    }
  }

  public String getOperator(String operator) {
    if (operator.contains(Cop.NOT)) {
      return Cop.not(operator.replace(Cop.NOT, ""));
    }
    if ("notIn".equals(operator)) {
      return Cop.not(Cop.IN);
    }
    return operator;
  }

  public Field getField(Class queries, String field) {
    Assert.notNull(field, "field");
    for (Field f : queries.getFields()) {
      if ("PREFIX".equals(f.getName())) {
        continue;
      }
      // QueryField
      if (f.getName().replaceAll("_", "").equalsIgnoreCase(field.replaceAll("[. ]", ""))) {
        return f;
      }
    }
    return null;
  }

  public Field getField(Class queries, String field, String operator) {
    Assert.notNull(field, "field");
    for (Field f : queries.getFields()) {
      if ("PREFIX".equals(f.getName())) {
        continue;
      }
      // QueryField
      if (f.getName().replaceAll("_", "").equalsIgnoreCase(field.replaceAll("[. ]", ""))) {
        return f;
      }
      // QueryOperation
      if (StringUtils.isNotBlank(operator)) {
        String newField = field;
        if (Cop.LIKES.equals(operator)) {
          newField = field + " " + "like";
        } else if (Cop.EQUALS.equals(operator)) {
          newField = field + " " + "equals";
        } else if (Cop.IN.equals(operator)) {
          newField = field + " " + "in";
        } else if ("notIn".equals(operator)) {
          newField = field + " " + "notIn";
        } else if ("!=".equals(operator)) {
          newField = field + " " + "not equals";
        } else if ("[,]".equals(operator)) {
          newField = field + " " + "btw";
        } else if ("exist".equals(operator)) {
          newField = field + " " + "exist";
        } else if ("startWith".equals(operator)) {
          newField = field + " " + "startWith";
        } else if (Cop.STARTS_WITH.equals(operator)) {
          newField = field + " " + "startWith";
        }
        if (f.getName().replaceAll("_", "").equalsIgnoreCase(newField.replaceAll("[. ]", ""))) {
          return f;
        }
      }
    }
    return null;
  }

  private String getPrefix(Class queries) throws IllegalAccessException, InstantiationException {
    String prefix = "";
    for (Field f : queries.getDeclaredFields()) {
      if ("PREFIX".equals(f.getName())) {
        f.setAccessible(true);
        prefix = (String) f.get(queries.newInstance());
        break;
      }
    }
    return prefix;
  }

  public QueryDefinitionBuilder mapParam(String property, ParamConverter converter) {
    this.converters.put(property, converter);
    return this;
  }

  public interface ParamConverter<T> {
    T[] converter(String property, Object value);
  }

  public static class StringArrayConverter implements ParamConverter<String> {
    @Override
    public String[] converter(String property, Object value) {
      if (value == null) {
        return null;
      }
      List<String> values = ((List<String>) value);
      return values.toArray(new String[]{});
    }
  }

  public static class IntBtwConverter implements ParamConverter<Integer> {
    @Override
    public Integer[] converter(String property, Object value) {
      if (value == null) {
        return null;
      }
      List<Integer> values = ((List<Integer>) value);
      Integer start = null;
      if (values.get(0) != null && StringUtils.isNotBlank(values.get(0) + "")) {
        start = new Integer(values.get(0) + "");
      }
      Integer end = null;
      if (values.get(1) != null && StringUtils.isNotBlank(values.get(1) + "")) {
        end = new Integer(values.get(1) + "");
      }
      return new Integer[]{
          start, end};
    }
  }

  public static class BigDecimalBtwConverter implements ParamConverter<BigDecimal> {
    @Override
    public BigDecimal[] converter(String property, Object value) {
      if (value == null) {
        return null;
      }
      List values = ((List) value);
      BigDecimal start = null;
      if (values.get(0) != null && StringUtils.isNotBlank(values.get(0) + "")) {
        start = new BigDecimal(values.get(0) + "");
      }
      BigDecimal end = null;
      if (values.get(1) != null && StringUtils.isNotBlank(values.get(1) + "")) {
        end = new BigDecimal(values.get(1) + "");
      }
      return new BigDecimal[]{
          start, end};
    }
  }

  private Object parseAsDate(Object value) {
    DateFormat sdf = new DateTimeFormat("yyyy-MM-dd");
    if (value instanceof String) {
      try {
        return sdf.parse((String) value);
      } catch (ParseException e) {
        throw new RuntimeException("日期转换失败");
      }
    } else if (value instanceof Integer) {
      return new Date((Integer) value);
    } else if (value instanceof Long) {
      return new Date((Long) value);
    } else if (value instanceof Collection) {
      return ((Collection) value).stream().map(this::parseAsDate).collect(Collectors.toList());
    }
    return value;
  }

  private Object parseAsBoolean(Object value) {
    if (value instanceof String) {
      value = StringUtil.toBoolean((String) value);
    } else if (value instanceof Collection) {
      return ((Collection) value).stream().map(this::parseAsBoolean).collect(Collectors.toList());
    }
    return value;
  }

  public static class TimeBtwConverter implements ParamConverter<Date> {
    @Override
    public Date[] converter(String property, Object value) {
      if (value == null) {
        return null;
      }

      List<String> values = ((List<String>) value);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date start = null;
      if (values.get(0) != null && StringUtils.isNotBlank(values.get(0) + "")) {
        try {
          start = sdf.parse(values.get(0));
        } catch (ParseException e) {
          throw new RuntimeException("日期转换失败");
        }
      }

      Date end = null;
      if (values.get(1) != null && StringUtils.isNotBlank(values.get(1) + "")) {
        try {
          end = sdf.parse(values.get(1));
        } catch (ParseException e) {
          throw new RuntimeException("日期转换失败");
        }
      }
      return new Date[]{
          start, end};
    }
  }

  public interface FilterParamProcessor {
    Condition process(FilterParam filterParam) throws IllegalArgumentException, InstantiationException, IllegalAccessException;
  }
}
