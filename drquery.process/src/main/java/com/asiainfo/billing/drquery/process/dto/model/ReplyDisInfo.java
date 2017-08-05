package com.asiainfo.billing.drquery.process.dto.model;

public class ReplyDisInfo {

	private String phoneNum = "";
	private String yearMonth = "";
	private FieldDefListList cdrDisplays = new FieldDefListList();
	private SumTypeList sums = new SumTypeList();
	private BillCust billCust = new BillCust();
	private CustomerInfo customerInfo = new CustomerInfo();

	public ReplyDisInfo() {
		super();
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public FieldDefListList getCdrDisplays() {
		return cdrDisplays;
	}

	public void setCdrDisplays(FieldDefListList cdrDisplays) {
		this.cdrDisplays = cdrDisplays;
	}

	public SumTypeList getSums() {
		return sums;
	}

	public void setSums(SumTypeList sums) {
		this.sums = sums;
	}

	public BillCust getBillCust() {
		return billCust;
	}

	public void setBillCust(BillCust billCust) {
		this.billCust = billCust;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	
}
