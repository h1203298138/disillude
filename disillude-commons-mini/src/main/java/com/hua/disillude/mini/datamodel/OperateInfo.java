/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： OperateInfo.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hua.disillude.mini.entity.hasinfo.HasOperateInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.hua.disillude.mini.utils.format.DateTimeFormat.DEFAULT_DATE_FORMAT;


/**
 * 操作上下文
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "操作上下文")
@EqualsAndHashCode(callSuper = true)
public class OperateInfo extends Operator implements HasOperateInfo {
  @NotNull(message = "操作时间不能为空")
  @JsonFormat(pattern = DEFAULT_DATE_FORMAT, timezone = "GMT+8")
  @DateTimeFormat(pattern = DEFAULT_DATE_FORMAT)
  @ApiModelProperty(value = "操作时间", required = true)
  private Date time;

  public static OperateInfo newInstance(Operator operator) {
    return newInstance(operator, new Date());
  }

  public static OperateInfo newInstance(Operator operator, Date operateTime) {
    OperateInfo info = new OperateInfo();
    info.inject(operator);
    info.setTime(operateTime);
    return info;
  }

  @Override
  public void inject(Object source) throws UnsupportedOperationException {
    super.inject(source);
    if (source instanceof HasOperateInfo) {
      this.setTime(((HasOperateInfo) source).getTime());
    }
  }
}