/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： OperationLog.java
 * 模块说明：
 * 修改历史：
 * 2022年07月05日 - Hedh - 创建。
 */
package com.hua.disillude.biz.log;

import com.hua.disillude.mini.datamodel.OperateInfo;
import com.hua.disillude.mini.datamodel.RefObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作日志
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel("操作日志")
public class OperationLog {
  @ApiModelProperty("租户")
  private String tenant;
  @ApiModelProperty("引用对象")
  private RefObject refObject;
  @ApiModelProperty("操作动作")
  private String action;
  @ApiModelProperty("操作动作名称")
  private String actionName;
  @ApiModelProperty("操作信息")
  private OperateInfo operateInfo;
  @ApiModelProperty("请求数据")
  private String payload;
}