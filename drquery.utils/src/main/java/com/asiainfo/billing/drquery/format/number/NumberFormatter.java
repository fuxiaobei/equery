package com.asiainfo.billing.drquery.format.number;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.util.ClassUtils;

/**
 * 数字类型转换器
 * 
 * @author Rex Wong
 *
 * @version
 */
public class NumberFormatter extends AbstractNumberFormatter {
	private static final boolean roundingModeOnDecimalFormat =
			ClassUtils.hasMethod(DecimalFormat.class, "setRoundingMode", RoundingMode.class);
	private RoundingMode roundingMode;
	private String pattern;
    private String positivePrefix;
    private int fractionDigits;//精度
	public NumberFormatter() {
	}

	public NumberFormatter(String pattern) {
		this.pattern = pattern;
	}

	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

	public void setRoundingMode(RoundingMode roundingMode) {
		this.roundingMode = roundingMode;
	}

	/**
	 * 设置前缀，例如"￥"、"$"、"+"、"-",但是当设置格式（pattern）时，该项将失效
	 * 
	 * @param positivePrefix
	 */
	public void setPositivePrefix(String positivePrefix) {
		this.positivePrefix = positivePrefix;
	}

	/**
	 * 格式设定,例如:"##000.000#"
	 * @param pattern
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	public NumberFormat getNumberFormat(Locale locale) {
		NumberFormat format = NumberFormat.getInstance(locale);
		if (!(format instanceof DecimalFormat)) {
			if (this.pattern != null) {
				throw new IllegalStateException("Cannot support pattern for non-DecimalFormat: " + format);
			}
			return format;
		}
		DecimalFormat decimalFormat = (DecimalFormat) format;
		decimalFormat.setParseBigDecimal(true);
		format.setMaximumFractionDigits(this.fractionDigits);
		format.setMinimumFractionDigits(this.fractionDigits);
		if (this.roundingMode != null && roundingModeOnDecimalFormat) {
			format.setRoundingMode(this.roundingMode);
		}
		if(positivePrefix!=null){
			decimalFormat.setPositivePrefix(positivePrefix);
		}
		
		if (this.pattern != null) {
			decimalFormat.applyPattern(this.pattern);
		}
		return decimalFormat;
	}

}
