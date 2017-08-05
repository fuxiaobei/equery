package com.asiainfo.billing.drquery.process.dto;

import com.asiainfo.billing.drquery.process.dto.model.PageInfo;

import java.util.List;

/**
 * 分页数据
 * @author tianyi
 *
 */
public class PageDTO extends ResultDTO {

    private PageInfo pageInfo = new PageInfo();

    private int totalCount;

    public PageDTO(List data, int totalCount) {
        super(data);
        this.totalCount = totalCount;
        pageInfo.setTotalCount(totalCount);
    }

    public PageDTO(List data, Object extData, int totalCount) {
        super(data, extData);
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public void buildPageInfo(int startIndex, int offset) {
        pageInfo.setStartIndex(startIndex);
        pageInfo.setOffset(offset);
    }

}
