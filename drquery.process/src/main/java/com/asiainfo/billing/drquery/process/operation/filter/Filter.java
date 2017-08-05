package com.asiainfo.billing.drquery.process.operation.filter;

import java.util.Map;

public interface Filter {
    boolean filter(Map<String,String> data);
}
