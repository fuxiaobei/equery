package com.asiainfo.billing.drquery.process.operation.distinct;

import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.operation.Operation;

/**
 * 基础去重逻辑接口
 * @author zhouquan3
 * 
 * 26 Apr 2012
 */
public interface DistinctOperation extends Operation{

	public boolean distinct(MetaModel busiMeta, List<Map<String, String>> list, Map<String, String> rowData, Map<String, Integer> repeatCheckMap, int index);

}
