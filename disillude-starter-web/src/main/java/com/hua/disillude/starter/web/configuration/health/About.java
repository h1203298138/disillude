/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： About.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration.health;

import com.hua.aroma.commons.mini.jar.ApplicationAbout;
import com.hua.disillude.biz.spring.ApplicationContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Hedh
 * @since 1.0
 */
@RestController
@Api(tags = "系统信息")
public class About {

  private final ConcurrentMap<String, Class<?>> abouts = new ConcurrentHashMap<>();

  @ApiOperation(value = "about")
  @GetMapping(value = "about", produces = "application/json;charset=utf-8")
  public ApplicationAbout about() {
    Map<String, Object> annotationBeans = ApplicationContextUtils.getApplicationContext()
        .getBeansWithAnnotation(SpringBootApplication.class);
    Class<?> mainClass = annotationBeans.isEmpty() ? null : annotationBeans.values().toArray()[0].getClass();
    if (mainClass != null) {
      String mainClassName = mainClass.getName();
      String mainClassSimpleName = mainClass.getSimpleName();
      int index = mainClassSimpleName.indexOf("$$");
      if (index != -1) {
        mainClassName = mainClass.getPackageName() + "." + mainClassSimpleName.substring(0, index);
      }
      try {
        if (!this.abouts.containsKey(mainClassName)) {
          this.abouts.putIfAbsent(mainClassName, Class.forName(mainClassName));
        }
        return ApplicationAbout.get(this.abouts.get(mainClassName));
      } catch (Exception e) {
        return ApplicationAbout.get(this.getClass());
      }
    }
    return ApplicationAbout.get(this.getClass());
  }
}
