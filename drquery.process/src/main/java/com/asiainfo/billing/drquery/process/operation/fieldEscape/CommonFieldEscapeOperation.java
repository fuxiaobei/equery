package com.asiainfo.billing.drquery.process.operation.fieldEscape;

import com.asiainfo.billing.drquery.model.Field;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.compile.BeanInvoker;
import com.asiainfo.billing.drquery.process.core.request.DRProcessRequest;
import com.asiainfo.billing.drquery.process.operation.fieldEscape.model.FieldEscapeLoader;
import com.asiainfo.billing.drquery.process.operation.fieldEscape.model.FieldEscapeModel;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rex Wong
 *
 * @version
 */
public class CommonFieldEscapeOperation implements FieldEscapeOperation{
	
	private static final Log log = LogFactory.getLog(CommonFieldEscapeOperation.class);
	
	/**
     * 转义处理
     * @param models
     * @param modeTypes
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
	@SuppressWarnings("rawtypes")
	public List<Map<String,String>> execute(List<Map<String,String>> models, MetaModel meta, DRProcessRequest request) throws Exception{
    	Map<String, Field> fieldEscapes = meta.getEscapeFields();
        if(MapUtils.isEmpty(fieldEscapes)) {
            return models;
        }
    	List<Map<String,String>> tempModels = new ArrayList<Map<String,String>>();
    	for(Map<String,String> tempModel : models){
    		Map<String,String> model = new HashMap<String,String>();
            for(Map.Entry<String, Field> entry : fieldEscapes.entrySet()) {
                model.put(entry.getKey(), fieldConversion(tempModel, entry.getValue(), request));
            }
    		tempModels.add(model);
    	}
		return tempModels;
    }
	
	
	/**
	 * 
	 * @param rowdata
	 * @param field
	 * @param request
	 * @return
	 */
	private String fieldConversion(Map<String,String> rowdata, Field field, DRProcessRequest request){
		String escapeFieldAll = field.getEscapeField();
		String[] escapeFieldCodeArr = escapeFieldAll.split(",");
		String escapedValue = "";
		for(String escapeFieldCode:escapeFieldCodeArr){
			if(escapeFieldCode.equals("-1")){
				escapedValue = escapedValue+"^^"+rowdata.get(field.getName());
				continue;
			}
            //log.info("escapeFieldCode=" + escapeFieldCode);
			FieldEscapeModel fieldEscape = FieldEscapeLoader.getFieldEscapeBeans().get(escapeFieldCode);
			if(fieldEscape==null){
				if(log.isWarnEnabled()){
					log.warn("fieldEscape is null via code:"+escapeFieldCode
							+", do you set this key value in fieldescape_mapping.xml");
				}
				escapedValue = escapedValue+"^^"+rowdata.get(field.getName());
				continue;
			}
			if(fieldEscape.getType().equals("enum")){
				if(StringUtils.isNotEmpty(fieldEscape.getDefaultEmun())){
					escapedValue = escapedValue+"^^"+fieldEscape.getDefaultEmun();
				}
				else if(!MapUtils.isEmpty(fieldEscape.getEnumeration())){
					String otherValue = fieldEscape.getLocalDefault();
					Map<String, String> enumeration = fieldEscape.getEnumeration();
					String feildvalue = rowdata.get(field.getName());
					String value = enumeration.get(feildvalue);
					if(value==null){
						if(enumeration.containsKey("null")){
							value = enumeration.get("null");
						}
						else if(StringUtils.equals(otherValue, "RAW")){
							value = feildvalue;
						}
						else{
							value = otherValue;
						}
					}
					escapedValue = escapedValue+"^^"+value;
				}
				else{
					escapedValue = escapedValue+"^^"+ handlerEscape(rowdata,field.getName(),fieldEscape, request);
				}
			}
			else{
				escapedValue = escapedValue+"^^" + handlerEscape(rowdata,field.getName(),fieldEscape, request);
                //log.info("escapedValue=" + escapedValue);
			}
		}
		return StringUtils.substring(escapedValue, 2, escapedValue.length());
	}
	
	
	/**
	 * 
	 * @param rowdata
	 * @param fieldName
	 * @param fieldEscape
	 * @param request
	 * @return
	 */
	public String handlerEscape(Map<String, String> rowdata, String fieldName, FieldEscapeModel fieldEscape, DRProcessRequest request ){
		Object val = null;
		Object[] params = new Object[]{rowdata, fieldName, fieldEscape, request};
		try {
			val = BeanInvoker.invoke("localFieldEscape", fieldEscape.getLocalMethod(), params);
		} catch (Exception e) {
			log.error("invoke this method["+fieldEscape.getLocalMethod()+"] failed", e);
		}
		return (String) val;
	}
}
