/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： HasOperator.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;

import com.hua.disillude.mini.entity.injectable.Injectable;

/**
 * 标识拥有操作人信息
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasOperator extends Injectable {
  /**
   * 获取用户标识「userId」。
   *
   * @return 「userId」
   */
  String getUserId();

  /**
   * 设置用户标识「userId」。
   *
   * @param userId
   *     用户标识「userId」
   * @throws UnsupportedOperationException
   *     不支持的操作异常
   */
  void setUserId(String userId) throws UnsupportedOperationException;

  /**
   * 获取用户名称「userName」。
   *
   * @return 「userName」
   */
  String getUserName();

  /**
   * 设置用户名称「userName」。
   *
   * @param userName
   *     用户名称「userName」
   * @throws UnsupportedOperationException
   *     不支持的操作异常
   */
  void setUserName(String userName) throws UnsupportedOperationException;

  /**
   * 获取组织标识「orgId」。
   *
   * @return 「orgId」
   */
  String getOrgId();

  /**
   * 设置组织标识「orgId」。
   *
   * @param orgId
   *     组织标识「orgId」
   * @throws UnsupportedOperationException
   *     不支持的操作异常
   */
  void setOrgId(String orgId) throws UnsupportedOperationException;

  /**
   * 获取组织名称「orgName」。
   *
   * @return 「orgName」
   */
  String getOrgName();

  /**
   * 设置组织名称「orgName」。
   *
   * @param orgName
   *     组织名称「orgName」
   * @throws UnsupportedOperationException
   *     不支持的操作异常
   */
  void setOrgName(String orgName) throws UnsupportedOperationException;
}
