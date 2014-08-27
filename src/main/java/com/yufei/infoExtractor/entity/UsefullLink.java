package com.yufei.infoExtractor.entity;

/**
 * @author jasstion
   2013-1-18
 *表示某个网站有用链接的特征
 */
public class UsefullLink {
/**
 * 表示目标连接的父页面
 */
public final static String LINKTYPE_SOURCE="linkTypeSource";
/**
 * 表示目标链接
 */
public final static String LINKTYPE_Target="linkTypeTarget";

public UsefullLink(String linkType, String linkUrl) {
	super();
	this.linkType = linkType;
	this.linkUrl = linkUrl;
}
public UsefullLink() {
	super();
	// TODO Auto-generated constructor stub
}
public String getLinkType() {
	return linkType;
}
public void setLinkType(String linkType) {
	this.linkType = linkType;
}
public String getLinkUrl() {
	return linkUrl;
}
public void setLinkUrl(String linkUrl) {
	this.linkUrl = linkUrl;
}
private String linkType;
private String linkUrl;
}
