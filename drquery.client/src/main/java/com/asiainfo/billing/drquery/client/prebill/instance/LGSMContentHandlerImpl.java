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
public class LGSMContentHandlerImpl {
   public Map getTailList(JSONObject data){
        System.out.println(data.toString(1));
        Map result=new HashMap(); 
        
        int vplmn1Index=0;
        int vplmn2Index=0;
        int durationIndex=0;
        int charge1Index=0;
        int charge2Index=0;
        int charge4Index=0;
        int freeResValIndex1=0;
        int freeResValIndex2=0;
        int freeResValIndex3=0;
        int freeResCodeIndex1=0;
        int freeResCodeIndex2=0;
        int freeResCodeIndex3=0;
        int billUniCodeIndex=0;
        int startTimeIndex=0;
        int jizhanIndex=0;
        
        long durationCount=0l;
        long freeResValCount1=0l;
        long freeResValCount2=0l;
        long freeResValCount3=0l;

        long charge1=0l;
        long charge2=0l;
        long charge4=0l;
        
        //**处理字段key**
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        int fieldsSize = fields.size();
        listCaption.add("序号");
        for (int i = 0; i < fieldsSize; i++) {
            String temp = (String) fields.get(i);
            if ("本地通话费".equals(temp)) { 
                charge1Index=i;
                listCaption.add("本地通话费(元)");
                listCaption.add("普通附加费(元)");//默认添0 戴贝确认
            }else if ("一级漫游地".equals(temp)) {
                vplmn1Index=i;
                listCaption.add("漫游地");
            }else if ("二级漫游地".equals(temp)) {
                vplmn2Index=i;
                continue;
            }else if ("通话起始时间".equals(temp)) {
                startTimeIndex=i;
                listCaption.add("通话开始时间");
            }else if ("长途通话费".equals(temp)) {
                charge2Index=i;
                listCaption.add("长途通话费(元)");
                listCaption.add("长途附加费(元)");//默认添0 戴贝确认
            }else if ("通话时长".equals(temp)) { 
                durationIndex=i;
                listCaption.add(temp);
            }else if ("免费分钟数1".equals(temp)) {
                freeResValIndex1=i;
                listCaption.add(temp);
            }else if ("免费分钟数2".equals(temp)) {
                freeResValIndex2=i;
                listCaption.add(temp);
            }else if ("免费分钟数3".equals(temp)) {
                freeResValIndex3=i;
                listCaption.add(temp);
            }else if ("呼转号码".equals(temp)) { 
                listCaption.add("主叫号码");
            }else if ("信息费".equals(temp)) { 
                charge4Index=i;
                listCaption.add(temp);
            }else if ("免费资源1".equals(temp)) { 
                freeResCodeIndex1=i;
                listCaption.add(temp);
            }else if ("免费资源2".equals(temp)) { 
                freeResCodeIndex2=i;
                listCaption.add(temp);
            }else if ("免费资源3".equals(temp)) { 
                freeResCodeIndex3=i;
                listCaption.add(temp);
            }else if ("基站代码".equals(temp)) { 
                jizhanIndex=i;
                listCaption.add(temp);
                listCaption.add("副号码");//副号码
            }else if ("长话单标志".equals(temp)) {                
                listCaption.add(temp);
                billUniCodeIndex=i;
                listCaption.add("话单唯一编号");//增加 话单唯一编号 默认添0 戴贝确认
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
            int columnSize = singleRow.size();
            for (int j = 0; j < columnSize; j++) {
                String input = (String) singleRow.get(j);
                if(j==charge1Index){
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge1+= PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                    //增加 普通附加费(元) 添0
                    singleContent.add("0.00");
                }else if(j==charge2Index){
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge2+= PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                    //增加 长途附加费(元) 添0
                    singleContent.add("0.00");
                }else if(j==charge4Index){
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                    charge4+= PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                }else if(j==vplmn1Index){
                    singleContent.add(singleRow.get(vplmn1Index).toString()
                        + singleRow.get(vplmn2Index).toString());
                }else if(j==vplmn2Index){
                    continue;
                }else if(j==durationIndex){
                    durationCount+=PreBillDRQueryUtil.formatNumber(input);
                    singleContent.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(input)));
                }else if(j==freeResValIndex1){
                    freeResValCount1+=PreBillDRQueryUtil.formatNumber(input)/60;
                    singleContent.add(PreBillDRQueryUtil.formatNumber(input)/60);
                }else if(j==freeResValIndex2){
                    freeResValCount2+=PreBillDRQueryUtil.formatNumber(input)/60;
                    singleContent.add(PreBillDRQueryUtil.formatNumber(input)/60);
                }else if(j==freeResValIndex3){
                    freeResValCount3+=PreBillDRQueryUtil.formatNumber(input)/60;
                    singleContent.add(PreBillDRQueryUtil.formatNumber(input)/60);
                }else if(j==freeResCodeIndex1){
                    singleContent.add(input);
                }else if(j==freeResCodeIndex2){
                    singleContent.add(input);
                }else if(j==freeResCodeIndex3){
                    singleContent.add(input);
                }else if(j==billUniCodeIndex){
                    singleContent.add(input);
                    singleContent.add("0");//增加 话单唯一编号 添0
                }else if(j==startTimeIndex){
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
                }else if(j == jizhanIndex){
                    singleContent.add(input);
                    singleContent.add("");
                }else{
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
            if(i==durationIndex){
                listTail.add(PreBillDRQueryUtil.formatTimeLength(String.valueOf(durationCount)));
            }else if(i==charge1Index){
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge1)));
                //增加 普通附加费(元) 添0
                listTail.add("0.00");
            }else if(i==charge2Index){
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge2)));
                //增加 长途附加费(元) 添0
                listTail.add("0.00");
            }else if(i==charge4Index){
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge4))); 
            }else if(i==freeResValIndex1){
                listTail.add(String.valueOf(freeResValCount1));
            }else if(i==freeResValIndex2){
                listTail.add(String.valueOf(freeResValCount2));
            }else if(i==freeResValIndex3){
                listTail.add(String.valueOf(freeResValCount3));
            }else if(i==vplmn2Index){
                continue;
            }else if(i==billUniCodeIndex){
                listTail.add("");
                //增加 话单唯一编号 添0
                listTail.add("");
            }else if(i == jizhanIndex){
                listTail.add("");
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
