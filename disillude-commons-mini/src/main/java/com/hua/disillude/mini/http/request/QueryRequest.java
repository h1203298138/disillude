/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： QueryRequest.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.http.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(value = "分页查询")
public class QueryRequest {
  @ApiModelProperty(value = "过滤字段")
  private List<FilterParam> filters = new ArrayList<>();
  @ApiModelProperty(value = "排序字段")
  private List<SortParam> sorters = new ArrayList<>();
  @ApiModelProperty(value = "扩展信息")
  private List<String> fetchParts = new ArrayList<>();
  @ApiModelProperty(value = "页码", example = "0")
  private int page = 0;
  @Min(0)
  @ApiModelProperty(value = "页大小", example = "100")
  private int pageSize = 0;

  public FilterParam getFilter(String property) {
    return property == null || this.filters == null
        ? null
        : this.filters.stream().filter(o -> property.equals(o.getProperty())).findFirst().orElse(null);
  }

  public QueryRequest addFilter(String property, Object value) {
    this.filters.add(new FilterParam(property, value));
    return this;
  }

  public String[] toParts() {
    if (CollectionUtils.isEmpty(this.fetchParts)) {
      return new String[]{};
    }
    return this.fetchParts.toArray(String[]::new);
  }
}
