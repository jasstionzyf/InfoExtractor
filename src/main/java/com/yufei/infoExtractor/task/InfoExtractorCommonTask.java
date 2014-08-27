package com.yufei.infoExtractor.task;

import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.Relatedlink;
import com.yufei.infoExtractor.action.InfoActionConfigHelper;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.InfoExtractTaskStatistics;
import com.yufei.infoExtractor.entity.Pattern;
import com.yufei.infoExtractor.entity.Seedsite;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.entity.TaskFingerprint;
import com.yufei.infoExtractor.listener.InfoExtractorCommonTaskListener;
import com.yufei.infoExtractor.listener.InfoExtractorEvent;
import com.yufei.utils.ExceptionUtil;

/**
 * created by @author jasstion at 2012-9-17
 * 此对象继承Cron4j.Task,具体的执行调度交友cron4j的Schedular
 */
public class InfoExtractorCommonTask  extends  it.sauronsoftware.cron4j.Task{
	private static Log mLog = LogFactory.getLog(InfoExtractorCommonTask.class);
	private Task task;
	private List<HActionContext> taskContext=new ArrayList();
    private InfoExtractorCommonTaskListener infoExtractorCommonTaskListener=null;
    public void registerInfoExtractorCommonTaskListener(InfoExtractorCommonTaskListener listener){
    	this.infoExtractorCommonTaskListener=listener;
    }
    /**
     * 里面包含此任务的运行时各种状态信息比如：当前任务的运行进度以及采集到的和解析到的网页数量
     */
    private InfoExtractTaskStatistics infoExtractTaskStatistics=new InfoExtractTaskStatistics();
/*    private List<String> readyUrls=java.util.Collections.synchronizedList(new ArrayList());
*/    public InfoExtractTaskStatistics getInfoExtractTaskStatistics() {
		return infoExtractTaskStatistics;
	}


	public void setInfoExtractTaskStatistics(
			InfoExtractTaskStatistics infoExtractTaskStatistics) {
		this.infoExtractTaskStatistics = infoExtractTaskStatistics;
	}
	public synchronized void increaseUrlFetchedNumber(){
    	this.infoExtractTaskStatistics.setUrlFetchedNumber(infoExtractTaskStatistics.getUrlFetchedNumber()+1);
    	if(this.infoExtractorCommonTaskListener!=null){
    		InfoExtractorEvent e=new InfoExtractorEvent(this);
    	infoExtractorCommonTaskListener.urlFetchedNumberChanged(e);
    	}
    	
    }
    public synchronized void increaseUrlExtractedNumber(){
    	this.infoExtractTaskStatistics.setUrlExtractedNumber(this.infoExtractTaskStatistics.getUrlExtractedNumber()+1);
    	if(this.infoExtractorCommonTaskListener!=null){
    		InfoExtractorEvent e=new InfoExtractorEvent(this);
    	infoExtractorCommonTaskListener.urlExtractedNumberChanged(e);
    	}
    }

		public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}



		public InfoExtractorCommonTask(Task task) {
			super();
			// TODO Auto-generated constructor stub
			this.task=task;
		}
		private void init() {


	    	List<Seedsite> seeds=task.getSeedsites();
	    	HActionContext contextForSeedsite =null;
	    	for(Seedsite seed:seeds){
	    	    try {
	    	    	contextForSeedsite= this.buildContextForSeedsite(seed);

					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					mLog.info("初始化种子上下文失败,种子信息是："+seed.toString()+"\n"+"具体错误信息是："+ExceptionUtil.getExceptionDetailsMessage(e));
					continue;
				}
	    	    
	    	    this.taskContext.add(contextForSeedsite);
	    		
	    	}
	    	//初始化infoExtractTaskStatistics
	    	this.infoExtractTaskStatistics.setTaskName(this.getTask().getTaskName());
	    	
		}
	
		
		
		
		
		
		
		
		


	//change able
		private HActionContext buildContextForSeedsite(Seedsite seed) throws Exception{
			
			String classFullName=task.getEntityFullName();
			HActionContext context=new HActionContext(seed); 
			
		
			
			
			Pattern pattern=(Pattern) seed.getUrlParserConfig().getPattern();//crawlerDao.getPatternByTaskName(task.getTaskName()).get(0);
			
            
	    	Relatedlink l=(Relatedlink) Class.forName(classFullName).newInstance();
	    	Class entityClass=Class.forName(classFullName);
	        context.setCurrentDepth(0);
	        context.setTask(task);
	        context.setInfoExtractorCommonTask(this);
			Relatedlink currentLink=null;
			try {
				currentLink=(Relatedlink) entityClass.newInstance();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
	    	context.setTargetEntity(currentLink);
	   
			return context;
		}


		@Override
		public void execute(TaskExecutionContext arg0) throws RuntimeException {
			// TODO Auto-generated method stub
			
			init();
			


			Seedsite seedsite=null;
			
		//	final CountDownLatch  taskCount=new CountDownLatch(taskContext.size());

			for(HActionContext context:taskContext){
			//	context.put("taskCount", taskCount);
				arg0.pauseIfRequested();

				seedsite=context.getSeedsite();
				infoExtractorDao.updateSdStatus(Seedsite.IS_CRAWLERING, seedsite.getId());
				
				
					Set[] linkSet = context.getLinkSets();
					Integer depth=linkSet.length;
					Seedsite seed = context.getSeedsite();
					mLog.info("开始对种子网站进行信息采集.............");
			        mLog.debug(seed.toString());


	 InfoActionConfigHelper.executeInfoExtractorActions(context, 
			 InfoActionConfigHelper.getTaskInfoExtractionActions(context.getTask()));
			       
				
			    	TaskFingerprint taskFingerprint=infoExtractorDao.getTaskFingerprintByTaskName(task.getTaskName());
                    if(taskFingerprint==null){
                    	infoExtractorDao.saveTaskFingerprint(new TaskFingerprint(context.getTask().getTaskName(), context.getLinkFingerPrints()));
                    }
                    //更新
                    else{
                    taskFingerprint.setFingerprints(context.getLinkFingerPrints());
                    infoExtractorDao.saveTaskFingerprint(taskFingerprint);
                    }
						
					
					
					
					


					
				
				
			}
	
			//结束所有线程
			
		}
		
		/**
		 * @param context
		 * @param linkSet
		 * @param depth
		 * @param seed
		 */
		private void traceUrlFetchList(Map context, Set[] linkSet, Integer depth,
				Seedsite seed) {
			    Iterator iterator = null;
				if(mLog.isTraceEnabled()){
					
			    	String str="此次抽取的url为一下列表：";
			    	mLog.trace(str+"\n\n\n");
			    	for(int i=0;i<depth.intValue();i++){
			    		iterator=linkSet[i].iterator();
			    		mLog.trace("********************************************************************************\n");

			    		mLog.trace("当前深度为："+i+"\n");
			    		while(iterator.hasNext()){
			    			mLog.trace(iterator.next()+"\n");
			    			
			    		}
			    		
			    	}
			    }
		}
		public boolean canBePaused() {
			return true;
		}

		public boolean canBeStopped() {
			return true;
		}

		public boolean supportsCompletenessTracking() {
			return true;
		}

		public boolean supportsStatusTracking() {
			return true;
		}
	
}
 
