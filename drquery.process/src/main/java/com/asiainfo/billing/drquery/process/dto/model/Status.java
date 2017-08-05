/**
 * 
 */
package com.asiainfo.billing.drquery.process.dto.model;

/**
 * @author yanghui8
 *
 */
public class Status {
	private  int  totalCount;
    private int startIndex;
    private int offset;
	
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
