package com.asiainfo.billing.drquery.process.core.process;

import com.asiainfo.billing.drquery.exception.BusinessException;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.compile.BeanHolder;
import com.asiainfo.billing.drquery.process.compile.BeanInvoker;
import com.asiainfo.billing.drquery.process.core.BaseProcess;
import com.asiainfo.billing.drquery.process.core.DRCommonProcess;
import com.asiainfo.billing.drquery.process.core.DRProcess;
import com.asiainfo.billing.drquery.process.core.request.CommonDRProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;
import com.asiainfo.billing.drquery.utils.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * Created by wangkai8 on 15/5/8.
 */
public class DefaultInvokeProcess extends DRCommonProcess {

    public static final int timeout = Integer.parseInt(PropertiesUtil.getProperty("drquery.service/runtime.properties", "redis.expiretime", "300"));

    public static final Log log = LogFactory.getLog(DefaultInvokeProcess.class);

    public static final String DEFAULT_METHOD = "execute";


    @Override
    public BaseDTO process(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {
        String invoke = viewMeta.getInvoke();
        if(StringUtils.isEmpty(invoke)) {
            throw new ProcessException("invoke value cann't be empty");
        }
        String[] arr = invoke.split("!");

       return (BaseDTO) BeanInvoker.invoke(arr[0], arr.length == 1 ? DEFAULT_METHOD : arr[1], new Object[]{request, viewMeta, extendParams});
    }


    @Override
    public void after(CommonDRProcessRequest rq, MetaModel viewMeta, Map extendParams) throws ProcessException, BusinessException {
        Object ins = BeanHolder.getInstance(viewMeta.getInvoke().split("!")[0]);
        if(ins instanceof BaseProcess) {
            ((DRProcess) ins).after(rq, viewMeta, extendParams);
        }
    }


    @Override
    public void before(CommonDRProcessRequest rq, MetaModel viewMeta, Map extendParams) throws ProcessException, BusinessException {
        Object ins = BeanHolder.getInstance(viewMeta.getInvoke().split("!")[0]);
        if(ins instanceof BaseProcess) {
            ((DRProcess) ins).before(rq, viewMeta, extendParams);
        }
    }
}