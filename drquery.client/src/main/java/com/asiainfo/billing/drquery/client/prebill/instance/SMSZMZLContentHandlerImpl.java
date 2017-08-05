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
public class SMSZMZLContentHandlerImpl {
    
    public Map getTailList(JSONObject data) {
        System.out.println(data.toString(1));
        Map result = new HashMap();
        //**处理字段key**
        int vplmn1 = 0;
        int vplmn2 = 0;
        int tailCharge4SumIndex = 0;
        int startTimeIdx = 0;
        int stopTimeIdx = 0;
        
        long charge4=0l;
        
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        int fieldsSize = fields.size();
        listCaption.add("序号");
        for (int i = 0; i < fieldsSize; i++) {
            String temp = fields.get(i).toString();
            if ("一级漫游地".equals(temp)) {
                //增加“漫游地” 合并一级以及二级漫游地
                listCaption.add("漫游地");
                vplmn1 = i;
            } else if ("二级漫游地".equals(temp)) {
                vplmn2 = i;
                continue;
            } else if ("开始时间".equals(temp)) {
                listCaption.add("发送时间");
                startTimeIdx = i;
            } else if ("结束时间".equals(temp)) {
                listCaption.add("结束时间");
                stopTimeIdx = i;
            } else if ("对方号码".equals(temp)) {
                listCaption.add("接收手机号码");
            } else if ("信息费用".equals(temp)) {
                listCaption.add("信息费(元)");
                listCaption.add("信息费优惠(元)");
                tailCharge4SumIndex = i;
            } else if ("商户代码".equals(temp)) {
                listCaption.add("业务代码");
            } else if ("商品代码".equals(temp)) {
                listCaption.add("服务代码");
            } else if ("用户计费类别".equals(temp)) {
                listCaption.add("计费类别");
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
            for (int j = 0; j < fieldsSize; j++) {
                String input = (String) singleRow.get(j);
                if (j == vplmn1) {
                    singleContent.add(singleRow.get(vplmn1).toString()
                            + singleRow.get(vplmn2).toString());
                } else if (j == vplmn2) {
                    continue;
                } else if (j == startTimeIdx) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == stopTimeIdx) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == tailCharge4SumIndex) {//tail逻辑
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge4 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                    singleContent.add("0.00");
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
            if (i == vplmn2) {
                continue;
            } else if (i != tailCharge4SumIndex) {
                listTail.add("");
            } else {//tailCharge4SumIndex
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge4)));
                listTail.add("0.00");
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
