/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： Certification.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hua.disillude.mini.constant.LengthConst;
import com.hua.disillude.mini.entity.Entity;
import com.hua.disillude.mini.entity.IdName;
import com.hua.disillude.mini.utils.format.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 证照信息对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "证照信息")
@EqualsAndHashCode(callSuper = true)
public class Certification extends Entity {
  private static final long serialVersionUID = 8895671383068886979L;

  @NotNull(message = "证照类型不能为空")
  @ApiModelProperty(value = "证照类型")
  private IdName licenseType;
  @Xss
  @Size(max = LengthConst.CODE, message = "证照编号不能超过" + LengthConst.CODE)
  @NotBlank(message = "证照编号不能为空")
  @ApiModelProperty(value = "证照编号", required = true)
  private String code;
  @NotNull(message = "有效期起始日期不能为空")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "有效期从", required = true)
  private Date validFrom;
  @NotNull(message = "有效期结束日期不能为空")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty(value = "有效期至", required = true)
  private Date validTo;
  @Size(min = 1, message = "至少上传1个证照附件")
  @Size(max = 10, message = "最多上传10个证照附件")
  @NotEmpty(message = "至少上传1个证照附件")
  @ApiModelProperty(value = "证照附件", required = true)
  private List<Attachment> attachments;
  @Xss
  @Size(max = LengthConst.REMARK, message = "备注说明长度不能超过" + LengthConst.REMARK)
  @Length(max = LengthConst.REMARK)
  @ApiModelProperty(value = "备注说明")
  private String remark;
}