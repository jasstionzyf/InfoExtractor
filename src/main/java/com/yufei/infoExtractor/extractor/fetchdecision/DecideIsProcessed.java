package com.yufei.infoExtractor.extractor.fetchdecision;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.Relatedlink;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.util.AppUtil;

public class DecideIsProcessed implements DecisionMaker {
	private static final Log mLog = LogFactory.getLog(DecideIsProcessed.class);
	private static DecisionMaker decisionMaker=null;
    public  static DecisionMaker  getInstance(){
    	if(decisionMaker==null){
    		decisionMaker=new DecideIsProcessed();

    	}
    	return decisionMaker;
    }
	private DecideIsProcessed() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean makeDecision(HActionContext context, String currentUrlStr) {
		// TODO Auto-generated method stub
		InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
		Relatedlink relatedlink=(Relatedlink)infoExtractorDao.getDataRepositoryI().queryEntity("link", currentUrlStr,context.getTargetEntity().getClass());
		if(!relatedlink.isProcessed()){
			return false;
		}
		return true;
	}

}
