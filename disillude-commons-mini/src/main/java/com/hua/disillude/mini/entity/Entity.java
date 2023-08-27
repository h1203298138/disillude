/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： Entity.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;

/**
 * 实体对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@ApiModel(value = "实体对象")
@EqualsAndHashCode(callSuper = true)
public class Entity extends IdentifiedDomainObject {
  private static final long serialVersionUID = 280080572393045495L;

  protected Entity() {
    super();
  }
}