/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： HasCreateInfo.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;


import com.hua.disillude.mini.datamodel.OperateInfo;

/**
 * 标识拥有创建信息
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasCreateInfo {
  /**
   * 获取创建信息「CreateInfo」。
   *
   * @return 「CreateInfo」
   */
  OperateInfo getCreateInfo();
}