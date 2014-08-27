package com.yufei.infoExtractor.parallel.thread;

import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.component.repetition.IsRepetive;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.entity.Seedsite;
import com.yufei.infoExtractor.extractor.fetchdecision.DecisionMaker;
import com.yufei.infoExtractor.util.FactoryUtil;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.HtmlUtil;
import com.yufei.utils.PatternUtils;

/**
 * @author jasstion
   2012-11-23
   url extractor
 *
 */
public class UrlParserThread extends Task {
	public static final Log mLog = LogFactory.getLog(UrlParserThread.class);
    private HActionContext context=null;
	private List<String> readyUrls = null;
	private CountDownLatch countDownLatch = null;
    
	public UrlParserThread(ThreadLocal<HActionContext> threadLocalcontext, List<String> readyUrls,
			CountDownLatch countDownLatch) {
		super();
    this.context=threadLocalcontext.get();
    this.readyUrls = readyUrls;
		this.countDownLatch = countDownLatch;
	}

	

	public List<String> getReadyUrls() {
		return readyUrls;
	}

	public void setReadyUrls(List<String> readyUrls) {
		this.readyUrls = readyUrls;
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	
	
		
	public void extractorUrls(HActionContext context,TaskExecutionContext taskExecutionContext){
		// TODO Auto-generated method stub
	
		Integer currentDepth = context.getCurrentDepth();
		
		IsRepetive  isRepetive=context.getIsRepetive();
		Iterator<String> iter = readyUrls.iterator();
		String htmlContent = null;
		Seedsite seed=context.getSeedsite();
		String  url1 = null;
		
		for(String url:readyUrls){
			
			taskExecutionContext.pauseIfRequested();
			 if(taskExecutionContext.isStopped()){
	        	   throw new IllegalStateException();
	           }

		if(isRepetive.isRepetive(url)){
			continue;
		}
			
			 htmlContent=HtmlUtil.getHtmlContent(url, 2, 0, false);
			if(CommonUtil.isEmptyOrNull(htmlContent)){
				continue;
			}
	
			String filterregexes = (String) PropertyMatch.getPropertyMatchFromListByPropertyName("linkFilterRegex", null).getMatch().get(PropertyMatch.regex_key);
				
			List<String> filterUrls = PatternUtils.getListStrByRegex(
					htmlContent, filterregexes);
			if (CommonUtil.isEmptyOrNull(filterUrls)) {
				mLog.debug("在对链接：'"+url+"'进行子链接提取的过程中，根据链接匹配正则表达式'"+filterregexes+"'对网页内容：'"+htmlContent+"'匹配失败，请检查网页内容或者是链接匹配表达式是否有误");
				continue;
			}
			int totalValidateUrlCount=0;
			for (String extractedUrl : filterUrls) {
				try {
				mLog.debug("开始对子链接:'"+extractedUrl+"'进行链接合法性检测以及链接补全");
				taskExecutionContext.pauseIfRequested();
				 if(taskExecutionContext.isStopped()){
		        	   throw new IllegalStateException();
		           }
				if (!CommonUtil.isValidUrl(extractedUrl)) {
	
					continue;
	
				}
				url1 = CommonUtil.formatUrl(extractedUrl);
				url1 = CommonUtil.normalizeUrl(extractedUrl, url);
				
		      if(url1==null){
		    	  continue;
		      }
		      //判断是否是itemUrl或者itemsUrl
				
				mLog.debug("补全后的链接为'"+url1+"'");
				totalValidateUrlCount++;
				if(isItemUrl(url1)){
					if(!isRepetive.isRepetive(url1)){
						
					}
					
				}
				else{
					if(!isRepetive.isRepetive(url1)){
						
					}
				}
			

		
				
		
				
				

			

			
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					mLog.info(ExceptionUtil.getExceptionDetailsMessage(e1));
					continue;
				} 
				
	
			}


			mLog.debug("子链接'"+url+"'总共抽取到有效链接个数为：'"+totalValidateUrlCount+"'");


		}

	}




private boolean isItemUrl(String url){
	boolean isItemUrl=false;
	if(FactoryUtil.getDecisionMakerByType(DecisionMaker.isUsefullLink).makeDecision(context, url)){
		isItemUrl=true;
	}
	return isItemUrl;
	
}





	@Override
	public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
		// TODO Auto-generated method stub
		extractorUrls(context,taskExecutionContext);

		countDownLatch.countDown();
		mLog.info("线程执行完毕");

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
