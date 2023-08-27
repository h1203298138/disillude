/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： FlowCodeUtil.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.generator;


import com.hua.disillude.mini.utils.StringUtil;

/**
 * @author Hedh
 * @since 1.0
 */
public class FlowCodeUtil {
  /**
   * 取得指定代码的下一个流水代码。参与流水可能的字符是'0'-'9''A'-'Z'，具体取决于参数scale。
   *
   * @param code
   *     指定输入的代码。not null，也不可以为空串。
   * @throws RuntimeException
   *     当参数非法时抛出；当越界时抛出；当字符非法时抛出。
   */
  public static String flowCode(String code) throws RuntimeException {
    if (StringUtil.isNullOrBlank(code)) {
      throw new RuntimeException("传入的代码无法流水(" + code + ")。");
    }

    char[] chars = code.toUpperCase().toCharArray();
    int index = chars.length - 1;
    while (index >= 0) {
      char c = chars[index];
      if (c >= '0' && c < '9' || c >= 'A' && c < 'Z') {
        chars[index] = (char) (c + 1);
        break;
      } else if (c == '9') {
        if (index == 0) {
          chars[index] = 'A';
          break;
        } else {
          chars[index] = '0';
        }
      } else if (c == 'Z') {
        chars[index] = 'A';
      }
      index--;
    }
    if (index < 0) {
      throw new RuntimeException("传入的代码已经是达到最大(" + code + ")。");
    }
    return String.valueOf(chars);
  }

  public static void main(String[] args) {
    System.out.println(flowCode("99"));
  }
}