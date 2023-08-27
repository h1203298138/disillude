/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： SimpleThreadFactory.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.biz.thread;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂，用于重命名线程
 *
 * @author Hedh
 * @since 1.0
 */
public class SimpleThreadFactory implements ThreadFactory {
  private String threadNamePrefix;

  private SimpleThreadFactory() {

  }

  public SimpleThreadFactory(String threadNamePrefix) {
    this.threadNamePrefix = threadNamePrefix;
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r);
    t.setName(this.threadNamePrefix + t.getName());
    return t;
  }
}
