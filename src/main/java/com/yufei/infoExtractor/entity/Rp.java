package com.yufei.infoExtractor.entity;

import com.yufei.annotation.ExcelFieldOrder;
import com.yufei.annotation.ExcelHeaders;

/**
 * @author jasstion
 * 上午9:57:35
 * 抢购商品
 */
@ExcelFieldOrder(fieldOrders="name,price,discount,endTime,orderedNumber,pictureName")
@ExcelHeaders(headers="商品名称,原价,折扣,到期日期,已售,照片文件名称")
public class Rp extends Relatedlink{
	private Integer orderedNumber;
public Integer getOrderedNumber() {
		return orderedNumber;
	}
	public void setOrderedNumber(Integer orderedNumber) {
		this.orderedNumber = orderedNumber;
	}
private String rpName=null;
public String getRpName() {
	return rpName;
}
public void setRpName(String rpName) {
	this.rpName = rpName;
}
private Double price;
private Double discount;
private String pictureName;
private Long endTime;
public Long getEndTime() {
	return endTime;
}
public void setEndTime(Long endTime) {
	this.endTime = endTime;
}
public Rp() {
	super();
	// TODO Auto-generated constructor stub
}


public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public Double getDiscount() {
	return discount;
}
public void setDiscount(Double discount) {
	this.discount = discount;
}
public String getPictureName() {
	return pictureName;
}
public void setPictureName(String pictureName) {
	this.pictureName = pictureName;
}



}
