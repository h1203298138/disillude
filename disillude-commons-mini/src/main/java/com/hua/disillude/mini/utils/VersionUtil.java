/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： jxc-parent
 * 文件名： VersionUtil.java
 * 模块说明：
 * 修改历史：
 * 2022年01月31日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils;


import com.hua.aroma.commons.biz.entity.HasVersionInfo;
import com.hua.disillude.mini.exception.BizServiceException;
import com.hua.disillude.mini.i18n.LocaleMessage;
import com.hua.disillude.mini.i18n.R;

/**
 * @author Hedh
 * @since 1.0
 */
public class VersionUtil {
  public static void checkVersionConflict(HasVersionInfo entity, Long expectedVersion) throws BizServiceException {
    VersionUtil.checkVersionConflict(entity, expectedVersion, R.VERSION_CONFLICT);
  }

  public static void checkVersionConflict(HasVersionInfo entity, Long expectedVersion, String message, Object... params) throws BizServiceException {
    if (expectedVersion == null) {
      return;
    }
    if ((entity.getVersion() - expectedVersion) != 0) {
      throw new BizServiceException(LocaleMessage.$t(message, params));
    }
  }

  public static void checkAffectedRows(int affectedRows, Long expectedRows) throws BizServiceException {
    VersionUtil.checkAffectedRows(affectedRows, expectedRows, R.VERSION_CONFLICT);
  }

  public static void checkAffectedRows(int affectedRows, Long expectedRows, String message, Object... params) throws BizServiceException {
    if (expectedRows == null) {
      return;
    }
    if ((affectedRows - expectedRows) != 0) {
      throw new BizServiceException(LocaleMessage.$t(message, params));
    }
  }
}