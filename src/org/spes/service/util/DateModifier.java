package org.spes.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateModifier {
	
	public static List<String> ModifyDateByQuarter(String from, String to) throws ParseException{
		List<String> fromAndTo = new ArrayList<String>();
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dFrom = sdf.parse(from);
		Date dTo = sdf.parse(to);
		calFrom.setTime(dFrom);
		calTo.setTime(dTo);
		int monthFrom = calFrom.get(Calendar.MONTH);//从0开始，所以在下面需要做调整
		int monthTo = calTo.get(Calendar.MONTH);
		//int quarterFrom = monthFrom/3 + 1;
		int numInQuarterFrom = monthFrom % 3;
		calFrom.add(Calendar.MONTH, -numInQuarterFrom);
		calFrom.set(Calendar.DATE, 1);
		//int quarterTo = monthTo / 3 + 1;
		int numInQuarterTo = monthTo % 3;
		calTo.add(Calendar.MONTH, +(2-numInQuarterTo));
		int maxDayOfMonth = calTo.getActualMaximum(Calendar.DAY_OF_MONTH);
		calTo.set(Calendar.DATE, maxDayOfMonth);
		fromAndTo.add(sdf.format(calFrom.getTime()));
		fromAndTo.add(sdf.format(calTo.getTime()));
		calFrom = null;
		calTo = null;
		sdf = null;
		return fromAndTo;
	}
	
	public static List<String> ModifyDateByYear(String from,String to) throws ParseException{
		List<String> fromAndTo = new ArrayList<String>();
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calFrom.setTime(sdf.parse(from));
		calTo.setTime(sdf.parse(to));
		int yearFrom = calFrom.get(Calendar.YEAR);
		int yearTo = calTo.get(Calendar.YEAR);
		calFrom.clear();
		calTo.clear();
		calFrom.set(yearFrom, 0, 1);
		calTo.set(yearTo, 11, 31);
		fromAndTo.add(sdf.format(calFrom.getTime()));
		fromAndTo.add(sdf.format(calTo.getTime()));
		calFrom = null;
		calTo = null;
		sdf = null;
		return fromAndTo;
	}
	
	public static List<String> ModifyDateByMonth(String from,String to){
		List<String> fromAndTo = new ArrayList<String>();
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			calFrom.setTime(sdf.parse(from));
			calTo.setTime(sdf.parse(to));
			calFrom.set(Calendar.DATE, 1);
			int dayOfMonth = calTo.getActualMaximum(Calendar.DAY_OF_MONTH);
			calTo.set(Calendar.DATE, dayOfMonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fromAndTo.add(sdf.format(calFrom.getTime()));
		fromAndTo.add(sdf.format(calTo.getTime()));
		calFrom = null;
		calTo = null;
		sdf = null;
		return fromAndTo;
	}
}
