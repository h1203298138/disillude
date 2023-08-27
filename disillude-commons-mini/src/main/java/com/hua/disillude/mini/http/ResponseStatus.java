/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： ResponseStatus.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态信息
 *
 * @author Hedh
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@ApiModel(value = "响应状态信息")
public enum ResponseStatus {
  /** 操作成功 */
  REQUEST_SUCCESS(200, "操作成功。"),
  /** 业务异常 */
  REQUEST_FAILURE(400, "业务异常，请联系开发人员。"),
  /** 服务未找到 */
  NOT_FOUND(404, "服务未找到。"),
  /** 服务异常 */
  ERROR(50001, "服务异常。"),
  /** 请求过多 */
  TOO_MANY_REQUESTS(429, "请求过多。"),
  /** 服务不可用 */
  SERVICE_UNAVAILABLE(503, "服务不可用。"),
  /** 参数不合法，请检查输入参数 */
  PARAMETER_INVALID(4000, "参数不合法，请检查输入参数。"),
  /** 获取当前用户失败 */
  CURRENT_USER_FAIL(10001, "获取当前用户失败。"),
  /** 用户是超级管理员，不可以修改状态 */
  UPDATE_USER_STATUS(10002, "用户是超级管理员，不可以修改状态。"),
  /** 用户是超级管理员，不可以修改密码 */
  UPDATE_USER_PASSWORD(10003, "用户是超级管理员，不可以修改密码。"),
  /** 用户未登录，请登录后进行访问 */
  USER_NEED_LOGIN(11001, "用户未登录，请登录后进行访问。"),
  /** 该用户已在其它地方登录 */
  USER_MAX_LOGIN(11002, "该用户已在其它地方登录。"),
  /** 长时间未操作，自动退出 */
  USER_LOGIN_TIMEOUT(11003, "长时间未操作，自动退出。"),
  /** 用户已被禁用 */
  USER_DISABLED(11004, "用户已被禁用。"),
  /** 用户已被锁定 */
  USER_LOCKED(11005, "用户已被锁定。"),
  /** 用户名或密码错误 */
  USER_PASSWORD_ERROR(11006, "用户名或密码错误"),
  /** 用户密码过期 */
  USER_PASSWORD_EXPIRED(11007, "用户密码过期"),
  /** 用户账号过期 */
  USER_ACCOUNT_EXPIRED(11008, "用户账号过期"),
  /** 没有该用户 */
  USER_NOT_EXIST(11009, "没有该用户"),
  /** 用户登录失败 */
  USER_LOGIN_FAIL(11010, "用户登录失败"),
  /** 验证码错误 */
  VERIFY_CODE_ERROR(11011, "验证码错误"),
  /** 用户已存在 */
  USER_IS_EXIST(11012, "用户已存在"),
  /** 无权访问 */
  NO_AUTHENTICATION(1003006, "无权访问"),
  /** 配置信息为空 */
  CONFIG_ID_IS_NOT_EXIST(14001, "配置信息为空"),
  /** 系统配置不允许修改 */
  CONFIG_IS_SYSTEM(14003, "系统配置不允许修改"),
  /** 系统配置不允许删除 */
  CONFIG_IS_NOT_DELETE(14003, "系统配置不允许删除"),
  /** 文件不存在 */
  FILE_DOES_NOT_EXIST(16001, "文件不存在"),
  /** 文件上传异常 */
  FILE_UPLOAD_EXCEPTION(16002, "文件上传异常"),
  /** 文件下载异常 */
  FILE_DOWNLOAD_ABNORMAL(16003, "文件下载异常"),
  /** 无效的资源ID */
  RESOURCE_NOT_FIND(12001, "无效的资源ID"),
  /** 找不到该数据，请刷新页面 */
  DICT_NOT_EXIST(18006, "找不到该数据，请刷新页面"),
  ;

  @ApiModelProperty(value = "响应状态码")
  private final int code;
  @ApiModelProperty(value = "响应信息")
  private final String message;
}