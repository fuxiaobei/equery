package com.asiainfo.billing.drquery.datasource.ocnosql;


import com.alibaba.druid.pool.DruidDataSource;
import com.asiainfo.ocsearch.datasource.jdbc.HbaseJdbcHelper;
import com.asiainfo.ocsearch.datasource.jdbc.phoenix.PhoenixJdbcHelper;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.Date;
public class HbaseQueryImplTest {
    @Test
    public void test(){
//        String sql = "select * from student where age>=25 order by age desc";
        DruidDataSource a;
        String sql = "select *  from  A";
        HbaseJdbcHelper help = null;
        org.apache.hadoop.hbase.protobuf.ProtobufUtil p;
        try {
            help = new PhoenixJdbcHelper();
            System.out.println("start1------------------------"+new Date().toString());
            ResultSet rs = help.executeQueryRaw(sql);
            while (rs.next()) {
                System.out.print("count=" + rs.getString("ID"));
//				System.out.print(",stuid=" + rs.getString("name"));
//				System.out.print(",age=" + rs.getString("age"));
//				System.out.print(",score=" + rs.getString("score"));
//				System.out.print(",classid=" + rs.getString("classid"));
                System.out.println();
            }
            System.out.println("end1------------------------"+new Date().toString());
            System.out.println("start2------------------------"+new Date().toString());
            ResultSet rs2 = help.executeQueryRaw(sql);
            while (rs2.next()) {
                System.out.print("count=" + rs2.getString("id"));
//				System.out.print(",stuid=" + rs.getString("name"));
//				System.out.print(",age=" + rs.getString("age"));
//				System.out.print(",score=" + rs.getString("score"));
//				System.out.print(",classid=" + rs.getString("classid"));
                System.out.println();
            }
            System.out.println("end2------------------------"+new Date().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            try {
                help.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

		
}
