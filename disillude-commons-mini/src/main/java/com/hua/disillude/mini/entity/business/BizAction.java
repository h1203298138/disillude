/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： BizAction.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 业务动作
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@AllArgsConstructor
@ApiModel(value = "业务动作")
public class BizAction {
  @ApiModelProperty("编辑")
  public static final BizAction MODIFY = new BizAction(
      Arrays.asList(BizStates.INITIAL, BizStates.REJECTED),
      BizActions.MODIFY, BizStates.INITIAL, false, true);
  @ApiModelProperty("提交")
  public static final BizAction SUBMIT = new BizAction(
      Arrays.asList(BizStates.INITIAL, BizStates.REJECTED),
      BizActions.SUBMIT, BizStates.SUBMITTED, true, true);
  @ApiModelProperty("生效")
  public static final BizAction EFFECT = new BizAction(
      Arrays.asList(BizStates.INITIAL, BizStates.SUBMITTED),
      BizActions.EFFECT, BizStates.EFFECT, true, false);
  @ApiModelProperty("驳回")
  public static final BizAction REJECT = new BizAction(
      BizStates.SUBMITTED,
      BizActions.REJECT, BizStates.REJECTED, true, false);
  @ApiModelProperty("删除")
  public static final BizAction DELETE = new BizAction(
      Arrays.asList(BizStates.INITIAL, BizStates.REJECTED),
      BizActions.DELETE, BizStates.DELETED, true, false);
  @ApiModelProperty("作废")
  public static final BizAction ABORT = new BizAction(
      BizStates.EFFECT,
      BizActions.ABORT, BizStates.ABORTED, true, false);
  @ApiModelProperty("完成")
  public static final BizAction FINISH = new BizAction(
      BizStates.EFFECT,
      BizActions.FINISH, BizStates.FINISHED, true, false);

  @ApiModelProperty(value = "动作名称")
  private String action;
  @ApiModelProperty(value = "目标状态")
  private String state;
  @ApiModelProperty(value = "是否可批量")
  private boolean batchable = true;
  @ApiModelProperty(value = "是否需要保存(操作前)")
  private boolean needSave = true;
  @ApiModelProperty(value = "来源状态")
  private List<String> srcStates = new ArrayList<>();

  public BizAction(String srcState, String action, String state) {
    this.action = action;
    this.state = state;
    this.srcStates.add(srcState);
  }

  public BizAction(List<String> srcStates, String action, String state) {
    this.action = action;
    this.state = state;
    this.srcStates = srcStates == null ? new ArrayList<>() : srcStates;
  }

  public BizAction(String srcState, String action, String state, boolean batchable, boolean needSave) {
    this.action = action;
    this.state = state;
    this.batchable = batchable;
    this.needSave = needSave;
    this.srcStates.add(srcState);
  }

  public BizAction(List<String> srcStates, String action, String state, boolean batchable, boolean needSave) {
    this.action = action;
    this.state = state;
    this.batchable = batchable;
    this.needSave = needSave;
    this.srcStates = srcStates == null ? new ArrayList<>() : srcStates;
  }
}