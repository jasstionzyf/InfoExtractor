package com.yufei.infoExtractor.action;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.entity.Hotel;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.exception.TaskResultExportException;
import com.yufei.pfw.entity.Entity;
import com.yufei.utils.AnnotationUtil;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.DateUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.FileUtil;

public class DataExportToExcelAction implements InfoExtractionAction <HActionContext>{
	private static Log mLog = LogFactory.getLog(DataExportToExcelAction.class);

	public void execute(HActionContext context) throws Exception {
		// TODO Auto-generated method stub
		Task task = context.getTask();

		InfoExtractorDao infoExtractorDao = InfoExtractorDaoFactory
				.getInfoExtractorDao();
		String destinationFoler = AppUtil.getInfoExtractorConfig()
				.getFileSavedDirectory()
				+ task.getTaskName()
				+ DateUtil.getDateString(task.getStartedTime(), DateUtil.DAY)
				+ File.separator;
		List<? extends Entity> needSavedList = null;

		needSavedList = infoExtractorDao
				.getDataRepositoryI()
				.getMongoOperations()
				.find(query(where("findTime").gt(task.getStartedTime())
						.and("taskId").is(task.getId())),
						context.getTargetEntity().getClass());

		if (CommonUtil.isEmptyOrNull(needSavedList)) {
			mLog.info("taskId:'" + task.getId() + "';url不为空的查询结果为空！");
			return;
		}
		
List<String> fieldOrders=AnnotationUtil.getFieldOrders(Hotel.class);
List<String> headers=AnnotationUtil.getExcelHeaders(Hotel.class);

 
		try {
			
			FileUtil.makeExcelFileForTaskResult(needSavedList,
					destinationFoler, context.getTargetEntity().getClass(),
					fieldOrders, headers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mLog.info("在将采集信息列表生成Excel文件时出错，错误信息是："
					+ ExceptionUtil.getExceptionDetailsMessage(e) + "");
			throw new TaskResultExportException(
					ExceptionUtil.getExceptionDetailsMessage(e));
		}
	}




}
