package com.asiainfo.billing.drquery.process.operation.summary.summaryRule;

import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.process.core.request.DRProcessRequest;

public interface SummaryRule {
	
	StatMetaDescriptor<String,Object> rule(Map<String, String> rowData, DRProcessRequest request, StatMetaDescriptor stat);
	
	StatMetaDescriptor<String,Object> historyRule(Map<String, String> rowData, DRProcessRequest request, StatMetaDescriptor stat);
	
	public List getResultFromHisConfig(StatMetaDescriptor stat, DRProcessRequest request);
}
