/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： PartsUtil.java
 * 模块说明：
 * 修改历史：
 * 2022年07月11日 - Hedh - 创建。
 */
package com.hua.disillude.mini.http.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class PartsUtil {
  private String[] parts;

  private PartsUtil() {

  }

  public PartsUtil(String... parts) {
    this.parts = parts;
  }

  public List<String> getParts() {
    if (this.parts == null) {
      return Collections.emptyList();
    }
    return Arrays.asList(this.parts);
  }

}