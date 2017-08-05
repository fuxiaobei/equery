package com.asiainfo.billing.drquery.process.operation.summary;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.format.number.NumberFormatter;
import com.asiainfo.billing.drquery.format.number.TimeFormatter;
import com.asiainfo.billing.drquery.model.Field;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.core.request.DRProcessRequest;
import com.asiainfo.billing.drquery.process.operation.summary.summaryRule.StatMetaDescriptor;

/**
 * 公共的汇总业务逻辑
 * @author zhouquan3
 * 26 Apr 2012
 */
public class CommonSummaryOperation implements SummaryOperation{
    private static final Log log = LogFactory.getLog(CommonSummaryOperation.class);
    public Map<String, Object> execute(List<Map<String, String>> mapList, String[] summaryFields,Map<String, Field> fields) {
        Map<String,Object> summaryMap=new HashMap<String, Object>();
        Map<String,String> currentMap=null;
        int mapListSize=mapList.size();
        int summaryFieldSize=summaryFields.length;
        String[] summaryContent=new String[summaryFieldSize];
        for(int i=0;i<mapListSize;i++){
            currentMap=mapList.get(i);
            for(int k=0;k<summaryFieldSize;k++){
            	String value = currentMap.get(summaryFields[k]);
            	if(StringUtils.isEmpty(value)){
            		continue;
            	}
            	value = value.trim();
            	Field field = fields.get(summaryFields[k]);
            	String type = field.getType();
				if(type.equals("currency")){
					value=value.equals("null")?"0":value;
					if(!NumberUtils.isNumber(value)){
						log.error("The field["+field.getName()+"]'s value["+value+"] is not number type, we cant convert it into currency type data");
						continue;
					}
					double currencyvalue = Double.parseDouble(value)/1000;
					NumberFormatter formatter = new NumberFormatter();
					formatter.setFractionDigits(2);
					value = formatter.print(currencyvalue,Locale.CHINA);
				}
				BigDecimal number = new BigDecimal(summaryContent[k]==null?"0":summaryContent[k]);
				number = number.add(new BigDecimal(value));
				summaryContent[k]=number.toString();
            }
        }
        for(int i=0;i<summaryFieldSize;i++){
        	String summaryValue = summaryContent[i];
        	Field field = fields.get(summaryFields[i]);
        	String type = field.getType();
        	if(type.equals("timelength")){
        		summaryValue=summaryValue==null||summaryValue.equals("null")?"0":summaryValue;
				TimeFormatter formatter = new TimeFormatter();
				summaryValue = formatter.getNumberFormat(Double.parseDouble(summaryValue==null?"0":summaryValue));
			}
			else if(type.equals("currency")){
				summaryValue=summaryValue==null||summaryValue.equals("null")?"0":summaryValue;
				if(!NumberUtils.isNumber(summaryValue)){
					log.error("The field["+field.getName()+"]'s value["+summaryValue+"] is not number type, we cant convert it into currency type data");
					continue;
				}
				double currencyvalue = Double.parseDouble(summaryValue==null?"0":summaryValue);
				NumberFormatter formatter = new NumberFormatter();
				formatter.setFractionDigits(2);
				summaryValue = formatter.print(currencyvalue,Locale.CHINA);
			}
            summaryMap.put(summaryFields[i], summaryValue);
        }
        return summaryMap;
    }
	public StatMetaDescriptor<String, Object> execute(Map<String, String> rowData, MetaModel meta, DRProcessRequest request, StatMetaDescriptor stat) {
		return null;
	}
	@Override
	public boolean hasRule(MetaModel meta) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public StatMetaDescriptor<String, Object> execute(
			Map<String, String> rowData, String methodName,
			DRProcessRequest request, StatMetaDescriptor stat) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
