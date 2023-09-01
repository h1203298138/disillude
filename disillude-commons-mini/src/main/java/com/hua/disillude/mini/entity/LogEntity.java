/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： LogEntity.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.hua.aroma.commons.biz.query.QueryEntity;
import com.hua.aroma.commons.biz.query.QueryField;
import com.hua.disillude.mini.datamodel.OperateInfo;
import com.hua.disillude.mini.queries.QueryFactors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.util.Date;

/**
 * 日志实体对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@ApiModel(value = "日志实体对象")
@EqualsAndHashCode(callSuper = true)
public class LogEntity extends TenantEntity {
  @ApiModelProperty("应用标识")
  private String appId;
  @ApiModelProperty("引用对象标识")
  private String refObjectId;
  @ApiModelProperty("操作动作")
  private String action;
  @ApiModelProperty("操作动作名称")
  private String actionName;
  @ApiModelProperty("状态")
  private String state;
  @ApiModelProperty("操作信息")
  private OperateInfo operateInfo;

  @QueryEntity(LogEntity.class)
  public static class Queries extends QueryFactors.TenantEntity {
    private static final String PREFIX = LogEntity.class.getName() + "::";
    @QueryField
    public static final String REF_OBJECT_ID = PREFIX + "refObjectId";
    @QueryField
    public static final String APP_ID = PREFIX + "appId";
    @QueryField
    public static final String ACTION = PREFIX + "action";
    @QueryField
    public static final String OPERATE_INFO_USER_ID = PREFIX + "operateInfo.userId";
    @QueryField(fieldType = Date.class)
    public static final String OPERATE_INFO_TIME = PREFIX + "operateInfo.time";
  }
}
