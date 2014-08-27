/**
 * 
 */
package com.yufei.infoExtractor.action;


/**
 * @author jasstion
   2013-1-22
 *
 */

public enum InfoActionType{
	  
	/**
	 * 表示任务执行之前的一些准备工作
	 */
	PRE("pre"),
	EXTRACTORURL("extractorurl"),
	EXTRACTORCONTENT("extractorcontent"),
	PROCESS("process"),
	EXPORT("export"),
	
	/**
	 * 表示任务执行完毕之后的最终收尾工作，此类Action一般是最后才执行
	 */
	POST("post");

	
    private String value=null;
	private InfoActionType(String name) {
		// TODO Auto-generated constructor stub
		value=name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	 @Override   
	    public  String toString() {  
	        return   value;  
	    }  
}

