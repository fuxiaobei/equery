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

public class WLANAUTOContentHandlerImpl {

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
                listCaption.add("帐号");
            } else if ("认证标识".equals(temp)) {
                listCaption.add("认证类型");
            } else if ("连接开始时间".equals(temp)) {
                listCaption.add("上网开始时间");
            } else if ("连接结束时间".equals(temp)) {
                listCaption.add("上网结束时间");
            } else if ("连接时长".equals(temp)) {
                listCaption.add("上网时长");
            } else if ("上行数据流量".equals(temp)) {
                listCaption.add("上行流量");
            } else if ("下行数据流量".equals(temp)) {
                listCaption.add("下行流量");
            } else if ("热点编号".equals(temp)) {
                listCaption.add("热点标识");
            } else if ("AC_ADDRESS".equals(temp)) {
                listCaption.add("AC的IP地址");
            } else if ("保留字段3".equals(temp)) {
                listCaption.add("上网终端MAC地址");
            } else if ("保留字段1".equals(temp)) {
                listCaption.add("AP的MAC地址");
            } else if ("STOP_CAUSE".equals(temp)) {
                listCaption.add("断线原因");
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
            singleContent.add(String.valueOf(i + 1));
            for (int j = 0; j < fieldsSize; j++) {
                String input = (String) singleRow.get(j);
                if (j == 3) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == 4) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == 5) {
                    singleContent.add(PreBillDRQueryUtil.formatTimeLength(input));
                    total1 += PreBillDRQueryUtil.formatNumber(input);
                } else if (j == 6) {
                    singleContent.add(input);
                    total2 += PreBillDRQueryUtil.formatNumber(input);
                } else if (j == 7) {
                    singleContent.add(input);
                    total3 += PreBillDRQueryUtil.formatNumber(input);
                } else if (j == 10) {
                    singleContent.add(input);
                    total4 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                } else if (j == 13) {
                    if("1".equals(input)) { singleContent.add("用户请求下线"); }
                    else if("2".equals(input)) { singleContent.add("端口连接丢失"); }
                    else if("3".equals(input)) { singleContent.add("不能提供服务，主要指连接异常中断"); }
                    else if("4".equals(input)) { singleContent.add("空闲超时"); }
                    else if("5".equals(input)) { singleContent.add("会话超时"); }
                    else if("6".equals(input)) { singleContent.add("管理员复位端口或会话"); }
                    else if("7".equals(input)) { singleContent.add("管理员重启NAS"); }
                    else if("8".equals(input)) { singleContent.add("端口错误，需要中断会话"); }
                    else if("9".equals(input)) { singleContent.add("NAS出错，要求中断会话"); }
                    else if("10".equals(input)) { singleContent.add("NAS因为其他原因要求中断会话"); }
                    else if("11".equals(input)) { singleContent.add("NAS意外重启"); }
                    else if("12".equals(input)) { singleContent.add("NAS认为不再需要保留该端口而中断会话"); }
                    else if("13".equals(input)) { singleContent.add("NAS需要重新优先分配该端口而中断会话"); }
                    else if("14".equals(input)) { singleContent.add("NAS需要挂起端口而中断当前会话"); }
                    else if("15".equals(input)) { singleContent.add("NAS需要挂起端口而中断当前会话"); }
                    else if("16".equals(input)) { singleContent.add("NAS为新会话回调而中断当前会话"); }
                    else if("17".equals(input)) { singleContent.add("用户输入错误"); }
                    else if("18".equals(input)) { singleContent.add("主机请求中断"); }
                    else { singleContent.add(""); }
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
            if (i == 5) {
                listTail.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(total1)));
            } else if (i == 6) {
                listTail.add(String.valueOf(total2));
            } else if (i == 7) {
                listTail.add(String.valueOf(total3));
            } else if (i == 10) {
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(total4)));
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
