/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： Schemas.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.biz.jdbc.schemas;

import com.hua.aroma.commons.biz.query.QueryField;
import com.hua.aroma.commons.jdbc.annotation.MapToProperty;
import com.hua.devops.ebt.annotation.Column;
import com.hua.disillude.mini.constant.LengthConst;

import java.util.Date;

/**
 * @author Hedh
 * @since 1.0
 */
public class Schemas {
  /**
   * 针对类{@link com.hua.disillude.mini.entity.Entity}的字段定义。
   */
  public static class Entity {
    /** ID */
    @QueryField
    @MapToProperty("id")
    @Column(name = Entity.ID, title = "数据标识", id = true, length = LengthConst.ID, generateSort = 1)
    public static final String ID = "id";
  }

  public interface HasVersion {
    @QueryField
    @Column(name = VersionEntity.VERSION, title = "版本号", fieldClass = Long.class, generateSort = Integer.MAX_VALUE - 40)
    String VERSION = "fversion";
    @QueryField
    @Column(name = VersionEntity.VERSION_TIME, title = "版本时间", fieldClass = Date.class, generateSort = Integer.MAX_VALUE - 40)
    String VERSION_TIME = "version_time";
  }

  public interface HasCreateInfo {
    @QueryField
    @Column(name = HasCreateInfo.CREATE_INFO_TIME, title = "创建时间", fieldClass = Date.class, generateSort = Integer.MAX_VALUE - 10)
    String CREATE_INFO_TIME = "create_info_time";
    @QueryField
    @Column(name = HasCreateInfo.CREATE_INFO_USER_ID, title = "创建人代码", length = LengthConst.ID, generateSort = Integer.MAX_VALUE - 10)
    String CREATE_INFO_USER_ID = "create_info_user_id";
    @QueryField
    @Column(name = HasCreateInfo.CREATE_INFO_USER_NAME, title = "创建人名称", length = LengthConst.NAME, generateSort = Integer.MAX_VALUE - 10)
    String CREATE_INFO_USER_NAME = "create_info_user_name";
  }

  public interface HasLastModifiedInfo {
    @QueryField
    @Column(name = HasLastModifiedInfo.LAST_MODIFIED_INFO_TIME, title = "最后修改时间", fieldClass = Date.class, generateSort = Integer.MAX_VALUE)
    String LAST_MODIFIED_INFO_TIME = "last_modified_info_time";
    @QueryField
    @Column(name = HasLastModifiedInfo.LAST_MODIFIED_INFO_USER_ID, title = "最后修改人代码", length = LengthConst.ID, generateSort = Integer.MAX_VALUE)
    String LAST_MODIFIED_INFO_USER_ID = "last_modified_info_user_id";
    @QueryField
    @Column(name = HasLastModifiedInfo.LAST_MODIFIED_INFO_USER_NAME, title = "最后修改人名称", length = LengthConst.NAME, generateSort = Integer.MAX_VALUE)
    String LAST_MODIFIED_INFO_USER_NAME = "last_modified_info_user_name";
  }

  public interface HasRemark {
    @QueryField
    @Column(name = HasRemark.REMARK, title = "备注说明", length = LengthConst.REMARK, generateSort = (Integer.MAX_VALUE >> 1) + 10)
    String REMARK = "remark";
  }

  public interface HasTenant {
    @QueryField
    @Column(name = HasTenant.TENANT, title = "租户标识", length = LengthConst.TENANT, generateSort = 2)
    String TENANT = "tenant";
  }

  public interface HasAppId {
    @QueryField
    @Column(name = HasAppId.APP_ID, title = "应用标识", length = LengthConst.ID, generateSort = 3)
    String APP_ID = "app_id";
  }

  public interface HasOrg {
    @QueryField
    @Column(name = HasOrg.ORG_ID, title = "组织标识", length = LengthConst.ID, generateSort = 5)
    String ORG_ID = "org_id";
  }

  public interface HasLog {
    @QueryField
    @Column(name = HasLog.REF_OBJECT_ID, title = "引用数据标识", length = LengthConst.ID, generateSort = 10)
    String REF_OBJECT_ID = "ref_object_id";
    @QueryField
    @Column(name = HasLog.ACTION, title = "操作动作", length = LengthConst.CODE, generateSort = 60)
    String ACTION = "action";
    @QueryField
    @Column(name = HasLog.ACTION_NAME, title = "动作名称", length = LengthConst.NAME, generateSort = 62)
    String ACTION_NAME = "action_name";
    @Column(name = HasLog.STATE, title = "状态", length = LengthConst.BIZ_STATE, generateSort = 80)
    String STATE = "state";

    @QueryField
    @Column(name = HasLog.OPERATE_INFO_TIME, title = "操作时间", fieldClass = Date.class, generateSort = Integer.MAX_VALUE - 10)
    String OPERATE_INFO_TIME = "operate_info_time";
    @QueryField
    @Column(name = HasLog.OPERATE_INFO_USER_ID, title = "操作人代码", length = LengthConst.ID, generateSort = Integer.MAX_VALUE - 10)
    String OPERATE_INFO_USER_ID = "operate_info_user_id";
    @QueryField
    @Column(name = HasLog.OPERATE_INFO_USER_NAME, title = "操作人名称", length = LengthConst.NAME, generateSort = Integer.MAX_VALUE - 10)
    String OPERATE_INFO_USER_NAME = "operate_info_user_name";
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.business.BusinessOrderEntity}的字段定义。
   */
  public static class BusinessOrderEntity extends Schemas.OrgStandardEntity {
    @Column(name = BusinessOrderEntity.BIZ_STATE, title = "业务状态", length = LengthConst.BIZ_STATE, generateSort = 3)
    public static final String BIZ_STATE = "biz_state";
  }

  public static class VersionEntity extends Schemas.Entity implements HasVersion {
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.StandardEntity}的字段定义。
   */
  public static class StandardEntity extends Schemas.VersionEntity implements HasCreateInfo, HasLastModifiedInfo, HasRemark {
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.TenantEntity}的字段定义。
   */
  public static class TenantEntity extends Schemas.Entity implements HasTenant {
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.TenantEntity}的字段定义。
   */
  public static class TenantStandardEntity extends Schemas.TenantEntity implements
      HasAppId, HasVersion, HasCreateInfo, HasLastModifiedInfo, HasRemark {
  }

  /**
   * 针对类{@link com.hua.disillude.mini.entity.OrgStandardEntity}的字段定义。
   */
  public static class OrgStandardEntity extends Schemas.TenantStandardEntity implements HasOrg {
  }

  /**
   * 日志对象
   */
  public static class LogEntity extends TenantEntity implements HasLog, HasAppId {
  }
}
