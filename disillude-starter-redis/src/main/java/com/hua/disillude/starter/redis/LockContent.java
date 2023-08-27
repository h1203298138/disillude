/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： LockContent.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.starter.redis;

import lombok.Data;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
public abstract class LockContent implements AutoCloseable {

  private String lockKey;
  private String lockValue;

  @Override
  public void close() {

  }
}
