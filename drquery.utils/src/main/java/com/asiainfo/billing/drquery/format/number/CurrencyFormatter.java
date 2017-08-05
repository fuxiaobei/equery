
package com.asiainfo.billing.drquery.format.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;

import com.asiainfo.billing.drquery.format.FormatterException;

/**
 * 货币转换器
 * 
 * @author Rex Wong
 *
 * @version
 */
public class CurrencyFormatter extends AbstractNumberFormatter {

	private static final boolean roundingModeOnDecimalFormat =
			ClassUtils.hasMethod(DecimalFormat.class, "setRoundingMode", RoundingMode.class);

	private int fractionDigits = 2;//精度

	private RoundingMode roundingMode;

	private Currency currency;

	/**
	 * 设置精度
	 * 
	 * @param fractionDigits
	 */
	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}
	public void setRoundingMode(RoundingMode roundingMode) {
		this.roundingMode = roundingMode;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


	public BigDecimal parse(String text, Locale locale) throws ParseException {
		if(!NumberUtils.isNumber(text)){
			throw new FormatterException("To format value["+text+"] is not Number type");
		}
		BigDecimal decimal = (BigDecimal) super.parse(text, locale);
		if (decimal != null) {
			if (this.roundingMode != null) {
				decimal = decimal.setScale(this.fractionDigits, this.roundingMode);
			}
			else {
				decimal = decimal.setScale(this.fractionDigits);
			}
		}
		return decimal;
	}

	protected NumberFormat getNumberFormat(Locale locale) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
		format.setParseBigDecimal(true);
		format.setMaximumFractionDigits(this.fractionDigits);
		format.setMinimumFractionDigits(this.fractionDigits);
		if (this.roundingMode != null && roundingModeOnDecimalFormat) {
			format.setRoundingMode(this.roundingMode);
		}
		if (this.currency != null) {
			format.setCurrency(this.currency);
		}
		return format;
	}

}
