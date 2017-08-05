package com.asiainfo.billing.drquery.client.rest;

/**
 *
 * @author tianyi
 */
public class BaseResponse {
    protected String result;
    protected String message;
    protected Object data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
