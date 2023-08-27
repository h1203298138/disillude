/**
 * 版权所有(C)，华子不脱发科技有限公司，2023，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： AccessUser.java
 * 模块说明：
 * 修改历史：
 * 2023年08月27日 - Hedh - 创建。
 */
package com.hua.disillude.biz.access;

import com.hua.disillude.mini.datamodel.OperateInfo;
import com.hua.disillude.mini.entity.ICN;
import com.hua.disillude.mini.entity.hasinfo.HasAppId;
import com.hua.disillude.mini.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 用户操作上下文
 *
 * @author Hedh
 * @since 1.0
 */
@Data
@ApiModel(value = "用户操作上下文")
public class AccessUser {

  public static final String BEAN_NAME = "accessUser";

  /** 租户 */
  private String tenant;
  /** 应用标识 */
  private String appId;
  /** 组织标识 */
  private String orgId;
  /** 组织名称 */
  private String orgName;
  /** 登录用户标识 */
  private String userId;
  /** 登录账号 */
  private String userCode;
  /** 登录用户名 */
  private String userName;
  /** 使用的语言 */
  private String lang = "zh-CN";

  public ICN getUser() {
    return new ICN(this.userId, this.userCode, this.userName);
  }

  public OperateInfo getOperateInfo() {
    OperateInfo operateInfo = new OperateInfo();
    operateInfo.setTime(Date.from(LocalDateTime.now().withNano(0).atZone(ZoneId.systemDefault()).toInstant()));
    operateInfo.setOrgId(this.orgId);
    operateInfo.setOrgName(this.orgName);
    operateInfo.setUserId(this.userCode);
    operateInfo.setUserName(this.userName);
    return operateInfo;
  }

  public void applyAppId(Object o) {
    if (o instanceof HasAppId && StringUtil.isNullOrBlank(((HasAppId) o).getAppId())) {
      ((HasAppId) o).setAppId(this.appId);
    }
  }

  public void applyHeaders(HttpHeaders headers) {
    headers.set("tenant", this.encodeTenant());
    headers.set("appId", this.encodeAppId());
    headers.set("orgId", this.encodeOrgId());
    headers.set("orgName", this.encodeOrgName());
    headers.set("userId", this.encodeUserId());
    headers.set("userCode", this.encodeUserCode());
    headers.set("userName", this.encodeUserName());
    headers.set("lang", this.lang);
  }

  public String encodeTenant() {
    return encode(this.tenant);
  }

  public String encodeAppId() {
    return encode(this.appId);
  }

  public String encodeOrgId() {
    return encode(this.orgId);
  }

  public String encodeOrgName() {
    return encode(this.orgName);
  }

  public String encodeUserId() {
    return encode(this.userId);
  }

  public String encodeUserCode() {
    return encode(this.userCode);
  }

  public String encodeUserName() {
    return encode(this.userName);
  }

  private static String encode(String str) {
    if (str == null) {
      return null;
    }
    return URLEncoder.encode(str, StandardCharsets.UTF_8);
  }
}
