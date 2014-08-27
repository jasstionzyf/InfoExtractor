package com.yufei.infoExtractor.util;

import com.yufei.component.repetition.IsRepetive;
import com.yufei.component.repetition.IsRepetiveBatSet;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.extractor.fetchdecision.DecideByIsHadFetched;
import com.yufei.infoExtractor.extractor.fetchdecision.DecideByUrlSimilarity;
import com.yufei.infoExtractor.extractor.fetchdecision.DecideIsProcessed;
import com.yufei.infoExtractor.extractor.fetchdecision.DecideIsTargetLinkOrNot;
import com.yufei.infoExtractor.extractor.fetchdecision.DecisionMaker;
import com.yufei.infoExtractor.extractor.paging.PagingAction;
import com.yufei.infoExtractor.extractor.paging.PagingActionForAssembleUrl;

/**
 * @author jasstion
   2013-1-14
 *used to create different objects that implements the same interface
 */
public class FactoryUtil {
public static PagingAction createPagingAction(String pageingType,HActionContext context){
	PagingAction pagingAction=null;
	if(pageingType.equals(PropertyMatch.matchType_collection_na_assembleUrl)){
		pagingAction=new PagingActionForAssembleUrl(context);
	}
	return pagingAction;
}
public static DecisionMaker getDecisionMakerByType(String type){
	DecisionMaker decisionMaker=null;
	if(type.equals(DecisionMaker.hasFetched)){
		decisionMaker=DecideByIsHadFetched.getInstance();
	}
	if(type.equals(DecisionMaker.hasProcessed)){
		decisionMaker=DecideIsProcessed.getInstance();
	}
	if(type.equals(DecisionMaker.isTargetLink)){
		decisionMaker=DecideIsTargetLinkOrNot.getInstance();
	}
	if(type.equals(DecisionMaker.isUsefullLink)){
		decisionMaker=DecideByUrlSimilarity.getInstance();
	}
	return decisionMaker;
}

public static IsRepetive createRepetiveJudge(){
	IsRepetive isRepetive=null;
	isRepetive=new IsRepetiveBatSet();
	return isRepetive;
	
}
}
 