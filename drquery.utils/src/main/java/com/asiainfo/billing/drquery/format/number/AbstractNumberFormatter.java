package com.asiainfo.billing.drquery.format.number;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.format.Formatter;

import com.asiainfo.billing.drquery.format.FormatterException;

public abstract class AbstractNumberFormatter implements Formatter<Number> {

	private boolean lenient = false;

	public void setLenient(boolean lenient) {
		this.lenient = lenient;
	}

	public String print(Number number, Locale locale) {
		return getNumberFormat(locale).format(number);
	}
	public String print(String strnumber, Locale locale) {
		if(!NumberUtils.isNumber(strnumber)){
			throw new FormatterException("To format value["+strnumber+"] is not Number type");
		}
		Number number = new BigDecimal(strnumber);
		return print(number, locale);
	}
	public Number parse(String text, Locale locale) throws ParseException {
		if(!NumberUtils.isNumber(text)){
			throw new FormatterException("To parse value["+text+"] is not Number type");
		}
		NumberFormat format = getNumberFormat(locale);
		ParsePosition position = new ParsePosition(0);
		Number number = format.parse(text, position);
		if (position.getErrorIndex() != -1) {
			throw new ParseException(text, position.getIndex());
		}
		if (!this.lenient) {
			if (text.length() != position.getIndex()) {
				// indicates a part of the string that was not parsed
				throw new ParseException(text, position.getIndex());
			}
		}
		return number;
	}

	protected abstract NumberFormat getNumberFormat(Locale locale);

}
