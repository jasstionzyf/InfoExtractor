package com.yufei.infoExtractor.entity;

import com.yufei.pfw.entity.Entity;


/**
 * @author jasstion
 * 下午5:31:57
 * 外部待抓取链接
 */
public class ExternalLink extends Entity{ 
	//链接对应的分类id
	private String catalogId=null;
	private String keyWords=null;
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

	private String link=null;

	public String getLink() {
		return link;
	}

	public ExternalLink() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setLink(String link) {
		this.link = link;
	}

}
