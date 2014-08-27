package com.yufei.infoExtractor.entity;

import com.yufei.entity.Entity;

/**
 * @author jasstion
 * 上午10:57:01
 * 用于将某个网站中的分类映射到自己系统的关键字或者分类名称上面
 */
public class CatalogMapping extends Entity{
	//用以表示某个网站中的某个分类
private String catalogId=null;

private String keyWords=null;
public CatalogMapping() {
	super();
	// TODO Auto-generated constructor stub
}
public String getCatalogId() {
	return catalogId;
}
public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
}
public String getKeyWords() {
	return keyWords;
}
public void setKeyWords(String keyWords) {
	this.keyWords = keyWords;
}
}
