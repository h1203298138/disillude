/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： BizFlow.java
 * 模块说明：
 * 修改历史：
 * 2022年05月29日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.business;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 业务流定义
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@NoArgsConstructor
@ApiModel(value = "业务流定义")
public class BizFlow {
  /** 简单流程：编辑、提交、生效、驳回 */
  public static final BizFlow SIMPLE_FLOW;
  /** 标准流程：编辑、提交、生效、驳回、作废 */
  public static final BizFlow STANDARD_FLOW;
  /** 带完成流程：编辑、提交、生效、驳回、作废、完成 */
  public static final BizFlow FINISH_FLOW;

  static {
    SIMPLE_FLOW = new BizFlow(BizStates.INITIAL, BizStates.SUBMITTED, BizStates.REJECTED, BizStates.EFFECT, BizStates.DELETED);
    SIMPLE_FLOW.addActions(BizAction.MODIFY, BizAction.SUBMIT, BizAction.REJECT, BizAction.EFFECT, BizAction.DELETE);

    STANDARD_FLOW = new BizFlow(BizStates.INITIAL, BizStates.SUBMITTED, BizStates.REJECTED, BizStates.DELETED, BizStates.EFFECT, BizStates.ABORTED);
    STANDARD_FLOW.addActions(BizAction.MODIFY, BizAction.SUBMIT, BizAction.REJECT, BizAction.EFFECT, BizAction.DELETE, BizAction.ABORT);

    FINISH_FLOW = new BizFlow(BizStates.INITIAL, BizStates.SUBMITTED, BizStates.REJECTED, BizStates.DELETED, BizStates.EFFECT, BizStates.FINISHED, BizStates.ABORTED);
    FINISH_FLOW.addActions(BizAction.MODIFY, BizAction.SUBMIT, BizAction.REJECT, BizAction.EFFECT, BizAction.FINISH, BizAction.DELETE, BizAction.ABORT);
  }

  @ApiModelProperty(value = "状态列表")
  private List<String> states = new ArrayList<>();
  @ApiModelProperty(value = "动作列表")
  private List<BizAction> actions = new ArrayList<>();

  public BizFlow(String... states) {
    if (states != null) {
      Collections.addAll(this.states, states);
    }
  }

  public void addAction(BizAction action) {
    if (action == null || action.getAction() == null) {
      return;
    }
    BizAction bizAction = this.getAction(action.getAction());
    if (bizAction != null) {
      return;
    }
    this.actions.add(action);
  }

  public void addActions(BizAction... action) {
    if (action == null) {
      return;
    }
    Arrays.asList(action).forEach(this::addAction);
  }

  public BizAction getAction(String action) {
    if (action == null) {
      return null;
    }
    for (BizAction biz : this.actions) {
      if (action.equals(biz.getAction())) {
        return biz;
      }
    }
    return null;
  }

  /**
   * *检查是含对应状态变化的操作列表
   *
   * @param srcState
   *     当前状态
   * @param targetState
   *     目标状态
   * @return 操作列表，如果没有找到返回空列表
   */
  public List<BizAction> getValidActions(String srcState, String targetState) {
    List<BizAction> list = new ArrayList<>();
    for (BizAction action : this.actions) {
      if (srcState == null || action.getSrcStates().isEmpty() || action.getSrcStates().contains(srcState)) {
        if (targetState == null || action.getState() == null || targetState.equals(action.getState())) {
          list.add(action);
        }
      }
    }
    return list;
  }
}