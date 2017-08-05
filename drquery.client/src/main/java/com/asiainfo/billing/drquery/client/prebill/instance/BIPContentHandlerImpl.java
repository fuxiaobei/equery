package com.asiainfo.billing.drquery.client.prebill.instance;

import com.asiainfo.billing.drquery.client.prebill.PreBillDRQueryUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BIPContentHandlerImpl{

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
            if ("用户号码".equals(temp)) { 
                listCaption.add("计费用户号码");
            } else if ("通话起始时间".equals(temp)) { 
                listCaption.add("起始时间");
            } else if ("通话时长".equals(temp)) { 
                listCaption.add("时长");
            } else if ("长途通话费".equals(temp)) { 
                listCaption.add("长途通话费(元)");
            } else if ("本地通话费".equals(temp)) { 
                listCaption.add("本地通信费(元)");
            } else {
                listCaption.add(temp);
            }
        }
        result.put("listCaption", listCaption);
        
        
        //**处理数据**++++++**处理表尾数据统计**
        
        long total1 = 0L;
        long total2 = 0L;
        long total3 = 0L;
        
        
        List content = data.getJSONArray("contents");
        List listBody = new ArrayList();
        int contentSize = content.size();
        for (int i = 0; i < contentSize; i++) {
            List singleRow = (List) content.get(i);
            List singleContent = new ArrayList();
            singleContent.add(String.valueOf(i+1));
            for (int j = 0; j < fieldsSize; j++) {
                String input = (String) singleRow.get(j);
                if(j == 5) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Date(input));
                    
                } else if(j == 6) {
                    singleContent.add(PreBillDRQueryUtil.formatTimeLength(input));
                    total1 += PreBillDRQueryUtil.formatNumber(input); 
                } else if(j == 7) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total2 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));                     
                } else if(j == 8) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total3 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));                 
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
          if (i == 6 ) {
                listTail.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(total1)));
            } else if (i == 7 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total2)));
            } else if (i == 8 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total3)));
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
