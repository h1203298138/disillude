/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： StandardEntity.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 标准实体对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(value = "标准实体")
@EqualsAndHashCode(callSuper = true)
public abstract class StandardEntity extends Entity implements HasVersionInfo, HasCreateInfo, HasLastModifiedInfo {
  private static final long serialVersionUID = -1114685495984360307L;

  @ApiModelProperty(value = "版本号", required = true)
  private long version;
  @NotNull(message = "版本时间不能为空")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "版本时间", required = true)
  private Date versionTime;

  @NotNull(message = "创建信息不能为空")
  @ApiModelProperty(value = "创建信息", required = true)
  private OperateInfo createInfo;
  @NotNull(message = "最后修改信息不能为空")
  @ApiModelProperty(value = "最后修改信息", required = true)
  private OperateInfo lastModifiedInfo;
  @Size(max = LengthConst.REMARK, message = "备注说明长度不能超过" + LengthConst.REMARK)
  @Length(max = LengthConst.REMARK)
  @ApiModelProperty(value = "备注说明")
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