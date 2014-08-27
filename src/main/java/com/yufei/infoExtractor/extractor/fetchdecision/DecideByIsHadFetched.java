package com.yufei.infoExtractor.extractor.fetchdecision;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.utils.EncryptUtil;

public class DecideByIsHadFetched implements DecisionMaker {
	private static final Log mLog = LogFactory.getLog(DecideByIsHadFetched.class);
	private static DecisionMaker decisionMaker=null;
    public  static DecisionMaker  getInstance(){
    	if(decisionMaker==null){
    		decisionMaker=new DecideByIsHadFetched();

    	}
    	return decisionMaker;
    }
	private DecideByIsHadFetched() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean makeDecision(HActionContext context, String currentUrlStr) {
		// TODO Auto-generated method stub
		Set<String> linkFingerPrints = context.getLinkFingerPrints();
		if(!linkFingerPrints.contains(EncryptUtil.md5(currentUrlStr))){
			return true;
		}
		else{
			mLog.debug("链接'"+currentUrlStr+"'已经被解析过，此次处理直接忽略");
			return false;

		}
	}

}
