package com.example.learn.tool;

import java.util.Calendar;

public class MyTime {
	//获取本周一0点时间点，单位毫秒
	public static long getTimesWeekmorning(){ 
		Calendar cal = Calendar.getInstance(); 
		cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0); 
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
		return  cal.getTimeInMillis(); 
		}
}
