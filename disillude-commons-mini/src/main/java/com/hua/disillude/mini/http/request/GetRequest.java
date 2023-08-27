/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： GetRequest.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.http.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

/**
 * 获取数据对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(value = "获取数据")
public class GetRequest {
  @NotBlank(message = "ID不能为空")
  @ApiModelProperty(value = "标识", example = "0001", required = true)
  private String id;
  @ApiModelProperty(value = "扩展信息")
  private List<String> fetchParts;

  public String[] toParts() {
    if (CollectionUtils.isEmpty(this.fetchParts)) {
      return new String[]{};
    }
    return this.fetchParts.toArray(String[]::new);
  }

  public static void main(String[] args) {
    GetRequest request = new GetRequest();
    System.out.println(Arrays.toString(request.toParts()));
  }
}