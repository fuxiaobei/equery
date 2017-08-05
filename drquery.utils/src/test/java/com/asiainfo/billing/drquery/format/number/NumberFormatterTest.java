package com.asiainfo.billing.drquery.format.number;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NumberFormatterTest {
	private NumberFormatter formatter = new NumberFormatter();
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
		formatter.setFractionDigits(2);
		assertEquals("0.40", formatter.print(new BigDecimal("0.402"), Locale.CHINA));
	}

	@Test
	public void parseValue() throws ParseException {
		assertEquals(new BigDecimal("23.01"), formatter.parse("23.01", Locale.US));
	}
}
