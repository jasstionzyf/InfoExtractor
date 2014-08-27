package com.yufei.infoExtractor.entity;

import com.yufei.entity.Entity;

public class Catalog extends Entity{
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	private String pId=null;
	//分类标题
	private String name=null;

	public Catalog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
