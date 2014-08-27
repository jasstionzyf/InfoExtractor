package com.yufei.infoExtractor.core;

import com.yufei.infoExtractor.entity.Task;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.TaskExecutor;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.listener.AbstractSchedulerListener;
import com.yufei.infoExtractor.listener.AbstractTaskExecutorListener;
import com.yufei.infoExtractor.task.InfoExtractorCommonTask;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
/**
 * created by @author jasstion at 2012-9-17
 * 执行指定的task,必须有一个构造函数参数为：此次要执行的所有task信息
 * 目前仅仅支持一下的场景使用：
 * 所有新增的任务的状态必须为：未启动的任务或者已经完成的任务
 * 任务的状态转化：
 * 未启动任务--》运行中---》pause or stop
 * pause--->运行中--》pause or stop
 * 此类应该设置为单实例
 * 
 */
public final class TaskRunner {
	private static Log mLog = LogFactory.getLog(TaskRunner.class);
	private List<Task> readyExcuteTasks=new ArrayList<Task>();
    private Scheduler scheduler=new Scheduler();
    private InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
    private Map<String,TaskExecutor> taskExecutorMap=new HashMap<String, TaskExecutor>();
    private static TaskRunner taskRunner=null;
    public static synchronized TaskRunner getTaskRunnerInstance(){
    	if(taskRunner==null){
    		taskRunner=new TaskRunner();
    	}
    	return taskRunner;
    }
	public List<Task> getReadyExcuteTasks() {
		return readyExcuteTasks;
	}

	public void setReadyExcuteTasks(List<Task> readyExcuteTasks) {
		this.readyExcuteTasks = readyExcuteTasks;
	}
	private TaskRunner() {
		super();
    	scheduler.start();
    	
	}
	private TaskRunner(List<Task> readyExcuteTasks) {
		super();
		if(CommonUtil.isEmptyOrNull(readyExcuteTasks)){
			throw new IllegalArgumentException();
		}
		this.readyExcuteTasks = readyExcuteTasks;
    	scheduler.start();

	}
	public void addSchedulerListener(AbstractSchedulerListener schedulerListener){
		this.scheduler.addSchedulerListener(schedulerListener);		
	}
	AbstractTaskExecutorListener abstractTaskExecutorListener=null;
	public void addTaskExecutorListener(AbstractTaskExecutorListener abstractTaskExecutorListener){
		this.abstractTaskExecutorListener=abstractTaskExecutorListener;
	}
	public static boolean isReadyStartStatus(Task task){
		boolean isReadyStartStatus = false;
		if(task.getStatus().equals(Task.IS_RUNED)||task.getStatus().equals(Task.UN_RUN)||task.getStatus().equals(Task.IS_FAILED)
				||task.getStatus().equals(Task.IS_STOPED)){
			isReadyStartStatus=true;
		}
		return isReadyStartStatus;
	}
	
	/**
	 * @param taskName
	 * @return
	 */
	private TaskExecutor getTaskExecutorByTaskName(String taskName){
		return taskExecutorMap.get(taskName);
	}
	private List<TaskExecutor> getSubTaskExecutorsByTaskName(String taskName){
		List<TaskExecutor> taskExecutors=new ArrayList<TaskExecutor>();
		for(String str:taskExecutorMap.keySet()){
			if(str.contains(taskName)){
				taskExecutors.add(taskExecutorMap.get(str));
			}
		}
		return taskExecutors;
	}
	/**
	 * @param needPauseTask
	 * @return null表示暂停任务失败，else 则表示暂停任务成功
	 * pause a task by the taskName
	 *不仅仅是暂停当前task还有其他的属于本人物的其他任务
	 */
	public synchronized String pauseTask(Task needPauseTask){
		String pauseResult=null;
		
		String taskName = needPauseTask.getTaskName();
		TaskExecutor needPauseTaskExecutor1=getTaskExecutorByTaskName(taskName);
		List<TaskExecutor> needPauseTaskExecutors=getSubTaskExecutorsByTaskName(taskName);
		needPauseTaskExecutors.add(needPauseTaskExecutor1);
		for(TaskExecutor needPauseTaskExecutor:needPauseTaskExecutors){
			if(needPauseTaskExecutor!=null){
				needPauseTaskExecutor.pause();
				

			}
				
		}
		pauseResult="名称为：'"+taskName+"'的任务暂停成功！";
		return pauseResult;
		
	}

	public synchronized String resumeTask(Task needResumeTask){
		String resumeResult=null;
		TaskExecutor needResumeTaskExecutor1=getTaskExecutorByTaskName(needResumeTask.getTaskName());
		List<TaskExecutor> needResumeTaskExecutors=getSubTaskExecutorsByTaskName(needResumeTask.getTaskName());
		needResumeTaskExecutors.add(needResumeTaskExecutor1);
		for(TaskExecutor needResumeTaskExecutor:needResumeTaskExecutors){
			if(needResumeTaskExecutor!=null){
			
				//会触发相关监听事件
				needResumeTaskExecutor.resume();
				resumeResult="名称为：'"+needResumeTask.getTaskName()+"'的任务有暂停到继续执行！";

			}
		
		
		    

		}
		
		return resumeResult;
	}
	//
	public synchronized String stopTask(Task needStopTask){
		String stopResult=null;
		String taskName = needStopTask.getTaskName();
		TaskExecutor needStopTaskExecutor=getTaskExecutorByTaskName(taskName);
		
		List<TaskExecutor> needStopSubTaskExecutor=getSubTaskExecutorsByTaskName(needStopTask.getTaskName());
		
		needStopSubTaskExecutor.add(needStopTaskExecutor);
		needStopSubTaskExecutor.remove(needStopTaskExecutor);
				

			
			for(TaskExecutor needPauseTaskExecutor1:needStopSubTaskExecutor){
				this.taskExecutorMap.remove(needPauseTaskExecutor1);

				needPauseTaskExecutor1.stop();

			}
			this.taskExecutorMap.remove(needStopTaskExecutor);

			needStopTaskExecutor.stop();

				stopResult="名称为：'"+taskName+"'的任务停止成功！";
				
		return stopResult;

	}

    /**
     * 启动readyExcuteTasks中的所有任务
     */
    public void run(){
    	
    	List<InfoExtractorCommonTask> tasks=(List<InfoExtractorCommonTask>)convertTaskToCron4jTask(readyExcuteTasks);
        TaskExecutor taskExecutor=null;
    	for(InfoExtractorCommonTask task:tasks){
    		//添加task相关监听器
    		//task 的状态是否合法（未启动或者已经能够执行完毕）
    		if(!isReadyStartStatus(task.getTask())){
    			continue;
    		}
    		Task task2 = task.getTask();
			if(CommonUtil.isEmptyOrNull(task2.getCron())){
				taskExecutor=scheduler.launch(task);
				addTaskExecutorListenerToTaskExecutor(taskExecutor);

    		}
    		else{
    			scheduler.schedule(task2.getCron(), task);
             
    		}
    		//设置task的状态，并持久化状态到数据库
			//有相关监听器来完成
    	/*	task2.setStatus(Task.IS_RUNNING);
    		infoExtractorDao.updateTaskStatus(task2.getStatus(), task2.getId());*/
    		
    	}
    	
    	
	
    }


	private List<? extends it.sauronsoftware.cron4j.Task> convertTaskToCron4jTask(
			List<Task> readyExcuteTasks2) {
		// TODO Auto-generated method stub
		List<it.sauronsoftware.cron4j.Task> tasks=new ArrayList<it.sauronsoftware.cron4j.Task>();
		it.sauronsoftware.cron4j.Task task=null;
		for(Task infoExtractorTask:readyExcuteTasks2){
			
			try {
				Class cron4jTaskClass=Class.forName(infoExtractorTask.getTaskExecuteJobFullName());
				Class[] pTypes={Task.class};
				Constructor constructor=cron4jTaskClass.getConstructor(pTypes);
				Object[] paraters={infoExtractorTask};
		    	task=(it.sauronsoftware.cron4j.Task)constructor.newInstance(paraters);
		    	tasks.add(task);


			} catch (Exception e) {
				mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
				}
			}
		return tasks;
	}	
	



	/**
	 * @param selectedTasks
	 * 用于启动指定的任务
	 */
	public void run(List<Task> selectedTasks) {
		// TODO Auto-generated method stub
		List<InfoExtractorCommonTask> tasks=(List<InfoExtractorCommonTask>)convertTaskToCron4jTask(selectedTasks);
        TaskExecutor taskExecutor=null;
        
    	for(InfoExtractorCommonTask task:tasks){
    		//添加task相关监听器
    		//task 的状态是否合法（未启动或者已经能够执行完毕，或者运行失败，或者强行终止）
    		/*if(!isReadyStartStatus(task.getTask())){
    			continue;
    		}*/
    		Task task2 = task.getTask();
			if(CommonUtil.isEmptyOrNull(task2.getCron())){
				taskExecutor=scheduler.launch(task);
				taskExecutorMap.put(task.getTask().getTaskName(), taskExecutor);
				addTaskExecutorListenerToTaskExecutor(taskExecutor);
    		}
    		else{
    			scheduler.schedule(task2.getCron(), task);
             
    		}
    		//设置task的状态，并持久化状态到数据库
			//有相关监听器来完成
    	/*	task2.setStatus(Task.IS_RUNNING);
    		infoExtractorDao.updateTaskStatus(task2.getStatus(), task2.getId());*/
    		
    	}
	}
	/**
	 * @param tasks:要运行的task
	 * @param taskNames:要运行的task的名字
	 */
	public void run(List<it.sauronsoftware.cron4j.Task> tasks,List<String> taskNames) {
		// TODO Auto-generated method stub
        TaskExecutor taskExecutor=null;
        
    	for(int i=0;i<tasks.size();i++){
    		
			
				taskExecutor=scheduler.launch(tasks.get(i));
				taskExecutorMap.put(taskNames.get(i), taskExecutor);
				//addTaskExecutorListenerToTaskExecutor(taskExecutor);
    		}
    		
    		
    		
    	
	}
	private void addTaskExecutorListenerToTaskExecutor(TaskExecutor taskExecutor) {
		if(abstractTaskExecutorListener!=null){
			taskExecutor.addTaskExecutorListener(abstractTaskExecutorListener);
		}
	}
	public synchronized void removeTaskExecutorByTaskName(String taskName){
		List<String> deleteKeys=new ArrayList<String>();
		for(String str:taskExecutorMap.keySet()){
			if(str.contains(taskName)){
				deleteKeys.add(str);
			}
		}
		for(String deleteKey:deleteKeys){
		taskExecutorMap.remove(deleteKey);	
		}
	}
	public void finalize(){
	
		//停止所有Task
		this.scheduler.stop();
		//程序正常推出
		System.exit(0);
		
	}
public String getTaskNameByTask(it.sauronsoftware.cron4j.Task currentTask){
		
		for(String name:taskExecutorMap.keySet()){
			if(taskExecutorMap.get(name).getTask()==currentTask){
				return  name;
			}
		}
		return null;
	}
public void startBackGroudThread(){
	//开启数据进程(此进程只在此处仅仅初始化一次)
	Thread dbThread=new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			AppUtil.startMongodb();
		}
	});
	//dbThread.setDaemon(true);
	dbThread.start();


}
/**
 * @param args:taskNames
 * 此方法用于启动taskRunner去执行任务（有方法参数指定）
 * 
 */
public static void main(String[] taskNames){

	/*TaskRunner taskRunner=TaskRunner.getTaskRunnerInstance();
	InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
	//taskRunner.startBackGroudThread();
	try {
		Thread.currentThread().sleep(1000*10);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    List<Task> tasks=new TaskBuilder().createTask("F:\\tasks\\");
   
    if(CommonUtil.isEmptyOrNull(tasks)){
    	tasks=infoExtractorDao.queryListEntity(null, Task.class);
    }

	taskRunner.run(tasks);
	infoExtractorDao.getDataRepositoryI().getMongoOperations().dropCollection(Task.class);*/
ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(2);
	


	//定期想spider-server 获取价格更行任务
	scheduledExecutorService.scheduleAtFixedRate(new TMallSpProductCollectThread(), 1,60*60*6, TimeUnit.SECONDS);
	
	
}
	/**
	 * @param taskNames:需要进行状态重置的任务名称s
	 * 重置那些非正常失败的任务状态
	 */
	public   static void resetTaskStatus(List<String> taskNames){
		InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
		Task task=null;
		for(String taskName:taskNames){
			task=infoExtractorDao.getDataRepositoryI().queryEntity("taskName", taskName, Task.class);
			task.setStatus(Task.IS_FAILED);
			mLog.info("名为:"+taskName+"的任务状态被重置！");
			infoExtractorDao.saveEntity(task);
		}
        
}
	
}

