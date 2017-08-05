package com.asiainfo.billing.drquery.process.core.process;

import com.ailk.oci.ocnosql.common.rowkeygenerator.MD5RowKeyGenerator;
import com.asiainfo.billing.drquery.cache.CacheProvider;
import com.asiainfo.billing.drquery.cache.ICache;
import com.asiainfo.billing.drquery.datasource.query.DefaultQueryParams;
import com.asiainfo.billing.drquery.exception.BusinessException;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.Executor;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.core.DRCommonProcess;
import com.asiainfo.billing.drquery.process.core.request.CommonDRProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;
import com.asiainfo.billing.drquery.process.dto.PageDTO;
import com.asiainfo.billing.drquery.process.dto.ResultDTO;
import com.asiainfo.billing.drquery.utils.DateUtil;
import com.asiainfo.billing.drquery.utils.NumberUtils;
import com.asiainfo.billing.drquery.utils.PropertiesUtil;
import com.asiainfo.billing.drquery.utils.ServiceLocator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;


/**
 *
 */
public class GPRSFlowProcess_ extends DRCommonProcess {

    public static final int timeout = Integer.parseInt(PropertiesUtil.getProperty("drquery.service/runtime.properties", "redis.expiretime", "300"));
    ICache redisCache = ServiceLocator.getInstance().getService("redisCache", ICache.class);

    public static final Log log = LogFactory.getLog(Process.class);

//    @Override
//    public BaseDTO process(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {
//        if("F1".equals(request.getInterfaceType())) {
//            return processF1(request, viewMeta, extendParams);
//        } else if("F2".equals(request.getInterfaceType())) {
//            return processF2(request, viewMeta, extendParams);
//        } else if("F3".equals(request.getInterfaceType()) || "F4".equals(request.getInterfaceType()) || "F5".equals(request.getInterfaceType())) {
//            return processF3(request, viewMeta, extendParams);
//        }  else if("F6".equals(request.getInterfaceType()) || "F7".equals(request.getInterfaceType()) || "F8".equals(request.getInterfaceType())) {
//            return processF6(request, viewMeta, extendParams);
//        }
//        return null;
//    }


    /**
     * 单表简单查询demo
     *
     * @param request
     * @param viewMeta
     * @param extendParams
     * @return
     * @throws ProcessException
     * @throws BusinessException
     */
    public BaseDTO processF1(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {
        //String currentDate = DateUtil.dateToString(new Date(), "YYYYMMdd");
        String rowkey = (String) new MD5RowKeyGenerator().generate(request.getParam("phoneNo"));
        String sql = "select PHONE_NO,NAME,EPARCHY_CODE,FLOW_PLAN_ID,MAIN_PLAN_FLAG,FREE_FLOW,USED_FLOW,REMAIN_FLOW,USER_TERM_BRAND,USER_TERM_MODEL from THB_USER_INFO_DAY " +
                "where id='" + rowkey + "'";
        List<Map<String, String>> list = loadData(DefaultQueryParams.newBuilder().buildSQL(sql));
        return new ResultDTO(list);
    }


    public BaseDTO processF2(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {
        if("1".equals(request.getParam("isCurrentMonth"))) {
            return processF1(request, viewMeta, extendParams);
        }

        String rowkey = new MD5RowKeyGenerator().generatePrefix(request.getParam("phoneNo")) + request.getParam("phoneNo");
        String sql = "select PHONE_NO,NAME,EPARCHY_CODE,FLOW_PLAN_ID,MAIN_PLAN_FLAG,FREE_FLOW,USED_FLOW,REMAIN_FLOW,USER_TERM_BRAND,USER_TERM_MODEL "+
                " from THB_USER_INFO_" + request.getParam("dataMonth") +
                " where id='" + rowkey + "'";
        List<Map<String, String>> list = loadData(DefaultQueryParams.newBuilder().buildSQL(sql));
        return new ResultDTO(list);
    }


    /**
     * 多表汇总查询demo
     *
     * @param request
     * @param viewMeta
     * @param extendParams
     * @return
     * @throws ProcessException
     * @throws BusinessException
     */
    public BaseDTO processF3(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {
//        String cacheKey = request.generateCacheKey();
//        //List dataList = redisCache.getValue(cacheKey, new CacheParameters.Range(request.getParam("startIndex"), request.getParam("startIndex") + request.getParam("offset")));
//        List dataList = redisCache.getValue(cacheKey, new CacheParameters.Range(1, 10));
//        if(dataList.size() > 0) {
//            return new PageDTO(dataList.subList(1, dataList.size()), (Integer) dataList.get(0));
//        }

        String startTime = request.getParam("startDate");
        String endTime = request.getParam("endDate");
        if(StringUtils.isEmpty(startTime) || startTime.length() != 8) {
            throw new IllegalArgumentException("startDate is invalid, required format yyyyMMdd, but found: " + startTime);
        }
        if(StringUtils.isEmpty(endTime) || endTime.length() != 8) {
            throw new IllegalArgumentException("endDate is invalid, required format yyyyMMdd, but found: " + startTime);
        }
        String phoneNo = request.getParam("phoneNo");
        List<String> months = DateUtil.getMonthsBetween(startTime, endTime, "yyyyMMdd");

        String sql = "";
        String md5Phone = (String) new MD5RowKeyGenerator().generate(phoneNo);
        String startKey = md5Phone + startTime;
        String stopKey = md5Phone + endTime + "g";
        for(String month : months) {
            sql += "select $0, " +
                    " sum(to_number(gprs_flow)) gprs_flow, sum(to_number(lte_flow)) lte_flow, " +
                    "sum(to_number(roam_flow)) roam_flow, sum(to_number(http_flow)) http_flow" +
                    " from THB_USER_FLOW_" + month  +
                    " where id >= '" + startKey + "' and id < '"+ stopKey + "' group by phone_No, $1 union all ";
        }
        sql = sql.substring(0, sql.lastIndexOf("union"));
        if("F3".equals(request.getInterfaceType())) {
            sql = sql.replace("$0", "data_date").replace("$1", "data_date");
        } else if("F4".equals(request.getInterfaceType())) {
            sql = sql.replace("$0", "PROTOCOL_TYPE_ID").replace("$1", "PROTOCOL_TYPE_ID");
        } else if("F5".equals(request.getInterfaceType())) {
            sql = sql.replace("$0", "BUSI_TYPE_ID").replace("$1", "BUSI_TYPE_ID");
        }
        List<Map<String, String>> list = loadData(DefaultQueryParams.newBuilder().buildSQL(sql));
        long totalFlow = 0;
        for(Map<String, String> record : list) {
            totalFlow += NumberUtils.parseLong(record.get("GPRS_FLOW"));
        }
        for(Map<String, String> record : list) {
            if("F3".equals(request.getInterfaceType())) {
                if(totalFlow != 0) {
                    record.put("FLOW_CNT", (NumberUtils.parseDouble(record.get("GPRS_FLOW")) / totalFlow) + "");
                } else {
                    record.put("FLOW_CNT", "0");
                }
            } else if("F4".equals(request.getInterfaceType())) {
                if(totalFlow != 0) {
                    record.put("PROTOCOL_TYPE_CNT", (NumberUtils.parseDouble(record.get("GPRS_FLOW")) / totalFlow) + "");
                } else {
                    record.put("PROTOCOL_TYPE_CNT", "0");
                }
            } else if("F5".equals(request.getInterfaceType())) {
                if(totalFlow != 0) {
                    record.put("BUSI_TYPE_CNT", (NumberUtils.parseDouble(record.get("GPRS_FLOW")) / totalFlow) + "");
                } else {
                    record.put("BUSI_TYPE_CNT", "0");
                }
            }
        }
        PageDTO dto = new PageDTO(list, list.size());
        return dto;
    }


    /**
     * 多表跨月查询demo
     *
     * @param request
     * @param viewMeta
     * @param extendParams
     * @return
     * @throws ProcessException
     * @throws BusinessException
     */
    public BaseDTO processF6(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {

        String phoneNo = request.getParam("phoneNo");
        int startIndex = Integer.valueOf(request.getParam("startIndex"));
        int limit = -1;
        String offset = request.getParam("offset");
        if(StringUtils.isNotEmpty(offset)) {
            limit = Integer.parseInt(offset);
        }

        String cacheKey = request.generateCacheKey();
        int countCache = -1;
        String pageCache = null;
        List counterAndRowKey = CacheProvider.getCountAndRowkeyInfo(cacheKey, startIndex);
        if(counterAndRowKey.get(0) != null) {  //不是第一次查询
            countCache = (Integer) counterAndRowKey.get(0);
            pageCache = (String) counterAndRowKey.get(1);
        }

        String[] sqls = buildSQL(countCache, pageCache, limit, request);

        List<Future<List<Map<String, String>>>> futures = new ArrayList<Future<List<Map<String, String>>>>();
        for(int i = 0; i < sqls.length; i++) {
            final String querySql = sqls[i];
            Callable<List<Map<String, String>>> callable = new Callable<List<Map<String, String>>>() {
                @Override
                public List<Map<String, String>> call() throws Exception {
                    return loadData(DefaultQueryParams.newBuilder().buildSQL(querySql));
                }
            };
            futures.add(Executor.getInstance().getThreadPool().submit(callable));
        }
        int i = 0;
        List<Map<String, String>> countRecords = null;
        List<Map<String, String>> detailRecords = null;
        try {
            for (Future<List<Map<String, String>>> future : futures) {
                if(i == 0 && countCache == -1)
                    countRecords = future.get();
                else
                    detailRecords = future.get();
                i ++;
            }
        } catch (Exception e) {
            throw new ProcessException("execute query exception", e);
        }
        int totalCount = 0;
        if(countCache == -1) {
            for (Map<String, String> record : countRecords) {
                totalCount += Integer.parseInt(record.get("C"));
            }
            CacheProvider.put(cacheKey, -9, totalCount, timeout);
        } else {
            totalCount = countCache;
        }
        String nextKey = null;
        if(limit != -1 && detailRecords.size() == limit + 1) {
            nextKey = detailRecords.get(detailRecords.size() -1).get("ID");
            CacheProvider.put(cacheKey, startIndex + limit, nextKey, timeout);
            detailRecords = detailRecords.subList(0, limit);
        }
        return new PageDTO(detailRecords, totalCount);
    }


    /**
     *  
     * @param countCache
     * @param pageCache
     * @param limit
     * @param request
     * @return
     */
    public String[] buildSQL(int countCache, String pageCache, int limit, CommonDRProcessRequest request) {
        String md5Phone = (String) new MD5RowKeyGenerator().generate(request.getParam("phoneNo"));
        String sql = "", countQuery = "", detailQuery = "", startKey = "", stopKey = "";
        if("F6".equals(request.getInterfaceType())) {
            String startTime = request.getParam("startDate");
            String endTime = request.getParam("endDate");
            List<String> months = DateUtil.getMonthsBetween(startTime, endTime, "yyyyMMdd");
            if(pageCache == null)
                startKey = md5Phone + startTime;
            else
                startKey = pageCache;
            stopKey = md5Phone + endTime + "g";
            for(String month : months) {
                sql +=  "select $1 "+
                        " from THB_GPRS_CHARGE_"  + month  +
                        " where id >= '" + startKey + "' and id < '"+ stopKey + "' union all ";
            }
            sql = sql.substring(0, sql.lastIndexOf("union"));
            countQuery = sql.replace("$1", "count(1) as c");
            detailQuery = sql.replace("$1", "ID,DATA_DATE,PHONE_NO,CHARGING_ID,EPARCHY_CODE,START_TIME,DURATION,CHARGE_FLOW,TOTAL_FLOW");
        } else if("F7".equals(request.getInterfaceType())) {
            if(pageCache == null)
                startKey = md5Phone + request.getParam("dataDate");
            else
                startKey = pageCache;
            stopKey = md5Phone + request.getParam("dataDate") + "g";
            sql +=  "select $1 "+
                    " from THB_GPRS_FLOW_"  + request.getParam("dataDate").substring(0, 6)  +
                    " where id >= '" + startKey + "' and id < '"+ stopKey + "' and data_date='" + request.getParam("dataDate") + "'" +
                    "  and CHARGING_ID='" + request.getParam("chargingId") + "'" +
                    //"  and to_date(start_time, 'yyyyMMddHHmmss') >= to_date('" + request.getParam("startTime") + "', 'yyyyMMddHHmmss') - 0.0000115740741 * 60 * 5 " +
                    "  and to_date(start_time, 'yyyyMMddHHmmss') >= to_date('" + request.getParam("startTime") + "', 'yyyyMMddHHmmss')" +
                    " union all ";
            sql = sql.substring(0, sql.lastIndexOf("union"));
            countQuery = sql.replace("$1", "count(1) as c");
            detailQuery = sql.replace("$1", "ID,DATA_DATE,PHONE_NO,CHARGING_ID,NET_TYPE,ACCESS_MODE,TERM_BRAND,TERM_MODEL,START_TIME,DURATION,TOTAL_FLOW,UP_FLOW,DOWN_FLOW,PROTOCOL_TYPE_ID,BUSI_ID,BUSI_REMARK,hour_id");
        } else if("F8".equals(request.getInterfaceType())) {
            if(pageCache == null)
                startKey = md5Phone + request.getParam("dataTime");
            else
                startKey = pageCache;
            stopKey = md5Phone + request.getParam("dataTime") + "g";
            sql +=  "select $1 "+
                    " from THB_GPRS_WAP_"  + request.getParam("dataTime").substring(0, 6)   +
                    " where id >= '" + startKey + "' and id < '"+ stopKey + "' and data_time='" + request.getParam("dataTime") + "'" +
                    "  and CHARGING_ID='" + request.getParam("chargingId") + "'" +
                    "  and busi_id='" + request.getParam("busiId") +  "'" +
                    //"  and to_date(start_time, 'yyyyMMddHHmmss') >= to_date('" + request.getParam("startTime") + "', 'yyyyMMddHHmmss') - 0.0000115740741 * 60 * 5 " +
                    "  and to_date(start_time, 'yyyyMMddHHmmss') >= to_date('" + request.getParam("startTime") + "', 'yyyyMMddHHmmss') " +
                    " union all ";
            sql = sql.substring(0, sql.lastIndexOf("union"));
            countQuery = sql.replace("$1", "count(1) as c");
            detailQuery = sql.replace("$1", "ID,DATA_TIME,PHONE_NO,CHARGING_ID,BUSI_ID,START_TIME,DURATION,TOTAL_FLOW,UP_FLOW,DOWN_FLOW,BUSI_REMARK");
        }

        if(limit > 0) {
            detailQuery += " limit "+ (limit + 1);
        }

        String[] sqls;
        if(countCache == -1) {
            sqls = new String[]{countQuery, detailQuery};
        } else {
            sqls = new String[]{detailQuery};
        }
        return sqls;
    }

}
