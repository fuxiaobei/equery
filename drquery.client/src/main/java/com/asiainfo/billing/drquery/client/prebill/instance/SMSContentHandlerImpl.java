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
public class SMSContentHandlerImpl {

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
            if ("信息费用".equals(temp)) {
                listCaption.add("通信费(元)");
            } else if ("普通附加费".equals(temp)) {
                listCaption.add("信息优惠费(元)");
                listCaption.add("备注");
            } else if ("长消息标识".equals(temp)) {
                listCaption.add("长短信标识");
            } else if ("消息长度".equals(temp)) {
                listCaption.add("信息长度");
            } else if ("一级漫游地".equals(temp)) {
                listCaption.add("漫游地");
                listCaption.add("业务名称");
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
            singleContent.add(String.valueOf(i + 1));
            for (int j = 0; j < fieldsSize; j++) {
                String input = (String) singleRow.get(j);
                if (j == 1) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == 3) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total1 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else if (j == 4) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    total1 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                    singleContent.add("");
                } else if (j == 11) {
                    singleContent.add(input);
                    singleContent.add("");
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
            if (i == 3) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total1)));
            } else if (i == 4) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total2)));
                listTail.add("");
            } else if (i == 11) {
                listTail.add("");
                listTail.add("");
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
