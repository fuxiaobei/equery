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
 * @author zhouquan3
 */
public class WLANIOContentHandlerImpl {
    
    public Map getTailList(JSONObject data) {
        System.out.println(data.toString(1));
        Map result = new HashMap();
        
        int startTimeIndex=0;
        int durationIndex=0;
        int upVolumeIndex=0;
        int downVolumeIndex=0;
        int charge1Index=0;
        
        long duration=0l;
        long upVolume=0l;
        long downVolume=0l;
        long charge1=0l;
        //**处理字段key**
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        int fieldsSize = fields.size();
        listCaption.add("序号");
        for (int i = 0; i < fieldsSize; i++) {
            String temp = (String) fields.get(i);
            if ("连接开始时间".equals(temp)) { 
                listCaption.add("开始时间");
                startTimeIndex=i;
            } else if ("连接时长".equals(temp)) { 
                listCaption.add("时长");
                durationIndex=i;
            } else if ("下行数据流量".equals(temp)) { 
                listCaption.add("下行流量");
                downVolumeIndex=0;
            } else if ("上行数据流量".equals(temp)) { 
                listCaption.add("上行流量");
                upVolumeIndex=0;
            } else if ("通信费".equals(temp)) { 
                listCaption.add("通信费(元)");
            } else if ("热点编号".equals(temp)) { 
                listCaption.add("HOSTSPOT_ID");
            } else if ("认证标识".equals(temp)) { 
                listCaption.add("认证类型");
            } else if ("用户号码".equals(temp)) { 
                listCaption.add("登录账号");
            } else if ("保留字段3".equals(temp)) { 
                listCaption.add("AP的MAC地址");
            } else if ("保留字段2".equals(temp)) { 
                listCaption.add("上网终端MAC地址");
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
            int columnSize = singleRow.size();
            singleContent.add(new Integer(i + 1));//序号从1开始
            for (int j = 0; j < columnSize; j++) {
                String input = (String) singleRow.get(j);
                if (j == startTimeIndex) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == durationIndex) {
                    duration += PreBillDRQueryUtil.formatNumber(input);
                    singleContent.add(PreBillDRQueryUtil.formatTimeLength(input));
                } else if (j == upVolumeIndex) {
                    singleContent.add(input);
                    upVolume += PreBillDRQueryUtil.formatNumber(input);
                } else if (j == downVolumeIndex) {
                    singleContent.add(input);
                    downVolume += PreBillDRQueryUtil.formatNumber(input);
                } else if (j == charge1Index) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge1+= PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else {
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
            if (i == durationIndex) {
                listTail.add("");
            } else if (i == charge1Index) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge1)));
            } else if (i == upVolumeIndex) {
                listTail.add(String.valueOf(upVolume));
            } else if (i == downVolumeIndex) {
                listTail.add(String.valueOf(downVolume));
            } else if (i == durationIndex) {
                listTail.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(duration)));
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
