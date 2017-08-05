package com.asiainfo.billing.drquery.format.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class PercentFormatter extends AbstractNumberFormatter {
	private int fractionDigits;//精度
	
	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	protected NumberFormat getNumberFormat(Locale locale) {
		NumberFormat format = NumberFormat.getPercentInstance(locale);
		format.setMaximumFractionDigits(this.fractionDigits);
		format.setMinimumFractionDigits(this.fractionDigits);
		if (format instanceof DecimalFormat) {
			((DecimalFormat) format).setParseBigDecimal(true);
		}
		return format;
	}

}
