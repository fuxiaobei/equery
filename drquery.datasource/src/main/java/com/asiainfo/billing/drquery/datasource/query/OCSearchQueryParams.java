package com.asiainfo.billing.drquery.datasource.query;

import com.alibaba.druid.util.StringUtils;
import com.asiainfo.ocsearch.service.OCSearchService;
import com.asiainfo.ocsearch.service.query.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.util.*;

/**
 * Created by mac on 2017/7/26.
 */
public class OCSearchQueryParams implements DRQueryParameters {
    @Override
    public String getDataSourceName() {
        return "ocsearchDataSource";
    }

    Map<String, Object> extParams = new HashMap<String, Object>();

    REQUEST request = null; //request  for ocsearch

    int start = 0;
    int rows = 10;

    String query = "";

    String condition = "";

    Set<String> returnNodes = new HashSet<String>();

    Set<String> tables = new HashSet<String>();

    List<Sort> sorts = new LinkedList<Sort>();


    public OCSearchQueryParams(REQUEST request) {
        this.request = request;
    }

    public Map<String, Object> getExtParams() {
        return extParams;
    }

    public void setExtParams(Map<String, Object> params) {
        this.extParams = params;
    }

    public void setExtParam(String key, Object value) {
        extParams.put(key, value);
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Set<String> getReturnNodes() {
        return returnNodes;
    }

    public void setReturnNodes(Set<String> returnNodes) {
        this.returnNodes = returnNodes;
    }

    public REQUEST getRequest() {
        return request;
    }

    public void setRequest(REQUEST request) {
        this.request = request;
    }

    public static class Sort {

        public Sort(String field, ORDER order) {
            this.field = field;
            this.order = order;
        }

        String field;
        ORDER order;

        public Sort(String field, int order) {
            if (order == 1)
                this.order = ORDER.asc;
            else
                this.order = ORDER.desc;
            this.field = field;
        }
    }

    public static enum ORDER {
        desc,
        asc;

        private ORDER() {
        }

        public ORDER reverse() {
            return this == asc ? desc : asc;
        }
    }

    public void setSort(String field, ORDER order) {
        if (!sorts.isEmpty()) {
            sorts.clear();
        }
        sorts.add(new Sort(field, order));
    }

    /**
     * @param field
     * @param order 1 = asc ;-1 = desc
     */
    public void setSort(String field, int order) {
        if (!sorts.isEmpty()) {
            sorts.clear();
        }
        sorts.add(new Sort(field, order));
    }

    /**
     * @param field
     * @param order 1 = asc ;-1 = desc
     */
    public void addSort(String field, int order) {
        for (Sort sort : sorts) {
            if (StringUtils.equals(sort.field, field)) {
                sorts.remove(sort);
                break;
            }
        }
        sorts.add(new Sort(field, order));
    }

    public void addSort(String field, ORDER order) {
        for (Sort sort : sorts) {
            if (StringUtils.equals(sort.field, field)) {
                sorts.remove(sort);
                break;
            }
        }
        sorts.add(new Sort(field, order));
    }

    public String getSorts() {
        StringBuilder sb = new StringBuilder();
        for (Sort sort : sorts) {
            sb.append(sort.field);
            sb.append(" ");
            sb.append(sort.order.name());
            sb.append(",");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public void setTable(String name) {
        if (!tables.isEmpty())
            tables.clear();
        tables.add(name);
    }

    public void addTable(String name) {
        tables.add(name);
    }

    public void addFields(Collection<String> fields) {
        for (String field : fields)
            addField(field);
    }

    public void addField(String field) {
        if (field != null)
            returnNodes.add(field);
    }

    public JsonNode toJsonNode() {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("start", start);
        objectNode.put("rows", rows);
        objectNode.put("query", query);
        objectNode.put("condition", condition);

        objectNode.put("return_fields", collection2ArrayNode(returnNodes));
        objectNode.put("tables", collection2ArrayNode(tables));

        objectNode.put("sort", getSorts());

        for (Map.Entry<String, Object> param : extParams.entrySet()) {
            if (param.getValue() instanceof Collection) {
                objectNode.put(param.getKey(), collection2ArrayNode((Collection<String>) param.getValue()));
            } else {
                objectNode.put(param.getKey(), (String) param.getValue());
            }
        }
        return objectNode;

    }

    public ArrayNode collection2ArrayNode(Collection<String> collection) {
        ArrayNode nodes = JsonNodeFactory.instance.arrayNode();
        for (String value : collection) {
            nodes.add(value);
        }
        return nodes;
    }

    public enum REQUEST {
        SEARCH {
            public OCSearchService getService() {
                return new SearchService();
            }
        },
        DEEPSEARCH {

            public OCSearchService getService() {
                return new DeepSearchService();
            }
        },
        SCAN {

            public OCSearchService getService() {
                return new ScanService();
            }
        },
        DEEPSCAN {

            public OCSearchService getService() {
                return new DeepScanService();
            }
        },
        GET {

            public OCSearchService getService() {
                return new GetService();
            }
        },
        FILEGET {

            public OCSearchService getService() {
                return new FileGetService();
            }
        },
        SQL {
            public OCSearchService getService() {
                return new SqlService();
            }
        };

        public OCSearchService getService() {
            return  null;
        }
    }
}
