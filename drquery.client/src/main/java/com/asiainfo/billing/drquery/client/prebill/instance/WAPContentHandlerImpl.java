package com.asiainfo.billing.drquery.client.prebill.instance;

import com.asiainfo.billing.drquery.client.prebill.PreBillDRQueryUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author zhouquan3
 */
public class WAPContentHandlerImpl {
    
    public Map getTailList(JSONObject data){
        System.out.println(data.toString(1));
        Map result=new HashMap();
        
        int charge1Index=0;
        int charge4Index=0;
        int startTimeIndex=0;
        int endTimeIndex=0;
        
        long charge1=0l;
        long charge4=0l;

        //**处理字段key**
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        int fieldsSize = fields.size();
        listCaption.add("序号");
        for (int i = 0; i < fieldsSize; i++) {
            String temp = (String) fields.get(i);
            if ("信息费".equals(temp)) { 
                listCaption.add("信息费(元)");
                charge4Index=i;
            } else if ("通讯费".equals(temp)) {
                charge1Index=i;
                listCaption.add("通信费(元)");
            } else if ("WAP信息计费类别".equals(temp)) {
                listCaption.add("计费类型");
            } else if ("开始时间".equals(temp)) {
                startTimeIndex=i;
                listCaption.add(temp);
            } else if ("结束时间".equals(temp)) {
                endTimeIndex=i;
                listCaption.add(temp);
            } else {
                listCaption.add(temp);
            }
        }
        result.put("listCaption", listCaption);
        
        //**处理数据**++++++**处理表尾数据统计**
        
        List content = data.getJSONArray("contents");
        List listBody = new ArrayList();       
        int contentSize = content.size();
        for (int i = 0; i < contentSize; i++) {
            List singleRow = (List) content.get(i);
            List singleContent = new ArrayList();
            singleContent.add(String.valueOf(i+1));//序号0开始＋1
            int columnSize = singleRow.size();
            for (int j = 0; j < columnSize; j++) {
                String input = (String) singleRow.get(j);
                if(j==charge1Index){
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge1 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                }else if(j==charge4Index){
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge4 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else if (j == startTimeIndex) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Date(input));
                } else if (j == endTimeIndex) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Date(input));
                } else{
                    singleContent.add(input);
                }
            }
            listBody.add(singleContent);
        }
        result.put("listBody", listBody);
        //**处理表尾数据统计**
        List listTail = new ArrayList();
        listTail.add("小计");
        for (int i = 0; i < fieldsSize; i++) {
            if (i == charge1Index) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge1)));
            } else if (i == charge4Index) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge4)));
            }else{
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
