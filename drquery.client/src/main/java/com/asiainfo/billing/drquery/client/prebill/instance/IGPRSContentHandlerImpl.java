/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class IGPRSContentHandlerImpl {
    
    public Map getTailList(JSONObject data){
        System.out.println(data.toString(1));
        Map result=new HashMap();
            
        int charge1Index=0;
        int vplmn1Index=0;
        int vplmn2Index=0;
        int durationIndex=0;
        int upVolumeIndex=0;
        int downVolumeIndex=0;
        int startTimeIndex=0;
        int apnNiIndex=0;
        
        long durationCount=0l;
        long upVolume=0l;
        long downVolume=0l;
        
        long charge1=0l;
//        BigDecimal charge1=BigDecimal.valueOf(0l);
        //**处理字段key**
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        int fieldsSize = fields.size();
        listCaption.add("序号");
        //默认填 业务名称 业务代码
        listCaption.add("业务名称");
        listCaption.add("业务代码");
        for (int i = 0; i < fieldsSize; i++) {
            String temp = (String) fields.get(i);
            if ("服务识别码".equals(temp)) { 
                listCaption.add("业务代码");
            }else if ("开始时间".equals(temp)) {
                listCaption.add(temp);
                startTimeIndex=i;
            } else if ("业务类型".equals(temp)) {
                listCaption.add("业务名称");
            } else if ("通信费".equals(temp)) {
                charge1Index=i;
                listCaption.add("通信费(元)");
                //添加 计费类别 默认填空
                listCaption.add("计费类别");
            }else if ("一级漫游地".equals(temp)) {
                vplmn1Index=i;
                listCaption.add("漫游地");
            }else if ("二级漫游地".equals(temp)) {
                vplmn2Index=i;
                listCaption.add("运营商信息");
            }else if ("原文件名".equals(temp)) { 
                listCaption.add("源文件名");
            }else if ("上行流量".equals(temp)) {
                upVolumeIndex=i;
                listCaption.add(temp);
            }else if ("下行流量".equals(temp)) { 
                listCaption.add(temp);
                downVolumeIndex=i;
            }else if ("接入网络标识".equals(temp)) { 
                listCaption.add(temp);
                apnNiIndex=i;
                //留字段 业务名称 默认填空
                listCaption.add("业务名称");
            }else if ("时长".equals(temp)) { 
                listCaption.add(temp);
                durationIndex=i;
            }else if ("接入网络类型".equals(temp)) { 
                listCaption.add("网络类型");
            }else {
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
            //默认填 业务名称 业务代码
            singleContent.add(String.valueOf("其它"));
            singleContent.add(String.valueOf(""));
            int columnSize = singleRow.size();
            for (int j = 0; j < columnSize; j++) {
                String input = (String) singleRow.get(j);
                if (j == charge1Index) {
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge1 += PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                    //添加 计费类别 默认填空
                    singleContent.add("");
                } else if (j == vplmn1Index) {
                    singleContent.add(singleRow.get(vplmn1Index).toString());
                } else if (j == durationIndex) {
                    durationCount += PreBillDRQueryUtil.formatNumber(input);
                    singleContent.add(PreBillDRQueryUtil.formatTimeLength(input));
                } else if (j == upVolumeIndex) {
                    singleContent.add(input);
                    upVolume += PreBillDRQueryUtil.formatNumber(input);
                } else if (j == downVolumeIndex) {
                    singleContent.add(input);
                    downVolume += PreBillDRQueryUtil.formatNumber(input);
                } else if (j == vplmn2Index) {
                    singleContent.add(singleRow.get(vplmn2Index).toString());
                } else if (j == startTimeIndex) {
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                } else if (j == apnNiIndex) {
                    singleContent.add(input);
                    //业务名称 默认留空
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
        listTail.add("小计");
        //默认填 业务名称 业务代码
        listTail.add("");
        listTail.add("");
        for (int i = 0; i < fieldsSize; i++) {
            if(i==durationIndex){
                listTail.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(durationCount)));
            }else if(i==upVolumeIndex){
                listTail.add(String.valueOf(upVolume));
            }else if(i==downVolumeIndex){
                listTail.add(String.valueOf(downVolume));
            }else if(i==charge1Index){
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge1)));
                //添加 计费类别 默认填空
                listTail.add("");
            }else if(i==apnNiIndex){
                listTail.add("");
                //添加 业务名称 默认填空
                listTail.add("");
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
