package com.asiainfo.billing.drquery.controller.reponse;

import com.asiainfo.billing.drquery.process.dto.model.PageInfo;

import java.util.HashMap;
import java.util.Map;

public class DataResponse extends BaseResponse {
	
	public DataResponse(Object object) {
		this.result = SUCC;
		this.message = null;
		this.data = object;
	}
	
	public DataResponse(Object object, Object message, PageInfo pageInfo) {
		this.result = SUCC;
		this.message = message;
		this.data = object;
        this.pageInfo = pageInfo;
	}


    public DataResponse(Object data, Object message, Object extData, PageInfo pageInfo) {
		this.result = SUCC;
		this.message = message;
		this.data = data;
        this.extData = extData;
        this.pageInfo = pageInfo;
	}

	public Map<String,Object> toMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", this.result);
		map.put("resMsg", this.message);
		map.put("data", this.data);
        if(extData != null)
            map.put("extData", this.extData);
        if(pageInfo != null)
            map.put("pageInfo", this.pageInfo);
        return map;
	}
        
}
