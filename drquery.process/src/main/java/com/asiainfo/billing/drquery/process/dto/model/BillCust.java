package com.asiainfo.billing.drquery.process.dto.model;

import java.io.Serializable;

import com.asiainfo.billing.drquery.utils.DateUtil;

public class BillCust implements Serializable {

	// data members
	private String m_strBillId = new String();
	private String m_strYearMonth = new String();
	private String m_strLastMemStartTime = new String();            //最后一个客户的过户时间,若本月不做过户则可空
	private CustSegmentList m_sCustSegments = new CustSegmentList();

	// construct function
	public BillCust() {
	}
	
	public BillCust(String billId, String yearMonth) {
		m_strBillId = billId;
		m_strYearMonth = yearMonth;
	}

	public BillCust(final BillCust rhs) {
		copy(rhs);
	}

	public void copy(final BillCust rhs) {
		if (rhs == null)
			return;
		m_strBillId = rhs.m_strBillId;
		m_strYearMonth = rhs.m_strYearMonth;
		m_strLastMemStartTime = rhs.m_strLastMemStartTime;
		m_sCustSegments = rhs.m_sCustSegments;
		return;
	}

	public boolean equals(final BillCust rhs) {
		if (rhs == null)
			return false;

		if (!(m_strBillId.equals(rhs.m_strBillId)))
			return false;

		if (!(m_strYearMonth.equals(rhs.m_strYearMonth)))
			return false;
		
		if (!(m_strLastMemStartTime.equals(rhs.m_strLastMemStartTime)))
			return false;

		if (!(m_sCustSegments.equals(rhs.m_sCustSegments)))
			return false;

		return true;
	}

	// get functions
	public String get_billId() {
		return m_strBillId;
	}

	public String get_yearMonth() {
		return m_strYearMonth;
	}
	
	public String get_lastMemStartTime() {
		return m_strLastMemStartTime;
	}

	public CustSegmentList get_custSegments() {
		return m_sCustSegments;
	}

	// set functions
	public void set_billId(String value) {
		m_strBillId = value;
	}

	public void set_yearMonth(String value) {
		m_strYearMonth = value;
	}
	
	public void set_lastMemStartTime(String value) {
		m_strLastMemStartTime = value;
	}

	public void set_custSegments(CustSegmentList value) {
		m_sCustSegments = value;
	}
}
