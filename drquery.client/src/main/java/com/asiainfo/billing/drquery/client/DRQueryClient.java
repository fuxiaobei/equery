package com.asiainfo.billing.drquery.client;

import net.sf.json.JSONObject;

public interface DRQueryClient {
	
	/**
	 * 常用字段查询
	 * 
	 * @param jsonParam
	 * @return
	 */
	JSONObject commonQuery(JSONObject jsonParam);

	/**
	 * 业务类型查询
	 * 
	 * @param jsonParam
	 * @return
	 */
	JSONObject buisTypeQuery(JSONObject jsonParam);
	
	/**
	 * 通讯清单核对接口
	 * 
	 * @param jsonParam
	 * @return
	 */
	JSONObject checkQuery(JSONObject jsonParam);
}
