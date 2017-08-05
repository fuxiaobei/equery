package com.asiainfo.billing.drquery.process.core.request;


import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.compile.BeanInvoker;

import java.util.*;

/**
 * 详单查询Process处理参数
 * 
 * @author Rex Wong
 *
 * @version
 */
public abstract class DRProcessRequest implements ProcessRequest{

	//billId,userId,billMonth,fromDate,thruDate,billType
	//regionCode,queryType,impType,startIndex,stopIndex
	
    private String billId;			//计费号码
    private String fromDate;		//开始日期YYYYMMDD
    private String thruDate;		//结束日期YYYYMMDD
	private String billType;		//业务类型(38类详单)
    private String dbType = "ocnosqlDataSource";	//数据源类型
    private String interfaceType;	//接口类型


    private String startIndex;		//起始行
    private String stopIndex;		//结束行
    private Integer pageIndex;
    private Integer pageSize;
    private String sortName;		//排序字段名
    private String sortDir;	


    /*增加处理投诉释疑的处理参数*/
    private String appCode;     //应用编码
    private String recordCode; //上网日志记录ID


    private Map<String, String> params;  //webService的json格式的参数

    public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getThruDate() {
		return thruDate;
	}
	public void setThruDate(String thruDate) {
		this.thruDate = thruDate;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	
	public String getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}
	public String getStopIndex() {
		return stopIndex;
	}
	public void setStopIndex(String stopIndex) {
		this.stopIndex = stopIndex;
	}

	public String generateCacheKey() throws ProcessException {
        if(params == null || params.isEmpty()){
          return null;
        }
        return (String) BeanInvoker.invoke("cacheKey", "generateCacheKey", new Object[] {params});
	}

    public String getParam(String param) {
        if(params == null) {
            throw new RuntimeException("params cannot be null");
        }
        return params.get(param);
    }

    public String get(String key) {
        return getParam(key);
    }

    public String get(String key, String defaultValue) {
        if(params.containsKey(key)) {
            return params.get(key);
        } else {
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        if(params.containsKey(key)) {
            return Integer.parseInt(params.get(key));
        } else {
            return defaultValue;
        }
    }

	public String getConvertBillType(){
		return null;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortDir() {
		return sortDir;
	}
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
