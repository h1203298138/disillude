/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： hua-project
 * 文件名： CrudServiceImpl.java
 * 模块说明：
 * 修改历史：
 * 2023年09月02日 - Hedh - 创建。
 */
package com.hua.disillude.template.domain.service;

import com.hua.aroma.commons.biz.query.QueryDefinition;
import com.hua.aroma.commons.biz.query.QueryResult;
import com.hua.disillude.biz.i18n.R;
import com.hua.disillude.biz.jdbc.utils.QueryDefinitionBuilder;
import com.hua.disillude.biz.spring.ServiceFactory;
import com.hua.disillude.mini.datamodel.OperateInfo;
import com.hua.disillude.mini.domain.CrudService;
import com.hua.disillude.mini.entity.TenantEntity;
import com.hua.disillude.mini.exception.BizServiceException;
import com.hua.disillude.starter.redis.LockContent;
import com.hua.disillude.starter.redis.LockPolicy;
import com.hua.disillude.starter.redis.RedisDistributedLocker;
import com.hua.disillude.template.domain.repository.CrudRepository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.hua.disillude.biz.i18n.LocaleMessage.$t;

/**
 * @author Hedh
 * @since 1.0
 */
public abstract class CrudServiceImpl<T extends TenantEntity> implements CrudService<T> {

  private Class typeClass;

  protected QueryDefinitionBuilder qdBuilder;

  public CrudServiceImpl() {
    try {
      CrudServiceEntity curdEntity = this.getClass().getAnnotation(CrudServiceEntity.class);
      if (curdEntity != null) {
        this.typeClass = curdEntity.value();
      } else {
        Type[] actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        if (actualTypeArguments.length > 0) {
          this.typeClass = (Class) actualTypeArguments[0];
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("必须设置注解@CrudServiceEntity");
    }

    try {
      this.qdBuilder = new QueryDefinitionBuilder(Class.forName((this.typeClass.getName() + "$Queries")));
    } catch (Exception e) {
      throw new RuntimeException("构建 QueryDefinitionBuilder 失败！");
    }
  }

  @Override
  public T get(String tenant, String id, String... parts) {
    return this.getCrudRepository().get(tenant, id, parts);
  }

  @Override
  public T safeGet(String tenant, String id, String... parts) throws BizServiceException {
    T target = this.getCrudRepository().get(tenant, id, parts);
    if (target == null) {
      throw new BizServiceException($t(R.NOT_EXISTS, $t(this.typeClass.getSimpleName()), id));
    }
    return target;
  }

  @Override
  public void delete(String tenant, String id, Long version, OperateInfo operateInfo) throws BizServiceException {
    T target = this.getCrudRepository().get(tenant, id);
    if (target != null) {
      this.getCrudRepository().delete(tenant, target);
    }
  }

  @Override
  public QueryResult<T> query(String tenant, QueryDefinition qd, String... parts) {
    return this.getCrudRepository().query(tenant, qd, parts);
  }

  public LockContent lock(String lockKey, LockPolicy policy) throws BizServiceException {
    return this.getDistributedLocker().lock(lockKey, policy);
  }

  public LockContent lock(String lockKey, String lockValue, LockPolicy policy) throws BizServiceException {
    return this.getDistributedLocker().lock(lockKey, lockValue, policy);
  }

  protected RedisDistributedLocker getDistributedLocker() {
    return ServiceFactory.getService(RedisDistributedLocker.class);
  }

  protected abstract CrudRepository<T> getCrudRepository();
}
