package com.asiainfo.billing.drquery.process.core;

import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.datasource.DataSourceException;
import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;
import com.asiainfo.billing.drquery.exception.BusinessException;
import com.asiainfo.billing.drquery.exception.DrqueryRuntimeException;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.core.request.CommonDRProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;
import com.asiainfo.billing.drquery.process.operation.distinct.DistinctOperation;
import com.asiainfo.billing.drquery.process.operation.merge.MergeOperation;
import com.asiainfo.billing.drquery.process.operation.summary.SummaryOperation;
import com.asiainfo.billing.drquery.utils.ServiceLocator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;


/**
 * 常用字段查询
 * @author wangyp5
 *
 * 25 Apr 2012
 * @version
 */
public class DRCommonProcess extends DRProcess<CommonDRProcessRequest>{
	
	private final static Log log = LogFactory.getLog(DRCommonProcess.class);
	protected DistinctOperation distinct;
	protected MergeOperation merge;
	protected SummaryOperation summary;
	private DataSourceRoute dataSourceRoute;

	public void setDataSourceRoute(DataSourceRoute dataSourceRoute) {
		this.dataSourceRoute = dataSourceRoute;
	}
	public void setDistinct(DistinctOperation distinct) {
		this.distinct = distinct;
	}
	public void setMerge(MergeOperation merge) {
		this.merge = merge;
	}
	public void setSummary(SummaryOperation summary) {
		this.summary = summary;
	}


    /**
     * 根据dataSourceName调用对应dataSource
     * @param parameters
     * @return
     * @throws ProcessException
     * @throws BusinessException
     */
	public List<Map<String,String>> loadData(DRQueryParameters parameters) throws ProcessException,BusinessException {
        //  通过自定义classLoader加载此类是不会进行spring注入，需要人为赋值
        if(dataSourceRoute == null) {
            dataSourceRoute =  ServiceLocator.getInstance().getService("baseDataSourceRoute", DataSourceRoute.class);
        }
        if(StringUtils.isEmpty(parameters.getDataSourceName())) {
            throw new ProcessException("dataSourceName cann't be empty");
        }
		BaseDataSource baseDataSource = this.dataSourceRoute.getDataSourceByTime(null, parameters.getDataSourceName());

		if(baseDataSource == null){
			throw new ProcessException("dataSource: " + parameters.getDataSourceName() + " not found");
		}

		try {
            return baseDataSource.loadDR(parameters);
		} catch (DataSourceException e) {
			throw new DrqueryRuntimeException(e);
		}
	}

    @Override
    public void before(CommonDRProcessRequest rq, MetaModel viewMeta, Map extendParams) throws ProcessException, BusinessException {

    }

    @Override
    public void after(CommonDRProcessRequest rq, MetaModel viewMeta, Map extendParams) throws ProcessException, BusinessException {

    }

    @Override
    public <E extends BaseDTO> E process(CommonDRProcessRequest rq, MetaModel meta, Map extendParams) throws ProcessException, BusinessException {
        return null;
    }

}
