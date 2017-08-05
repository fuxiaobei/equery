package com.asiainfo.billing.drquery.client.prebill;

import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;

/**
 *
 * @author zhouquan3
 */
public class PreBillDRQueryClientTest {

    private final static Log log = LogFactory.getLog(PreBillDRQueryClientTest.class);

    public PreBillDRQueryClientTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of query_allCdrInfoDetail method, of class PreBillDRQueryClient.
     */
    
    @Test
    public void testISMG() {
        System.out.println("testISMG");
        int operateType = 0;
        String strBillId = "13402187693";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 4;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }

    @Test
    public void testKjava() {
        System.out.println("testKjava");
        int operateType = 0;
        String strBillId = "15801948919";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 36;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }

    @Test
    public void testWLAN() {
        System.out.println("testWLAN");
        int operateType = 0;
        String strBillId = "13512113173";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 18;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
   @Test
    public void testWLANXY() {
        System.out.println("testWLANXY");
        int operateType = 0;
        String strBillId = "13651918558";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 45;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testWLANJT() {
        System.out.println("testWLANJT");
        int operateType = 0;
        String strBillId = "15821700696";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 46;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }    
    
    @Test
    public void testWLANAUTO() {
        System.out.println("testWLANAUTO");
        int operateType = 0;
        String strBillId = "13472620632";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 56;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }      
    
    @Test
    public void testVGSM() {
        System.out.println("testVGSM");
        int operateType = 0;
        String strBillId = "15802197490";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 39;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
      //  log.debug(result);
    }

    @Test
    public void testSX() {
        System.out.println("testSX");
        int operateType = 0;
        String strBillId = "13701878660";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 30;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
//        log.debug(result);
    }
     @Test
    public void testSMS_MPAY() {
        System.out.println("testSMS_MPAY");
        int operateType = 0;
        String strBillId = "13402000804";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 43;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
//        log.debug(result);
    }

    @Test
    public void testYYZX() {
        System.out.println("testYYZX");
        int operateType = 0;
        String strBillId = "15901841290";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 20;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
       @Test
    public void testMMK() {
        System.out.println("testMMK");
        int operateType = 0;
        String strBillId = "15800789238";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 42;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }

    @Test
    public void testYXHD() {
        System.out.println("testYXHD");
        int operateType = 0;
        String strBillId = "15821326270";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 21;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }  

    @Test
    public void testMMS() {
        System.out.println("testMMS");
        int operateType = 0;
        String strBillId = "15800945481";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 11;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
//        log.debug(result);
    }   

    @Test
    public void testGSML() {
        System.out.println("testGSML");
        int operateType = 0;
        String strBillId = "13524854030";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 35;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    } 

     @Test
    public void testGSMR() {
        System.out.println("testGSMR");
        int operateType = 0;
        String strBillId = "13564326330";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 33;//33:GSM_R
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
//        log.debug(result);
    }     
     
     @Test
    public void testGSMT() {
        System.out.println("testGSMT");
        int operateType = 0;
        String strBillId = "13764537490";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 32;//32:GSM_T
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
//        log.debug(result);
    }      
     
      @Test
    public void testGSMHZ() {
        System.out.println("testGSMHZ");
        int operateType = 0;
        String strBillId = "13916125060";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 34;//32:GSM_HZ
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
//        log.debug(result);
    } 
      
      @Test
    public void testBIP() {
        System.out.println("testBIP");
        int operateType = 0;
        String strBillId = "13801693825";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 7;//32:GSM_HZ
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
//        log.debug(result);
    } 
     
    @Test
    public void testSMS() {
        System.out.println("testSMS");
        int operateType = 0;
        String strBillId = "13585994430";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 2;//2:短信
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
//        log.debug(result);
    }       
    
    @Test
    public void testGPRS() {
        System.out.println("testGPRS");
        int operateType = 0;
        String strBillId = "13818903050";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 3;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
        @Test
    public void testIGPRS() {
        System.out.println("testIGPRS");
        int operateType = 0;
        String strBillId = "13501922374";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 19;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testWAP() {
        System.out.println("testWAP");
        int operateType = 0;
        String strBillId = "13764488629";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 10;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }

    @Test
    public void testMMSZMZL() {
        System.out.println("testMMSZMZL");
        int operateType = 0;
        String strBillId = "13564736814";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 27;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testGAMEGP() {
        System.out.println("testGAMEGP");
        int operateType = 0;
        String strBillId = "13482541090";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 28;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testGAMEDKXF() {
        System.out.println("testGAMEDKXF");
        int operateType = 0;
        String strBillId = "13472598660";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 29;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testLMT() {
        System.out.println("testLMT");
        int operateType = 0;
        String strBillId = "13524077347";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 24;//35:GSM_L 4:ISMG
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
     @Test
    public void testVPMN() {
        System.out.println("testVPMN");
        int operateType = 0;
        String strBillId = "13501867232";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 6;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testINBOSS() {
        System.out.println("testINBOSS");
        int operateType = 0;
        String strBillId = "13761131910";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 23;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testLGSM() {
        System.out.println("testLGSM");
        int operateType = 0;
        String strBillId = "13524060891";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 44;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }

    @Test
    public void testHGPRS() {
        System.out.println("testHGPRS");
        int operateType = 0;
        String strBillId = "13901785643";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 41;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testSMSZMZL() {
        System.out.println("testSMSZMZL");
        int operateType = 0;
        String strBillId = "13818759357";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 26;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }
    
    @Test
    public void testWLANIO() {
        System.out.println("testWLANIO");
        int operateType = 0;
        String strBillId = "13917520505";
        String strBeginDate = "2012-06-01";
        String strEndDate = "2012-06-28";
        String strOppBillId = "";
        int nCdrType = 48;
        String cErrorMsg = "";
        PreBillDRQueryClient instance = new PreBillDRQueryClient();
        Map expResult = null;
        Map result = instance.query_allCdrInfoDetail(operateType, strBillId, strBeginDate, strEndDate, strOppBillId, nCdrType, cErrorMsg);
        showResult(result);
    }


    private void showResult(Map result) {
        System.out.println("title");
        List title = (List) result.get("listCaption");
        int length = title.size();
        for (int j = 0; j < title.size(); j++) {
            System.out.print("[" + title.get(j) + "]");
            System.out.print("\t");
        }
        System.out.println("");
        System.out.println("title");
        List body = (List) result.get("listBody");
        for (int i = 0; i < body.size(); i++) {
            List line = (List) body.get(i);
            for (int j = 0; j < line.size(); j++) {

                System.out.print("[" + line.get(j) + "]");
                System.out.print("\t");
            }
            System.out.println("");
            
            if (line.size() != length) {
                Assert.fail("title与body的长度不一致");
            }
        }
        System.out.println("tail");
        List tail = (List) result.get("listTail");
        for (int j = 0; j < tail.size(); j++) {
            System.out.print("[" + tail.get(j) + "]");
            System.out.print("\t");
        }
        System.out.println("");
        
        if (tail.size() != length) {
            Assert.fail("title与tail的长度不一致");
        }

        //listSpecialSum
        System.out.println("stat");
        List stat = (List) result.get("listSpecialSum");
        for (int j = 0; j < stat.size(); j++) {
            System.out.print("[" + stat.get(j) + "]");
            System.out.print("\n");
        }
    }
}
