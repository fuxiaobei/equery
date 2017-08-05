package com.asiainfo.billing.drquery.format.number;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import net.sf.json.JSONObject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TimeFormatterTest {
	private TimeFormatter formatter = new TimeFormatter();

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
		assertEquals("01:01:11", formatter.getNumberFormat(3671));
		String str = "{\"replyDisInfo\":null,\"result\":0,\"resMsg\":null}";
		 JSONObject json = JSONObject.fromObject(str);
		 Object r = json.get("replyDisInfo");
		 if(!(r instanceof TimeFormatterTest)){
			 
		 }
		 System.out.println(r.getClass());
	}

}
