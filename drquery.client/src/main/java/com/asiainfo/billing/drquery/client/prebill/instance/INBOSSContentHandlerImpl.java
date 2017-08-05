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
public class INBOSSContentHandlerImpl {
    
    public Map getTailList(JSONObject data){
        System.out.println(data.toString(1));
        Map result=new HashMap();
            
        
        int vplmn1Index=0;
        int vplmn2Index=0;
        int durationIndex=0;
        int charge1Index=0;
        int charge2Index=0;
        int charge3Index=0;
        int charge4Index=0;
        int freeResValIndex1=0;
        int freeResValIndex2=0;
        int freeResValIndex3=0;
        int startTimeIndex=0;
        
        long durationCount=0l;
        long freeResValCount1=0l;
        long freeResValCount2=0l;
        long freeResValCount3=0l;
        
        
        long charge1=0l;
        long charge2=0l;
        long charge3=0l;
        long charge4=0l;
        
        //**处理字段key**
        List listCaption = new ArrayList();
        List fields = data.getJSONArray("fields");
        int fieldsSize = fields.size();
        listCaption.add("序号");
        for (int i = 0; i < fieldsSize; i++) {
            String temp = (String) fields.get(i);
            if ("通话时长".equals(temp)) { 
                durationIndex=i;
                listCaption.add(temp);
            }else if ("一级漫游地".equals(temp)) {
                vplmn1Index=i;
                listCaption.add("漫游地");
            }else if ("二级漫游地".equals(temp)) {
                vplmn2Index=i;
                continue;
            }else if ("开始时间".equals(temp)) {
                startTimeIndex=i;
                listCaption.add(temp);
            }else if ("本地通话费".equals(temp)) {
                charge1Index=i;
                listCaption.add("通话费(元)");
            }else if ("信息费".equals(temp)) { 
                charge4Index=i;
                listCaption.add("普通附加费(元)");
            }else if ("长途通话费".equals(temp)) {
                charge2Index=i;
                listCaption.add("长途费(元)");
            }else if ("长途附加费".equals(temp)) { 
                charge3Index=i;
                listCaption.add("长途附加费(元)");
            }else if ("免费分钟数1".equals(temp)) { 
                freeResValIndex1=i;
                listCaption.add(temp);
            }else if ("免费分钟数2".equals(temp)) { 
                freeResValIndex2=i;
                listCaption.add(temp);
            }else if ("免费分钟数3".equals(temp)) { 
                freeResValIndex3=i;
                listCaption.add(temp);
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
                }else if(j==charge2Index){
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                        charge2+= PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
                }else if(j==charge3Index){
                    singleContent.add(PreBillDRQueryUtil.formatMoney(input));
                        charge3+= PreBillDRQueryUtil.formatNumber(PreBillDRQueryUtil.formatMoneyForSum(input));
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
                    freeResValCount1+=PreBillDRQueryUtil.formatNumber(input);
                    singleContent.add(input);
                }else if(j==freeResValIndex2){
                    freeResValCount2+=PreBillDRQueryUtil.formatNumber(input);
                    singleContent.add(input);
                }else if(j==freeResValIndex3){
                    freeResValCount3+=PreBillDRQueryUtil.formatNumber(input);
                    singleContent.add(input);
                }else if(j==startTimeIndex){
                    singleContent.add(PreBillDRQueryUtil.formatTime2Time(input));
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
            }else if(i==charge2Index){
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge2)));  
            }else if(i==charge3Index){
                listTail.add(PreBillDRQueryUtil.formatMoney(String.valueOf(charge3)));  
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
