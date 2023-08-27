/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： HasAttachment.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.entity.hasinfo;

import com.hua.disillude.mini.datamodel.Attachment;
import com.hua.disillude.mini.entity.injectable.Injectable;

/**
 * 标识拥有附件的实体
 *
 * @author Hedh
 * @since 1.0
 */
public interface HasAttachment extends Injectable {
  /**
   * 获取应用标识「AppId」。
   *
   * @return 「AppId」
   */
  Attachment getAttachment();

  /**
   * 设置应用标识「AppId」。
   *
   * @param appId
   *     应用标识「AppId」
   * @throws UnsupportedOperationException
   *     当实现类不支持修改应用标识时抛出。
   */
  void setAttachment(Attachment attachment);
}
