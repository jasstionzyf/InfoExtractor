/**
 * 
 */
package com.yufei.infoExtractor.core.impl;

import javax.xml.bind.JAXBException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.yufei.infoExtractor.core.InfoExtractorMI;
import com.yufei.infoExtractor.util.AppUtil;

/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-9-2 下午12:17:56
 */
public class InfoExtractorMTest {

/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.yufei.infoExtractor.core.impl.InfoExtractorM#updateTask(java.io.InputStream)}.
	 * @throws JAXBException 
	 * @throws Exception 
	 */
	@Test
	public void testUpdateTask() {
		
	}
public static void main(String[] args) throws Exception, JAXBException{
	/* InfoExtractorMI infoExtractorMI=(InfoExtractorMI) AppUtil.getBeanFromBeanContainer(InfoExtractorMI.class);
	 CrawlerDao crawlerDao=(CrawlerDao) AppUtil.getBeanFromBeanContainer(CrawlerDao.class);
		//infoExtractorMI.updateTask(new FileInputStream(new File("D:\\temp\\crawlerConfig.xml")));
	    List<Task>  tasks=crawlerDao.getTasks();
	    for(Task task:tasks){
	    	System.out.print(task.toString()+"\n");
	    }
	    System.out.print(infoExtractorMI.count("weibo"));
	    List<String> classNames=infoExtractorMI.getAllEntityFullClassName();
	    infoExtractorMI.
	    exportTaskToXml("hotel","d:\\temp\\");
	    try {
	         // Grab the Scheduler instance from the Factory
	         Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

	         // and start it off
	         scheduler.start();

	         scheduler.shutdown();
	     } catch (SchedulerException se) {
	         se.printStackTrace();
	     }
	 String keyWords = "钓鱼岛";
	infoExtractorMI.startTask(keyWords);*/
	 InfoExtractorMI infoExtractorMI=(InfoExtractorMI) AppUtil.getBeanFromBeanContainer(InfoExtractorMI.class);
     infoExtractorMI.dropTable("news");
}
}
