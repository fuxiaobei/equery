package com.asiainfo.billing.drescaping;

import com.asiainfo.billing.drescaping.redis.RedisUtil;
import com.asiainfo.billing.drescaping.util.EscapingUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.RedisConnectionFailureException;

/**
 *
 * @author zhouquan3
 */
public class EscapingProcess implements Runnable {

    private final static Log log = LogFactory.getLog(EscapingProcess.class);
    private static Log monitor_log = LogFactory.getLog("monitor");
    private List<DBExtractBean> extractorList;
    private RedisUtil redisUtil;
    private String mapKey;
    private long mapSize = 10000;

    public void setMapSize(long mapSize) {
        this.mapSize = mapSize;
    }

    public void setExtractorList(List<DBExtractBean> extractorList) {
        this.extractorList = extractorList;
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public void run() {
        log.debug(mapKey + " start...");
        Map<String, Object> escapingMap = new HashMap<String, Object>();
        for (int i = 0; i < extractorList.size(); i++) {
            Map<String, Object> map = extractorList.get(i).getEscapingMap();
            if (!map.isEmpty()) {
                escapingMap.putAll(map);
            } else {
                System.out.println("empty map");
            }

        }
        try {
            List<Map<String, Object>> mapList = getMapList(escapingMap);
            log.debug("escaping map size: [" + escapingMap.size() + "] map size [" + mapSize + "] mapList size [" + mapList.size() + "]");
            for (int i = 0; i < mapList.size(); i++) {
                log.debug("mapList put map [" + (i + 1) + "/" + mapList.size() + "] start...");
                redisUtil.putData2Cache(mapKey, mapList.get(i), false);
                log.debug("mapList put map [" + (i + 1) + "/" + mapList.size() + "] done...");
            }
        } catch (RedisConnectionFailureException e) {
            monitor_log.error(EscapingUtil.getLocalHostIP() + "," + "Put data to redis cache failed. Connection TimeOut.");
            log.error(e);
        }
        log.debug(mapKey + " done!.");
    }

    private List<Map<String, Object>> getMapList(Map<String, Object> map) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> tempMap = new HashMap<String, Object>();
        for (String key : map.keySet()) {
            if (tempMap.size() > mapSize) {
                list.add(tempMap);
                tempMap = new HashMap<String, Object>();
            }
            tempMap.put(key, map.get(key));
        }
        list.add(tempMap);
        return list;
    }
}
