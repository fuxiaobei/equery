package com.asiainfo.billing.drquery.datasource.db;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.connection.ConnectionFactory;
import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.datasource.DataSourceException;
import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;
import com.asiainfo.billing.drquery.model.MetaModel;

public class DBDataSource implements BaseDataSource {

	private final static Log log = LogFactory.getLog(DBDataSource.class);
    private ConnectionFactory<DBConnectionHolder> factory;
    
    public void setFactory(ConnectionFactory<DBConnectionHolder> factory) {
		this.factory = factory;
	}


    @Override
    public List<Map<String, String>> loadDR(DRQueryParameters queryParameters) throws DataSourceException {
        return null;
    }
}
