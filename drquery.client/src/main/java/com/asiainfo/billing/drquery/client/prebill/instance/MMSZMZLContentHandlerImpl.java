package com.asiainfo.billing.drquery.client.prebill.instance;

import com.asiainfo.billing.drquery.client.prebill.PreBillDRQueryUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MMSZMZLContentHandlerImpl{

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
            if ("接收方地址".equals(temp)) { 
                listCaption.add("接收号码");
            } else if ("一级漫游地".equals(temp)) { 
                listCaption.add("漫游地");
            } else if ("二级漫游地".equals(temp)) { 
                continue;
            } else if ("应用类型".equals(temp)) { 
                listCaption.add("MMS类型");
            } else if ("通信费".equals(temp)) { 
                listCaption.add("通信费(元)");
            } else if ("信息费".equals(temp)) { 
                listCaption.add("信息费(元)");
            } else {
                listCaption.add(temp);
            }
        }
        result.put("listCaption", listCaption);
        
        
        //**处理数据**++++++**处理表尾数据统计**
        
        long total1 = 0L;
        long total2 = 0L;
        
        
        List content = data.getJSONArray("contents");
        List listBody = new ArrayList();
        int contentSize = content.size();
        for (int i = 0; i < contentSize; i++) {
            List singleRow = (List) content.get(i);
            List singleContent = new ArrayList();
            singleContent.add(String.valueOf(i+1));
            int columnSize = singleRow.size();
            for (int j = 0; j < columnSize; j++) {
                String input = (String) singleRow.get(j);
                if(j == 1) {
                    singleContent.add(input + (String) singleRow.get(2));//VPMN1+VPMN2
                    j++;
                } else if(j == 9) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Date(input));               
                } else if(j == 10) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total1 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));                  
                } else if(j == 11) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total2 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else {
                    singleContent.add(input);
                }
            }
            listBody.add(singleContent);
        }
        result.put("listBody", listBody);
        //**处理表尾数据统计**
        List listTail = new ArrayList();
        int columnSize = listCaption.size();
        listTail.add("");
        for (int i = 0; i < columnSize; i++) {
            if(i == 1) {
                listTail.add("");
                i++;
            } else if (i == 10 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total1)));
            } else if (i == 11 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total2)));
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
