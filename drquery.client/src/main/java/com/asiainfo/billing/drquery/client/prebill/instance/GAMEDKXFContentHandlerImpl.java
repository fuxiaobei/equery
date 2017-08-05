package com.asiainfo.billing.drquery.client.prebill.instance;

import com.asiainfo.billing.drquery.client.prebill.PreBillDRQueryUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GAMEDKXFContentHandlerImpl{

    public Map getTailList(JSONObject data) {
        System.out.println(data.toString(1));
        Map result = new HashMap();
        //**处理字段key**
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        int fieldsSize = fields.size();
        listCaption.add("序号");
        for (int i = 0; i < fieldsSize; i++) {
            String temp = (String) fields.get(i);
            if ("优惠后信息费".equals(temp)) { 
                listCaption.add("优惠后信息费(元)");
            } else if ("SP企业代码".equals(temp)) { 
                listCaption.add("SP代码");
            } else if ("话单序列号".equals(temp)) { 
                listCaption.add("充值序列号");
            } else if ("业务编码".equals(temp)) { 
                listCaption.add("业务名称");
            } else {
                listCaption.add(temp);
            }
        }
        result.put("listCaption", listCaption);
        
        
        //**处理数据**++++++**处理表尾数据统计**
        
        long total1 = 0L;
        
        
        List content = data.getJSONArray("contents");
        List listBody = new ArrayList();
        int contentSize = content.size();
        for (int i = 0; i < contentSize; i++) {
            List singleRow = (List) content.get(i);
            List singleContent = new ArrayList();
            singleContent.add(String.valueOf(i+1));
            for (int j = 0; j < fieldsSize; j++) {
                String input = (String) singleRow.get(j);
                if(j == 2) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Date(input));
                } else if (j == 5){
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total1 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else {
                    singleContent.add(input);
                }
            }
            listBody.add(singleContent);
        }
        result.put("listBody", listBody);
        //**处理表尾数据统计**
        List listTail = new ArrayList();
        listTail.add("");
        for (int i = 0; i < fieldsSize; i++) {
            if(i == 5) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total1)));
            } else {
                listTail.add("");
            }
        }
        result.put("listTail", listTail);
        //**处理统计数据**
        if (!"0".equals(data.getString("count"))) {
            List listSpecialSum = PreBillDRQueryUtil.formatStats(data.getJSONArray("stats"));
            result.put("listSpecialSum", listSpecialSum);
        } else {
            List listSpecialSum = new JSONArray();
            result.put("listSpecialSum", listSpecialSum);
        }
        
        return result;
    }
    
}
