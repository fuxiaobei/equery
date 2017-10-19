package com.asiainfo.billing.drquery.controller;

import com.asiainfo.billing.drquery.controller.reponse.DataResponse;
import com.asiainfo.billing.drquery.controller.reponse.FailResponse;
import com.asiainfo.billing.drquery.process.core.request.CommonDRProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;
import com.asiainfo.billing.drquery.process.dto.EmptyDTO;
import com.asiainfo.billing.drquery.process.dto.PageDTO;
import com.asiainfo.billing.drquery.process.dto.ResultDTO;
import com.asiainfo.billing.drquery.process.dto.model.ResMsg;
import com.asiainfo.billing.drquery.service.Query;
import com.asiainfo.billing.drquery.utils.EncryptJsonStringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tianyi
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	private static Log log = LogFactory.getLog(CommonController.class);

    private static final String ENCODING_TYPE = "UTF-8";
    private static final String CONTENT_TYPE = "text/plain;charset=UTF-8";
    private static final int RECORD_BYTES = 1000;//估设置单条记录转换出json的最大字节数，按照上网详单为最大记录

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView postQueryDetailRecord(HttpServletRequest req, HttpServletResponse resp) {

        try {
            req.getParameterMap().put("request_body",IOUtils.toString(req.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                req.getInputStream().close();
            } catch (IOException e) {

            }
        }
        return queryDetailRecord(req, resp);
	}


	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView queryDetailRecord(HttpServletRequest req, HttpServletResponse resp) {

		CommonDRProcessRequest request = new CommonDRProcessRequest();

        request.setParams(bindParams(req));
        request.setInterfaceType(req.getParameter("interfaceType"));

        ServletOutputStream sos = null;
        Map<String, Object> retMap = new HashMap<String, Object>();
        String retJsonStr = "";
		try {
            resp.setContentType(CONTENT_TYPE);
            sos = resp.getOutputStream();
            BaseDTO dto = Query.query(request);

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
			msg.setErrorMsg("process request failed : " + ex.getMessage());
			msg.setHint("request failed");
            retMap = new FailResponse(msg).toMap();
		} finally {
            try{

                String encryptStr;
                if(retMap.get("data") == null&&retMap.get("result").equals(0)){ //for file get service
                    encryptStr= (String) retMap.get("extData");
                }else{
                    ObjectMapper om = new ObjectMapper();
                    int recordSize = retMap.size();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream((recordSize>1?RECORD_BYTES*recordSize:RECORD_BYTES));//根据返回的记录数设置临时输出流的大小
                    JsonGenerator jsonGenerator = om.getJsonFactory().createJsonGenerator(baos, JsonEncoding.UTF8);
                    om.writeValue(jsonGenerator, retMap);//map to json
                    retJsonStr = new String(baos.toByteArray(),ENCODING_TYPE);
                    encryptStr = EncryptJsonStringUtil.encrypt(retJsonStr);
                    if(baos!=null)
                        baos.close();
                }

                //log.info("encryptStr = " + encryptStr.getBytes(ENCODING_TYPE));
                sos.write(encryptStr.getBytes(ENCODING_TYPE));

                if(sos!=null)
                   sos.close();
            }catch(Exception e){
                log.error("write json to client error " , e);
            }
        }
        return null;
	}


    public Map<String, String> bindParams(HttpServletRequest req) {
        Map<String, String> params = new HashMap<String, String>();
        Enumeration<String> en = req.getParameterNames();
        while(en.hasMoreElements()) {
            String key = en.nextElement();
            params.put(key, req.getParameter(key));
        }

        return params;
    }


}
