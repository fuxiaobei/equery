package com.asiainfo.billing.drquery.format.number;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CurrencyFormatterTest {
	private CurrencyFormatter formatter = new CurrencyFormatter();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void formatValue() {
		formatter.setCurrency(Currency.getInstance(Locale.CHINA));
		formatter.setFractionDigits(2);
		System.out.print(formatter.print(new BigDecimal("1223.146"), Locale.CHINA));
	}

	
}
