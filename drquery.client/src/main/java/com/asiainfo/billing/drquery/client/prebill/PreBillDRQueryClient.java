package com.asiainfo.billing.drquery.client.prebill;

import com.asiainfo.billing.drquery.client.DRQueryClient;
import com.asiainfo.billing.drquery.client.config.Config;
import com.asiainfo.billing.drquery.client.impl.DRQueryClientImpl;
import com.asiainfo.billing.drquery.utils.PropertiesUtil;
import com.asiainfo.billing.drquery.utils.UtilsNestedException;
import java.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 上海移动详单帐前查询客户端
 *
 * @author zhouquan3
 * @version 1.0
 */
public class PreBillDRQueryClient implements IPreBillDRQuery {

    private final static Log log = LogFactory.getLog(PreBillDRQueryClient.class);

    /**
     * *
     *
     * @param operateType 默认是0
     * @param strBillId 查询手机号
     * @param strBeginDate 查询起始时间 时间格式yyyy-MM-dd
     * @param strEndDate 查询结束时间 时间格式yyyy-MM-dd
     * @param strOppBillId 对端号码 若为多个对端号码，以","分割
     * @param nCdrType 业务类型，具体的参照resources/runtime.properties
     * @param cErrorMsg 错误信息
     * @return { listBody=[["","","13524854030","13564027777","","2012-06-26
     * 22:15:42","6277","被叫","0","0","-","","210","210","0","0","0","6000181","6300","0","0","0","0","","合并后的超长话单","21","0","8613440492","1824","c2f3","","-","","GSM网络","0"]],
     * listTail=[],
     * listSpecialSum=[{"title":"本地通话费","stat":0},{"title":"通话时长","stat":6277},{"title":"信息费","stat":0}],
     * listCaption=["业务类型","帐户号","用户号码","对方号码","呼转号码","通话起始时间","通话时长","呼叫类型","漫游类型","访问类型","业务名称","一级归属地","一级漫游地","二级漫游地","本地通话费","长途通话费","信息费","免费资源1","免费分钟数1","免费资源2","免费分钟数2","免费资源3","免费分钟数3","国际移动台识别码","是否是合并话单","对端号码归属地","长途类型","交换机代码","定位地点识别","基站代码","批价处理时间","优惠区信息","是否是视频通话","网络类型","延迟标志"]}
     */
    public Map<String, List> query_allCdrInfoDetail(int operateType, String strBillId, String strBeginDate,
            String strEndDate, String strOppBillId, int nCdrType, String cErrorMsg) {
        DRQueryClient drQueryClient = new DRQueryClientImpl();
        JSONObject queryParameter = new JSONObject();
        queryParameter.put("billId", strBillId);//strBillId
        queryParameter.put("destId", strOppBillId);//strOppBillId 
        strBeginDate = strBeginDate.substring(0, 4) + strBeginDate.substring(5, 7) + strBeginDate.substring(8);
        queryParameter.put("fromDate", strBeginDate);//strBeginDate
        strEndDate = strEndDate.substring(0, 4) + strEndDate.substring(5, 7) + strEndDate.substring(8);
        queryParameter.put("thruDate", strEndDate);//strEndDate
        String busiType = Config.getBusiType(nCdrType);
        queryParameter.put("busiType", busiType);//nCdrType
        try {
            JSONObject jsonResult = drQueryClient.commonQuery(queryParameter);
            Map<String, List> result = new HashMap<String, List>();
            JSONObject data = jsonResult.getJSONObject("data");
            PreBillDataHandler handler = new PreBillDataHandler();
            result = handler.getTailList(busiType, data);
            return result;
        } catch (JSONException e) {
            log.error("Query cause a JSONException. Return empty data.");
            Map<String, List> result = new HashMap<String, List>();
            return result;
        }

    }

    /**
     * *
     *
     * @param operateType 默认是0
     * @param strBillId 查询手机号
     * @param strBeginDate 查询起始时间 时间格式yyyy-MM-dd
     * @param strEndDate 查询结束时间 时间格式yyyy-MM-dd
     * @param busiType 业务类型
     * @return { listBody=[["","","13524854030","13564027777","","2012-06-26
     * 22:15:42","6277","被叫","0","0","-","","210","210","0","0","0","6000181","6300","0","0","0","0","","合并后的超长话单","21","0","8613440492","1824","c2f3","","-","","GSM网络","0"]],
     * listTail=[],
     * listSpecialSum=[{"title":"本地通话费","stat":0},{"title":"通话时长","stat":6277},{"title":"信息费","stat":0}],
     * listCaption=["业务类型","帐户号","用户号码","对方号码","呼转号码","通话起始时间","通话时长","呼叫类型","漫游类型","访问类型","业务名称","一级归属地","一级漫游地","二级漫游地","本地通话费","长途通话费","信息费","免费资源1","免费分钟数1","免费资源2","免费分钟数2","免费资源3","免费分钟数3","国际移动台识别码","是否是合并话单","对端号码归属地","长途类型","交换机代码","定位地点识别","基站代码","批价处理时间","优惠区信息","是否是视频通话","网络类型","延迟标志"]}
     */
    public Map<String, List> query_allCdrInfoDetail(String strBillId, String strBeginDate,
            String strEndDate, String busiType) {
        DRQueryClient drQueryClient = new DRQueryClientImpl();
        JSONObject queryParameter = new JSONObject();
        queryParameter.put("billId", strBillId);//strBillId
        queryParameter.put("fromDate", strBeginDate);//strBeginDate
        queryParameter.put("thruDate", strEndDate);//strEndDate
        queryParameter.put("busiType", busiType);//nCdrType
        JSONObject jsonResult = drQueryClient.commonQuery(queryParameter);
        Map<String, List> result = new HashMap<String, List>();
        JSONObject data = jsonResult.getJSONObject("data");
        PreBillDataHandler handler = new PreBillDataHandler();
        result = handler.getTailList(busiType, data);
        return result;
    }
}
