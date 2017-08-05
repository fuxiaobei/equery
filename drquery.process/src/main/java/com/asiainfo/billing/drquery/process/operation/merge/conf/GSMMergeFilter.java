package com.asiainfo.billing.drquery.process.operation.merge.conf;

import java.util.Map;

public class GSMMergeFilter implements MergeFilterAdapter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean filter(Map data) {
		String SRC_CHARGE_DISC = (String)data.get("SRC_CHARGE_DISC");
		String SRC_START_TIME = (String)data.get("SRC_START_TIME");
		if("-1".equals(SRC_CHARGE_DISC) && "20100731235959".compareTo(SRC_START_TIME) < 0){
			return true;
		}
		return false;
	}

}
