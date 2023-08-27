/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： DatabaseTypeEnum.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hedh
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@ApiModel(value = "数据库类型")
public enum DatabaseTypeEnum {
  /** mysql */
  MYSQL("mysql", "mysql"),
  /** pgsql */
  PG_SQL("pgsql", "postgresql"),
  /** oracle */
  ORACLE("oracle", "oracle"),
  /** mssql */
  MS_SQL("mssql", "sqlserver");

  @ApiModelProperty(value = "数据库编码")
  private final String code;
  @ApiModelProperty(value = "数据库名称")
  private final String name;
}
