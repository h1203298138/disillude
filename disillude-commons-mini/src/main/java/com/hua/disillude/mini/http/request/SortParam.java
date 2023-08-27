/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： SortParam.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.http.request;

import com.hua.disillude.mini.datamodel.enums.SortEnum;
import com.hua.disillude.mini.utils.format.enumtype.EnumType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排序字段对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "排序字段")
public class SortParam {
  @ApiModelProperty(value = "字段名", example = "createInfo.time")
  private String property;
  @EnumType(value = SortEnum.class)
  @ApiModelProperty(value = "排序方向", example = "DESC")
  private String direction = SortEnum.DESC.name();
}