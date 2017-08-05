package com.asiainfo.billing.drquery.process.operation.distinct;

import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.model.ModelReader;

/**
 * 公共的去重业务逻辑
 *
 * @author zhouquan3
 *
 * 26 Apr 2012
 */
public class CommonDistinctOperation implements DistinctOperation {

    
    public boolean distinct(MetaModel busiMeta, List<Map<String, String>> list, Map<String, String> rowData,
                            Map<String, Integer> repeatCheckMap, int index){
		boolean isDistinct = false;
		String modelId = busiMeta.getModelId();
		if(ModelReader.getMetaModels().get(modelId) == null){
			throw new NullPointerException("cann't get model where modelId='" + modelId + "'");
		}
		String[] keyFields = ModelReader.getMetaModels().get(modelId).getDistinctFields();
        if(keyFields.length == 0){
        	return false;
        }
        int keyFieldsSize = keyFields.length;
        String distinctKey = "";
        String value = null;
        for (int j = 0; j < keyFieldsSize; j++) {
            value = rowData.get(keyFields[j]);
            value = value == null ? "" : value;
            distinctKey = distinctKey.concat(value) + "|";
        }
        if(repeatCheckMap.containsKey(distinctKey)){
        	isDistinct = true;
        }else{
        	repeatCheckMap.put(distinctKey, index);
        }
		return isDistinct;
	}
    
}
