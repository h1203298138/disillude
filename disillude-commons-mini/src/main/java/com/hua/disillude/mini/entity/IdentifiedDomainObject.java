/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： IdentifiedDomainObject.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import com.hua.disillude.mini.entity.hasinfo.HasId;
import com.hua.disillude.mini.utils.generator.IdGenerator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * 具有唯一标识领域对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@AllArgsConstructor
@ApiModel(value = "具有唯一标识领域对象")
public class IdentifiedDomainObject implements Serializable, HasId<String> {
  private static final long serialVersionUID = 5861367010581110985L;

  @ApiModelProperty(value = "唯一标识", required = true)
  private String id;

  protected IdentifiedDomainObject() {
    if (StringUtils.isBlank(this.getId())) {
      this.setId(IdGenerator.nextIid());
    }
  }
}