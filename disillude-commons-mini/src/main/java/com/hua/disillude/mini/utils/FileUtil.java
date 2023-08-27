/**
 * 版权所有(C)，华仔不脱发科技有限公司，2021，所有权利保留。
 * <p>
 * 项目名： bmw
 * 文件名： FileUtil.java
 * 模块说明：
 * 修改历史：
 * 2021年07月16日 - 简单就好 - 创建。
 */
package com.hua.disillude.mini.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Hedh
 * @since 1.0
 */
public class FileUtil {
  public static final String[] IMG_FILE = {"bmp", "jpg", "png", "tif", "gif", "jpeg"};
  public static final String[] DOC_FILE = {"doc", "docx", "ppt", "pptx", "xls", "xlsx", "txt", "hlp", "wps", "rtf", "html", "pdf"};
  public static final String[] VIDEO_FILE = {"avi", "mp4", "mpg", "mov", "swf"};
  public static final String[] MUSIC_FILE = {"wav", "aif", "au", "mp3", "ram", "wma", "mmf", "amr", "aac", "flac"};
  public static final int IMAGE_TYPE = 1;
  public static final int DOC_TYPE = 2;
  public static final int VIDEO_TYPE = 3;
  public static final int MUSIC_TYPE = 4;
  public static final int OTHER_TYPE = 5;
  public static final int SHARE_FILE = 6;
  public static final int RECYCLE_FILE = 7;

  /**
   * 判断是否为图片文件
   *
   * @param extendName
   *     文件扩展名
   * @return 是否为图片文件
   */
  public static boolean isImageFile(String extendName) {
    return ArrayUtils.contains(IMG_FILE, extendName.toLowerCase());
  }

  /**
   * 获取文件扩展名
   *
   * @param fileName
   *     文件名
   * @return 文件扩展名
   */
  public static String getFileExtendName(String fileName) {
    if (fileName.lastIndexOf(".") == -1) {
      return "";
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  /**
   * 获取不包含扩展名的文件名
   *
   * @param fileName
   *     文件名
   * @return 文件名（不带扩展名）
   */
  public static String getFileNameNotExtend(String fileName) {
    String fileType = getFileExtendName(fileName);
    return fileName.replace("." + fileType, "");
  }
}