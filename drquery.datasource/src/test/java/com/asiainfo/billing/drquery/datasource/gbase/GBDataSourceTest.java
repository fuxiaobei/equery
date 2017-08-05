package com.asiainfo.billing.drquery.datasource.gbase;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.datasource.DataSourceAdapter;
import com.asiainfo.billing.drquery.datasource.DataSourceException;
import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.model.ModelReader;

public class GBDataSourceTest {
//	DataSourceAdapter adapter = null;
//	DRQueryParameters queryParameters = null;
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
//				new String[] { "app_gbase.xml" });
//		adapter = (DataSourceAdapter) appContext.getBean("dataSourceAdapter");
//		ModelReader reader = new ModelReader();
//		try {
//			//reader.loadModel();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		queryParameters = new DRQueryParameters();
//		queryParameters.setBillType("IGPRS");
//		queryParameters.setMonth("201204");
//		queryParameters.setBillId("13501922374");
//		queryParameters.setFrom("20120401");
//		queryParameters.setThru("20120430");
//		queryParameters.setCommonQuery(true);// true:�����ֶβ�ѯ
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void test() {
//		BaseDataSource dataSource = adapter.getDataSource("gBDataSource");
//		MetaModel metaModel = ModelReader.getMetaModels().get("IGPRS");
//		List<Map<String, String>> ret=null;
//		try {
//			ret = dataSource.loadDR(queryParameters, metaModel);
//		} catch (DataSourceException e) {
//			e.printStackTrace();
//		}
//		System.out.println(ret.size());
//	}

}
