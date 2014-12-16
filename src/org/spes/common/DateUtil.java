package org.spes.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���ڹ�����
 * 
 * @author HeGang
 * 
 */
public class DateUtil {
	/**
	 * ���ڸ�ʽ
	 */
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	/**
	 * ʱ�����������ת��ΪDate���������ַ���
	 * 
	 * @param stamp
	 *            ʱ���
	 * @return �����ַ���
	 */
	public static String TimeStamp2Date(Timestamp stamp) {
		return format.format(new Date(stamp.getTime()));
	}
}
