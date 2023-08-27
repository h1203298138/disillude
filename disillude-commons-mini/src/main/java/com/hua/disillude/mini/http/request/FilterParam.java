/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： FilterParam.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.http.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 过滤字段对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "过滤字段")
public class FilterParam {
  @ApiModelProperty(value = "字段名", example = "id:=")
  private String property;
  @ApiModelProperty(value = "字段值", example = "9999")
  private Object value;
}
