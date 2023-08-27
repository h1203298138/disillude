/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： R.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.biz.i18n;

/**
 * @author Hedh
 * @since 1.0
 */
public interface R {
  /** 请求过多 */
  String TOO_MANY_REQUESTS = "too.many.requests";
  /** 指定的{0}({1})不存在或不可用 */
  String NOT_EXISTS = "not.exists";
  /** 指定的{0}({1})当前不允许修改 */
  String FORBID_MODIFY = "forbid.modify";
  /** 指定的{0}({1})当前不允许删除 */
  String FORBID_DELETE = "forbid.delete";
  /** 指定的ID({0})发生重复 */
  String ID_DUPLICATED = "id.duplicated";
  /** 指定的代码({0})发生重复 */
  String CODE_DUPLICATED = "code.duplicated";
  /** 指定的{0}名称({1})发生重复 */
  String NAME_DUPLICATED = "name.duplicated";
  /** 指定的{0}({1})已锁定 */
  String ALREADY_LOCKED = "already.locked";
  /** 指定的{0}({1})已禁用 */
  String ALREADY_DISABLED = "already.disabled";
  /** 指定的{0}({1})已启用 */
  String ALREADY_ENABLED = "already.enabled";
  /** 指定的上级{0}({1})已禁用 */
  String UPPER_ALREADY_DISABLED = "upper.already.disabled";
  /** 指定的上级{0}({1})已启用 */
  String UPPER_ALREADY_ENABLED = "upper.already.disabled";
  /** 指定的{0}({1})禁止当前操作 */
  String OPERATION_FORBIDDEN = "operation.forbidden";
  /** 指定的{0}({1})禁止当前操作 */
  String BEYOND_MAX_LEVEL = "beyond.max.level";
  /** 已被修改，请刷新重试 */
  String VERSION_CONFLICT = "VersionConflict";
}
