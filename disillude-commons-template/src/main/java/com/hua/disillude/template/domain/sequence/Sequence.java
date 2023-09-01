/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： Sequence.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.domain.sequence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于描述顺序发生器任意时刻状态的持久化对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sequence {
  private String scope;
  private long value;
}
