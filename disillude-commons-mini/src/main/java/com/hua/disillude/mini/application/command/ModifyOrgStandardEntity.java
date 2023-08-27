/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： ModifyOrgStandardEntity.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.application.command;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "编辑组织标准实体")
public abstract class ModifyOrgStandardEntity extends ModifyTenantStandardEntity {
}
