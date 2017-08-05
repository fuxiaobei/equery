package com.asiainfo.billing.drquery.client.rest;

import static org.junit.Assert.fail;

import java.util.Properties;

import net.sf.json.JSONObject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.asiainfo.billing.drquery.client.impl.DRQueryClientImpl;
import com.asiainfo.billing.drquery.utils.JsonUtils;

public class DRQueryClientImplTest {

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
	public void testCommonQuery() {
		DRQueryClientImpl impl = new DRQueryClientImpl();
		String strJson = "{\"billId\":\"13402187693\",\"billMonth\":\"201206\",\"fromDate\":\"20120601\",\"thruDate\":\"20120627\",\"busiType\":\"ISMG\"}";
		JSONObject jsonParam = JsonUtils.string2JsonObject(strJson);
		JSONObject ret = impl.commonQuery(jsonParam);
		BaseResponse target = new BaseResponse();
		try {
			JsonUtils.buildJsonObj2Object(ret, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(ret.toString());
	}

	@Test
	public void testBuisTypeQuery() {
		DRQueryClientImpl impl = new DRQueryClientImpl();
		String strJson = "{\"billId\":\"13402187693\",\"billMonth\":\"201206\",\"fromDate\":\"20120601\",\"thruDate\":\"20120627\"}";
		JSONObject jsonParam = JsonUtils.string2JsonObject(strJson);
		JSONObject ret = impl.buisTypeQuery(jsonParam);
		BaseResponse target = new BaseResponse();
		try {
			JsonUtils.buildJsonObj2Object(ret, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(ret.toString());
	}

	@Test
	public void testCheckQuery() {
		fail("Not yet implemented");
	}

}
