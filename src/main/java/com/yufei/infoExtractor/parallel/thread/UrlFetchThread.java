package com.yufei.infoExtractor.parallel.thread;

import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.Relatedlink;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.extractor.PropertyExtractor;
import com.yufei.infoExtractor.extractor.propertyExtractor.PropertyExtractorFactory;
import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.pfw.entity.PropertyMatch;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.EncryptUtil;
import com.yufei.utils.ExceptionUtil;


/**
 * created by @author jasstion at 2012-10-31 用于采集Urls的进程
 */
public class UrlFetchThread  extends Task {
	public static final Log mLog = LogFactory.getLog(UrlFetchThread.class);
	InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
    private HActionContext context=null;
	private List<String> readyUrls = null;
	private CountDownLatch countDownLatch = null;



	public List<String> getReadyUrls() {
		return readyUrls;
	}




	public UrlFetchThread(ThreadLocal<HActionContext> threadLocalcontext, List<String> urlList,
			CountDownLatch countDownLatch) {
		// TODO Auto-generated constructor stub
		super();
		this.context=threadLocalcontext.get();
		this.readyUrls = urlList;
		this.countDownLatch = countDownLatch;
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

	

	



	@SuppressWarnings("unchecked")
	private void fetchUrlList(HActionContext context,TaskExecutionContext taskExecutionContext) {
		String currentHtmlContent = null;
		Relatedlink currentLink = null;
		Class entityClass =context.getTargetEntity().getClass();
		String taskName =context.getTask().getTaskName();
		for (String url : readyUrls) {
			taskExecutionContext.pauseIfRequested();
           if(taskExecutionContext.isStopped()){
        	   break;
           }
            context.getInfoExtractorCommonTask().increaseUrlFetchedNumber();
		
			String urlMd5 = EncryptUtil.md5(url);

			try {
				// Element
				// element=AppUtil.getCacheByCacheName(AppUtil.defaultUrlCache).get(urlMd5);
				/*InfoExtractorCache<String, String> urlCache = (InfoExtractorCache<String, String>) context
						.get("urlCache");
				currentHtmlContent = urlCache.get(urlMd5);*/
				//直接从数据库中读取
				currentLink=(Relatedlink)infoExtractorDao.getDataRepositoryI().queryEntity("hashCode", urlMd5, entityClass);
			   // Thread.currentThread().sleep(1000*10);
				if(currentLink==null){
					continue;
				}
				currentHtmlContent=currentLink.getOriginalHtmlContent();



				mLog.debug("获取连接内容：" + url);
		
			
          if(CommonUtil.isEmptyOrNull(currentHtmlContent)){
        	  //一次访问失败多次重试
        	  currentHtmlContent=getHtmlContent(currentLink.getLink(),context.getSeedsite().getRetryCount()+1);//getHtmlContent(currentLink.getLink());
              currentLink.setOriginalHtmlContent(currentHtmlContent);
          }
          if(CommonUtil.isEmptyOrNull(currentHtmlContent)){
        	  continue;
          }

			// 目前无论采取何种方式抓取数据深度都设置为零
			currentLink.setDepth(0);
			
			context.setTargetEntity(currentLink);
			currentLink.setOriginalHtmlContent(currentHtmlContent);
			
			boolean isSuccess = setPropertiesOnLink(context);
			if (!isSuccess) {
				// 针对此链接处理失败，原因可能是此链接不是内容链接将此链接加入到当前指纹集合中避免下次重复处理
				mLog.debug("链接：" + url + "很可能为非内容链接，直接忽略！");
				continue;
			}
            //对处理过的数据的保存
			InfoExtractorDao infoExtractorDao = (InfoExtractorDao) AppUtil
					.getBeanFromBeanContainer(InfoExtractorDao.class);
			currentLink.setProcessed(true);
			infoExtractorDao.saveEntity(currentLink);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				mLog.info("处理链接:" + url + "出错!");
				mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
				continue;
			}

		}
	}
	
	private String getHtmlContent(String url,int visitCount){
		String htmlContent=null;
		if(visitCount==0){
			mLog.info("针对链接:"+url+"的访问失败！");
			return null;
		}
		else{
			try {
				htmlContent = CommonUtil.getHtmlContent(context.getSeedsite().getDataRetrieverFeatures().getProxy(), context.getSeedsite().getDataRetrieverFeatures().getRequestExecuteJs(), url);
			   
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				htmlContent=getHtmlContent(url, visitCount-1);
			}
			if (CommonUtil.isEmptyOrNull(htmlContent)) {
				htmlContent=getHtmlContent(url, visitCount-1);
		    }	
			else{
				return htmlContent;
			}
		}
		
	return htmlContent;
	}

	private String getHtmlContent(String url){
		String htmlContent=null;
		try {
			htmlContent = CommonUtil.getHtmlContent(context.getSeedsite().getDataRetrieverFeatures().getProxy(), context.getSeedsite().getDataRetrieverFeatures().getRequestExecuteJs(), url);
		   

		} catch (IOException e) {
			// TODO Auto-generated catch block
			mLog.info("在对链接进行子链接提取的过程中访问+'"+url+"'出错，直接忽略该Url的子链接提取,错误信息："+ExceptionUtil.getExceptionDetailsMessage(e)+"");
			
		}
		if (CommonUtil.isEmptyOrNull(htmlContent)) {
			mLog.info("链接+'"+url+"'请求返回的内容为空，直接忽略该Url的子链接提取");

			
		}
		return htmlContent;
		
	}
	/**
	 * @param context
	 * @return 表示针对此链接的处理是否成功用于判断后续是否保存
	 */
	public boolean setPropertiesOnLink(HActionContext context) {
		Relatedlink link = (Relatedlink) context.getTargetEntity();
		List<PropertyMatch> propertyMatches = context.getSeedsite().getPattern().getPropertyMatches();
		List<Field> fileds = CommonUtil.getAllDeclareFields(link.getClass());
		String htmlContent =link.getOriginalHtmlContent();
		



		// 设置链接发现时间
		link.setFindTime(new Date());

		String fieldName = null;
		String value = null;
		PropertyMatch propertyMatch = null;
		String fieldRegex = null;
		for (Field field : fileds) {
			// collection
			propertyMatch = PropertyMatch.getPropertyMatchFromListByPropertyName(
					field.getName(), propertyMatches);

			if (propertyMatch == null) {
				continue;
			}
			context.put("currentPropertyMatch", propertyMatch);
			PropertyExtractor propertyExtractor = PropertyExtractorFactory
					.getPropertyExtractor(propertyMatch);
			propertyExtractor.extract(context);

		}

		context.put("currentLink", link);
		return true;
	}




	@Override
	public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		fetchUrlList(this.context,taskExecutionContext);

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
