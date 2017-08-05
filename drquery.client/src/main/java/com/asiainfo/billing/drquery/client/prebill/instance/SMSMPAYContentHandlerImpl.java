package com.asiainfo.billing.drquery.client.prebill.instance;

import com.asiainfo.billing.drquery.client.prebill.PreBillDRQueryUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SMSMPAYContentHandlerImpl {

    public Map<String, List> getTailList(JSONObject data) {
        System.out.println(data.toString(1));
        Map<String, List> result = new HashMap<String, List>();
        //**处理字段key**
        long tailCharge4Sum = 0;
        long tailCharge1Sum = 0;
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        listCaption.add("序号");
        int fieldsSize = fields.size();
        for (int i = 0; i < fieldsSize; i++) {
            String temp = fields.get(i).toString();
            if ("一级漫游地".equals(temp)) {
                listCaption.add("漫游地");
            } else if ("二级漫游地".equals(temp)) {
                continue;
            } else if ("记录类型".equals(temp)) {
                listCaption.add("呼叫类型");
            } else if ("开始时间".equals(temp)) {
                listCaption.add("发送时间");
            } else if ("信息费".equals(temp)) {
                listCaption.add("交易金额(元)");
            } else if ("交易类型".equals(temp)) {
                listCaption.add("交易类型");
                listCaption.add("备注");
            } else {
                listCaption.add(temp);
            }


        }
        //增加“漫游地” 合并一级以及二级漫游地

        result.put("listCaption", listCaption);
        //**处理数据**++++++**处理表尾数据统计**

        long total1 = 0L;
        List content = data.getJSONArray("contents");
        List listBody = new ArrayList();
        int contentSize = content.size();
        for (int i = 0; i < contentSize; i++) {
            List singleRow = (List) content.get(i);
            List singleContent = new ArrayList();
            singleContent.add(String.valueOf(i + 1));
            for (int j = 0; j < fieldsSize; j++) {
                String input = (String) singleRow.get(j);
                if (j == 1) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == 2) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == 4) {
                    singleContent.add(input + (String) singleRow.get(5));
                    j++;
                } else if (j == 6) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total1 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else {
                    singleContent.add(input);
                }
            }
            singleContent.add("");
            listBody.add(singleContent);
        }
        result.put("listBody", listBody);
        //**处理表尾数据统计**
        List listTail = new ArrayList();
        listTail.add("");
        for (int i = 0; i < fieldsSize; i++) {
            if (i == 6) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total1)));
            } else if (i == 4) {
                listTail.add("");
                i++;
            } else {
                listTail.add("");
            }
        }
        listTail.add("");
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
