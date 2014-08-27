/**
 * 
 */
package com.yufei.infoExtractor.entity;

import com.yufei.pfw.entity.Relatedlink;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-8-23 下午5:32:05
 */
public class Meishi extends Relatedlink {
private String name=null;
private String description=null;
private String city=null;
private String leftTimeAvailabled;
private Integer orderedNumber;
//酒店地址，公交路线等
private String area;
private String price;
private String oPrice;
private String discount;
private String pictureNameMedia;
private List<String> comments=new ArrayList();
private Integer commentsNumber=0;
public List<String> getComments() {
	return comments;
}
public void setComments(List<String> comments) {
	this.comments = comments;
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getCommentsNumber() {
	return commentsNumber;
}
public void setCommentsNumber(Integer commentsNumber) {
	this.commentsNumber = commentsNumber;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getLeftTimeAvailabled() {
	return leftTimeAvailabled;
}
public void setLeftTimeAvailabled(String leftTimeAvailabled) {
	this.leftTimeAvailabled = leftTimeAvailabled;
}
public Integer getOrderedNumber() {
	return orderedNumber;
}
public void setOrderedNumber(Integer orderedNumber) {
	this.orderedNumber = orderedNumber;
}
public String getArea() {
	return area;
}
public void setArea(String area) {
	this.area = area;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public String getoPrice() {
	return oPrice;
}
public void setoPrice(String oPrice) {
	this.oPrice = oPrice;
}
public String getDiscount() {
	return discount;
}
public void setDiscount(String discount) {
	this.discount = discount;
}
public String getPictureNameMedia() {
	return pictureNameMedia;
}
public void setPictureNameMedia(String pictureNameMedia) {
	this.pictureNameMedia = pictureNameMedia;
}
public void addComments(String comment) {
	if (!comments.contains(comment)) {
		comments.add(comment);
	}

}
}
