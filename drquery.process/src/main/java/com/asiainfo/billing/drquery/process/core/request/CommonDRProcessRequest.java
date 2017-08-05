package com.asiainfo.billing.drquery.process.core.request;

import com.asiainfo.billing.drquery.utils.BeanToMap;

import java.util.Map;


/**
 * 常用字段查询处理参数
 * 
 * @author tianyi
 */
public class CommonDRProcessRequest extends DRProcessRequest {

	public Map<String, Object> ToMap(){
		return BeanToMap.copyProperties(this);
	}
	
	public String getProcessType(){
        return this.getInterfaceType();
	}
}
