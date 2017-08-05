package com.asiainfo.billing.drquery.datasource.gbase;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.model.ModelReader;

/**
 * 
 * @author Administrator
 */
public class GBDataSourceMutiThreadTest extends TestCase {

//	GBDataSourceMutiThread dataSource = null;
//	DRQueryParameters queryParameters = null;
//
//	public GBDataSourceMutiThreadTest(String testName) {
//		super(testName);
//	}
//
//	@Override
//	protected void setUp() throws Exception {
//		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
//				new String[] { "app_gbase.xml" });
//		dataSource = (GBDataSourceMutiThread) appContext.getBean("gBDataSourceMutiThread");
//
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
//		queryParameters.setCommonQuery(true);// 
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
//
//	/**
//	 * Test of loadDR method, of class GBDataSource.
//	 */
//	public void testLoadDR() {
//		System.out.println("loadDR");
//		if (ModelReader.getMetaModels() == null) {
//			return;
//		}
//		MetaModel metaModel = ModelReader.getMetaModels().get("IGPRS");
//		List<Map<String, String>> ret = dataSource.loadDR(queryParameters,
//				metaModel);
//		System.out.println(ret.size());
//		// Assert.isTrue(ret.size() == 20);
//		// Assert.notEmpty(ret);
//	}
}
