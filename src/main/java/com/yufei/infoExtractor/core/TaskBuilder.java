package com.yufei.infoExtractor.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.UrlParameter;
import com.yufei.infoExtractor.core.impl.InfoExtractorM;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.exception.TaskCreateFailedEx;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.pfw.service.PfwService;
import com.yufei.utils.CommonUtil;

/**
 * @author jasstion
   2013-1-7
 *this class used to create varities kinds of task
 */
public class TaskBuilder {
	private static Log mLog = LogFactory.getLog(InfoExtractorM.class);
	
	private PfwService  pfwService=(PfwService) AppUtil.getBeanFromBeanContainer(PfwService.class);


/**
 * @param taskName:任务名称
 * @param keyWords：任务要查询的关键字（利用通用的搜索引擎）
 * @return
 * @throws TaskCreateFailedEx 
 */
public Task createTask(String taskName,List<String> keyWords) throws TaskCreateFailedEx  {

	 String searchFilePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"search.xml";
	   Task task = null;
	try {
		task = getTaskByConfigFile(new File(searchFilePath));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		String message = "文件："+searchFilePath+"不存在！";
		mLog.error(message);
		throw new TaskCreateFailedEx(message);
	}
	   List<UrlParameter> urlParameters= task.getSeedsites().get(0).getPaginationRule().getUrlParameters();
		for(UrlParameter urlParameter:urlParameters){
			if(urlParameter.getParameterIndex()==1){
				urlParameter.setParameterValue(CommonUtil.LinkStringWithSpecialSymbol(keyWords, AppUtil.stringSplitSymbol));
			}
		}
	  task.setTaskName(taskName);
      pfwService.save(task);
	return task;
}
private Task getTaskByConfigFile(File file) throws FileNotFoundException {
	InputStream in = null;
	in = new FileInputStream(file);
	
	TaskConfig demoTasks=(TaskConfig) CommonUtil.getObjectFromXml(in, TaskConfig.class);
	Task task = demoTasks.getTasks().get(0);

	try {
		in.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return task;
}
/**
 * 
 */
/**
 * @param taskFilesPath:保存任务配置文件的目录
 * 此方法主要是将某个目录中的所有任务配置文件全部导入到系统数据库中
 * @return TODO
 */
public List<Task> createTask(String taskFilesPath){
	if(CommonUtil.isEmptyOrNull(taskFilesPath)){
		throw new IllegalArgumentException();
	}
	File taskFileDir=new File(taskFilesPath);
	if(!taskFileDir.exists()){
		mLog.error("任务配置文件目录："+taskFilesPath+"不存在！");
	}
	Task createdTask=null;
	List<Task> savedTasks=new ArrayList<Task>();
	for(File file:taskFileDir.listFiles()){
		try {
			createdTask=this.getTaskByConfigFile(file);
			createdTask.setId(null);
			//如果存在则进行更行
			String taskName=createdTask.getTaskName();
			Task task=infoExtractorDao.getDataRepositoryI().queryEntity("taskName", taskName, Task.class);
			if(task!=null){
				
					createdTask.setId(task.getId());
					//设置状态
				   //防止重新提交的任务覆盖正在运行的对应任务
					//createdTask.setStatus(task.getStatus());
	
				
			}
			createdTask=(Task) infoExtractorDao.saveEntityWithReturnValue(createdTask);
			savedTasks.add(createdTask);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			mLog.info("处理任务文件："+file.getName()+"出错！");
			continue;
		}
	}
	for(File file:taskFileDir.listFiles()){
		file.delete();
	}
	return savedTasks;
}        


}
 