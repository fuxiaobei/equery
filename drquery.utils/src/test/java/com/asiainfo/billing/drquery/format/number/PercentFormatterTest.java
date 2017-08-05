package com.asiainfo.billing.drquery.format.number;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PercentFormatterTest {
	private PercentFormatter formatter = new PercentFormatter();
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
	public void testPrint() {
		assertEquals("25%", formatter.print(new BigDecimal("0.25"), Locale.CHINA));
		assertEquals("25.1%", formatter.print(new BigDecimal(".251"), Locale.CHINA));
	}

	@Test
	public void testParse() {
		try {
			assertEquals(new BigDecimal(".2416"), formatter.parse("24.16%",Locale.CHINA));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
