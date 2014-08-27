package com.yufei.infoExtractor.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.pfw.InfoExtractorDaoFactory;
import com.yufei.infoExtractor.pfw.entity.Task;
import com.yufei.utils.DateUtil;

/**
 * @author jasstion
   2013-1-23
 * 表示任务执行完毕之后的最终收尾工作，此类Action一般是最后才执行比如：任务状态信息的更行以及相关资源
 * 的释放
 */
public class InfoPostAction implements InfoExtractionAction <HActionContext>{
	private static Log mLog = LogFactory.getLog(InfoPostAction.class);

	@Override
	public void execute(HActionContext context) throws Exception {
		// TODO Auto-generated method stub
		Task task=context.getTask();
	    Date endDate = new Date();

		InfoExtractorDao infoExtractorDao=InfoExtractorDaoFactory.getInfoExtractorDao();
		infoExtractorDao.setTaskEndTime(endDate,task.getId());
	    task.setEndTime(endDate);
	    String message="任务："+task.getTaskName()+"执行结束,时间为："+DateUtil.getDate(endDate, DateUtil.DATE_TIME)+"";
	    mLog.info(message);
	    infoExtractorDao.updateTaskStatus(Task.IS_RUNED, task.getId());

	}







}
