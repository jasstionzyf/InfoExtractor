package com.yufei.infoExtractor.parallel.executor;

import it.sauronsoftware.cron4j.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.TaskRunner;
import com.yufei.infoExtractor.parallel.ParallelThreadExecutor;
import com.yufei.infoExtractor.parallel.thread.UrlParserThread;
import com.yufei.utils.CommonUtil;

public class UrlExtractorParallelThreadExecutor implements
		ParallelThreadExecutor {
	public static final Log mLog = LogFactory.getLog(UrlExtractorParallelThreadExecutor.class);

	@Override
	public void parallelExecuteThreadsForCollectionData(Collection ks,
			int blockSize, HActionContext context) {
		// TODO Auto-generated method stub
		List<List<String>> urlsGroup=CommonUtil.splitKs(ks, blockSize);
		int groupCount=urlsGroup.size();
		CountDownLatch countDownLatch = new CountDownLatch(groupCount);
		//主线程实例化一个线程安全的threadLocal变量，这样子线程就可以安全的方位某些共享对象
		ThreadLocal<HActionContext> threadLocalcontext=new ThreadLocal<HActionContext>();
		threadLocalcontext.set(context);
		List<Task> needExecutorTasks=new ArrayList<Task>();
		List<String> subTaskNames=new ArrayList<String>();
        String taskName=context.getTask().getTaskName();
		Task executorThread=null;
			int threadNumber=0;
			List<String> urlList=null;
			for(int i=0;i<urlsGroup.size();i++){
				urlList=urlsGroup.get(i);
				threadNumber+=1;
				
				executorThread =new UrlParserThread(threadLocalcontext,urlList, countDownLatch);
                needExecutorTasks.add(executorThread);
                subTaskNames.add(""+taskName+"#"+i+"");
			}
			TaskRunner.getTaskRunnerInstance().run(needExecutorTasks,subTaskNames);
			try {
				countDownLatch.await();
				
	

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			throw new IllegalStateException();
			}finally{
			}
	}


}
