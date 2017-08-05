package com.asiainfo.billing.drquery.process.operation.merge;

import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.model.MetaModel;

/**
 * 基础合并逻辑接口
 * @author zhouquan3
 * 26 Apr 2012
 */
public interface MergeOperation {
    
    public boolean merge(MetaModel busiMeta, List<Map<String, String>> list, Map<String, String> rowData, Map<String, Integer> repeatCheckMap, int index);
}
