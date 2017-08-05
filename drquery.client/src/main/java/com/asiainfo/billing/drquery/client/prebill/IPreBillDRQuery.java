package com.asiainfo.billing.drquery.client.prebill;

import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouquan3
 */
public interface IPreBillDRQuery {

    public Map<String,List> query_allCdrInfoDetail(int operateType, String strBillId,String strBeginDate,
            String strEndDate,String strOppBillId, int nCdrType, String cErrorMsg);
}
