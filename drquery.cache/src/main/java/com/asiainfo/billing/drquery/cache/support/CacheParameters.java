package com.asiainfo.billing.drquery.cache.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author Rex Wong
 */
public interface CacheParameters {

    public static class Range {
        private final int start;//从1开始，0存储统计信息
        private final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }

        public Collection<Object> getLimitKey() {
            List<Object> limitKeys = new ArrayList<Object>();
            for (int i = start; i <= end; i++) {
                limitKeys.add(i + "");
            }
            return limitKeys;
        }
    }

}
