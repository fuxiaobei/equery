/**
 * 
 */
package com.asiainfo.billing.drquery.axis2server;


import com.asiainfo.billing.drquery.controller.reponse.DataResponse;
import com.asiainfo.billing.drquery.controller.reponse.FailResponse;
import com.asiainfo.billing.drquery.process.core.request.CommonDRProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;
import com.asiainfo.billing.drquery.process.dto.EmptyDTO;
import com.asiainfo.billing.drquery.process.dto.PageDTO;
import com.asiainfo.billing.drquery.process.dto.ResultDTO;
import com.asiainfo.billing.drquery.process.dto.model.ResMsg;
import com.asiainfo.billing.drquery.service.Query;
import com.asiainfo.billing.drquery.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liujs3
 *
 */
public class Axis2ServerAdaptor extends CommonAxis {

    private static Log log = LogFactory.getLog(Axis2ServerAdaptor.class);

    private static final String ENCODING_TYPE = "UTF-8";
    private static final int RECORD_BYTES = 1000;//估设置单条记录转换出json的最大字节数，按照上网详单为最大记录

    public String getAxis2Result(String params) {

        CommonDRProcessRequest request = new CommonDRProcessRequest();

        request.setParams(JsonUtils.parserToMap(params));
        request.setInterfaceType(request.getParams().get("interfaceType"));

        Map<String, Object> retMap = new HashMap<String, Object>();
        String retJsonStr = "";
        try {
            BaseDTO dto = null;
            dto = Query.query(request);

            ResMsg msg = dto.getResMsg();
            if("0".equals(msg.getRetCode())) {
                if(dto instanceof EmptyDTO) {
                    retMap = new DataResponse(null, msg, null).toMap();
                } else {
                    ResultDTO resultDTO = (ResultDTO) dto;
                    if(resultDTO instanceof PageDTO) {
                        retMap = new DataResponse(resultDTO.getData(), msg, resultDTO.getExtData(), ((PageDTO)resultDTO).getPageInfo()).toMap();
                    } else {
                        retMap = new DataResponse(resultDTO.getData(), msg, resultDTO.getExtData(), null).toMap();
                    }

                }

            } else {
                retMap = new FailResponse(msg).toMap();
            }
        } catch (Exception ex) {
            log.error("process request failed", ex);
            ResMsg msg = new ResMsg();
            msg.setRetCode("-1");
            msg.setErrorMsg("process request failed : "+ex.getMessage());
            msg.setHint("查询失败");
            //return new ModelAndView("/common", new FailResponse(msg).toMap());
            retMap = new FailResponse(msg).toMap();
        } finally {
            try {
                ObjectMapper om = new ObjectMapper();
                int recordSize = retMap.size();
                ByteArrayOutputStream baos = new ByteArrayOutputStream((recordSize>1?RECORD_BYTES*recordSize:RECORD_BYTES));//根据返回的记录数设置临时输出流的大小
                JsonGenerator jsonGenerator = om.getJsonFactory().createJsonGenerator(baos, JsonEncoding.UTF8);
                om.writeValue(jsonGenerator, retMap);//map to json
                retJsonStr = new String(baos.toByteArray(),ENCODING_TYPE);
            } catch(Exception e){
                log.error("write json to client error " , e);
            }
        }
        return retJsonStr;
	}
}