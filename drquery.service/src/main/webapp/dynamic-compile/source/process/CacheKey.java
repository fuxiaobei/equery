package com.asiainfo.billing.drquery.process.dynamic;

import java.util.Map;


public class CacheKey {

    public static String generateCacheKey(Map<String, String> params) {
        StringBuffer buf = new StringBuffer();
        String interfaceType = params.get("interfaceType");
        buf.append(interfaceType).append("|");
        if(interfaceType.trim().equalsIgnoreCase("F3") || interfaceType.trim().equalsIgnoreCase("F4") || interfaceType.trim().equalsIgnoreCase("F5")){
            buf.append(params.get("phoneNo")).append("|").
                    append(params.get("startDate")).append("|").
                    append(params.get("endDate")).append("|");
        } else if("F6".equalsIgnoreCase(interfaceType)){
            buf.append(params.get("phoneNo")).append("|").
                    append(params.get("startDate")).append("|").
                    append(params.get("endDate")).append("|").append(params.get("offset"));
        } else if("F7".equalsIgnoreCase(interfaceType)){
            buf.append(params.get("phoneNo")).append("|").
                    append(params.get("dataDate")).append("|").
                    append(params.get("startTime")).append("|").
                    append(params.get("chargingId")).append("|").append(params.get("offset"));
        } else if("F8".equalsIgnoreCase(interfaceType)){
            buf.append(params.get("phoneNo")).append("|").
                    append(params.get("dataTime")).append("|").append(params.get("chargingId")).append("|").append(params.get("startTime")).append("|").
                    append(params.get("busiId")).append("|").
                    append(params.get("offset"));
        } else if("F13".equalsIgnoreCase(interfaceType) || "F14".equalsIgnoreCase(interfaceType)){
            buf.append(params.get("phoneNo")).append("|").
                    append(params.get("startTime")).append("|").
                    append(params.get("endTime")).append("|").
                    append(params.get("appId")).append("|").
                    append(params.get("mainDomain")).append("|").
                    append(params.get("offset"));
        }
        else  if("F01".equalsIgnoreCase(interfaceType)){
            buf.append(params.get("condition")).append("|").
                    append(params.get("table"));

        }
        else  if("F02".equalsIgnoreCase(interfaceType)){
            buf.append(params.get("start_time")).append("|").
                    append(params.get("stop_time")).append("|").
                    append(params.get("phoneNum"));
        }
        return buf.toString();
    }
}




