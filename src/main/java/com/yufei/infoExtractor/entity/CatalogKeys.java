package com.yufei.infoExtractor.entity;

import java.util.List;

public class CatalogKeys {
private Long catalogId=null;
private List<String> keyWords=null;
public CatalogKeys(Long catalogId, List<String> keyWords) {
	super();
	this.catalogId = catalogId;
	this.keyWords = keyWords;
}
public Long getCatalogId() {
	return catalogId;
}
public void setCatalogId(Long catalogId) {
	this.catalogId = catalogId;
}
public List<String> getKeyWords() {
	return keyWords;
}
public void setKeyWords(List<String> keyWords) {
	this.keyWords = keyWords;
}
}
