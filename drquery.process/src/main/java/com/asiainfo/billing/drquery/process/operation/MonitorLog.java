package com.asiainfo.billing.drquery.process.operation;

import com.asiainfo.billing.drquery.process.core.request.CommonDRProcessRequest;
import org.apache.commons.collections.map.LinkedMap;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MonitorLog {
	private CommonDRProcessRequest request;
	private boolean success;
	private Map extendParams;
	private long costTime;
	
	public MonitorLog(CommonDRProcessRequest request, boolean success, long costTime, Map extendParams){
		this.request = request;
		this.success = success;
		this.extendParams = extendParams;
		this.costTime = costTime;
	}
	
	public MonitorLog(boolean success, Map params){
		this.success = success;
		this.extendParams = params;
	}

    public MonitorLog(CommonDRProcessRequest request, Map params){
        this.request = request;
        this.extendParams = params;
    }

	public String buildResult(){
		Map paramMap = new TreeMap();
        paramMap.put(IS_SUCCESS, success);
        paramMap.put(COST_TIME, costTime);
		if(extendParams != null){
			paramMap.putAll(extendParams);
		}
		
		StringBuffer sb = new StringBuffer();
		for(Iterator it = paramMap.keySet().iterator(); it.hasNext();){
			String key = (String)it.next();
			sb.append(key).append("=($").append(paramMap.get(key) == null ? "" : paramMap.get(key)).append("$)");
			if(it.hasNext()){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	public String toString(){
		return buildResult();
	}
	
	
	
	
	public static final String DONE_CODE = "doneCode";
	public static final String OP_ID = "opId";
	public static final String BILL_ID = "billId";
	public static final String CDR_TYPE = "cdrType";
	public static final String REGION_CODE = "regionCode";
	public static final String BILL_MONTH = "billMonth";
	public static final String QUERY_TYPE = "queryType";
	public static final String CHANNEL_TYPE = "channelType";
	public static final String QUERY_DATE = "queryDate";
	public static final String TOTAL_COUNT = "TOTAL_COUNT";
	public static final String INTERFACE_TYPE = "INTERFACE_TYPE";
	public static final String ENTRY_POINT = "ENTRY_POINT";

    public static final String ITF_NAME = "ITF_NAME"; //接口名称
    public static final String ITF_PARAM = "ITF_PARAM";//接口参数
    public static final String START_TIME = "START_TIME"; //开始时间  yyyymmddHHMMSS
    public static final String COST_TIME = "COST_TIME";//接口耗时 单位：毫秒
    public static final String IS_SUCCESS = "IS_SUCCESS";//执行状态  true or fase
    public static final String RESULT_STATS = "RESULT_STATS";//结果统计信息
    public static final String REMARK = "REMARK"; //备注
    public static final String FROM_CACHED = "FROM_CACHED";//是否从cache查询  true or fase
    public static final String SQL = "SQL";//sql语句
    public static final String SQL_COST_TIME = "SQL_COST_TIME";//sql执行时间
    public static final String DEAL_SUMMARYRESULT_COST_TIME = "DEAL_SUMMARYRESULT_COST_TIME";//处理汇总结果的时间F11
    public static final String DEAL_DETAILRECORD_COST_TIME = "DEAL_PROCESSDETAILRECORD_COST_TIME";//处理日志明细记录的时间F12
}
