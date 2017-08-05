/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drescaping.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * @author zhouquan3
 * May 10
 */
public class RedisUtilTest extends TestCase {
    private final static Log log = LogFactory.getLog(RedisUtilTest.class);
    private RedisUtil redisUtil;
    
    public RedisUtilTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "spring/applicationContext.xml"});
    	redisUtil = appContext.getBean("redisUtil", RedisUtil.class);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of setRedisTemplate method, of class RedisUtil.
     */
    public void testSetRedisTemplate() {
        System.out.println("setRedisTemplate");
        RedisTemplate<String, Object> redisTemplate = null;
        RedisUtil instance = new RedisUtil();
        instance.setRedisTemplate(redisTemplate);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setTimeout method, of class RedisUtil.
     */
    public void testSetTimeout() {
        System.out.println("setTimeout");
        long timeout = 0L;
        RedisUtil instance = new RedisUtil();
        instance.setTimeout(timeout);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of putData2Cache method, of class RedisUtil.
     */
    public void testPutData2Cache() {
        System.out.println("putData2Cache");
        String mapKey = "testMapKey";
        Map<String, Object> dataMap = new HashMap();
        dataMap.put("testKey", "testValue");
        boolean isTimeout = false;
        redisUtil.putData2Cache(mapKey, dataMap, isTimeout);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    /**
     * Test of getHashValue2Map method, of class RedisUtil.
     */
    public void testGetHashValue2Map() {
        System.out.println("getHashValue2Map");
        String mapKey = "testMapKey";
        Map result = redisUtil.getHashValue2Map(mapKey);
        Iterator it = result.keySet().iterator();
        while (it.hasNext()){
            String key=(String)it.next();
            log.debug("key: "+key+" value: "+result.get(key).toString());
//            System.out.println("key: "+key+" value: "+result.get(key).toString());
        }
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
    /**
     * Test of removeByKey method, of class RedisUtil.
     */
    public void testRemoveByKey() {
        System.out.println("removeByKey");
        String mapKey = "testMapKey";
        redisUtil.removeByKey(mapKey);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }


}
