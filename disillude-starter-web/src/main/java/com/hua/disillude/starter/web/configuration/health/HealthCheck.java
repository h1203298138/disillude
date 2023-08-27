/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： HealthCheck.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.web.configuration.health;

import com.hua.aroma.commons.mini.jar.ApplicationAbout;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author Hedh
 * @since 1.0
 */
public class HealthCheck {

  @Component
  public static class Version implements HealthIndicator {

    @Override
    public Health health() {
      try {
        Health.Builder builder = Health.up();
        ApplicationAbout applicationAbout = ApplicationAbout.get(this.getClass());
        if (applicationAbout.getVersion() != null) {
          builder.withDetail("version",
              applicationAbout.getVersion() + "-" + applicationAbout.getBuild());
        }
        return builder.build();
      } catch (Exception e) {
        e.printStackTrace();
        return Health.down(e).build();
      }
    }
  }
}
