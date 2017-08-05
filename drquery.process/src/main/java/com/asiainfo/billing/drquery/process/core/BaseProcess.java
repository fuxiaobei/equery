package com.asiainfo.billing.drquery.process.core;

import com.asiainfo.billing.drquery.exception.BusinessException;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.core.request.ProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;

import java.util.*;

/**
 * Process公共接口
 * </p>
 * 业务逻辑公共方法基础接口定义,此接口主要用于当非详单查询出现时
 * 
 * @author tianyi
 * @version 0.1
 */
public interface BaseProcess<T extends ProcessRequest> {

    void before(T rq, MetaModel viewMeta, Map extendParams) throws ProcessException, BusinessException;

    void after(T rq, MetaModel viewMeta, Map extendParams) throws ProcessException, BusinessException;
	
	<E extends BaseDTO> E process(T rq, MetaModel meta,Map extendParams) throws ProcessException, BusinessException;
}
