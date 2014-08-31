/**
 * 
 */
package com.yufei.infoExtractor.extractor;

import com.yufei.dataget.entity.PaginationRule;
import com.yufei.dataget.utils.HtmlUtil;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.entity.Seedsite;
import com.yufei.infoExtractor.entity.UrlExtractorCfg;
import com.yufei.infoExtractor.listener.EventType;
import com.yufei.infoExtractor.listener.InfoExtractorEvent;
import com.yufei.infoExtractor.listener.InfoExtractorListener;
import com.yufei.infoExtractor.parallel.ParallelThreadExecutor;
import com.yufei.infoExtractor.parallel.executor.UrlExtractorParallelThreadExecutor;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.utils.CommonUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author zhaoyufei zhao-0244@qq.com
 *抽取目标Url并且负责去重处理，防止抽取重复的url,另外再次过程中还将对部分目标url进行内容解析以及解析后得到的数据的保存
 * created at 2012-8-14 下午1:31:13
 */
public class UrlExtractor  extends Extractor implements ExtractorAction,InfoExtractionAction <HActionContext>{
	private Seedsite seedsite=null;
	private ItemUrlsSave itemUrlsSave=null;
	
	public UrlExtractor() {
		super();
		
		//this.eventListeners.add(new ContentExtractor());
		// TODO Auto-generated constructor stub
	}
	private static final Log mLog = LogFactory.getLog(UrlExtractor.class);
	private final List<InfoExtractorListener> eventListeners=new ArrayList<InfoExtractorListener>();


	
	public void addEventListener(InfoExtractorListener eventListener){
		if(eventListener==null){
			throw new IllegalArgumentException();
		}
		this.eventListeners.add(eventListener);
	}

   private void fireEvent(InfoExtractorEvent event){
	   for(InfoExtractorListener infoExtractorListener:eventListeners){
		   infoExtractorListener.actionPerformed(event);
	   }
   }




	/* (non-Javadoc)
	 * @see com.rzx.crawler.extractor.ExtractorAction#extract(java.util.Map)
	 * 将抽取到的所有目标url放到readyUrlList集合中
	 */
	@Override
	public void extract(HActionContext context) {

		seedsite=context.getSeedsite();
		UrlExtractorCfg urlExtractorConfig=seedsite.getUrlExtractorConfig();
		if(urlExtractorConfig==null){
			throw new RuntimeException("请配置UrlExtractorConfig");
		}
		int depth=urlExtractorConfig.getDepth();
		if(depth==-1){
			//全网抓取
			String entryUrl=seedsite.getSiteName();
			if(CommonUtil.isEmptyOrNull(entryUrl)){
				throw new RuntimeException("全网抓取，入口地址不能为空，请配置siteName");

			}
			context.getIsRepetive().isRepetive(entryUrl);
			extractAllUrl(context);
		}
		
	    if(depth>0){
	    	this.extractUrlByDepth(context);
	    }
	    if(depth==0){
	    	//按照模板抓取
	    	if(urlExtractorConfig.getPaginationRule()==null||urlExtractorConfig.getPaginationRule().getPaginationTemplate()==null){
				throw new RuntimeException("请配置分页模板！");
				
			}
			this.extractUrlByPaginationTemplate(context);

	    }

		
     
	}
	private void extractUrlByPaginationTemplate(HActionContext context){
		mLog.info("开始根据分页模板进行目标链接抽取。。。。");
		Seedsite seedsite=context.getSeedsite();
		Set[] linkSet =context.getLinkSets();


		PaginationRule paginationRule=seedsite.getUrlExtractorConfig().getPaginationRule();
		if(!(paginationRule==null||paginationRule.getPaginationTemplate()==null||paginationRule.getUrlParameters().size()==0)){
			List<String> urls=null;
            urls=HtmlUtil.generateUrlsByPaginationRule(paginationRule);
            mLog.info("根据分页模板进行目标链接抽取结束，共抽取到'"+urls.size()+"'条链接");
			for(String url:urls){
				//对于分页链接就是一样的链接名称可能还是会有更行因此无需去重
				/*boolean isOk=FactoryUtil.getDecisionMakerByType(DecisionMaker.hasFetched).makeDecision(context, url);
			//对于分页提取url，肯定都是有用的链接无需在判断
						//&&FactoryUtil.getDecisionMakerByType(DecisionMaker.isUsefullLink).makeDecision(context, url);
				if (!isOk) {
					continue;
				}*/
				
				linkSet[0].add(url);
			}
		}
		

		
	}
	private void extractUrlByDepth(HActionContext context){
		 InfoExtractorEvent infoExtractorEvent=new InfoExtractorEvent(context);
		infoExtractorEvent.setEventType(EventType.CONTENTEXTRACTOR);
		Integer currentDepth = context.getCurrentDepth();
		Integer depth = seedsite.getUrlExtractorConfig().getDepth();

		if (currentDepth+1>=depth) {
			mLog.info("对深度为'"+currentDepth+"'上的链接的二次链接提取结束，开始对当前深度上的链接进行具体信息抽取");

			fireEvent(infoExtractorEvent);
			mLog.info("对深度为'"+currentDepth+"'上的链接具体信息抽取结束，整个递归深度信息采集结束");
			context.getInfoExtractorCommonTask().increaseUrlExtractedNumber();
			
			return;
		}
		
        mLog.info("对深度为'"+currentDepth+"'上采集到的链接进行二次链接提取");
		parseLinksFormHtml(context);
		//对当前深度已经进行子链接抽取完成的所有链接进行内容抽取，数据保存等操作并且最终将清除此深度的所有信息已提高效率
		mLog.info("对深度为'"+currentDepth+"'上的链接的二次链接提取结束，开始对当前深度上的链接进行具体信息抽取");
		fireEvent(infoExtractorEvent);
		mLog.info("对深度为'"+currentDepth+"'上的链接具体信息抽取结束，继续下层处理");

		currentDepth = currentDepth + 1;
		context.setCurrentDepth(currentDepth);
		extractUrlByDepth(context);
	    //上一层的链接进行处理
		
		
	}


	// 此方法仅仅抽取当前深度的url中的所有符合条件的链接放到下一深度集合中
	public  void parseLinksFormHtml(HActionContext context) {
		// 首先获取当前深度的所有url
		// 首先判断缓存中有没有此url对应的内容，如果有则听出处理，如果没有则请求此url然后解析返回的网页内容并抽取更多的符合条件的链接，并将解析出的链接依次加入下一深度集合中
		int blockSize=AppUtil.getInfoExtractorConfig().getUrlBlockSize();
		Set[] linkSet = context.getLinkSets();
		Integer currentDepth = context.getCurrentDepth();

		final Set<String> readyUrlList=linkSet[currentDepth];

        //判断当前深度上采集到的链接数目是否为零
        if(CommonUtil.isEmptyOrNull(readyUrlList)){
        	mLog.info("针对该网站:"+context.getSeedsite().getSiteName()+"进行Url抽取个数为零，请检查相关合法链接过滤设置!");
        	//直接返回
        	return;
        }
		ParallelThreadExecutor parallelThreadExecutor=new UrlExtractorParallelThreadExecutor();
		parallelThreadExecutor.parallelExecuteThreadsForCollectionData(readyUrlList, blockSize, context);
	}

	@Override
	public void execute(HActionContext context) throws Exception {
		// TODO Auto-generated method stub
		this.extract(context);
	}

private void extractAllUrl(HActionContext context){
	//入口地址
	String directory=AppUtil.getInfoExtractorConfig().getTempDirectory();
	String urlsDir=directory+seedsite.getDomain();
	int currentDepth=context.getCurrentDepth();
	Collection readyUrlList=null;
	if(currentDepth==0){
		readyUrlList=new ArrayList<String>();

		readyUrlList.add(seedsite.getSiteName());

		
	}
	
	
	
	
}
 
public ItemUrlsSave getItemUrlsSave() {
	return itemUrlsSave;
}

public void setItemUrlsSave(ItemUrlsSave itemUrlsSave) {
	this.itemUrlsSave = itemUrlsSave;
}


}
