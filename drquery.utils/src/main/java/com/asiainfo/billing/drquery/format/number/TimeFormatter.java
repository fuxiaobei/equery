package com.asiainfo.billing.drquery.format.number;

import java.util.Locale;

/**
 * @author Rex Wong
 *
 * @version
 */
public class TimeFormatter {
	private static final NumberFormatter formatter = new NumberFormatter("##00");
	
	public String getNumberFormat(Number sec) {
		long longsec = sec.longValue();
		long hour = (longsec / (60 * 60));
		long min = ((longsec /60 ) - hour * 60);
		long s = (longsec  - hour * 60 * 60 - min * 60);
		
		StringBuilder text = new StringBuilder(formatter.print(hour, Locale.CHINA));
		text.append(":").append(formatter.print(min, Locale.CHINA));
		text.append(":").append(formatter.print(s, Locale.CHINA));
		return text.toString();
	}

}
