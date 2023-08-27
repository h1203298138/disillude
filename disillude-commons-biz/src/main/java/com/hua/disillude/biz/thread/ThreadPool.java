/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： ThreadPool.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.biz.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 线程池调度，用于并发执行任务
 *
 * @author Hedh
 * @since 1.0
 */
public interface ThreadPool {
  /**
   * 释放线程池。
   */
  void stop();

  /**
   * 执行任务（非阻塞）。
   *
   * @param task
   *     任务，not null
   * @return 可以执行则返回true，否则返回false。
   */
  boolean execute(Runnable task);

  /**
   * 执行任务（阻塞）。
   *
   * @param task
   *     任务，not null
   */
  void executeWait(Runnable task);

  /**
   * 提交任务。
   *
   * @param task
   *     任务，not null
   */
  Future submit(Callable task);

  /**
   * 正在执行的任务数。
   */
  int getActiveCount();

  /**
   * 队列中等待的任务数。
   */
  int getWaitingCount();

  /**
   * 空闲线程数。
   */
  int getAvailableCount();
}
