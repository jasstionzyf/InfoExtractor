package com.yufei.infoExtractor.listener;

/**
 * @author zhao-0244@qq.com 
   create at 2012-9-13
 *
 */
public enum EventType {
	CONTENTEXTRACTOR("contentExtractor");
	
    private String value=null;
	private EventType(String name) {
		// TODO Auto-generated constructor stub
		value=name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
