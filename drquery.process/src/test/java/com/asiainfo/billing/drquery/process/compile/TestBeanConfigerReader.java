package com.asiainfo.billing.drquery.process.compile;

import org.junit.Test;

/**
 * Created by wangkai8 on 15/5/14.
 */
public class TestBeanConfigerReader {

    @Test
    public void testCompile() throws Exception {
        BeanConfigerReader reader = new BeanConfigerReader();
        reader.afterPropertiesSet();
    }
}
