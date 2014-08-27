package com.yufei.infoExtractor.extractor.fetchdecision;

import com.yufei.infoExtractor.context.HActionContext;

/**
 * created by @author jasstion at 2012-10-2
 * 用于判断是否对当前链接进行解析
 */
public interface DecisionMaker {
	public static final String hasFetched="hasFetched";
	public static final String hasProcessed="hasProcessed";
	public static final String isTargetLink="isTargetLink";
	public static final String isUsefullLink="isUsefullLink";
    

	

public boolean  makeDecision(HActionContext context,String currentUrlStr);

}
