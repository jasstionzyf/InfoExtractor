package com.yufei.infoExtractor.pfw.mongodb;

import com.yufei.infoExtractor.entity.Hotel;
import com.yufei.infoExtractor.entity.Seedsite;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.entity.Weibo;
import java.io.File;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.util.AppUtil;

public class CrawlerDaoMongodImplTest {
static InfoExtractorDao crawlerDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	public void testGetDataRepository() {
		
	}

	
	public void testSetDataRepository() {
		
	}

	
	public void testGetSeedSiteByStatus() {
		
	}

	
	public void testGetSeedSiteByTaskId() {
		
	}

	
	public void testUpdateSdStatus() {
		
	}

	
	public void testSeedsite_setTime() {
		
	}

	
	public void testGetTasks() {
		
	}

	
	public void testGetTaskByStatus() {
		
	}

	
	public void testUpdateTaskStatus() {
		
	}

	
	public void testSetTaskTime() {
		
	}

	
	public void testBatchInsertLinks() {
		
	}

	
	public void testInsertTF() {
		
	}

	
	public void testGetLinkFingerprints() {
		
	}

	
	public void testAddSeedsite() {
//		DataRepository datar=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
//		datar.getMongoOperations().dropCollection(Seedsite.class);
//		Seedsite seedsite=new Seedsite();
//		seedsite.setDomain("qq.com");
//		seedsite.getUrlExtractorConfig().setDepth(2);
//		seedsite.setSiteName("weibo");
//		seedsite.setStatus(Seedsite.UN_CRAWLERED);
//		crawlerDao.addSeedsite(seedsite);
//		List<Seedsite> seedsites=crawlerDao.getSeedSiteByStatus(Seedsite.UN_CRAWLERED);
//		Assert.assertNotNull(seedsites.get(0));
//		System.out.print(seedsites.size());

		
	}

	@Test
	public void testAddTask() {
//		DataRepository datar=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
//		datar.getMongoOperations().dropCollection(Task.class);
//
//		Task task=new Task();
//		task.setStatus(Task.UN_RUN);
//		task.setTaskExecuteJobFullName("com.rzx.crawler.job.WeiboTaskExecuteJob");
//		task.setTaskName("weibo");
//		crawlerDao.addTask(task);
//		//Assert.assertNotNull(crawlerDao.getTaskByStatus(Task.UN_RUN).get(0).getTaskName());
//		System.out.print(task.getTaskName());

		
		
		
	}
	
	public void queryMediaFileById(){
		File file=new File("F:\\孤单的习惯.mp3");
		String fileId=crawlerDao.saveMediaFile(file);
		File file1=crawlerDao.queryMediaFileById(fileId);
		
		Assert.assertNotNull(file1);
	}
	
	public void generateXmlForTaskCrawlerResult(){
		Weibo weibo=new Weibo();
		weibo.setUserName("jasstion");
	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	
	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	

	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	

	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	

	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	
//	    DataRepository dataR=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
//	    dataR.save(weibo);
//		crawlerDao.generateXmlForTaskCrawlerResult(Weibo.class);
	}
	
	
	public void getLinkFingerprintsTest(){
//		DataRepository dataRepository=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
//
//
//		Map queryObject=new HashMap();
//	//	queryObject.put("userName", weibo.getUserName());
//		//dbObject.put("taskName", weibo);
//		Set set=crawlerDao.getLinkFingerprints("", Hotel.class);
//		//DataRepository dataRepository=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);

		
	}
	public static void main(String[] args){
		//DataRepository dataRepository=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
      /*  Weibo weibo=new Weibo();
        weibo.setName("zhaoyufe");
        weibo.setTitle("sdf");
        dataRepository.save(weibo);
       Weibo weibo1= (Weibo) dataRepository.queryEntity("name", "zhaoyufe", Weibo.class);
       System.out.print(weibo1.getId());
       dataRepository.removeEntity(weibo1);
       Weibo weibo2= (Weibo) dataRepository.queryEntity("id", weibo1.getId(), Weibo.class);
       Assert.assertNull(weibo2);
		Map queryObject=new HashMap();
		List<Goods> list=dataRepository.getMongoOperations().findAll(Goods.class);
//dataRepository.getMongoOperations().dropCollection(Goods.class);
		int num=0;
		List<String> demoTexts=new ArrayList();
		for(Goods hotel:list){
			if(!CommonUtil.isEmptyOrNull(hotel.getGoodName())){
				System.out.print(hotel.toString()+"\n");		
			}
		}
		System.out.print(list.size());
		//Map<String,Double> map=CommonUtil.getEigenvector(demoTexts);
		
		
		
		List<Task> list1=dataRepository.getMongoOperations().findAll(Task.class);
	for(Task task:list1){
			
				System.out.print(task.toString()+"\n");
				break;*/
		//dataRepository.getMongoOperations().dropCollection(Hotel.class);
				
			
		}



	
}
