package com.asiainfo.billing.drquery.process.dynamic;

import com.asiainfo.billing.drquery.Constants;
import com.asiainfo.billing.drquery.cache.CacheProvider;
import com.asiainfo.billing.drquery.cache.ICache;
import com.asiainfo.billing.drquery.cache.support.CacheParameters;
import com.asiainfo.billing.drquery.datasource.query.OCSearchQueryParams;
import com.asiainfo.billing.drquery.exception.BusinessException;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.core.DRCommonProcess;
import com.asiainfo.billing.drquery.process.core.request.CommonDRProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;
import com.asiainfo.billing.drquery.process.dto.PageDTO;
import com.asiainfo.billing.drquery.process.dto.ResultDTO;
import com.asiainfo.billing.drquery.process.dto.model.PageInfo;
import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by mac on 2017/7/25.
 */
public class OCSearchProcess extends DRCommonProcess {
    public static final Log log = LogFactory.getLog(OCSearchProcess.class);

    public BaseDTO processGet(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {

        OCSearchQueryParams dr = new OCSearchQueryParams(OCSearchQueryParams.REQUEST.GET);

        dr.setExtParam("ids", Arrays.asList(request.get("id")));
        dr.setExtParam("table", request.get("table"));
        dr.setTable(request.get("table"));
        List<Map<String, String>> list = loadData(dr);

        Map<String, String> header = list.remove(0);

        int total = Integer.parseInt(header.get("total"));

        PageDTO dto = new PageDTO(list, total);
        return dto;
    }

    public BaseDTO processSearch(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {

        int offset = request.getInt("offset", 100);
        int startIndex = request.getInt("startIndex", 1);
        String cacheKey = request.generateCacheKey();

        PageDTO dto = beforeCache(cacheKey, startIndex, offset);
        if (dto != null) {
            extendParams.put("FROM_CACHED", Boolean.valueOf(true));
            dto.setPageInfo(new PageInfo(dto.getTotalCount(), startIndex, offset));
            return dto;
        }

        OCSearchQueryParams dr = new OCSearchQueryParams(OCSearchQueryParams.REQUEST.SEARCH);

        dr.setTable(request.get("table"));
        dr.setStart(request.getInt("startIndex", 1) - 1);
        dr.setRows(request.getInt("offset", 100));
        dr.setCondition(request.get("condition"));

        List<Map<String, String>> list = loadData(dr);

        Map<String, String> header = list.remove(0);

        int total = Integer.parseInt(header.get("total"));

        dto = new PageDTO(list, total);

        dto.setPageInfo(new PageInfo(total, startIndex, offset));

        afterCache(cacheKey, startIndex, total, list);

        return dto;
    }

    void afterCache(String cacheKey, int startIndex, int total, List list) {

        ICache redisCache = CacheProvider.getRedisCache();
        Map map = new LinkedHashMap();
        map.put(Constants.TOTAL_COUNT_CACHE_INDEX, total);
        for (int i = 0; i < list.size(); i++) {
            map.put(String.valueOf(i + startIndex), list.get(i));
        }
        //cache data to redis, async thread
        redisCache.putData2Cache(cacheKey, map, expiretime);
    }

    int validData(List data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null)
                return i;
        }
        return data.size();

    }

    PageDTO beforeCache(String cachekey, int startIndex, int offset) {

        ICache redisCache = CacheProvider.getRedisCache();
        int stopIndex = offset + startIndex - 1;
        PageDTO dto = null;
        if (offset == -1) {
            Integer totalCount = redisCache.getValue(cachekey, Constants.TOTAL_COUNT_CACHE_INDEX);
            if (totalCount != null) {
                Collection range = new CacheParameters.Range(startIndex, totalCount).getLimitKey();
                List data = redisCache.getValue(cachekey, range);
                dto = new PageDTO(data, totalCount);
                dto.setPageInfo(new PageInfo(totalCount, startIndex, offset));
            }
        } else {
            Collection range = new CacheParameters.Range(startIndex, stopIndex).getLimitKey();
            range.add(Constants.TOTAL_COUNT_CACHE_INDEX);
            List data = redisCache.getValue(cachekey, range);
            Integer totalCount = (Integer) data.get(data.size() - 1);
            if (totalCount != null) {
                if (totalCount == 0 || totalCount < startIndex) {
                    dto = new PageDTO(new ArrayList(), totalCount);
                } else {
                    /**
                     * 对于从redis中取mutliGet操作，Range个数有多少个就返回多少个值，即使redis中没有对应的值，这样会造成无效数据，比如redis中就一条记录,
                     * 如果range是1到10000，那么redis接口会返回10000个记录，9999是null值，对于这种情况分页需要处理
                     */
                    int validSize = validData(data);

                    if (validSize < data.size() && validSize + startIndex <= totalCount)
                        dto = null;
                    else if (validSize == data.size())
                        dto = new PageDTO(data.subList(0, validSize - 1), totalCount);
                    else
                        dto = new PageDTO(data.subList(0, validSize), totalCount);
                }
            }
        }

        return dto;
    }


    public BaseDTO processScan(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {

        int offset = request.getInt("offset", 100);
        int startIndex = request.getInt("startIndex", 1);

        String cacheKey = request.generateCacheKey();

        PageDTO dto = beforeCache(cacheKey, startIndex, offset);
        if (dto != null) {
            extendParams.put("FROM_CACHED", Boolean.valueOf(true));
            dto.setPageInfo(new PageInfo(dto.getTotalCount(), startIndex, offset));
            return dto;
        }

        OCSearchQueryParams dr = new OCSearchQueryParams(OCSearchQueryParams.REQUEST.SCAN);

        dr.setTable(request.get("table"));
        dr.setStart(startIndex - 1);
        dr.setRows(offset);
        String startTime = request.get("start_time");
        String stopTime = request.get("stop_time");

        dr.setCondition("time>='" + startTime + "' and time<='" + stopTime + "'");
        dr.setExtParam("rowkey_prefix", request.get("phoneNum"));
        List<Map<String, String>> list = loadData(dr);

        Map<String, String> header = list.remove(0);

        int total = Integer.parseInt(header.get("total"));

        dto = new PageDTO(list, total);

        dto.setPageInfo(new PageInfo(total, startIndex, offset));

        afterCache(cacheKey, startIndex, total, list);

        return dto;
    }

    public BaseDTO processDeepScan(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {

        OCSearchQueryParams dr = new OCSearchQueryParams(OCSearchQueryParams.REQUEST.DEEPSCAN);

        dr.setTable(request.get("table"));
        dr.setRows(request.getInt("offset", 100));

        String startTime = request.get("start_time");
        String stopTime = request.get("stop_time");

        dr.setCondition("time>='" + startTime + "' and time<='" + stopTime + "'");
        dr.setExtParam("rowkey_prefix", request.get("phoneNum"));
        dr.setExtParam("cursor_mark", request.get("cursor_mark"));

        List<Map<String, String>> list = loadData(dr);

        Map<String, String> header = list.remove(0);


        String next_cursor_mark = header.get("next_cursor_mark");

        Map<String, Object> extData = new HashMap<String, Object>();

        extData.put("next_cursor_mark", next_cursor_mark);

        return new ResultDTO(list, extData);
    }


    public BaseDTO processDeepSsearch(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {

        OCSearchQueryParams dr = new OCSearchQueryParams(OCSearchQueryParams.REQUEST.DEEPSEARCH);

        dr.setTable(request.get("table"));
        dr.setRows(request.getInt("offset", 100));
        dr.setCondition(request.get("condition"));
        dr.addSort("id", OCSearchQueryParams.ORDER.asc);
        dr.setExtParam("cursor_mark", request.get("cursor_mark"));

        List<Map<String, String>> list = loadData(dr);

        Map<String, String> header = list.remove(0);

        int total = Integer.parseInt(header.get("total"));
        String next_cursor_mark = header.get("next_cursor_mark");

        Map<String, Object> extData = new HashMap<String, Object>();

        extData.put("next_cursor_mark", next_cursor_mark);
        extData.put("total", total);

        return new ResultDTO(list, extData);
    }

    public BaseDTO processSql(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {

        OCSearchQueryParams dr = new OCSearchQueryParams(OCSearchQueryParams.REQUEST.SQL);

        String area = request.get("SECURITY_AREA");

        String sql = "select PHONENUM  from TEST2 where SECURITY_AREA='" + area + "'";


        dr.setExtParam("sql", sql);

        List<Map<String, String>> list = loadData(dr);

        Map<String, String> header = list.remove(0);

        return new ResultDTO(list, header);
    }

    public BaseDTO processFileget(CommonDRProcessRequest request, MetaModel viewMeta, final Map extendParams) throws ProcessException, BusinessException {

        OCSearchQueryParams dr = new OCSearchQueryParams(OCSearchQueryParams.REQUEST.FILEGET);

        dr.setExtParam("id",  request.get("id"));

        List<Map<String, String>> list = loadData(dr);

        Map<String, String> header = list.remove(0);

        return new ResultDTO(null, header.get("result"));
    }




}
