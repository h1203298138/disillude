/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： QueryFactors.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.mini.queries;

import com.hua.aroma.commons.biz.query.QueryFactorName;
import com.hua.aroma.commons.biz.query.QueryField;
import com.hua.disillude.mini.entity.business.BusinessOrderEntity;

import java.util.Date;

/**
 * @author Hedh
 * @since 1.0
 */
public class QueryFactors {

  /**
   * 针对接口{@link com.hua.disillude.mini.entity.IdentifiedDomainObject}的查询要素定义。
   */
  public interface HasId {

    @QueryField()
    String ID = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.IdentifiedDomainObject.class)
        .nameOf("id");
  }

  /**
   * 针对接口{@link com.hua.disillude.mini.entity.TenantEntity}的查询要素定义。
   */
  public interface HasTenant {

    @QueryField()
    String TENANT = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.TenantEntity.class)
        .nameOf("tenant");
  }

  public interface HasAppId {

    @QueryField()
    String APP_ID = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.TenantStandardEntity.class)
        .nameOf("appId");
  }

  /**
   * 针对接口{@link com.hua.disillude.mini.entity.TenantEntity}的查询要素定义。
   */
  public interface HasOrgId {
    @QueryField
    String ORG_ID = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.OrgStandardEntity.class)
        .nameOf("orgId");
  }

  public interface IsEntity extends HasId {
    // Do Nothing
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.Entity}的查询要素定义。
   */
  public static abstract class Entity implements IsEntity {
    // Do Nothing
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.Entity}的查询要素定义。
   */
  public static abstract class TenantEntity extends Entity implements HasTenant {
    // Do Nothing
  }

  public interface HasCreateInfo {
    @QueryField(fieldType = Date.class)
    String CREATE_INFO_TIME = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.StandardEntity.class)
        .nameOf("createInfo.time");
    @QueryField
    String CREATE_INFO_USER_ID = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.StandardEntity.class)
        .nameOf("createInfo.userId");
    @QueryField
    String CREATE_INFO_USER_NAME = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.StandardEntity.class)
        .nameOf("createInfo.userName");
  }

  public interface HasLastModifiedInfo {
    @QueryField(fieldType = Date.class)
    String LAST_MODIFIED_INFO_TIME = QueryFactorName
        .prefix(StandardEntity.class)
        .nameOf("lastModifiedInfo.time");
    @QueryField
    String LAST_MODIFIED_INFO_USER_ID = QueryFactorName
        .prefix(StandardEntity.class)
        .nameOf("lastModifiedInfo.userId");
    @QueryField
    String LAST_MODIFIED_INFO_USER_NAME = QueryFactorName
        .prefix(StandardEntity.class)
        .nameOf("lastModifiedInfo.userName");
  }

  public interface IsStandardEntity extends IsEntity, HasCreateInfo, HasLastModifiedInfo {
    @QueryField
    String REMARK = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.StandardEntity.class)
        .nameOf("remark");
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.StandardEntity}的查询要素定义。
   */
  public static abstract class StandardEntity implements IsStandardEntity {
    // Do Nothing
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.TenantStandardEntity}的查询要素定义。
   */
  public static abstract class TenantStandardEntity implements IsStandardEntity, HasTenant, HasAppId {
    // Do Nothing
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.OrgStandardEntity}的查询要素定义。
   */
  public static abstract class OrgStandardEntity implements IsStandardEntity, HasTenant, HasAppId, HasOrgId {
    // Do Nothing
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.business.HasBizState}的查询要素定义。
   */
  public interface HasBizStateEntity {
    @QueryField
    String BIZ_STATE = QueryFactorName
        .prefix(BusinessOrderEntity.class)
        .nameOf("bizState");
  }

  public interface HasLog {
    @QueryField()
    String REF_OBJECT_ID = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.LogEntity.class)
        .nameOf("refObjectId");
    @QueryField()
    String ACTION = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.LogEntity.class)
        .nameOf("action");
    @QueryField(fieldType = Date.class)
    String CREATE_INFO_TIME = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.LogEntity.class)
        .nameOf("operateInfo.time");
    @QueryField
    String CREATE_INFO_USER_ID = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.LogEntity.class)
        .nameOf("operateInfo.userId");
    @QueryField
    String CREATE_INFO_USER_NAME = QueryFactorName
        .prefix(com.hua.disillude.mini.entity.LogEntity.class)
        .nameOf("operateInfo.userName");

  }

  public static abstract class LogEntity implements HasLog, HasTenant, IsEntity {
    // Do Nothing
  }
}
