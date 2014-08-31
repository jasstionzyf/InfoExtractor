package com.yufei.infoExtractor.extractor.fetchdecision;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.Seedsite;
import com.yufei.infoExtractor.entity.UsefullLink;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.utils.CommonUtil;

public class DecideIsTargetLinkOrNot implements DecisionMaker {
	private static final Log mLog = LogFactory.getLog(DecideIsTargetLinkOrNot.class);
	private static DecisionMaker decisionMaker=null;
    public static DecisionMaker getInstance(){
    	if(decisionMaker==null){
    		decisionMaker=new DecideIsTargetLinkOrNot();

    	}
    	return decisionMaker;
    }
	private DecideIsTargetLinkOrNot() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean makeDecision(HActionContext context, String currentUrlStr) {
		// TODO Auto-generated method stub
		double similartity = 0.00;
		double passedSimilartity = 0.00;
		Seedsite seedsite = context.getSeedsite();
		passedSimilartity =0.0;// seedsite.getPassedSimilarityValue();
		List<UsefullLink> usefullLinks=null;//seedsite.getUsefullLinks();


		if(CommonUtil.isEmptyOrNull(usefullLinks)){
			//如果为空的话表示所有链接都去采集和分析
			return true;
		}
		if (passedSimilartity == 0.00) {
			// get the global value
			passedSimilartity = AppUtil.getInfoExtractorConfig()
					.getPassedSimilarityValue();
		}
		boolean isOk = false;
		String targetUrl=null;
		for (UsefullLink usefullLink : usefullLinks) {
			if(usefullLink.getLinkType().equals(UsefullLink.LINKTYPE_Target)){
				targetUrl=usefullLink.getLinkUrl();
				List<Integer>[] vectorofString = CommonUtil.getVectorofString(
						targetUrl, currentUrlStr);
				similartity = CommonUtil.calculateSimilartityByInteger(vectorofString[0],
						vectorofString[1]);
				if (similartity >= passedSimilartity) {
					isOk = true;
					break;
		
				}
			}
			
	
		}
		if(!isOk){
			mLog.debug("链接'"+currentUrlStr+"'不符合目标链接特征，很可能为不相干链接，此次处理直接忽略");

		}

		return isOk;
		
	}


}
