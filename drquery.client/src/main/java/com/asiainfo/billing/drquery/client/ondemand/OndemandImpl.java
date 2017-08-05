package com.asiainfo.billing.drquery.client.ondemand;

import net.sf.json.JSONObject;
import com.asiainfo.billing.drquery.client.impl.DRQueryClientImpl;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;

/**
 * 
 * @author daibei
 * @version 1.0
 */
public final class OndemandImpl implements Ondemand{
    /**
     * 
     * @param llDomainId    域编号，默认domainId=-1
     * @param llChannelId   各个渠道
     * @param strBillId     手机号
     * @param strStartDate  起始时间 "yyyy-mm-dd"
     * @param strStopDate   截止时间 "yyyy-mm-dd"
     * @param strBillType   手机业务类型
     * @param fieldSeq      字段列表
     * @param billIdSeq     电话单据列表
     * @param sErrMsg       错误信息
     * @return              成功或失败
     */
    public int get_billId
        (
        long llDomainId, //域编号，默认domainId=-1
        long llChannelId,    //各个渠道
        String strBillId, //手机号
        String strStartDate, //起始时间 "yyyy-mm-dd"
        String strStopDate, //截止时间 "yyyy-mm-dd"
        String strBillType, //手机业务类型
        List<String>  fieldSeq, //字段列表
        List<String>  billIdSeq, //电话单据列表
        String    sErrMsg //错误信息
        ){
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("billId", strBillId);
            jsonParam.put("billMonth", strStartDate.substring(0, 8));
            jsonParam.put("fromDate", strStartDate);
            jsonParam.put("thruDate", strStopDate);
                    
            DRQueryClientImpl drQueryClientImpl = new DRQueryClientImpl();               
            JSONObject ret = drQueryClientImpl.buisTypeQuery(jsonParam);
            
            sErrMsg = ret.getString("message");
            String result = ret.getString("result");
            
            if (!result.equals("success")){
            return -1;}
          
            JSONArray datas = ret.getJSONArray("data");
            for(int i=0;i<datas.size();i++){
                JSONObject data = datas.getJSONObject(i);
                String billId = data.getString("code");
                billId = String.valueOf(i) + "|" + strBillId + "|" + strStopDate + "|" + billId + "|"; 
                billIdSeq.add(billId);
            }         
            
            fieldSeq.add("电话号码");
            fieldSeq.add("结账日期");
            fieldSeq.add("话单类型");
        
    return 0;
    }
    /**
     * 
     * @param llOperId      操作员ID默认-1
     * @param llOrgId       组织ID默认-1
     * @param llDomainId    域编号，默认domainId=-1
     * @param llChannelId   各个渠道
     * @param strClientIp   客户端ip地址
     * @param strFolderName 根据目录列表中的选中的目录的字符串,默认'Boss'
     * @param strBillId     手机号码
     * @param strStartDate  起始时间 "yyyy-mm-dd"
     * @param strStopDate   截止时间 "yyyy-mm-dd"
     * @param strBillType   手机业务类型
     * @param llOperaterType 0:查询 1:导出 2:过滤
     * @param llStartLine   返回记录起始行，默认-1
     * @param llStopLine    返回记录结束行，默认-1
     * @param strFilterField 过滤字段值，默认为空字符串
     * @param strFilterValue 过滤字段值，默认为空字符串
     * @param llRecordCount 总记录数
     * @param seqCaption    话单标题行
     * @param seqBody       话单数据主体
     * @param seqTail       话单统计数据
     * @param sErrMsg       错误信息
     * @return              成功或失败
     */
    public int get_odDocDetails
    (
        long    llOperId, //操作员ID默认-1
        long    llOrgId, //组织ID默认-1
        long llDomainId, //域编号，默认domainId=-1
        long llChannelId,    //各个渠道 
        String strClientIp, //客户端ip地址
        String strFolderName, //根据目录列表中的选中的目录的字符串,默认'Boss'
        String       strBillId, //手机号码
        String strStartDate, //起始时间 "yyyy-mm-dd"
        String strStopDate, //截止时间 "yyyy-mm-dd"
        String strBillType, //手机业务类型
        long  llOperaterType, //0:查询 1:导出 2:过滤
        long llStartLine, //返回记录起始行，默认-1
        long llStopLine, //返回记录结束行，默认-1
        String strFilterField, //过滤字段名，默认为空字符串
        String strFilterValue, //过滤字段值，默认为空字符串
        long llRecordCount, //总记录数
        List<String>   seqCaption, //话单标题行
        List<List<String>>   seqBody, //话单数据主体
        List<String>   seqTail, //话单统计数据
        String    sErrMsg //错误信息
    ){
    JSONObject jsonParam = new JSONObject();
    jsonParam.put("billId", strBillId);
    jsonParam.put("billMonth", strStartDate.substring(0, 6));
    jsonParam.put("fromDate", strStartDate);
    jsonParam.put("thruDate", strStopDate);
    jsonParam.put("busiType", strBillType);
    jsonParam.put("startIndex", llStartLine);
    jsonParam.put("stopIndex", llStopLine);
    
    DRQueryClientImpl drQueryClientImpl = new DRQueryClientImpl();               
    JSONObject ret = drQueryClientImpl.commonQuery(jsonParam);
    
    sErrMsg = ret.getString("message");
    String result = ret.getString("result");
            
    if (!result.equals("success")){
        return -1;}
    
    JSONObject data = ret.getJSONObject("data"); 
    llRecordCount = Integer.valueOf(data.getString("count"));
    
   JSONArray temp = data.getJSONArray("fields");
   for(int i=0;i<temp.size();i++) {
        seqCaption.add(temp.getString(i));
   }
    
    //seqBody = data.getJSONArray("contents");
    
    temp = data.getJSONArray("contents");
    for(int i=0;i<temp.size();i++) {
        List<String> line = new ArrayList<String>();
        JSONArray lineArr = temp.getJSONArray(i);
        for(int j=0;j<lineArr.size();j++) {
            line.add(lineArr.getString(j));
        }
        seqBody.add(line);
    }
    
    JSONArray stats = data.getJSONArray("stats");
    
    for(int i=0;i<stats.size();i++){
        JSONObject stat = stats.getJSONObject(i);
        String title = stat.getString("title");
        String tail = stat.getString("stat");
        tail = ";" + title +";"+ tail;
        seqTail.add(tail);
    }          
    return 0;
    }
  
}
