/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： TenantStandardEntity.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.hua.aroma.commons.biz.entity.HasVersionInfo;
import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.datamodel.OperateInfo;
import com.hua.disillude.mini.entity.hasinfo.HasCreateInfo;
import com.hua.disillude.mini.entity.hasinfo.HasLastModifiedInfo;
import com.hua.disillude.mini.entity.hasinfo.HasOperateInfo;
import com.hua.disillude.mini.entity.hasinfo.HasOperator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 租户标准实体
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(description = "租户级别的标准实体")
@EqualsAndHashCode(callSuper = true)
public abstract class TenantStandardEntity extends TenantEntity implements HasCreateInfo, HasLastModifiedInfo, HasVersionInfo {
  @NotBlank
  @Length(max = LengthConst.ID)
  @ApiModelProperty(value = "所属应用标识", required = true)
  private String appId;
  @ApiModelProperty(value = "版本号")
  private long version;
  @ApiModelProperty(value = "版本时间")
  private Date versionTime;

  @ApiModelProperty(value = "创建信息")
  private OperateInfo createInfo;
  @ApiModelProperty(value = "最后修改信息")
  private OperateInfo lastModifiedInfo;
  @ApiModelProperty("说明")
  private String remark;

  public void onCreated(HasOperateInfo operateInfo) {
    if (operateInfo == null) {
      return;
    }
    this.createInfo = new OperateInfo();
    this.createInfo.setTime(operateInfo.getTime());
    this.createInfo.setUserId(operateInfo.getUserId());
    this.createInfo.setUserName(operateInfo.getUserName());
    this.createInfo.setOrgId(operateInfo.getOrgId());
    this.createInfo.setOrgName(operateInfo.getOrgName());
    this.onModified(operateInfo);
  }

  public void onCreated(Date occurredOn, HasOperator operator) {
    this.createInfo = new OperateInfo();
    this.createInfo.setTime(occurredOn);
    if (operator != null) {
      this.createInfo.setUserId(operator.getUserId());
      this.createInfo.setUserName(operator.getUserName());
      this.createInfo.setOrgId(operator.getOrgId());
      this.createInfo.setOrgName(operator.getOrgName());
    }
    this.onModified(occurredOn, operator);
  }

  public void onModified(HasOperateInfo operateInfo) {
    if (operateInfo == null) {
      return;
    }
    this.lastModifiedInfo = new OperateInfo();
    this.lastModifiedInfo.setTime(operateInfo.getTime());
    this.lastModifiedInfo.setUserId(operateInfo.getUserId());
    this.lastModifiedInfo.setUserName(operateInfo.getUserName());
    this.lastModifiedInfo.setOrgId(operateInfo.getOrgId());
    this.lastModifiedInfo.setOrgName(operateInfo.getOrgName());
  }

  public void onModified(Date occurredOn, HasOperator operator) {
    this.lastModifiedInfo = new OperateInfo();
    this.lastModifiedInfo.setTime(occurredOn);
    if (operator != null) {
      this.lastModifiedInfo.setUserId(operator.getUserId());
      this.lastModifiedInfo.setUserName(operator.getUserName());
      this.lastModifiedInfo.setOrgId(operator.getOrgId());
      this.lastModifiedInfo.setOrgName(operator.getOrgName());
    }
  }
}
