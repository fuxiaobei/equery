package com.asiainfo.billing.drescaping.file;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author zhouquan3
 */
public class FileUtilTest {
    private final static Log log = LogFactory.getLog(FileUtilTest.class);
    
    public FileUtilTest() {
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
     * Test of putMap2File method, of class FileUtil.
     */
    @Test
    public void testPutMap2File() {
        System.out.println("putMap2File");
        String fileName = "logs/testMap";
        Map map = new HashMap();
        map.put("testKey", "testValue");
        FileUtil instance = new FileUtil();
        instance.putMap2File(fileName, map);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getMapFromFile method, of class FileUtil.
     */
    @Test
    public void testGetMapFromFile() {
        System.out.println("getMapFromFile");
        String fileName = "logs/testMap";
        FileUtil instance = new FileUtil();
//        Map expResult = null;
        Map result = instance.getMapFromFile(fileName);
        log.debug(result);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}
