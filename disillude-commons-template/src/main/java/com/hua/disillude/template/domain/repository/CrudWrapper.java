/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： CrudWrapper.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.domain.repository;

import com.hua.aroma.commons.jdbc.qd.QueryProcessor;
import com.hua.aroma.commons.jdbc.temapper.TEMapper;
import lombok.Data;

/**
 * @author Hedh
 * @since 1.0
 */
@Data
public class CrudWrapper {
  private Class clazz;
  private String[] allParts;

  private String tableName;
  private TEMapper mapper;
  private QueryProcessor queryProcessor;
}
