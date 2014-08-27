/**
 * 
 */
package com.yufei.infoExtractor.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.utils.CommonUtil;

/**
 * @author jasstion
   2013-1-22
   此Action用于在启动任务之前检查此次任务上次的运行状态，如果运行状态为失败的话，则将上次任务已经采集到的链接但是
   尚未处理的直接进行处理，如果状态是成功的话或者没有任务未被处理的链接的话则直接返回继续执行其他的action
 *
 */
public class PreTaskCheckAction implements InfoExtractionAction <HActionContext> {
	private InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
	private static Log mLog = LogFactory.getLog(PreTaskCheckAction.class);

	/* (non-Javadoc)
	 * @see com.yufei.infoExtractor.core.InfoExtractionAction#execute(com.yufei.infoExtractor.core.HActionContext)
	 */
	@Override
	public void execute(HActionContext context) throws Exception {
		// TODO Auto-generated method stub
		
		


		List<String> unProcessedLinks=null;
		Map queryMap=new HashedMap();
		
		queryMap.put("isProcessed", false);
		unProcessedLinks= infoExtractorDao.queryAllValuesOfField("link",context.getTargetEntity().getClass(), queryMap);
		if(CommonUtil.isEmptyOrNull(unProcessedLinks)){
		  return;
		}


		context.put("unProcessedLinks", unProcessedLinks);
		
		List<InfoExtractionAction> infoExtractionActions=InfoActionConfigHelper.getTaskInfoExtractionActionsByInfoActionType(context.getTask(),""+InfoActionType.EXTRACTORCONTENT.getValue()+"");
	    InfoActionConfigHelper.executeInfoExtractorActions(context, infoExtractionActions);
	    //执行完毕之后清楚此标记
	    context.remove("unProcessedLinks");
	}






}
