package com.asiainfo.billing.drquery.process.operation.summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.model.Field;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.compile.BeanInvoker;
import com.asiainfo.billing.drquery.process.core.request.DRProcessRequest;
import com.asiainfo.billing.drquery.process.operation.summary.summaryRule.StatMetaDescriptor;
import com.asiainfo.billing.drquery.process.operation.summary.summaryRule.SummaryRule;
import com.asiainfo.billing.drquery.utils.ServiceLocator;

public class DRNewJFCommonSummaryOperation implements SummaryOperation{
	
	private final static Log log = LogFactory.getLog(DRNewJFCommonSummaryOperation.class);
	
	public Map<String, Object> execute(List<Map<String, String>> mapList, String[] summaryFields, Map<String, Field> fields) {
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StatMetaDescriptor<String,Object> execute(Map<String, String> rowData, String methodName, DRProcessRequest request, StatMetaDescriptor stat) {
		
		StatMetaDescriptor<String,Object> val = null;
		if(rowData == null){
			rowData = new HashMap();
		}
		try{
			//直调
//			CommonSummaryRule summaryRule = new CommonSummaryRule();
//			if(methodName.equals("rule")){
//				val = summaryRule.rule(rowData, request, stat);
//			}else{
//				val = summaryRule.historyRule(rowData, request, stat);
//			}
			//通过反射调用
			val = (StatMetaDescriptor<String,Object>) BeanInvoker.invoke("commonSummaryRule", methodName, new Object[]{rowData, request, stat});
		}catch(Exception e){
			log.error("BeanInvoker invoke this method["+ methodName +"] failed", e);
		}
		
		return val;
	}

	@Override
	public boolean hasRule(MetaModel meta) {
		SummaryRule rule=ServiceLocator.getInstance().getService(meta.getStatRule(), SummaryRule.class);
		if(rule == null){
			return false;
		}
		return true;
	}
}
