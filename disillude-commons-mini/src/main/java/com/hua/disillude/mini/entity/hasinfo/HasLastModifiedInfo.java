/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： HasLastModifiedInfo.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;


import com.hua.disillude.mini.datamodel.OperateInfo;

/**
 * 标识拥有最后修改信息
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasLastModifiedInfo {
  /**
   * 获取最后修改信息「LastModifiedInfo」。
   *
   * @return 「LastModifiedInfo」
   */
  OperateInfo getLastModifiedInfo();
}
