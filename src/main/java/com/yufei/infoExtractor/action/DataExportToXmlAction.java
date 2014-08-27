package com.yufei.infoExtractor.action;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.Entity;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.pfw.InfoExtractorDaoFactory;
import com.yufei.infoExtractor.pfw.entity.Task;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.DateUtil;
import com.yufei.utils.FileUtil;

/**
 * @author jasstion
   2013-1-23
 *用于将处理过的最终数据导出（比如：导出本地文件，Excel或者远程数据库等等）
 *最终数据针对的是本次任务采集并处理的数据
 *
 */
public class DataExportToXmlAction implements InfoExtractionAction <HActionContext>{
	private static Log mLog = LogFactory.getLog(DataExportToXmlAction.class);

	@Override
	public void execute(HActionContext context) throws Exception {
		
		Task task=context.getTask();
	
		
		InfoExtractorDao infoExtractorDao=InfoExtractorDaoFactory.getInfoExtractorDao();
		String destinationFoler=task.getTaskName()+DateUtil.getDateString(task.getStartedTime(), DateUtil.DAY)+File.separator;
		List needSavedList=null;
		needSavedList= infoExtractorDao.getDataRepositoryI().getMongoOperations().find(query(where("findTime").gt(task.getStartedTime()).
		          and("taskId").is(task.getId())), context.getTargetEntity().getClass());	
		if(CommonUtil.isEmptyOrNull(needSavedList)){
			mLog.info("taskId:'"+task.getId()+"';url不为空的查询结果为空！");
			return;
		}
		
     FileUtil.makeExcelFileForTaskResult(needSavedList, destinationFoler, context.getTargetEntity().getClass(), null, null);		

	}







}
