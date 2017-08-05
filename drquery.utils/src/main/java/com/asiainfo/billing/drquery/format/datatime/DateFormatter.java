package com.asiainfo.billing.drquery.format.datatime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date> {

	private String pattern;

	private int style = DateFormat.DEFAULT;

	private TimeZone timeZone;

	private boolean lenient = false;


	public DateFormatter() {
	}

	public DateFormatter(String pattern) {
		this.pattern = pattern;
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public void setLenient(boolean lenient) {
		this.lenient = lenient;
	}


	public String print(Date date, Locale locale) {
		return getDateFormat(locale).format(date);
	}

	public Date parse(String text, Locale locale) throws ParseException {
		return getDateFormat(locale).parse(text);
	}


	protected DateFormat getDateFormat(Locale locale) {
		DateFormat dateFormat;
		if (this.pattern != null) {
			dateFormat = new SimpleDateFormat(this.pattern, locale);
		}
		else {
			dateFormat = DateFormat.getDateInstance(this.style, locale);
		}
		if (this.timeZone != null) {
			dateFormat.setTimeZone(this.timeZone);
		}
		dateFormat.setLenient(this.lenient);
		return dateFormat;
	}

}
