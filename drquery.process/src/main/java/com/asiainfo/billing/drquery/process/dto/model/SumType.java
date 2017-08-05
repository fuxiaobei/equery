package com.asiainfo.billing.drquery.process.dto.model;


public class SumType {

	//{sortIndex=3, desc=, value=0.0, code=0, type=2, bigType=GPRS}
	public static final String ACC_CODE = "code";
	public static final String ACC_NAME = "desc";
	public static final String TYPE = "type";
	public static final String BIG_TYPE = "bigType";
	public static final String SORT_ID = "sortIndex";
	public static final String FEE ="value";
	// data members
	private int accCode;
	private String accName = new String();
	private String fee = new String();
	private int sumType;
	private int type;
	private int sortId;

	// construct function
	public SumType() {
	}

	public SumType(final SumType rhs) {
		copy(rhs);
	}

	public void copy(final SumType rhs) {
		if (rhs == null)
			return;
		accCode = rhs.accCode;
		if (rhs.accName != null)
			accName = rhs.accName;
		else
			accName = rhs.accName;
		if (rhs.fee != null)
			fee = rhs.fee;
		else
			fee = rhs.fee;
		sumType = rhs.sumType;
		type = rhs.type;
		sortId = rhs.sortId;
		return;
	}

	public boolean equals(final SumType rhs) {
		if (rhs == null)
			return false;

		if (!(accCode == rhs.accCode))
			return false;

		if (!(accName.equals(rhs)))
			return false;

		if (!(fee.equals(rhs)))
			return false;

		if (!(sumType == rhs.sumType))
			return false;

		if (!(type == rhs.type))
			return false;

		if (!(sortId == rhs.sortId))
			return false;

		return true;
	}

	// get functions
	public int getAccCode() {
		return accCode;
	}

	public String getAccName() {
		return accName;
	}

	public String getFee() {
		return fee;
	}

	public int getSumType() {
		return sumType;
	}

	public int getType() {
		return type;
	}

	public int getSortId() {
		return sortId;
	}

	// set functions
	public void setAccCode(int value) {
		accCode = value;
	}

	public void setAccName(String value) {
		accName = value;
	}

	public void setFee(String value) {
		fee = value;
	}

	public void setSumType(int value) {
		sumType = value;
	}

	public void setType(int value) {
		type = value;
	}

	public void setSortId(int value) {
		sortId = value;
	}

}
