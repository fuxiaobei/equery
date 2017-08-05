package com.asiainfo.billing.drquery.client.impl;

import com.asiainfo.billing.drquery.client.DRQueryClient;
import com.asiainfo.billing.drquery.client.config.Config;
import com.asiainfo.billing.drquery.client.config.SequenceCreator;
import com.asiainfo.billing.drquery.client.rest.RestManager;
import com.asiainfo.billing.drquery.utils.JsonUtils;
import com.asiainfo.billing.drquery.utils.PropertiesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

public final class DRQueryClientImpl implements DRQueryClient {

    private JSONObject postWithJson(String operationCode, JSONObject jsonParam) {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("ClientId", Config.getPath("esb.clientId"));
        headerMap.put("OperationCode", operationCode);
        SequenceCreator random = new SequenceCreator();
        headerMap.put("TransactionId", random.getUID());
        JSONObject result = null;
        try {
            result = JsonUtils.string2JsonObject(RestManager.postWithJson(headerMap, jsonParam));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public JSONObject commonQuery(JSONObject jsonParam) {
        JSONObject result = postWithJson(Config.getPath("esb.operationCode.common"), jsonParam);
        String fieldKeys = PropertiesUtil.getProperty("drquery.service/runtime.properties", jsonParam.getString("busiType"));
        if (fieldKeys.length() > 0) {
            distinct(result, fieldKeys);
        }
        return result;
    }

    private void distinct(JSONObject result, String fieldKeys) {
        JSONObject data = result.getJSONObject("data"); 
        if (!"0".equals(data.getString("count"))) {  
            JSONArray fields = data.getJSONArray("fields");
            List<Integer> afterDistinctIndex = new ArrayList<Integer>();
            String[] distinctField = StringUtils.split(fieldKeys, ",");
            JSONArray distinctfields = new JSONArray();
            for (String fieldKey : distinctField) {
                int pos = fields.indexOf(fieldKey);
                if (pos != -1) {
                    afterDistinctIndex.add(pos);
                    distinctfields.add(fieldKey);
                }
            }
            data.put("fields", distinctfields);
            JSONArray contents = data.getJSONArray("contents");
            for (int i = 0; i < contents.size(); i++) {
                JSONArray rows = contents.getJSONArray(i);
                JSONArray afterrow = new JSONArray();
                for (Integer after : afterDistinctIndex) {
                    afterrow.add(rows.get(after));
                }
                contents.set(i, afterrow);
            }
        }else{
            String[] distinctField = StringUtils.split(fieldKeys, ",");
            JSONArray distinctfields = new JSONArray();
            for(String fieldKey:distinctField){
                distinctfields.add(fieldKey);
            }
            data.put("fields", distinctfields);
            JSONArray contents=new JSONArray();
            data.put("contents", contents);
        }
    }

    public JSONObject buisTypeQuery(JSONObject jsonParam) {
        return postWithJson(Config.getPath("esb.operationCode.types"), jsonParam);
    }

    public JSONObject checkQuery(JSONObject jsonParam) {
        return postWithJson(Config.getPath("esb.operationCode.drcheck"), jsonParam);
    }
}
