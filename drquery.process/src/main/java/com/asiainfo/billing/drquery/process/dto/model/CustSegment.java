package com.asiainfo.billing.drquery.process.dto.model;

import java.io.Serializable;


public class CustSegment implements Serializable {

	private String m_strCustId = new String();
	private String m_strCustType = new String();
	private String m_strCustName = new String();
	private String m_strCustPrivName = new String();
	private String m_strCustPrivNumber = new String();
	private String m_strStartTime = new String();
	private String m_strEndTime = new String();
	
	public CustSegment() {
	}

	public CustSegment(final CustSegment rhs) {
		copy(rhs);
	}

	public void copy(final CustSegment rhs) {
		if (rhs == null)
			return;
		m_strCustId = rhs.m_strCustId;
		m_strCustType = rhs.m_strCustType;
		m_strCustName = rhs.m_strCustName;
		m_strCustPrivName = rhs.m_strCustPrivName;
		m_strCustPrivNumber = rhs.m_strCustPrivNumber;
		m_strStartTime = rhs.m_strStartTime;
		m_strEndTime = rhs.m_strEndTime;
		return;
	}

	// equals function
	public boolean equals(final CustSegment rhs) {
		if (rhs == null)
			return false;

		if (!(m_strCustId.equals(rhs.m_strCustId)))
			return false;

		if (!(m_strCustType.equals(rhs.m_strCustType)))
			return false;
		
		if (!(m_strCustName.equals(rhs.m_strCustName)))
			return false;

		if (!(m_strCustPrivName.equals(rhs.m_strCustPrivName)))
			return false;

		if (!(m_strCustPrivNumber.equals(rhs.m_strCustPrivNumber)))
			return false;

		if (!(m_strStartTime.equals(rhs.m_strStartTime)))
			return false;

		if (!(m_strEndTime.equals(rhs.m_strEndTime)))
			return false;
		
		return true;
	}

	// get functions
	public String get_custId() {
		return m_strCustId;
	}
	
	public String get_custType() {
		return m_strCustType;
	}

	public String get_custName() {
		return m_strCustName;
	}

	public String get_custPrivName() {
		return m_strCustPrivName;
	}

	public String get_custPrivNumber() {
		return m_strCustPrivNumber;
	}

	public String get_startTime() {
		return m_strStartTime;
	}

	public String get_endTime() {
		return m_strEndTime;
	}

	// set functions
	public void set_custId(String value) {
		m_strCustId = value;
	}
	
	public void set_custType(String value) {
		m_strCustType = value;
	}

	public void set_custName(String value) {
		m_strCustName = value;
	}

	public void set_custPrivName(String value) {
		m_strCustPrivName = value;
	}

	public void set_custPrivNumber(String value) {
		m_strCustPrivNumber = value;
	}

	public void set_startTime(String value) {
		m_strStartTime = value;
	}

	public void set_endTime(String value) {
		m_strEndTime = value;
	}

}
