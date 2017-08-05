package com.asiainfo.billing.drquery.process.dto.model;

/**
 * Created by wangkai8 on 15/6/2.
 */
public class PageInfo {

    private int totalCount;
    private int startIndex;
    private int offset;

    public PageInfo(int totalCount, int startIndex, int offset) {
        this.totalCount = totalCount;
        this.startIndex = startIndex;
        this.offset = offset;
    }

    public PageInfo() {

    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
