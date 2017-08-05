package com.asiainfo.billing.drquery.datasource.ondemand;

public class OndemandDataSourceTest {

//    private OndemandDataSource instance = null;
//
//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownAfterClass() throws Exception {
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"app_ondemand.xml"});
//        instance = (OndemandDataSource) appContext.getBean("ondemandDataSource");
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    @Test
//    public void testLoadDR() {
//        System.out.println("java.library.path=[" + System.getProperty("java.library.path") + "]");
//        
//        ModelReader reader = new ModelReader();
//        try {
//            //reader.loadModel();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.assertFalse(true);
//        }
//        //testGSM();
//        //testSMS();
//        //testMMS();
//        //testVOIP();
//        //testWLAN();
//        testGPRS();
//        testGPRS();
//        testGPRS();
//        testGPRS();
//        testGPRS();
//
//    }
//    @Test
//    public void testBillType() {
//        DRQueryBusiTypes param = new DRQueryBusiTypes();
//        param.setBillMonth("201201");
//        param.setBillId("15711661147");
//        ModelReader reader = new ModelReader();
//        try {
//            //reader.loadModel();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.assertFalse(true);
//        }
//        try {
//            Map<String, String> ret = instance.queryTypes(param);
//            for (String key : ret.keySet()) {
//                System.out.println("key=[" + key + "]value=[" + ret.get(key) + "]");
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(OndemandDataSourceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    private void testGSM() {
//        DRQueryParameters param = new DRQueryParameters();
//        param.setFrom("20110530");
//        param.setMonth("201105");
//        param.setThru("20110531");
//        param.setBillId("13621912000");
//        param.setBillType("GSM_L");
//        param.setCommonQuery(true);//true:常用字段查询
//        MetaModel metaModel = ModelReader.getMetaModels().get("GSM_L");
//        try {
//            List<Map<String, String>> ret = instance.loadDR(param, metaModel);
//            for (Map<String, String> record : ret) {
//                for (String key : record.keySet()) {
//                    System.out.println("key=[" + key + "]value=[" + record.get(key) + "]");
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(OndemandDataSourceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    private void testSMS() {
//        DRQueryParameters param = new DRQueryParameters();
//        param.setFrom("20110701");
//        param.setMonth("201107");
//        param.setThru("20110731");
//        param.setBillId("13918617470");
//        param.setBillType("SMS");
//        param.setCommonQuery(true);//true:常用字段查询
//        MetaModel metaModel = ModelReader.getMetaModels().get("SMS");
//        try {
//            List<Map<String, String>> ret = instance.loadDR(param, metaModel);
//            for (Map<String, String> record : ret) {
//                for (String key : record.keySet()) {
//                    System.out.println("key=[" + key + "]value=[" + record.get(key) + "]");
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(OndemandDataSourceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    private void testMMS() {
//        DRQueryParameters param = new DRQueryParameters();
//        param.setFrom("20120401");
//        param.setMonth("201204");
//        param.setThru("20120430");
//        param.setBillId("13564736814");
//        param.setBillType("MMS_ZMZL");
//        param.setCommonQuery(true);//true:常用字段查询
//        MetaModel metaModel = ModelReader.getMetaModels().get("MMS_ZMZL");
//        try {
//            List<Map<String, String>> ret = instance.loadDR(param, metaModel);
//            for (Map<String, String> record : ret) {
//                for (String key : record.keySet()) {
//                    System.out.println("key=[" + key + "]value=[" + record.get(key) + "]");
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(OndemandDataSourceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    private void testVOIP() {
//        DRQueryParameters param = new DRQueryParameters();
//        param.setFrom("20120401");
//        param.setMonth("201204");
//        param.setThru("20120430");
//        param.setBillId("32814101");
//        param.setBillType("VOIP");
//        param.setCommonQuery(true);//true:常用字段查询
//        MetaModel metaModel = ModelReader.getMetaModels().get("VOIP");
//        try {
//            List<Map<String, String>> ret = instance.loadDR(param, metaModel);
//            for (Map<String, String> record : ret) {
//                for (String key : record.keySet()) {
//                    System.out.println("key=[" + key + "]value=[" + record.get(key) + "]");
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(OndemandDataSourceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    
//    private void testWLAN() {
//        DRQueryParameters param = new DRQueryParameters();
//        param.setFrom("20120401");
//        param.setMonth("201204");
//        param.setThru("20120430");
//        param.setBillId("13512113173");
//        param.setBillType("WLAN");
//        param.setCommonQuery(true);//true:常用字段查询
//        MetaModel metaModel = ModelReader.getMetaModels().get("WLAN");
//        try {
//            List<Map<String, String>> ret = instance.loadDR(param, metaModel);
//            for (Map<String, String> record : ret) {
//                for (String key : record.keySet()) {
//                    System.out.println("key=[" + key + "]value=[" + record.get(key) + "]");
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(OndemandDataSourceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    
//    private void testGPRS() {
//        DRQueryParameters param = new DRQueryParameters();
//        param.setFrom("20120401");
//        param.setMonth("201204");
//        param.setThru("20120430");
//        param.setBillId("13818903050");
//        param.setBillType("GPRS");
//        param.setCommonQuery(true);//true:常用字段查询
//        MetaModel metaModel = ModelReader.getMetaModels().get("GPRS");
//        try {
//            List<Map<String, String>> ret = instance.loadDR(param, metaModel);
//            for (Map<String, String> record : ret) {
//                for (String key : record.keySet()) {
//                    System.out.println("key=[" + key + "]value=[" + record.get(key) + "]");
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(OndemandDataSourceTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
