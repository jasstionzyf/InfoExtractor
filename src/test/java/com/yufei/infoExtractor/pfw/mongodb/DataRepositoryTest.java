package com.yufei.infoExtractor.pfw.mongodb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.MongoDbFactory;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.yufei.infoExtractor.pfw.entity.Entity;
import com.yufei.infoExtractor.pfw.entity.Pattern;
import com.yufei.infoExtractor.pfw.entity.Seedsite;
import com.yufei.infoExtractor.pfw.entity.Task;
import com.yufei.infoExtractor.pfw.entity.Weibo;
import com.yufei.infoExtractor.util.AppUtil;

public class DataRepositoryTest {
	static DataRepository dataRepository=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
	static Weibo weibo=null;
	@Before
	public void setUp() throws Exception {
		DataRepository datar=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
		datar.getMongoOperations().dropCollection(Weibo.class);
		weibo=new Weibo();
		
		java.util.List list=new ArrayList();
		list.add("test");
		weibo.setContents(list);
		weibo.setUserName("jasstion");
		weibo.setSign("weibo");
		weibo.setHashCode("458935nknskdfu9ou5kn3k");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {
		//测试嵌套对象的保存以及更新
	//	dataRepository.getMongoOperations().dropCollection(Task.class);
		Task task=new Task();
		String taskName = "test";
		task.setTaskName(taskName);
		List<Seedsite> seedsites=new ArrayList<Seedsite>();
		Pattern pattern=new Pattern();
		pattern.setTaskName(taskName);
		Seedsite seedsite=new Seedsite();
		seedsite.setPattern(pattern);
		
	  task.getSeedsites().add(seedsite);
	
		dataRepository.save((Object)task);
		Task queryEntity = (Task) dataRepository.queryEntity("taskName", taskName, Task.class);
		System.out.print(queryEntity.getId()+"\n");
		queryEntity.getSeedsites().get(0).setTaskName("sdfsdf");
		dataRepository.save((Object)queryEntity);
		queryEntity = (Task) dataRepository.queryEntity("taskName", taskName, Task.class);
		System.out.print(queryEntity.getId()+"\n");

		//Assert.assertEquals(taskName, queryEntity.getSeedsites().get(0).getTaskName());
	//	Assert.assertEquals(taskName, queryEntity.getSeedsites().get(0).getPattern().getTaskName());
	
		dataRepository.getMongoOperations().dropCollection(Task.class);
	}

	@Test
	public void testQueryBySign() {
		//fail("Not yet implemented");
	}
	@Test

	public void queryEntity(){
		Pattern pattern=new Pattern();
		pattern.setTaskName("weibo");
		String currentContentRegex="msgCnt>(.*)</div>";
		String pageUrlRegex="pageBtn href=\"(.*?)\">下一页";
		String userNameRegex="nickName = '(.*)'";
		Map<String,String> matches=new HashMap<String, String>();
		matches.put("userName", userNameRegex);
		matches.put("contents", pageUrlRegex+":"+currentContentRegex);
		dataRepository.save(pattern);
		Entity entity=dataRepository.queryEntity("taskName", "weibo",Pattern.class);
		Assert.assertNotNull(entity.getId());
		Pattern pattern1 =(Pattern)entity;
		
	}
	@Test
	public void updateEntityTest(){
		dataRepository.save(weibo);
		Weibo weibo1=(Weibo) dataRepository.queryBySign("weibo", Weibo.class).get(0);
		dataRepository.updateEntity(weibo1.getId(), Weibo.class, "userName", "zhaoyufei");
		weibo1=(Weibo) dataRepository.queryBySign("weibo", Weibo.class).get(0);
		Assert.assertEquals("zhaoyufei", weibo1.getUserName());
	}

	public void saveFile() throws IOException{
		String filePath="F:\\我相信.mp3";
		MongoDbFactory mongoDbFactory=dataRepository.mongoDbFactory;
		
		GridFSInputFile file=new GridFS(mongoDbFactory.getDb(), "media").createFile(new File(filePath));
	//	Assert.assertEquals("我相信", file.getFilename());
		file.setContentType("mp3");

	    file.save();
	    String savePath="F:\\rtrtrt1234.mp3";
	    OutputStream out=new FileOutputStream(savePath);
	    GridFSDBFile gridFile= new GridFS(mongoDbFactory.getDb(), "media").find(new ObjectId(file.getId().toString())); 
	    gridFile.writeTo(out);
		
		
		
	}
	@Test
	public void queryAllValuesOfFieldTest(){
	/*	dataRepository.getMongoOperations().dropCollection(Weibo.class);
		dataRepository.save(weibo);
		Map queryObject=new HashMap();
		queryObject.put("userName", weibo.getUserName());
		//dbObject.put("taskName", weibo);
		List hashCodes=dataRepository.queryAllValuesOfField("hashCode", Weibo.class,queryObject);
		System.out.print(hashCodes.get(0));
		Assert.assertEquals(1, hashCodes.size());*/
	}
	public static  void main(String[] args){
		
		DataRepository datar=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
		datar.getMongoOperations().dropCollection(Weibo.class);
		Entity e=new Weibo();
		System.out.print(e.getClass().getCanonicalName());
		weibo=new Weibo();
		
		/*java.util.List list=new ArrayList();
		list.add("test");
		weibo.setContents(list);
		weibo.setUserName("jasstion");
		weibo.setSign("weibo");
		weibo.setHashCode("458935nknskdfu9ou5kn3k");
		dataRepository.save(weibo);
		Weibo weibo1=(Weibo) dataRepository.queryBySign("weibo", Entity.class).get(0);
		
		System.out.print(weibo1.getContents().size()+"\n");
		weibo1.getContents().add("test");
		datar.save(weibo1);
		System.out.print(weibo1.getContents().size()+"\n");*/

		dataRepository.getMongoOperations().dropCollection("weibo");
	}
}
