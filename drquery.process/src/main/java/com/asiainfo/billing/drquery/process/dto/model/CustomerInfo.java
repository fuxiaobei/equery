package com.asiainfo.billing.drquery.process.dto.model;

import java.io.Serializable;

public class CustomerInfo implements Serializable {

	// data members
	private long m_llCustId;
	private String m_strCustName = new String();
	private String m_strCardName = new String();
	private String m_strCardCode = new String();

	public CustomerInfo() {
		m_llCustId = 0l;
		m_strCustName = "未知";
	}

	public CustomerInfo(final CustomerInfo rhs) {
		copy(rhs);
	}

	public void copy(final CustomerInfo rhs) {
		if (rhs == null)
			return;
		m_llCustId = rhs.m_llCustId;
		m_strCardName = rhs.m_strCardName;
		m_strCustName = rhs.m_strCustName;
		m_strCardCode = rhs.m_strCardCode;

		return;
	}

	public boolean equals(final CustomerInfo rhs) {
		if (rhs == null)
			return false;

		if (!(m_llCustId == rhs.m_llCustId))
			return false;

		if (!(m_strCustName.equals(rhs.m_strCustName)))
			return false;

		if (!(m_strCardName == rhs.m_strCardName))
			return false;

		if (!(m_strCardCode.equals(rhs.m_strCardCode)))
			return false;

		return true;
	}

	public long get_custId() {
		return m_llCustId;
	}

	public String get_custName() {
		return m_strCustName;
	}

	public String get_cardName() {
		return m_strCardName;
	}
 
	public String get_cardCode() {
		return m_strCardCode;
	}

	public void set_custId(long value) {
		m_llCustId = value;
	}

	public void set_custName(String value) {
		m_strCustName = value;
	}

	public void set_cardName(String value) {
		m_strCardName = value;
	}

	public void set_cardCode(String value) {
		m_strCardCode = value;
	}

}
