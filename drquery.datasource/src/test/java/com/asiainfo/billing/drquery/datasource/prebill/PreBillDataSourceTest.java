/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drquery.datasource.prebill;

import com.asiainfo.billing.drquery.datasource.DataSourceException;
import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.model.ModelReader;
import java.util.List;
import java.util.Map;
import org.junit.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author tianyi
 */
public class PreBillDataSourceTest {

//    private PreBillDataSource instance = null;
//
//    public PreBillDataSourceTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }
//
//    @Before
//    public void setUp() {
//        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "app_prebill.xml" });
//        instance = (PreBillDataSource) appContext.getBean("prebillSource");
//        ModelReader reader = new ModelReader();
//        try {
//            instance.afterPropertiesSet();
//            //reader.loadModel();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of loadDR method, of class PreBillDataSource.
//     */
//    @Test
//    public void testLoadDR() {
//        System.out.println("loadDR");
//        DRQueryParameters param = new DRQueryParameters();
//        param.setBillType("ADDVALUE");
//        param.setBillId("4001175917");//1376113191 INBOSS /15800945481 MMS/15802170580
//        param.setFrom("20120601");
//        param.setThru("20120630");
//        param.setDestId("");
//	    param.setCommonQuery(true);
//        MetaModel metaModel = ModelReader.getMetaModels().get("ADDVALUE");
//        //List expResult = null;
//        List<Map<String, String>> result=null;
//		try {
//			result = instance.loadDR(param, metaModel);
//		} catch (DataSourceException e) {
//			e.printStackTrace();
//		}
//        for(Map<String, String> record : result) {
//            for(String key : record.keySet()) {
//                System.out.println("key=["+key+"]value=["+record.get(key) +"]");
//            }
//        }
//        //assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//    }
}
