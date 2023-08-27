/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： SimpleThreadPool.java
 * 模块说明：
 * 修改历史：
 * 2023年08月28日 - Hedh - 创建。
 */
package com.hua.disillude.biz.thread;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 默认的线程池实现，使用固定大小线程池。 <br>
 * 开发人员必须指定线程数和队列数，开放出配置，不提供默认配置方案，结合业务需要进行配置
 *
 * @author Hedh
 * @since 1.0
 */
public class SimpleThreadPool implements ThreadPool {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private int threads = 2; // 默认2个线程
  private int queueSize = 100; // 默认100个任务队列
  private ThreadPoolExecutor executor;
  private String threadNamePrefix = "Simple-";
  private StatusReporter reporter;

  private SimpleThreadPool() {
    this.reporter = new StatusReporter();
  }

  /**
   * @param threadNamePrefix
   *     线程名称前缀，不指定默认为“Simple-"
   * @param threads
   *     并发线程数。
   * @param queueSize
   *     线程等待队列大小。
   */
  public SimpleThreadPool(String threadNamePrefix, int threads, int queueSize) {
    this();
    this.threads = threads;
    this.queueSize = queueSize;
    if (StringUtils.isNotBlank(threadNamePrefix)) {
      this.threadNamePrefix = threadNamePrefix;
    }
    this.initPool();
  }

  /**
   * @param threads
   *     并发线程数。
   * @param queueSize
   *     线程等待队列大小。
   */
  public SimpleThreadPool(int threads, int queueSize) {
    this(null, threads, queueSize);
  }

  private void initPool() {
    this.executor = new MyExecutor(this.threads, this.threads, 60, TimeUnit.SECONDS,
        new LinkedBlockingQueue<Runnable>(this.queueSize),
        new SimpleThreadFactory(this.threadNamePrefix));
    this.executor.setRejectedExecutionHandler(new MyPolicy());
  }

  @Override
  public void stop() {
    this.logger.info("stop");
    if (this.executor != null) {
      this.safetyShutdown(this.executor);
      this.executor = null;
    }
  }

  @Override
  public boolean execute(final Runnable task) {
    return this.executeRunnable(task);
  }

  @Override
  public void executeWait(final Runnable task) {
    this.executeRunnable(new WaitableTask(task));
  }

  private boolean executeRunnable(final Runnable task) {
    this.logger.debug("execute: {}", task);
    this.reporter.onSubmit().print();
    this.ensureStarted();
    try {
      this.executor.execute(task);
    } catch (RejectedExecutionException e) {
      this.reporter.onReject();
      return false;
    }
    return true;
  }

  @Override
  public Future submit(Callable task) {
    this.logger.debug("submit: {}", task);
    this.reporter.onSubmit().print();
    this.ensureStarted();
    return this.executor.submit(task);
  }

  @Override
  public int getActiveCount() {
    return this.executor.getActiveCount();
  }

  @Override
  public int getWaitingCount() {
    return this.executor.getQueue().size();
  }

  @Override
  public int getAvailableCount() {
    int available = this.threads - this.executor.getActiveCount();
    return available > 0 ? available : 0;
  }

  private void safetyShutdown(ExecutorService executor) {
    try {
      executor.shutdown();
    } catch (Exception ignored) {
    }
    try {
      // 等待10秒钟
      for (int index = 0; index < 10; index++) {
        if (executor.awaitTermination(1, TimeUnit.SECONDS)) {
          return;
        }
      }
      executor.shutdownNow();
    } catch (Exception e) {
      try {
        executor.shutdownNow();
      } catch (Exception ignored) {
      }
    }
  }

  private void ensureStarted() {
    if (this.executor == null) {
      throw new IllegalStateException("ThreadPool not started.");
    }
  }

  class MyExecutor extends ThreadPoolExecutor {

    public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                      BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
      super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
      super.beforeExecute(t, r);
      SimpleThreadPool.this.reporter.onStart();
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
      super.afterExecute(r, t);
      SimpleThreadPool.this.reporter.onFinish();
      if (t != null) {
        SimpleThreadPool.this.reporter.onError();
      }
    }
  }

  // 当线程池满时可以阻塞
  class MyPolicy implements RejectedExecutionHandler {
    public MyPolicy() {
    }

    @Override
    @SneakyThrows
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
      if (r instanceof WaitableTask) { // 阻塞等待
        try {
          SimpleThreadPool.this.executor.getQueue().put(r);
        } catch (InterruptedException e1) {
          SimpleThreadPool.this.logger.error("", e1);
          throw e1;
        }
      } else { // 抛出异常
        String msg = String.format(
            "Thread pool is full!" + " Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), "
                + " Task: %d (completed: %d),"
                + " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)",
            e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(),
            e.getLargestPoolSize(), e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(),
            e.isTerminated(), e.isTerminating());
        SimpleThreadPool.this.logger.debug(msg);
        throw new RejectedExecutionException(msg);
      }
    }
  }

  class WaitableTask implements Runnable {
    private Runnable task;

    public WaitableTask(Runnable r) {
      this.task = r;
    }

    @Override
    public void run() {
      this.task.run();
    }

    @Override
    public String toString() {
      return this.task != null ? this.task.toString() : super.toString();
    }
  }

  // 打印报告
  class StatusReporter {
    private AtomicLong submited = new AtomicLong();
    private AtomicLong rejected = new AtomicLong();
    private AtomicLong started = new AtomicLong();
    private AtomicLong finished = new AtomicLong();
    private AtomicLong error = new AtomicLong();

    public void print() {
      SimpleThreadPool.this.logger.debug(
          "pool({} threads {} queue), {} submited, {} rejected, {} started, {} finished, {} error",
          SimpleThreadPool.this.threads, SimpleThreadPool.this.queueSize, this.submited.get(), this.rejected.get(), this.started.get(), this.finished.get(), this.error.get());
    }

    public StatusReporter onSubmit() {
      this.submited.incrementAndGet();
      return this;
    }

    public StatusReporter onReject() {
      this.rejected.incrementAndGet();
      return this;
    }

    public StatusReporter onStart() {
      this.started.incrementAndGet();
      return this;
    }

    public StatusReporter onFinish() {
      this.finished.incrementAndGet();
      return this;
    }

    public StatusReporter onError() {
      this.error.incrementAndGet();
      return this;
    }

  }
}
