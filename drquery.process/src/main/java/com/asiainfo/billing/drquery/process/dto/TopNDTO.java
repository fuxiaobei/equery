package com.asiainfo.billing.drquery.process.dto;

import java.util.List;

/**
 * Created by wangkai8 on 15/6/2.
 */
public class TopNDTO extends ResultDTO {

    private int topN = -1;

    public TopNDTO(List data) {
        super(data);
    }

    public TopNDTO(List data, Object extData) {
        super(data, extData);
    }

    @Override
    public List getData() {
        if(topN == -1) {
            return data;
        }
        if(topN > data.size()) {
            return data;
        } else {
            return data.subList(0, topN);
        }
    }
}
