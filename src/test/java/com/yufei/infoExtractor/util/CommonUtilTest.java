package com.yufei.infoExtractor.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.yufei.infoExtractor.pfw.entity.Weibo;
import com.yufei.infoExtractor.util.CommonUtil;
import com.yufei.infoExtractor.util.PatternUtils;
public class CommonUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetPropertyForEntity() {
		Weibo weibo=new Weibo();
		String userName="sdfsdfsfd";
		CommonUtil.setPropertyForEntity(weibo, userName, "userName");
		assertEquals(userName, weibo.getUserName());
	}
   @Test
   public void testIsCollectionType(){
	  /* Object list=new ArrayList();
	   Class[] listClasses=list.getClass().getClasses();
	   for(Class c:listClasses){
		   System.out.print(c.getSimpleName());
		   
		   
	   }
	   assertEquals(true,	   CommonUtil.isCollectionType(list));
	   Object str="sdf";
	   assertEquals(false, CommonUtil.isCollectionType(str));*/
   }
   @Test
   public void getStringFromFile(){
		File file=new File("src/test/resources/html/qqweiboTest.html");

		String currentHtmlContent=CommonUtil.getStringFromFile(file);
		Assert.assertNotNull(currentHtmlContent);

	   
   }
   @Test
   public void testWorkerPool() throws InterruptedException{
	  /* WorkerPool workerPool=(WorkerPool) AppUtil.getBeanFromBeanContainer(WorkerPool.class);
	   Assert.assertEquals(0, workerPool.getBusyWorkerCount());
	   WeiboTaskExecuteJob job=new WeiboTaskExecuteJob(null);
	   workerPool.dispatchJob(job);
	   Assert.assertEquals(1, workerPool.getBusyWorkerCount());
*/
	   
   }
   @Test
   public void testAddUniqueElement(){
	   ArrayList<String> list=new ArrayList<String>();
	   PatternUtils.addUniqueElement(list, "");
	   PatternUtils.addUniqueElement(list, "");
	   Assert.assertEquals(1, list.size());

   }
   @Test
   public void splitKs(){
	   List<String> data0=new ArrayList<String>(),
			   data1=new ArrayList<String>(),
			   data2=new ArrayList<String>();
	   int blockSize0=0,blockSize1=1,blockSize2=2;
	   data1.add("sdfsdf");
	   data2.add("sdfdf");
	   data2.add("sdfdf");
	   data2.add("sdfdf");
	   data2.add("sdfdf");

	   int resultSize1 = CommonUtil.splitKs(data1, blockSize1).size();
	   int resultSize2 = CommonUtil.splitKs(data1, blockSize2).size();
	   int resultSize3= CommonUtil.splitKs(data2, blockSize2).size();
	   List<List<String>> str=CommonUtil.splitKs(data2, blockSize2);
	   for(List<String> urlList:str){
		   System.out.print(urlList.get(0));
	   }

	Assert.assertEquals(1, resultSize1);
	Assert.assertEquals(1, resultSize2);
	Assert.assertEquals(2, resultSize3);



	  
   }
}
