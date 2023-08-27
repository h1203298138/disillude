/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： LogService.java
 * 模块说明：
 * 修改历史：
 * 2022年07月05日 - Hedh - 创建。
 */
package com.hua.disillude.biz.log;

/**
 * 日志服务
 *
 * @author Hedh
 * @since 1.0
 */
public interface LogService {
  /**
   * 提交
   *
   * @param tenant
   *     租户
   * @param log
   *     日志
   */
  void submit(String tenant, OperationLog log);
}
