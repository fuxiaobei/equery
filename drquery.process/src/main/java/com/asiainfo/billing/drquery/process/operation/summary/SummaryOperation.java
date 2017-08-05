package com.asiainfo.billing.drquery.process.operation.summary;

import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.model.Field;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.core.request.DRProcessRequest;
import com.asiainfo.billing.drquery.process.operation.summary.summaryRule.StatMetaDescriptor;

/**
 * 基础汇总逻辑接口
 * @author zhouquan3
 * 26 Apr 2012
 */
public interface SummaryOperation {
	@Deprecated
    public Map<String, Object> execute(List<Map<String, String>> mapList, String[] summaryFields,Map<String, Field> fields);
	
	public StatMetaDescriptor<String,Object> execute(Map<String, String> rowData, String methodName, DRProcessRequest request, StatMetaDescriptor stat);
	
	public boolean hasRule(MetaModel meta);
}
