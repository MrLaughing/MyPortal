/**============================================================
 * 版权： pss_team 版权所有 (c) 2015 - 2020
 * 包： com.myapp.util
 * 修改记录：
 * 日期                作者           内容
 * =============================================================
 * 2015年3月22日       pangshenshen        
 * ============================================================*/

package com.zai360.portal.test.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * <p>
 * 日期工具类
 * </p>
 *
 * <p>
 * Copyright: 版权所有 (c) 2015 - 2020<br>
 * Company: pss_team
 * </p>
 *
 * @author pangshenshen
 * @version 2015年3月22日
 */

public class DateUtil {

	/** 日期格式化--如：2015-03-22 12:31:50 */
	public static final String DATE_FOTMART_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 日期格式化--无时间 如：2015-03-22 */
	public static final String DATE_FOTMART_PATTERN_NO_TIME = "yyyy-MM-dd";

	/**
	 * 日期转字符串
	 *
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			if (StringUtils.isEmpty(pattern))
				pattern = DATE_FOTMART_PATTERN;
			return DateFormatUtils.format(date, pattern);
		}
		return "";
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DATE_FOTMART_PATTERN);
	}

	/**
	 * 只保留日期
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringNoTime(Date date) {
		return dateToString(date, DATE_FOTMART_PATTERN_NO_TIME);
	}

	/**
	 * 获得传入日期的最小时间
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getActualMinimumDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = DateUtils.toCalendar(date);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMinimum(Calendar.SECOND));
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获得当前传入的最大日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getActualMaximumDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = DateUtils.toCalendar(date);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE,
				calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND,
				calendar.getActualMaximum(Calendar.SECOND));
		date = calendar.getTime();
		return date;
	}
}
