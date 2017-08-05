package com.asiainfo.billing.drquery.process.dto;

import com.asiainfo.billing.drquery.process.dto.model.ResMsg;

/**
 * Created by wangkai8 on 15/6/1.
 */
public abstract class AbstractDTO implements BaseDTO {

    private ResMsg resMsg;

    @Override
    public void setResMsg(ResMsg resMsg) {
        this.resMsg = resMsg;
    }

    @Override
    public ResMsg getResMsg() {
        return resMsg;
    }
}
