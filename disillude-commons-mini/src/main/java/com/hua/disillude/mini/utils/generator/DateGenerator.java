/**
 * 版权所有(C)，华仔不脱发科技有限公司，2022，所有权利保留。
 * <p>
 * 项目名： disillude-parent
 * 文件名： DateGenerator.java
 * 模块说明：
 * 修改历史：
 * 2022年05月30日 - Hedh - 创建。
 */
package com.hua.disillude.mini.utils.generator;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author Hedh
 * @since 1.0
 */
public class DateGenerator extends DateUtils {

  /** 获取当前日期时间，格式：yyyy-MM-dd HH:mm:ss */
  public static String getCurrentDatetime() {
    return String.format("%tF %<tT", new Date());
  }

  /** 获取当前日期，格式：yyyy-MM-dd */
  public static String getCurrentDate() {
    return String.format("%tF", new Date());
  }

  /** 获取指定日期时间，格式：yyyy-MM-dd HH:mm:ss */
  public static String getDatetime(Date date) {
    return String.format("%tF %<tT", date);
  }

  /** 获取指定日期，格式：yyyy-MM-dd */
  public static String getDate(Date date) {
    return String.format("%tF", date);
  }

  /** 获取指定年份的随机日期 */
  public static Date randomDate(final int year) {
    Random random = new Random();
    int month = random.nextInt(12), day;
    switch (month) {
      case 0:
        month = Calendar.JANUARY + 1;
        break;
      case 1:
        month = Calendar.FEBRUARY + 1;
        break;
      case 2:
        month = Calendar.MARCH + 1;
        break;
      case 3:
        month = Calendar.APRIL + 1;
        break;
      case 4:
        month = Calendar.MAY + 1;
        break;
      case 5:
        month = Calendar.JUNE + 1;
        break;
      case 6:
        month = Calendar.JULY + 1;
        break;
      case 7:
        month = Calendar.AUGUST + 1;
        break;
      case 8:
        month = Calendar.SEPTEMBER + 1;
        break;
      case 9:
        month = Calendar.OCTOBER + 1;
        break;
      case 10:
        month = Calendar.NOVEMBER + 1;
        break;
      case 11:
        month = Calendar.DECEMBER + 1;
        break;
      default:
    }
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
      day = random.nextInt(31) + 1;
    } else if (month == 2 && year % 4 == 0) {
      day = random.nextInt(29) + 1;
    } else if (month == 2) {
      day = random.nextInt(28) + 1;
    } else {
      day = random.nextInt(30) + 1;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month, day);
    return calendar.getTime();
  }

  /** 获取指定年份，月份的随机日期 */
  public static Date randomDate(final int year, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(randomDate(year));
    while (calendar.get(Calendar.MONTH) != (month - 1)) {
      calendar.setTime(randomDate(year));
    }
    return calendar.getTime();
  }

  /** 获取指定年份的随机日期字符串 */
  public static String randomDateString(final int year) {
    return getDate(randomDate(year));
  }

  /** 获取指定年份，月份的随机日期字符串 */
  public static String randomDateString(final int year, int month) {
    return getDate(randomDate(year, month));
  }

  public static void main(String[] args) {
    System.out.println(randomDate(2022));
    System.out.println(randomDate(2022, 4));
    System.out.println(randomDateString(2022));
    System.out.println(randomDateString(2022, 4));
  }
}