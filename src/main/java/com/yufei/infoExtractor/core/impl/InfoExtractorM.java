
package com.yufei.infoExtractor.core.impl;
import com.yufei.dataget.entity.ProxyServer;
import com.yufei.dataget.entity.UrlParameter;
import com.yufei.dataget.utils.HtmlUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.yufei.infoExtractor.core.InfoExtractorMI;
import com.yufei.infoExtractor.core.TaskBuilder;
import com.yufei.infoExtractor.core.TaskConfig;
import com.yufei.infoExtractor.entity.News;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.pfw.entity.Entity;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.PatternUtils;

/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-9-2 上午11:40:21
 */
@Service
public class InfoExtractorM implements InfoExtractorMI {
	private static Log mLog = LogFactory.getLog(InfoExtractorM.class);
	final static String entityPackageName="com.yufei.infoExtractor.pfw.entity";
	@Autowired
	private InfoExtractorDao  infoExtractorDao=null;
    

	public InfoExtractorDao getinfoExtractorDao() {
		return infoExtractorDao;
	}


	public void setinfoExtractorDao(InfoExtractorDao infoExtractorDao) {
		this.infoExtractorDao = infoExtractorDao;
	}


	/* (non-Javadoc)
	 * @see com.yufei.infoExtractor.core.InfoExtractorMI#updateTask(java.io.InputStream)
	 */
	@Override
	public void updateTask(InputStream inputStream) throws JAXBException {
		// TODO Auto-generated method stub
		TaskConfig taskConfig=null;
		//URL resource=Thread.currentThread().getContextClassLoader().getResource(AppUtil.defaultCrawlerDateFilePath);//new ClassPathResource(AppUtil.defaultCrawlerDateFilePath);
		
		JAXBContext jaxbContext;
	
			jaxbContext = JAXBContext.newInstance(TaskConfig.class);
			Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
			taskConfig=(TaskConfig) unmarshaller.unmarshal(inputStream);
			List<Task> tasks = taskConfig.getTasks();
            List<String> taskNames= infoExtractorDao  .queryAllValuesOfField("taskName", Task.class, new HashMap());
			for(Task task:tasks){
				if(taskNames.contains(task.getTaskName())){
					//drop add
					Task taskD=infoExtractorDao.queryEntity("taskName", task.getTaskName(), Task.class);
					infoExtractorDao.removeEntity(taskD);
					infoExtractorDao.addTask(task);
				}
				else{
					infoExtractorDao.addTask(task);
				}
			}
			
				
			
		
		
	}





	/* (non-Javadoc)
	 * @see com.yufei.infoExtractor.core.InfoExtractorMI#count(java.lang.String)
	 */
	@Override
	public long count(String collectionName) {
		// TODO Auto-generated method stub
		long count;
		count=infoExtractorDao.getMongoTemplate().count(new Query(), collectionName);
		return count;
	}


	/* (non-Javadoc)
	 * @see com.yufei.infoExtractor.core.InfoExtractorMI#getAllEntityFullClassName()
	 */
	@Override
	public List<String> getAllEntityFullClassName() {
		// TODO Auto-generated method stub
		List<String> classNames=new ArrayList();
		Set<String> collectionNames=infoExtractorDao.getMongoTemplate().getCollectionNames();
		for(String collectionName:collectionNames){
			classNames.add(entityPackageName+"."+CommonUtil.upFirstChar(collectionName));
		}
		
		return classNames;
	}


	/* (non-Javadoc)
	 * @see com.yufei.infoExtractor.core.InfoExtractorMI#exportTaskToXml(java.lang.String)
	 */
	@Override
	public void exportTaskToXml(String taskName,String path) {
		// TODO Auto-generated method stub
		TaskConfig taskConfig=new TaskConfig();
		
		if(CommonUtil.isEmptyOrNull(taskName)){
			taskConfig.getTasks().addAll(infoExtractorDao.getTasks());
		}
		else{
		Task task= infoExtractorDao.queryEntity("taskName", taskName, Task.class);
		taskConfig.getTasks().add(task);

		}
		try {
			CommonUtil.generateXmlForObject(taskConfig, TaskConfig.class, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public String getHtmlContentByDataRetriver(String url, Boolean isRequireJS) throws IOException {
		// TODO Auto-generated method stub
		return HtmlUtil.getHtmlContent(url, isRequireJS);
	}






	@Override
	public List<String> matchStrByRegex(String htmlContent, String regex) {
		// TODO Auto-generated method stub
		
		return PatternUtils.getListStrByRegex(htmlContent, regex);
	}


	@Override
	public List<? extends Entity> queryEntity(String collectionName)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		List<? extends Entity> resultList=null;
		
		resultList=(List<? extends Entity>) infoExtractorDao.getMongoTemplate().findAll(Class.forName(collectionName));
	
	return resultList;
	}


	@Override
	public void saveObject(Entity object) {
		// TODO Auto-generated method stub
		infoExtractorDao.saveEntity(object);
	}


	@Override
	public void deleteObject(Entity entity) {
		// TODO Auto-generated method stub
		infoExtractorDao.removeEntity(entity);
	}
	public static void  main(String [] args) throws FileNotFoundException{
		/*String filePath="D:\\backup\\baidusearch.xml";
		InfoExtractorMI infoM=(InfoExtractorMI) AppUtil.getBeanFromBeanContainer(InfoExtractorM.class);
		try {
			infoM.updateTask(new FileInputStream(new File(filePath)));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			ExceptionUtil.getExceptionDetailsMessage(e);
		}*/
		String path = InfoExtractorMI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		System.out.print(path);
	}


	
	/* (non-Javadoc)
	 * @see com.yufei.infoExtractor.core.InfoExtractorMI#createTask(java.lang.String, java.util.List)
	 */
	@Override
	public Task createTask(String taskName,List<String> keyWords) {
		// TODO Auto-generated method stub

		   InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("search.xml");


		TaskConfig demoTasks=(TaskConfig) CommonUtil.getObjectFromXml(in, TaskConfig.class);
		Task task = demoTasks.getTasks().get(0);

		List<UrlParameter> urlParameters= task.getSeedsites().get(0).getUrlExtractorConfig().getPaginationRule().getUrlParameters();
		for(UrlParameter urlParameter:urlParameters){
			if(urlParameter.getParameterIndex()==1){
				urlParameter.setParameterValue(CommonUtil.LinkStringWithSpecialSymbol(keyWords, AppUtil.stringSplitSymbol));
			}
		}
        task.setTaskName(taskName);
        
		return (Task) infoExtractorDao.saveEntityWithReturnValue(task);
		
	}


	@Override
	public void exportEntityToXml(String calssName, String path) {
		// TODO Auto-generated method stub
		Class entity = null;
		try {
			entity = Class.forName(calssName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			mLog.info(calssName+" not exsited!");
			return;
		}
		List<? extends Entity> sources=infoExtractorDao.getMongoTemplate().findAll(entity);
		for(Entity entity1:sources){
			if(((News)entity1).getContent().length()<400){
				continue;
			}
			try {
				CommonUtil.generateXmlForObject(entity1,entity,  path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}



	@Override
	public void startTask(String keyWords) {
		// TODO Auto-generated method stub
		this.dropTable("news");
	   mLog.info("this is a Demo!");
	   String searchFilePath=System.getProperty("user.dir")+System.getProperty("file.separator")+"search.xml";
	   InputStream in = null;
		try {
			in = new FileInputStream(new File(searchFilePath));
		} catch (FileNotFoundException e1) {
			mLog.error(e1.getMessage());
		}
	   TaskConfig demoTasks=(TaskConfig) CommonUtil.getObjectFromXml(in, TaskConfig.class);
		List<UrlParameter> urlParameters= demoTasks.getTasks().get(0).getSeedsites().get(0).getUrlExtractorConfig().getPaginationRule().getUrlParameters();
		for(UrlParameter urlParameter:urlParameters){
			if(urlParameter.getParameterIndex()==1){
				urlParameter.setParameterValue(keyWords);
			}
		}
		List<Task> tasks=demoTasks.getTasks();
		
		/*try {
			//TaskRunner taskRunner=new TaskRunner(tasks);
			taskRunner.run();
			
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}*/
		
		
	   
	}


	@Override
	public void dropTable(String tableName) {
		// TODO Auto-generated method stub
	infoExtractorDao.getMongoTemplate().dropCollection(tableName);
	}


	@Override
	public List<Task> getTasksByStatus(Integer status) {
		// TODO Auto-generated method stub
		
		return infoExtractorDao.getTaskByStatus(status);
	}


	


	@Override
	public void updateTaskStatus(Integer taskStatus,String taskid) {
		// TODO Auto-generated method st
		infoExtractorDao.updateTaskStatus(taskStatus, Long.parseLong(taskid));
		
	}


	@Override
	public Task queryEntityByPropertyValue(String queryParameter,
			Object queryValue, Class cla) {
		// TODO Auto-generated method stub
		
		return (Task)infoExtractorDao.queryEntity(queryParameter, queryValue, cla);
	}


	

	@Override
	public void updateBaiduSearchConfig(String pages) {
		// TODO Auto-generated method stub
	//	String filePath=Thread.currentThread().getContextClassLoader().getResource("conf/baidusearch.xml").getPath();
	//  InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/baidusearch.xml");
		String searchFilePath=CommonUtil.getJarParentPath(InfoExtractorM.class)+System.getProperty("file.separator")+"search.xml";
		mLog.info("当前项目路径为："+searchFilePath+"");
		InputStream in = null;
		try {
			in = new FileInputStream(new File(searchFilePath));
		} catch (FileNotFoundException e1) {
			mLog.error(e1.getMessage());
		}
		
		TaskConfig demoTasks=(TaskConfig) CommonUtil.getObjectFromXml(in, TaskConfig.class);
		List<UrlParameter> urlParameters=demoTasks.getTasks().get(0).getSeedsites() .get(0).getUrlExtractorConfig().getPaginationRule().getUrlParameters();
		for(UrlParameter urlParameter:urlParameters){
			if(urlParameter.getParameterIndex()==2){
				urlParameter.setParameterValue(pages);
			}
		}
		try {
			CommonUtil.generateXmlForObjectA(demoTasks, TaskConfig.class, searchFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void saveProxyServer(ProxyServer proxyServer) {
		// TODO Auto-generated method stub
		infoExtractorDao.saveProxyServer(proxyServer);
	}


	@Override
	public ProxyServer getProxyServer(String proxyServerIdentity) {
		// TODO Auto-generated method stub
		return infoExtractorDao.getProxyServerByIdentify(proxyServerIdentity);
	}


	public void cleanDataBaseRelatedWithTask(String taskName) {
		
	}


	@Override
	public List<Task> createTask(String taskFilesPath) {
		// TODO Auto-generated method stub
		List<Task> tasks=null;
		TaskBuilder taskBuilder=new TaskBuilder();

		if(taskFilesPath==null){
			tasks=taskBuilder.createTask(AppUtil.getInfoExtractorConfig().getTaskConfigFileDir());
		}
		else{
			tasks=taskBuilder.createTask(taskFilesPath);

		}
		return tasks;
	}


	

}
