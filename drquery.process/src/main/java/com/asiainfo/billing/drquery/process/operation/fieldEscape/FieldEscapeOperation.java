package com.asiainfo.billing.drquery.process.operation.fieldEscape;

import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.core.request.DRProcessRequest;
import com.asiainfo.billing.drquery.process.operation.Operation;

/**
 * 特使转义逻辑
 * 
 * @author Rex Wong
 *
 * @version
 */
public interface FieldEscapeOperation extends Operation{
	/**
	 * @param models
	 * @param meta
	 * @throws Exception
	 */
	List<Map<String,String>> execute(List<Map<String,String>> models,MetaModel meta, DRProcessRequest request) throws Exception;
}
