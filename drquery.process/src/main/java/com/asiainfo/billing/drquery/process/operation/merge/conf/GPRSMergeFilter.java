package com.asiainfo.billing.drquery.process.operation.merge.conf;

import java.util.Map;

public class GPRSMergeFilter implements MergeFilterAdapter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean filter(Map data) {
		String SRC_IMSI = (String)data.get("SRC_IMSI");
		String SRC_START_TIME = (String)data.get("SRC_START_TIME");
		if(!"0".equals(SRC_IMSI) && "20100731235959".compareTo(SRC_START_TIME) < 0){
			return true;
		}
		return false;
	}

}
