package com.asiainfo.billing.drquery.controller.reponse;

import com.asiainfo.billing.drquery.process.dto.model.PageInfo;

/**
 *
 * @author tianyi
 */
public class BaseResponse {
    public final static int SUCC = 0;
    public final static int FAIL = -1;
    protected int result;
    protected Object message;
    protected Object data;
    protected Object extData;
    protected PageInfo pageInfo;

	public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
