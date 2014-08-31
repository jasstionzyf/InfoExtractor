package com.yufei.infoExtractor.core;

import com.yufei.infoExtractor.entity.Task;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.util.AppUtil;

public class TaskRunnerTest {
TaskBuilder taskBuilder; 
InfoExtractorDao infoExtractorDao=null;
	@Before
	public void setUp() throws Exception {
		taskBuilder=new TaskBuilder();
		infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
	}

	@After
	public void tearDown() throws Exception {
	
	}

	@Test
	public void testRunTask() {
		TaskRunner taskRunner=TaskRunner.getTaskRunnerInstance();

		taskBuilder.createTask(AppUtil.getInfoExtractorConfig().getTaskConfigFileDir());
		Task task=infoExtractorDao.queryEntity("taskName", "MeiShi", Task.class);
		Assert.assertNotNull(task);
		Assert.assertEquals("http://www.dianping.com/", task.getSeedsites().get(0).getSiteName());
		List<Task> tasks=new ArrayList<Task>();
		tasks.add(task);
		taskRunner.run(tasks);
	  
		
		
	}

}
