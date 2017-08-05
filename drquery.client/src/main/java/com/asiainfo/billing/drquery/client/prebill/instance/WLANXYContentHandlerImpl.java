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

public class WLANXYContentHandlerImpl{

    public Map getTailList(JSONObject data) {
        Map result = new HashMap();
        //**处理字段key**
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        int fieldsSize = fields.size();
        listCaption.add("序号");
        for (int i = 0; i < fieldsSize; i++) {
            String temp = (String) fields.get(i);
            if ("连接开始时间".equals(temp)) { 
                listCaption.add("开始时间");
            } else if ("连接时长".equals(temp)) { 
                listCaption.add("时长");
            } else if ("下行数据流量".equals(temp)) { 
                listCaption.add("下行流量");
            } else if ("上行数据流量".equals(temp)) { 
                listCaption.add("上行流量");
            } else if ("通信费".equals(temp)) { 
                listCaption.add("通信费(元)");
            } else if ("热点编号".equals(temp)) { 
                listCaption.add("HOSTSPOT_ID");
            } else if ("认证标识".equals(temp)) { 
                listCaption.add("认证类型");
            } else if ("用户号码".equals(temp)) { 
                listCaption.add("登录账号");
            } else if ("保留字段1".equals(temp)) { 
                listCaption.add("AP的MAC地址");
            } else if ("保留字段3".equals(temp)) { 
                listCaption.add("上网终端MAC地址");
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
        
        
        List content = data.getJSONArray("contents");
        List listBody = new ArrayList();
        int contentSize = content.size();
        for (int i = 0; i < contentSize; i++) {
            List singleRow = (List) content.get(i);
            List singleContent = new ArrayList();
            singleContent.add(String.valueOf(i+1));
            for (int j = 0; j < fieldsSize; j++) {
                String input = (String) singleRow.get(j);
                if(j == 0) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if(j == 1) {
                    singleContent.add(PreBillDRQueryUtil.formatTimeLength(input));
                    total1 += PreBillDRQueryUtil.formatNumber(input);                    
                } else if(j == 2) {
                    singleContent.add(input);
                    total2 += PreBillDRQueryUtil.formatNumber(input);                    
                } else if(j == 3) {
                    singleContent.add(input);
                    total3 += PreBillDRQueryUtil.formatNumber(input);                    
                } else if(j == 4) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total4 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
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
            if (i == 1 ) {
                listTail.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(total1)));
            } else if (i == 2 ) {
                listTail.add(String.valueOf(total2));
            } else if (i == 3 ) {
                listTail.add(String.valueOf(total3));
            } else if (i == 4 ) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total4)));
            } else {
                listTail.add("");
            }
        }
        result.put("listTail", listTail);
        //**处理统计数据**
        if (!"0".equals(data.getString("count"))){
            List listSpecialSum = PreBillDRQueryUtil.formatStats(data.getJSONArray("stats"));
            result.put("listSpecialSum", listSpecialSum);
        }else{
            List listSpecialSum=new JSONArray();
            result.put("listSpecialSum", listSpecialSum);
        }     
        return result;
    }
}
