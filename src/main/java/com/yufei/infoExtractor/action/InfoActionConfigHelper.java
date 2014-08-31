package com.yufei.infoExtractor.action;

import com.yufei.infoExtractor.context.BaseContext;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.core.InfoActionConfig;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.extractor.ContentExtractor;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;



/**
 * @author jasstion
   2013-1-22
 *用于处理actionConfig相关的一些方法比如：从当前任务配置的所有InfoActions中获取特定ActionType的所有InfoAction,
 *
 */
public class InfoActionConfigHelper {
	private static Log mLog = LogFactory.getLog(InfoActionConfigHelper.class);

public static  List<InfoExtractionAction> getTaskInfoExtractionActions(Task task){
if(CommonUtil.isEmptyOrNull(task.getActionConfigs())){
	throw new IllegalArgumentException();
}
	List<InfoActionConfig> infoActionConfigs=task.getActionConfigs();
     List<InfoExtractionAction> infoExtractionActions =new ArrayList<InfoExtractionAction>();
 	InfoExtractionAction temInfoExtractionAction=null;
     for(InfoActionConfig infoActionConfig:infoActionConfigs){
			temInfoExtractionAction = getInfoActionFromActionConfig(infoActionConfig); 
     	infoExtractionActions.add(temInfoExtractionAction);
     }
     return infoExtractionActions;
	
}
private static InfoExtractionAction getInfoActionFromActionConfig(
		InfoActionConfig infoActionConfig) {
	InfoExtractionAction temInfoExtractionAction=null;
	try {
	temInfoExtractionAction=(InfoExtractionAction) Class.forName(infoActionConfig.getInfoActionClassFullClassName()).newInstance();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		String message = "InfoExtractionActions 初始化失败，任务将以失败退出，请仔细检查InfoExtractionAction类名配置是否正确！  异常是："+ExceptionUtil.getExceptionDetailsMessage(e);
		mLog.info(message);
		throw new RuntimeException(message);
	}
	return temInfoExtractionAction;
}
public static void executeInfoExtractorActions(BaseContext context,
		List<InfoExtractionAction> infoExtractionActions) {
	for (InfoExtractionAction infoExtractionAction : infoExtractionActions) {
		if (infoExtractionAction instanceof ContentExtractor) {
			boolean isExtractorContent = Boolean.valueOf(AppUtil
					.getInfoExtractorConfig().getIsExtractorContent());
			if (!isExtractorContent) {
				continue;
			}
		}
		try {
			infoExtractionAction.execute(context); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//后续会针对此异常进行特殊的处理，此异常最终会直接交给Task框架处理，也会被前端系统捕获
			throw new RuntimeException(e);
			}

	}
	
}
public static List<InfoExtractionAction> getTaskInfoExtractionActionsByInfoActionType(Task task,String infoActionType){
    List<InfoExtractionAction> infoExtractionActions =new ArrayList<InfoExtractionAction>();
    String[] infoActionTypes=infoActionType.split("&&");
    for(InfoActionConfig infoActionConfig:task.getActionConfigs()){
    	for(String actionType:infoActionTypes){
    		if(infoActionConfig.getInfoActionType().toString().equals(actionType)){
    			infoExtractionActions.add(getInfoActionFromActionConfig(infoActionConfig));
    			break;
        	}
    	}
    	
    }
    return infoExtractionActions;
}
}
