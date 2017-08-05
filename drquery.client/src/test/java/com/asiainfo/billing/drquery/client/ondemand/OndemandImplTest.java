/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drquery.client.ondemand;

import com.asiainfo.billing.drquery.client.ondemand.OndemandImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class OndemandImplTest {
    
    public OndemandImplTest() {
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
     * Test of get_billId method, of class OndemandImpl.
     */
    @Test
    public void testGet_billId() {
        System.out.println("get_billId");
        long llDomainId = 0L;
        long llChannelId = 0L;
        String strBillId = "13524854030";
        String strStartDate = "20120601";
        String strStopDate = "20120630";
        String strBillType = "";
        List<String>  fieldSeq = new ArrayList<String>();
        List<String>  billIdSeq = new ArrayList<String>();
        String sErrMsg = "";
        OndemandImpl instance = new OndemandImpl();
        
        int result = instance.get_billId(llDomainId, llChannelId, strBillId, strStartDate, strStopDate, strBillType, fieldSeq, billIdSeq, sErrMsg);
        
//        if(billIdSeq != null){
        for(int i=0;i<billIdSeq.size();i++){
            System.out.println(billIdSeq.get(i));
        }
    }

    /**
     * Test of get_odDocDetails method, of class OndemandImpl.
     */
    @Test
    public void testGet_odDocDetails() {
        System.out.println("get_odDocDetails");
        long llOperId = 0L;
        long llOrgId = 0L;
        long llDomainId = 0L;
        long llChannelId = 0L;
        String strClientIp = "";
        String strFolderName = "";
        String strBillId = "13524854030";
        String strStartDate = "20120601";
        String strStopDate = "20120630";
        String strBillType = "GSM_L";
        long llOperaterType = 0L;
        long llStartLine = 1L;
        long llStopLine = 20L;
        String strFilterField = "";
        String strFilterValue = "";
        long llRecordCount = 0L;
        List<String>  seqCaption = new ArrayList<String>();
        List<List<String>> seqBody = new ArrayList<List<String>>();
        List<String>  seqTail = new ArrayList<String>();
        String sErrMsg = "";
        
        OndemandImpl instance = new OndemandImpl();
        int expResult = 0;
        int result = instance.get_odDocDetails(llOperId, llOrgId, llDomainId, llChannelId, strClientIp, strFolderName, strBillId, strStartDate, strStopDate, strBillType, llOperaterType, llStartLine, llStopLine, strFilterField, strFilterValue, llRecordCount, seqCaption, seqBody, seqTail, sErrMsg);
        
        if(seqCaption != null){
        for(int i=0;i<seqCaption.size();i++){
            System.out.println(seqCaption.get(i));
        }}   
        
        if(seqBody != null){
        for(int i=0;i<seqBody.size();i++){
            for(int j=0;j<seqBody.get(i).size();j++){
            System.out.println(seqBody.get(i).get(j));}         
        }}         

        if(seqTail != null){
        for(int i=0;i<seqTail.size();i++){
            System.out.println(seqTail.get(i));
        }}         
        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
