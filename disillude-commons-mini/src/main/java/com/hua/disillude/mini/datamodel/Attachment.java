/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-project
 * 文件名： Attachment.java
 * 模块说明：
 * 修改历史：
 * 2022年05月28日 - Hedh - 创建。
 */
package com.hua.disillude.mini.datamodel;

import com.hua.disillude.mini.entity.Entity;
import com.hua.disillude.mini.utils.format.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * 附件对象
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@Valid
@NoArgsConstructor
@ApiModel(value = "附件")
@EqualsAndHashCode(callSuper = true)
public class Attachment extends Entity {
  private static final long serialVersionUID = -3357323190256976768L;

  /** 附件oss Key字段长度定义 */
  public static final int LENGTH_KEY = 255;
  /** 附件名称字段长度定义 */
  public static final int LENGTH_NAME = 128;
  /** 附件地址字段长度定义 */
  public static final int LENGTH_URL = 512;

  @Xss
  @Size(max = LENGTH_KEY, message = "oss Key长度不能超过" + LENGTH_KEY)
  @ApiModelProperty(value = "oss Key")
  private String key;
  @Xss
  @Size(max = LENGTH_NAME, message = "名称长度不能超过" + LENGTH_NAME)
  @ApiModelProperty(value = "名称")
  private String name;
  @Xss
  @Size(max = LENGTH_URL, message = "下载地址长度不能超过" + LENGTH_URL)
  @ApiModelProperty(value = "下载地址")
  private String url;
}