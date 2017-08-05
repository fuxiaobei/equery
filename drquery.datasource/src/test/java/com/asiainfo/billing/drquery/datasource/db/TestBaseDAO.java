package com.asiainfo.billing.drquery.datasource.db;

import com.asiainfo.billing.drquery.connection.ConnectionException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by wangkai8 on 15/6/8.
 */
public class TestBaseDAO {

    private BaseDAO dao;

    @Before
    public void setup() {
        dao = new BaseDAO("CSQRYConnectionFactory");
    }


    @Test
    public void testQuery1() throws ConnectionException {
        String sql = "select id, name from user where id= ?";
        List<Map> list = dao.queryBySQL(sql, new Object[] {"0001"});
    }

    @Test
    public void testQuery2() throws ConnectionException {
        String sql = "select id, name from user where id= ?";
        List<Object[]> list = dao.queryBySQLArr(sql, new Object[] {"0001"});
    }


    @Test
    public void testUpdate() throws ConnectionException {
        String sql = "update user set name=? where id=?";
        int n = dao.update(sql, new Object[]{"xiaoming", "0001"});
    }
}
