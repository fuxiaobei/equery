package com.asiainfo.billing.drquery.client.prebill.instance;

import com.asiainfo.billing.drquery.client.prebill.PreBillDRQueryUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author daibei
 */
public class GSMRContentHandlerImpl{

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
                i++;
            } else if ("本地通话费".equals(temp)) { 
                listCaption.add("漫游通话费(元)");
            } else if ("信息费".equals(temp)) { 
                listCaption.add("普通附加费(元)");
            } else if ("长途通话费".equals(temp)) { 
                listCaption.add("长途通话费(元)");
                listCaption.add("长途附加费(元)");
            } else if ("呼转号码".equals(temp)) { 
                listCaption.add("副号码");
            } else if ("对端号码归属地".equals(temp)) { 
                listCaption.add("对方号码归属地");
            } else if ("用户号码".equals(temp)) { 
                listCaption.add("主叫号码");
            }else if ("业务名称".equals(temp)) { 
                listCaption.add("业务名称");
                listCaption.add("信息费");
            } else if("二级漫游地".equals(temp)) {
                listCaption.add("运营商代码");
            } else {
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
        long total7 = 0L;
        
        
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
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if(j == 6) {
                    singleContent.add(input + (String) singleRow.get(7));
                    j++;
                } else if(j == 8) {
                    singleContent.add(PreBillDRQueryUtil.formatTimeLength(input));
                    total1 += PreBillDRQueryUtil.formatNumber(input);                    
                } else if(j == 9) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total2 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));                   
                } else if(j == 11) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total3 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));                  
                } else if(j == 12) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    singleContent.add("0.00");//代收费
                    total4 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else if(j == 13) {
                    singleContent.add(input);
                    total5 += PreBillDRQueryUtil.formatNumber(input);
                } else if(j == 20) {
                    singleContent.add(input);
                    singleContent.add(PreBillDRQueryUtil.formatMoney((String) singleRow.get(11)));//信息费
                } else if(j == 23) {
                    singleContent.add(input);
                    total6 += PreBillDRQueryUtil.formatNumber(input);//免费分钟数2
                } else if(j == 25) {
                    singleContent.add(input);
                    total7 += PreBillDRQueryUtil.formatNumber(input);//免费分钟数3
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
            if(i == 6) {
                listTail.add("");
                i++;
            } else if (i == 8 ) {
                listTail.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(total1)));
            } else if (i == 9 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total2)));
            } else if (i == 11 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total3)));
            } else if (i == 12 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total4)));
                listTail.add("0.00");
            } else if (i == 13 ) {
                listTail.add(String.valueOf(total5));
            } else if (i == 20 ) {
                listTail.add("");
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total3)));
            } else if (i == 23 ) {
                listTail.add(String.valueOf(total6));
            } else if (i == 25 ) {
                listTail.add(String.valueOf(total7));
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
