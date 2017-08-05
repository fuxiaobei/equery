package com.asiainfo.billing.drquery.process.operation.merge.conf;

public class MergeConfig {

	private MergeFilterAdapter mergeFilterAdapter;
	
	public void registFilterAdapter(String callType){
		if("11".equals(callType) || "16".equals(callType)){
			setMergeFilterAdapter(new GPRSMergeFilter());
		}else if("1".equals(callType)||"2".equals(callType)||"3".equals(callType)||
				"6".equals(callType)||"18".equals(callType)){
			setMergeFilterAdapter(new GSMMergeFilter());
		}
	}
	
	public void setMergeFilterAdapter(MergeFilterAdapter mergeFilterAdapter){
		this.mergeFilterAdapter = mergeFilterAdapter;
	}

	public MergeFilterAdapter getMergeFilterAdapter() {
		return mergeFilterAdapter;
	}
	
	
	
}
