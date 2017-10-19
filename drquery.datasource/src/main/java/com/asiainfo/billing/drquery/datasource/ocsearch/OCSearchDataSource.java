package com.asiainfo.billing.drquery.datasource.ocsearch;

import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.datasource.DataSourceException;
import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;
import com.asiainfo.billing.drquery.datasource.query.OCSearchQueryParams;
import com.asiainfo.ocsearch.exception.ServiceException;
import com.asiainfo.ocsearch.listener.SystemListener;
import com.asiainfo.ocsearch.service.OCSearchService;
import com.asiainfo.ocsearch.service.query.QueryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Rex Wong
 */
public class OCSearchDataSource implements BaseDataSource {

    private final static Log log = LogFactory.getLog(OCSearchDataSource.class);

    private Boolean initialed = false;

    @Override
    public List<Map<String, String>> loadDR(DRQueryParameters parameters) throws DataSourceException {

        OCSearchQueryParams params = (OCSearchQueryParams) parameters;

        long t1 = System.currentTimeMillis();
        OCSearchQueryParams.REQUEST re = params.getRequest();


        List<Map<String, String>> retArr = new ArrayList<Map<String, String>>();
        try {
            OCSearchService ocSearchService = re.getService();

            JsonNode request = params.toJsonNode();

            log.info("ocsearch request is:"+request.toString());

            if (ocSearchService instanceof QueryService) {
                QueryService queryService = (QueryService) ocSearchService;
                JsonNode result = queryService.query(request);

                Map<String, String> title = new HashMap<String, String>();

                Iterator<Map.Entry<String, JsonNode>> it = result.getFields();

                while (it.hasNext()) {
                    Map.Entry<String, JsonNode> kv = it.next();
                    if (kv.getKey().equals("docs")) {
                        ArrayNode arrayNode = (ArrayNode) kv.getValue();

                        Iterator<JsonNode> docIt = arrayNode.iterator();
                        while (docIt.hasNext()) {
                            retArr.add(trans2Map(docIt.next()));
                        }
                    } else {
                        title.put(kv.getKey(), kv.getValue().asText());
                    }
                }
                retArr.add(0, title);

            } else {
                byte[] result = ocSearchService.doService(request);
                Map<String, String> value = new HashMap<String, String>();
                value.put("result", new String(result, "UTF-8"));
                retArr.add(value);
            }

            log.info("ocsearch return " + retArr.size() + " records, query token: " + (System.currentTimeMillis() - t1) + "ms");
        } catch (ServiceException se) {
            try {
                String message = "ocsearch exec exception,error is [" + (new String(se.getErrorResponse(), "UTF-8")) + "]";

                log.error(message, se);
                throw new DataSourceException(message);

            } catch (UnsupportedEncodingException e) {

            }
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                throw new DataSourceException("ocsearch exec exception,error is [" + cause.getMessage() + "]");
            }
        }
        return retArr;
    }

    private Map<String, String> trans2Map(JsonNode node) {
        Map<String, String> data = new HashMap<String, String>();
        Iterator<Map.Entry<String, JsonNode>> it = node.getFields();

        while (it.hasNext()) {
            Map.Entry<String, JsonNode> kv = it.next();

            data.put(kv.getKey(), kv.getValue().asText());
        }
        return data;
    }
    public void init(){
        synchronized (initialed) {
            if (initialed == false) {
                log.info("make new instance of ocsearch connection");
                try {
                    new SystemListener().initAll();
                    initialed = true;
                } catch (Exception e) {
                    log.error("make new instance of ocsearch connection", e);
                }
            }
        }
    }
    public void destroy(){
        synchronized (initialed) {
            if (initialed == true) {
                log.info("destroy instance of ocsearch connection");
                try {
                    new SystemListener().contextDestroyed(null);
                } catch (Exception e) {
                    log.error("destroy instance of ocsearch connection", e);
                }
                initialed = false;
            }
        }
    }
}
