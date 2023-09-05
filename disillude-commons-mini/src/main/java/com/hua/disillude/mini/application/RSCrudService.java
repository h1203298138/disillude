package com.hua.disillude.mini.application;

import com.hua.aroma.commons.biz.query.QueryResult;
import com.hua.disillude.mini.dto.VersionDTO;
import com.hua.disillude.mini.entity.TenantEntity;
import com.hua.disillude.mini.exception.BizServiceException;
import com.hua.disillude.mini.http.Response;
import com.hua.disillude.mini.http.request.GetRequest;
import com.hua.disillude.mini.http.request.QueryRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface RSCrudService<T extends TenantEntity> {
  @ApiOperation(value = "获取", notes = "获取数据详情")
  @PostMapping(value = "get")
  Response<T> get(
      @ApiParam(value = "租户标识") @PathVariable("tenant") String tenant, @Validated @ApiParam(value = "id对象") @RequestBody GetRequest request);

  @ApiOperation(value = "删除", notes = "删除数据</br>" +
      "<ul>" +
      "<li>若id对应数据不存在，返回也是成功，因为结果与期望结果一致</li>" +
      "</ul>")
  @PostMapping(value = "delete")
  Response<Void> delete(
      @ApiParam(value = "租户标识") @PathVariable("tenant") String tenant,
      @ApiParam(value = "id对象") @RequestBody VersionDTO dto) throws BizServiceException;

  @ApiOperation(value = "分页查询", notes = "分页查询数据</br>" +
      "<ul>" +
      "<li>fetchParts 取值范围同 get </li>" +
      "</ul>")
  @PostMapping(value = "query")
  Response<QueryResult<T>> query(
      @ApiParam(value = "租户标识") @PathVariable("tenant") String tenant,
      @ApiParam(value = "查询过滤器") @RequestBody QueryRequest request);
}
