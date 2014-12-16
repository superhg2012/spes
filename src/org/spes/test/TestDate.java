package org.spes.test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TestDate {

	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Date date = calendar.getTime();
		System.out.println(date.getYear());

		int month = calendar.get(Calendar.MONTH);
		System.out.println(month + 1);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(days);//本月有多少天
		System.out.println(date.getDate());//本月24号
		System.out.println(date.getDay());//星期二
		
		
		System.out.println();
		Timestamp ts = new Timestamp(new Date().getTime());
		System.out.println(ts.getYear());
		System.out.println(ts.getMonth());
		
	}
}