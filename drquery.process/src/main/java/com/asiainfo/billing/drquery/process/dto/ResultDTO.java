package com.asiainfo.billing.drquery.process.dto;

import java.util.List;

/**
 * Created by wangkai8 on 15/6/1.
 */
public class ResultDTO extends AbstractDTO {

    protected List data;

    protected Object extData;

    public ResultDTO(List data) {
        this.data = data;
    }

    public ResultDTO(List data, Object extData) {
        this(data);
        this.extData = extData;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }
}
