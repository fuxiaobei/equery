package com.asiainfo.billing.drquery.datasource.query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangkai8 on 15/6/8.
 */
public class DefaultQueryParams implements DRQueryParameters {

    private String sql;

    private String[] args;

    private Map<Object, Object> extParams = new HashMap<Object, Object>();

    private  DefaultQueryParams() {

    }


    public static DefaultQueryParams newBuilder() {
        return new DefaultQueryParams();
    }


    public DefaultQueryParams buildSQL(String sql) {
        this.sql = sql;
        return this;
    }

    public DefaultQueryParams buildArgs(String[] args) {
        this.args = args;
        return this;
    }

    public DefaultQueryParams buildExtParams(Map<Object, Object> extParams) {
        this.extParams = extParams;
        return this;
    }

    public String getSql() {
        return sql;
    }

    public String[] getArgs() {
        return args;
    }

    public Map<Object, Object> getExtParams() {
        return extParams;
    }

    @Override
    public String getDataSourceName() {
        return DEFAULT_DATASOURCE_NAME;
    }
}
