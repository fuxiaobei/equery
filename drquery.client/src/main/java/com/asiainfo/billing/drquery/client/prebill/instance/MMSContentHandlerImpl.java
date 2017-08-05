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
public class MMSContentHandlerImpl {

    public Map getTailList(JSONObject data) {
        Map result = new HashMap();
        //**处理字段key**
        int vplmnIndex1=0;
        int vplmnIndex2=0;
        int tailCharge4SumIndex = 0;
        int tailCharge1SumIndex = 0;
        int startTimeIndex=0;
        int endTimeIndex=0;
        
        long charge4=0l;
        long charge1=0l;

        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        listCaption.add("序号");
        int fieldsSize = fields.size();
        for (int i = 0; i < fieldsSize; i++) {
            String temp = fields.get(i).toString();
            if ("一级漫游地".equals(temp)) {
                vplmnIndex1 = i;
                //增加“漫游地” 合并一级以及二级漫游地
                listCaption.add("漫游地");
            } else if ("二级漫游地".equals(temp)) {
                vplmnIndex2 = i;
                continue;
            }else if ("发送时间".equals(temp)) {
                startTimeIndex=i;
                listCaption.add(temp);
            } else if ("接收方地址".equals(temp)) {
                listCaption.add("接收号码");
            } else if ("转发的手机号码".equals(temp)) {
                listCaption.add("转发手机号码");
            } else if ("应用类型".equals(temp)) {
                listCaption.add("MMS类型");
            } else if ("通信费".equals(temp)) {
                listCaption.add("通信费(元)");
                tailCharge1SumIndex = i;
            } else if ("信息费".equals(temp)) {
                listCaption.add("信息费(元)");
                tailCharge4SumIndex = i;
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
            singleContent.add(i + 1);//序号从1开始
            int columnSize = singleRow.size();
            for (int j = 0; j < columnSize; j++) {
                String input = (String) singleRow.get(j);
                if (j == vplmnIndex1) {
                    singleContent.add(singleRow.get(vplmnIndex1).toString()
                            + singleRow.get(vplmnIndex2).toString());
                } else if (j == vplmnIndex2) {
                    continue;
                } else if (j == tailCharge4SumIndex) {//tail逻辑
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge4 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else if (j == tailCharge1SumIndex) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge1 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else if (j == startTimeIndex) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Date(input));
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
            if (i == tailCharge1SumIndex) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge1)));
            } else if (i == vplmnIndex2) {
                    continue;
            } else if (i == tailCharge4SumIndex) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge4)));
            } else if (i == vplmnIndex2) {
                continue;
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
