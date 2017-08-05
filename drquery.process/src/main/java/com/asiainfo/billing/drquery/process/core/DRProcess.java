package com.asiainfo.billing.drquery.process.core;

import com.asiainfo.billing.drquery.Constants;
import com.asiainfo.billing.drquery.cache.ICache;
import com.asiainfo.billing.drquery.cache.support.CacheParameters;
import com.asiainfo.billing.drquery.cache.support.RedisSwitch;
import com.asiainfo.billing.drquery.exception.BusinessException;
import com.asiainfo.billing.drquery.exception.DrqueryRuntimeException;
import com.asiainfo.billing.drquery.model.MetaModel;
import com.asiainfo.billing.drquery.model.ViewModelReader;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.compile.BeanHolder;
import com.asiainfo.billing.drquery.process.core.request.DRProcessRequest;
import com.asiainfo.billing.drquery.process.dto.BaseDTO;
import com.asiainfo.billing.drquery.process.dto.EmptyDTO;
import com.asiainfo.billing.drquery.process.dto.PageDTO;
import com.asiainfo.billing.drquery.process.dto.ResultDTO;
import com.asiainfo.billing.drquery.process.operation.MonitorLog;
import com.asiainfo.billing.drquery.process.operation.fieldEscape.FieldEscapeOperation;
import com.asiainfo.billing.drquery.utils.PropertiesUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 详单查询Process基类 </p> 定义针对所有详单查询的基础业务逻辑方法
 * 
 * @author Rex Wong
 * 
 *         14 May 2012
 * @version
 */
public abstract class DRProcess<T extends DRProcessRequest> implements BaseProcess<T> {
	private static final Log log = LogFactory.getLog(DRProcess.class);
	public int restrictIndex = Integer.parseInt(PropertiesUtil.getProperty("drquery.service/runtime.properties", "restrictIndex", "10000"));//最大返回值限制
    public long expiretime = Long.parseLong(PropertiesUtil.getProperty("drquery.service/runtime.properties", "redis.expiretime", "300"));//最大返回值限制
	
	protected FieldEscapeOperation fieldEscape;
	protected RedisSwitch redisSwitch;
	protected ICache redisCache;
    protected boolean isCache = false;
		
	@Autowired
	public void setRedisSwitch(RedisSwitch redisSwitch) {
		this.redisSwitch = redisSwitch;
	}

	@Autowired
	public void setRedisCache(ICache redisCache) {
		this.redisCache = redisCache;
	}
	
	public void setFieldEscape(FieldEscapeOperation fieldEscape) {
		this.fieldEscape = fieldEscape;
	}

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDTO pagingProcess(T request, Map extendParams) throws ProcessException, BusinessException {

        log.info("request params: " + request.getParams());

        int startIndex = request.getInt("startIndex", 1);
        if (startIndex <= 0) {
            startIndex = 1;
        }
        int offset = request.getInt("offset", 100);
        if (offset <= 0) {
            offset = -1;
        }
        int stopIndex = offset + startIndex - 1;

        MetaModel viewMeta = ViewModelReader.getMetaModels().get(request.getInterfaceType());
        before(request, viewMeta, extendParams);
        try {
            if (viewMeta.isUseCache()) {
                PageDTO dto = null;
                if (offset == -1) {
                    Integer totalCount = redisCache.getValue(request.generateCacheKey(), Constants.TOTAL_COUNT_CACHE_INDEX);
                    if (totalCount != null) {
                        Collection range = new CacheParameters.Range(startIndex, totalCount).getLimitKey();
                        List data = redisCache.getValue(request.generateCacheKey(), range);
                        dto = new PageDTO(data, totalCount);
                    }
                } else {
                    Collection range = new CacheParameters.Range(startIndex, stopIndex).getLimitKey();
                    range.add(Constants.TOTAL_COUNT_CACHE_INDEX);
                    List data = redisCache.getValue(request.generateCacheKey(), range);
                    Integer totalCount = (Integer) data.get(data.size() - 1);
                    if (totalCount != null) {
                        if (totalCount == 0 || totalCount < startIndex) {
                            dto = new PageDTO(new ArrayList(), 0);
                        } else {
                            /**
                             * 对于从redis中取mutliGet操作，Range个数有多少个就返回多少个值，即使redis中没有对应的值，这样会造成无效数据，比如redis中就一条记录,
                             * 如果range是1到10000，那么redis接口会返回10000个记录，9999是null值，对于这种情况分页需要处理
                             */
                            if (data.size() <= totalCount)
                                dto = new PageDTO(data.subList(0, data.size() - 1), totalCount);
                            else
                                dto = new PageDTO(data.subList(0, totalCount), totalCount);
                        }
                    }
                }
                if (dto != null) {
                    extendParams.put(MonitorLog.FROM_CACHED, true);
                    return dto;
                }
            }

            BaseDTO dto = this.process(request, viewMeta, extendParams);
            if (dto instanceof EmptyDTO) {
                return dto;
            }
            ResultDTO resultDTO = (ResultDTO) dto;

            if (viewMeta.isUseCache() && dto instanceof PageDTO) {
                /**
                 * 往redis中放数据时，为了缓存记录总条数，同时只跟redis做一次交互，把记录总条数跟数据放在一起，取数据时按指定顺序取
                 */
                Map map = new LinkedHashMap();
                map.put(Constants.TOTAL_COUNT_CACHE_INDEX, ((PageDTO) dto).getTotalCount());
                for (int i = 0; i < resultDTO.getData().size(); i++) {
                    map.put(String.valueOf(i + 1), resultDTO.getData().get(i));
                }
                //cache data to redis, async thread
                redisCache.putData2Cache(request.generateCacheKey(), map, expiretime);
                resultDTO.setData(getPageData(resultDTO.getData(), startIndex, offset));
            }

            /**
             * 如果配置转义规则，则执行转义规则，返回的字段为配置的字段
             */
            if (MapUtils.isNotEmpty(viewMeta.getEscapeFields())) {
                try {
                    List<Map<String, String>> result = fieldEscape.execute(resultDTO.getData(), viewMeta, request); //转义
                    resultDTO.setData(result);  //reset result
                } catch (Exception e) {
                    throw new DrqueryRuntimeException(e);
                }
            }
            return resultDTO;
        } finally {
            after(request, viewMeta, extendParams);
        }
    }


    public List getPageData(List list, int startIndex, int offset) {
        if(offset == -1) {
            return list;
        }

        if(startIndex > list.size()) {
            return new ArrayList();
        }

        if(startIndex + offset > list.size()) {
            return list.subList(startIndex -1, list.size());
        } else {
            return list.subList(startIndex -1, startIndex + offset -1);
        }
    }

}
