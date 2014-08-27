/**
 * 
 */
package com.yufei.infoExtractor.entity;

import com.yufei.annotation.CollectionElementType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;



/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-8-23 下午5:46:46
 */
public class Goods extends Relatedlink implements Comparable<Goods>{
	//商品类别
	
private String category=null;
private String pictureNameMedia=null;
public String getPictureNameMedia() {
	return pictureNameMedia;
}
public void setPictureNameMedia(String pictureNameMedia) {
	this.pictureNameMedia = pictureNameMedia;
}
public String getCategoryId() {
	return categoryId;
}
public void setCategoryId(String categoryId) {
	this.categoryId = categoryId;
}
private String categoryId=null;

@Override
public String toString() {
	return "Goods [category=" + category + ", categoryId=" + categoryId
			+ ", goodName=" + goodName + ", description=" + description + ", price="
			+ price + ", goodCommentsNumber=" + goodCommentsNumber
			+ ", badCommentsNumber=" + badCommentsNumber + ", comments="
			+ comments + super.toString()+"]";
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}

public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public Integer getGoodCommentsNumber() {
	return goodCommentsNumber;
}
public void setGoodCommentsNumber(Integer goodCommentsNumber) {
	this.goodCommentsNumber = goodCommentsNumber;
}
public Integer getBadCommentsNumber() {
	return badCommentsNumber;
}
public void setBadCommentsNumber(Integer badCommentsNumber) {
	this.badCommentsNumber = badCommentsNumber;
}
public List<String> getComments() {
	return comments;
}
public void setComments(List<String> comments) {
	this.comments = comments;
}
//商品简介(可以理解为商品的简短的描述)
private String goodName=null;
public String getGoodName() {
	return goodName;
}
public void setGoodName(String goodName) {
	this.goodName = goodName;
}
//商品详细介绍
private String description=null;
private String price=null;
//好评数目
private Integer goodCommentsNumber=null;
//坏评数目
private Integer badCommentsNumber=null;
//商品评论内容集合
@CollectionElementType(type=String.class)
private List<String> comments=new ArrayList();

/* (non-Javadoc)
 * @see java.lang.Comparable#compareTo(java.lang.Object)
 */


public static void main(String[] args){
	Goods good=new Goods(),good1=new Goods();
	good.setFindTime(new Date());
	Date date=new Date();
	date.setMinutes(54);
	good1.setFindTime(date);
	System.out.print(good.getFindTime()+"    "+good1.getFindTime());
	List<Goods> arrays=new ArrayList<Goods>();
	arrays.add(good);
	arrays.add(good1);
	for(Goods good0:arrays){
		System.out.print(good0.getFindTime()+"\n");
	}
	Collections.sort(arrays);
	for(Goods good0:arrays){
		System.out.print(good0.getFindTime()+"\n");
	}

	/*Object[] goods=(Object[]) arrays.toArray();
	
	Arrays.sort(goods);
	for(Object good01:goods){
		System.out.print(((Goods)good01).getFindTime()+"\n");
	}*/
	
	
}
/* (non-Javadoc)
 * @see java.lang.Comparable#compareTo(java.lang.Object)
 */
@Override
public int compareTo(Goods o) {
	// TODO Auto-generated method stub
	return 0;
}

}
