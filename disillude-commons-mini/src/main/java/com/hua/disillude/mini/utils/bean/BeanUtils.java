/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： BeanUtils.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.bean;

import com.hua.disillude.mini.dto.VersionDTO;
import com.hua.disillude.mini.entity.Entity;
import com.hua.disillude.mini.utils.generator.NameGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Hedh
 * @since 1.0
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
  /**
   * Bean方法名中属性名开始的下标
   */
  private static final int BEAN_METHOD_PROP_INDEX = 3;

  /**
   * 匹配getter方法的正则表达式
   */
  private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

  /**
   * 匹配setter方法的正则表达式
   */
  private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

  /**
   * Bean属性复制工具方法。
   *
   * @param dest
   *     目标对象
   * @param src
   *     源对象
   */
  public static void copyBeanProp(Object dest, Object src) {
    try {
      copyProperties(src, dest);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取对象的setter方法。
   *
   * @param obj
   *     对象
   * @return 对象的setter方法列表
   */
  public static List<Method> getSetterMethods(Object obj) {
    // setter方法列表
    List<Method> setterMethods = new ArrayList<Method>();

    // 获取所有方法
    Method[] methods = obj.getClass().getMethods();

    // 查找setter方法
    for (Method method : methods) {
      Matcher m = SET_PATTERN.matcher(method.getName());
      if (m.matches() && (method.getParameterTypes().length == 1)) {
        setterMethods.add(method);
      }
    }
    // 返回setter方法列表
    return setterMethods;
  }

  /**
   * 获取对象的getter方法。
   *
   * @param obj
   *     对象
   * @return 对象的getter方法列表
   */
  public static List<Method> getGetterMethods(Object obj) {
    // getter方法列表
    List<Method> getterMethods = new ArrayList<Method>();
    // 获取所有方法
    Method[] methods = obj.getClass().getMethods();
    // 查找getter方法
    for (Method method : methods) {
      Matcher m = GET_PATTERN.matcher(method.getName());
      if (m.matches() && (method.getParameterTypes().length == 0)) {
        getterMethods.add(method);
      }
    }
    // 返回getter方法列表
    return getterMethods;
  }

  /**
   * 获取对象的getter方法。
   *
   * @param obj
   *     对象
   * @return 对象的getter方法列表
   */
  public static List<Method> getPropGetterMethods(Object obj) {
    return getGetterMethods(obj).stream().filter(method -> !"getClass".equals(method.getName())).collect(Collectors.toList());
  }

  /**
   * 检查Bean方法名中的属性名是否相等。<br>
   * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
   *
   * @param m1
   *     方法名1
   * @param m2
   *     方法名2
   * @return 属性名一样返回true，否则返回false
   */
  public static boolean isMethodPropEquals(String m1, String m2) {
    return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
  }


  public static <S, T> T convertTo(S source, Supplier<T> targetSupplier) {
    return convertTo(source, targetSupplier, null);
  }

  /**
   * 转换对象
   *
   * @param source
   *     源对象
   * @param targetSupplier
   *     目标对象供应方
   * @param callBack
   *     回调方法
   * @param <S>
   *     源对象类型
   * @param <T>
   *     目标对象类型
   * @return 目标对象
   */
  public static <S, T> T convertTo(S source, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
    if (null == source || null == targetSupplier) {
      return null;
    }
    T target = targetSupplier.get();
    copyProperties(source, target);
    if (callBack != null) {
      callBack.callBack(source, target);
    }
    return target;
  }

  /**
   * 转换List对象
   *
   * @param sources
   *     源对象List
   * @param targetSupplier
   *     目标对象供应方
   * @param callBack
   *     回调方法
   * @param <S>源对象类型
   * @param <T>
   *     目标对象类型
   * @return 目标对象List
   */
  public static <S, T> List<T> convertListTo(List<S> sources, Supplier<T> targetSupplier, ConvertCallBack<S, T> callBack) {
    if (null == sources || null == targetSupplier) {
      return null;
    }
    List<T> list = new ArrayList<>(sources.size());
    for (S source : sources) {
      T target = targetSupplier.get();
      copyProperties(source, target);
      if (null != callBack) {
        callBack.callBack(source, target);
      }
      list.add(target);
    }
    return list;
  }

  public static <S, T> List<T> convertListTo(List<S> sources, Supplier<T> targetSupplier) {
    return convertListTo(sources, targetSupplier, null);
  }

  @FunctionalInterface
  public interface ConvertCallBack<S, T> {
    /**
     * 回调方法
     *
     * @param source
     *     源对象
     * @param target
     *     目标对象
     */
    void callBack(S source, T target);
  }

  public static void main(String[] args) {
    VersionDTO versionDTO = new VersionDTO();
    List<Method> getterMethods = BeanUtils.getPropGetterMethods(versionDTO);
    getterMethods.forEach(method -> System.out.println(method.getName()));

    List<TestSourceList> sources = new ArrayList<>();
    sources.add(new TestSourceList(NameGenerator.nextName()));
    sources.add(new TestSourceList(NameGenerator.nextName()));
    sources.add(new TestSourceList(NameGenerator.nextName()));
    List<TestSourceList> targetSupplierList = BeanUtils.convertListTo(sources, TestSourceList::new, (s, t) -> {
      t.setName("test: " + s.getName());
    });
    targetSupplierList.forEach(o -> System.out.println(o.getId() + ": " + o.getName()));
  }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
class TestSourceList extends Entity {
  private static final long serialVersionUID = -3491994116633918374L;
  private String name;
}

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
class TestTargetList extends Entity {
  private static final long serialVersionUID = -3491994116633918374L;
  private String name;
}