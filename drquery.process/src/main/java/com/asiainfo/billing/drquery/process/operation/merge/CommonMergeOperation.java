package com.asiainfo.billing.drquery.process.operation.merge;

import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.model.ModelReader;
import com.asiainfo.billing.drquery.process.operation.merge.conf.MergeConfig;
import com.asiainfo.billing.drquery.process.operation.merge.conf.MergeFilterAdapter;
import com.asiainfo.billing.drquery.utils.NumberUtils;

/**
 * 公共的合并业务逻辑
 * @author zhouquan3
 * 26 Apr 2012
 */
public class CommonMergeOperation implements MergeOperation{
	
	private MergeConfig mergeConfig = new MergeConfig();
	
	/**
	 * 进行合并操作
	 */
    public boolean merge(MetaModel busiMeta, List<Map<String, String>> list, Map<String, String> rowData, Map<String, Integer> repeatCheckMap, int index){
    	boolean isMerged = false;
    	String modelId = busiMeta.getModelId();
		if(ModelReader.getMetaModels().get(modelId) == null){
			throw new NullPointerException("cann't get model where modelId='" + modelId + "'");
		}
    	String[] keyFields = ModelReader.getMetaModels().get(modelId).getMergeKeys();
    	String[] mergeFields = ModelReader.getMetaModels().get(modelId).getMergeFields();
        if(keyFields.length == 0){
        	return false;
        }
        
        int keyFieldsSize = keyFields.length;
        String mergeKey = "";
        String value = null;
        for (int j = 0; j < keyFieldsSize; j++) {
        	value = rowData.get(keyFields[j]);
            value = value == null ? "" : value;
            mergeKey = mergeKey.concat(value) + "||";
        }
        
        mergeConfig.registFilterAdapter(modelId);
        MergeFilterAdapter mergeFilterAdapter = mergeConfig.getMergeFilterAdapter();
        
        if(index == 0){
        	repeatCheckMap.put(mergeKey, index);
        	return false;
        }
        
    	//满足规则条件的才进行合并
    	if(mergeFilterAdapter != null && !mergeFilterAdapter.filter(rowData)){
    		return false;
    	}

        if(repeatCheckMap.containsKey(mergeKey)){
        	int prevMapIndex = repeatCheckMap.get(mergeKey);
        	Map<String, String> prevMap = list.get(prevMapIndex);
            int mergeFieldSize = mergeFields.length;
            for(int k = 0; k < mergeFieldSize; k++){
            	if(prevMap.get(mergeFields[k])!=null && rowData.get(mergeFields[k])!=null) {
                    double prevMapField = NumberUtils.parseDouble(prevMap.get(mergeFields[k]));
                    double currentMapField = NumberUtils.parseDouble(rowData.get(mergeFields[k]));
                    prevMap.put(mergeFields[k], String.valueOf(prevMapField + currentMapField));
                }
            }
            isMerged =  true;
        }else{
        	repeatCheckMap.put(mergeKey, index);
        }
        
        return isMerged;
    }
}
