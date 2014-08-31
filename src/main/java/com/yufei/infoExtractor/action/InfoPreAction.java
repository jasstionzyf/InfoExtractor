package com.yufei.infoExtractor.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.pfw.InfoExtractorDaoFactory;
import com.yufei.utils.DateUtil;

/**
 * @author jasstion
   2013-1-23
 *主要是任务在执行具体action之前的一些准备工作比如：设置任务状态等等
 */
public class InfoPreAction implements InfoExtractionAction <HActionContext>{
private static Log mLog = LogFactory.getLog(InfoPreAction.class);

	@Override
	public void execute(HActionContext context) throws Exception{
		// TODO Auto-generated method stub
		Task task=context.getTask();
	    Date startDate = new Date();

		InfoExtractorDao infoExtractorDao=InfoExtractorDaoFactory.getInfoExtractorDao();
		infoExtractorDao.setTaskStartedTime(startDate,task.getId());
	    task.setStartedTime(startDate);
	    String message="任务："+task.getTaskName()+"开始执行,时间为："+DateUtil.getDate(startDate, DateUtil.DATE_TIME)+"";
	    mLog.info(message);
	    infoExtractorDao.updateTaskStatus(Task.IS_RUNNING, task.getId());
	}


}
