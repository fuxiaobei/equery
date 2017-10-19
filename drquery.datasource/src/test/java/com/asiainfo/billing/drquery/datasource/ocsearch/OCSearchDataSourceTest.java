package com.asiainfo.billing.drquery.datasource.ocsearch;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

/**
 * Created by mac on 2017/7/27.
 */
public class OCSearchDataSourceTest {
    @Test
    public void testLoadDR() throws Exception {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"app_ocsearch.xml"});

        OCSearchDataSource ocSearchDataSource = (OCSearchDataSource) appContext.getBean("ocsearchDataSource");



//        OCSearchQueryParams dr = new OCSearchQueryParams(OCSearchQueryParams.REQUEST.GET);
//
//        dr.setCondition("*:*");
//        dr.setTable("FILE__201707");
//        dr.setExtParam("ids", Arrays.asList("3ce24995-5110-4ae9-a972-f03ab53ec211"));
//        dr.setExtParam("table", "FILE__201707");
//        dr.setSort("id", OCSearchQueryParams.ORDER.asc);
//        dr.addField("id");
//        System.out.println(ocSearchDataSource.loadDR(dr));
    }

}