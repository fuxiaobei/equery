package com.asiainfo.billing.drquery.controller.reponse;

import java.util.HashMap;
import java.util.Map;

public class FailResponse extends BaseResponse {
	public FailResponse() {
		this.result = FAIL;
		this.message = null;
		this.data = null;
	}

	public FailResponse(Object message) {
		this.result = FAIL;
		this.message = message;
		this.data = null;
	}
	
	public Map<String,Object> toMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", this.result);
		map.put("resMsg", this.message);
		map.put("data", this.data);
		return map;
	}
}
