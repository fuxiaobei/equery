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

public class VPMNContentHandlerImpl {

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
            if ("通话起始时间".equals(temp)) { 
                listCaption.add("通话开始时间");
            } else if ("一级漫游地".equals(temp)) { 
                listCaption.add("漫游地");
            } else if ("二级漫游地".equals(temp)) { 
                continue;
            } else if ("本地通话费".equals(temp)) { 
                listCaption.add("基本通话费(元)");
            } else if ("信息费".equals(temp)) { 
                listCaption.add("基本附加费(元)");
            } else if ("长途通话费".equals(temp)) { 
                listCaption.add("长途通话费(元)");
            } else if ("长途附加费".equals(temp)) { 
                listCaption.add("长途附加费(元)");
            } else if ("话单类型".equals(temp)) { 
                listCaption.add("业务类型");
            } else if ("呼叫标志".equals(temp)) { 
                listCaption.add("通话类型");
            }else if ("免费分钟数1".equals(temp)) { 
                listCaption.add("免费分钟数");
            }
            else {
                listCaption.add(temp);
            }
        }
        result.put("listCaption", listCaption);
        
        
        //**处理数据**++++++**处理表尾数据统计**
        
        long total1 = 0L;
        long total2 = 0L;
        long total3 = 0L;
        long total4 = 0L;
        long total5 = 0L;
        long total6 = 0L;
        
        List content = data.getJSONArray("contents");
        List listBody = new ArrayList();
        int contentSize = content.size();
        for (int i = 0; i < contentSize; i++) {
            List singleRow = (List) content.get(i);
            List singleContent = new ArrayList();
            singleContent.add(String.valueOf(i+1));
            for (int j = 0; j < fieldsSize; j++) {
                String input = (String) singleRow.get(j);
                if(j == 4) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if(j == 5) {    //漫游地
                    singleContent.add(input + (String) singleRow.get(6));
                    j++;
                } else if(j == 7) {   //通话时长
                    singleContent.add(PreBillDRQueryUtil.formatTimeLength(input));
                    total1 += PreBillDRQueryUtil.formatNumber(input);                    
                } else if(j == 8) {   //基本通话费
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total2 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));                   
                } else if(j == 9) {   //信息费
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total3 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));                   
                } else if(j == 10) {   //长途通话费
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total4 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));                  
                } else if(j == 11) {   //长途附加费
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total5 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else if(j == 14) {   //免费分钟数
                    singleContent.add(PreBillDRQueryUtil.formatNumber(input)/60);
                    total6 += PreBillDRQueryUtil.formatNumber(input)/60;
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
                listTail.add("");
                i++;
            }  else if (i == 7 ) {
                listTail.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(total1)));
            } else if (i == 8 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total2)));;
            } else if (i == 9 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total3)));
            } else if (i == 10 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total4)));
            } else if (i == 11 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total5)));
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