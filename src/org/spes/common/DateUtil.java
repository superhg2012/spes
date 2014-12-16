package org.spes.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author HeGang
 * 
 */
public class DateUtil {
	/**
	 * 日期格式
	 */
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	/**
	 * 时间戳日期类型转换为Date类型日期字符串
	 * 
	 * @param stamp
	 *            时间戳
	 * @return 日期字符串
	 */
	public static String TimeStamp2Date(Timestamp stamp) {
		return format.format(new Date(stamp.getTime()));
	}
}
