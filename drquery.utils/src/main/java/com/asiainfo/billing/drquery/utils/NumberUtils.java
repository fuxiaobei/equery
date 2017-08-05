package com.asiainfo.billing.drquery.utils;

public class NumberUtils {

	public static double parseDouble(String str){
		double val = 0;
		if(str != null && !str.equals("")){
			val = Double.parseDouble(str);
		}
		return val;
	}
	
	public static long parseLong(String str){
		long val = 0;
		if(str != null && !str.equals("")){
			val = Long.parseLong(str);
		}
		return val;
	}
	
	public static int parseInt(String str){
		int val = 0;
		if(str != null && !"".equals(str)){
			val = Integer.parseInt(str);
		}
		return val;
	}
}
