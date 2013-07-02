package org.jmin.test.jda.order;

import java.util.Date;
import java.util.List;

public class Order {
	
	private String orderNo;
	
	private String custNo;
	
	private Date openDate;
	
	private List itemList;
	
	private List custList;

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public List getCustList() {
		return custList;
	}

	public void setCustList(List custList) {
		this.custList = custList;
	}
}