/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： LoggerController.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.facade.log;

import com.hua.aroma.commons.biz.query.Cop;
import com.hua.aroma.commons.biz.query.QueryDefinition;
import com.hua.aroma.commons.biz.query.QueryResult;
import com.hua.disillude.biz.jdbc.utils.QueryDefinitionBuilder;
import com.hua.disillude.mini.entity.LogEntity;
import com.hua.disillude.mini.http.Response;
import com.hua.disillude.mini.http.request.QueryRequest;
import com.hua.disillude.template.domain.log.LogAdapterBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hedh
 * @since 1.0
 */
@RestController
@Api(tags = LoggerController.TAG)
@RequestMapping("/v1/{tenant}/log/{bizType}/{refObjectId}")
public class LoggerController {
  public static final String TAG = "v1-日志统一查询接口";

  @ApiOperation(value = "分页查询日志")
  @PostMapping(value = "query")
  public Response<T> queryLogs(
      @ApiParam(value = "租户标识") @PathVariable("tenant") String tenant,
      @ApiParam(value = "bizType") @PathVariable("bizType") String bizType,
      @ApiParam(value = "refObjectId") @PathVariable("refObjectId") String refObjectId,
      @Validated @RequestBody QueryRequest request) {
    QueryDefinition qd = new QueryDefinitionBuilder(LogEntity.Queries.class).build(request);
    qd.addByField(LogEntity.Queries.REF_OBJECT_ID, Cop.EQUALS, refObjectId);
    QueryResult<LogEntity> queryResult = LogAdapterBuilder.getLogAdapter(bizType).query(tenant, qd);
    return Response.success(queryResult);
  }
}
