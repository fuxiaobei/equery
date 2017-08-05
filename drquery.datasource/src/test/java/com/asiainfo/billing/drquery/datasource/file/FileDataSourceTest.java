package com.asiainfo.billing.drquery.datasource.file;

import javax.activation.FileDataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asiainfo.billing.drquery.datasource.DataSourceAdapter;
import com.asiainfo.billing.drquery.datasource.DataSourceException;
import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;

public class FileDataSourceTest {
	DataSourceAdapter adapter = null;
	DRQueryParameters queryParameters = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "app_file.xml" });
		adapter = (DataSourceAdapter) appContext.getBean("dataSourceAdapter");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadDR() {
//		FileDataSource dataSource = adapter.getDataSource("fileDataSource");
//		try {
//			dataSource.loadDR(queryParameters, null);
//		} catch (DataSourceException e) {
//			e.printStackTrace();
//		} 
	}

}
