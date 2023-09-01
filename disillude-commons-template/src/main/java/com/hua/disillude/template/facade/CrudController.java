/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： CrudController.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.facade;

import com.hua.aroma.commons.biz.query.Cop;
import com.hua.aroma.commons.biz.query.QueryDefinition;
import com.hua.aroma.commons.biz.query.QueryResult;
import com.hua.disillude.biz.access.AccessUser;
import com.hua.disillude.biz.jdbc.utils.QueryDefinitionBuilder;
import com.hua.disillude.mini.domain.CrudService;
import com.hua.disillude.mini.dto.VersionDTO;
import com.hua.disillude.mini.entity.TenantEntity;
import com.hua.disillude.mini.exception.BizServiceException;
import com.hua.disillude.mini.http.Response;
import com.hua.disillude.mini.http.request.GetRequest;
import com.hua.disillude.mini.http.request.QueryRequest;
import com.hua.disillude.mini.queries.QueryFactors;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Hedh
 * @since 1.0
 */
public abstract class CrudController<T extends TenantEntity> {

  protected QueryDefinitionBuilder qdBuilder;
  @Resource(name = AccessUser.BEAN_NAME)
  protected AccessUser accessUser;

  public CrudController() {
    try {
      Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      this.qdBuilder = new QueryDefinitionBuilder(Class.forName(((Class) type).getName() + "$Queries"));
    } catch (Exception e) {
      throw new RuntimeException("构建 QueryDefinitionBuilder 失败！");
    }
  }

  @ApiOperation(value = "获取", notes = "获取数据详情")
  @PostMapping(value = "get")
  public Response<T> get(
      @ApiParam(value = "租户标识") @PathVariable("tenant") String tenant, @Validated @ApiParam(value = "id对象") @RequestBody GetRequest request) {
    T target = this.getCrudService().get(tenant, request.getId(), request.toParts());
    return Response.success(target);
  }

  @ApiOperation(value = "删除", notes = "删除数据</br>" +
      "<ul>" +
      "<li>若id对应数据不存在，返回也是成功，因为结果与期望结果一致</li>" +
      "</ul>")
  @PostMapping(value = "delete")
  public Response<Void> delete(
      @ApiParam(value = "租户标识") @PathVariable("tenant") String tenant,
      @ApiParam(value = "id对象") @RequestBody VersionDTO dto) throws BizServiceException {
    this.getCrudService().delete(tenant, dto.getId(), dto.getVersion(), this.accessUser.getOperateInfo());
    return Response.success();
  }

  @ApiOperation(value = "分页查询", notes = "分页查询数据</br>" +
      "<ul>" +
      "<li>fetchParts 取值范围同 get </li>" +
      "</ul>")
  @PostMapping(value = "query")
  public Response<QueryResult<T>> query(
      @ApiParam(value = "租户标识") @PathVariable("tenant") String tenant,
      @ApiParam(value = "查询过滤器") @RequestBody QueryRequest request) {
    QueryDefinition qd = this.buildQd(tenant, request);
    qd.addByField(QueryFactors.TenantEntity.TENANT, Cop.EQUALS, tenant);
    QueryResult<T> queryResult = this.getCrudService().query(tenant, qd, request.toParts());
    return Response.success(queryResult);
  }

  protected QueryDefinition buildQd(String tenant, QueryRequest request) {
    assert tenant != null;
    return this.qdBuilder.build(request);
  }

  protected abstract CrudService<T> getCrudService();
}
