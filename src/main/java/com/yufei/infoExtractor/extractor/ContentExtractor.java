/**
 * 
 */
package com.yufei.infoExtractor.extractor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.listener.EventType;
import com.yufei.infoExtractor.listener.InfoExtractorEvent;
import com.yufei.infoExtractor.listener.InfoExtractorListener;
import com.yufei.infoExtractor.parallel.ParallelThreadExecutor;
import com.yufei.infoExtractor.parallel.executor.UrlFetchParallelThreadExecutor;
import com.yufei.utils.CommonUtil;

/**
 * @author zhaoyufei zhao-0244@qq.com
 * 对文本内容进行加工分析抽取符合目标信息
 * created at 2012-8-14 下午1:32:24
 */
public class ContentExtractor extends Extractor implements InfoExtractorListener,InfoExtractionAction <HActionContext> {

















	public ContentExtractor() {
		super();
		
		
		// TODO Auto-generated constructor stub
	}

	public static final Log mLog = LogFactory.getLog(ContentExtractor.class);
	

	/* 
	 * @see com.rzx.crawler.extractor.ExtractorAction#extract(java.util.Map)
	 */
	@Override
	public void extract(final HActionContext context) {
		//unProcessedLinks
        //待解析的url集合，从数据库里面获取
		final Set<String> readyUrlList=new HashSet<String>();
		
       List<String> unProcessedLinks=(List<String>) context.get("unProcessedLinks");
       if(!CommonUtil.isEmptyOrNull(unProcessedLinks)){
    	    for(String readyUrl:unProcessedLinks){
    	    	readyUrlList.add(readyUrl);
    	    }
       }
       else{
   		Set[] linkSets = context.getLinkSets();

    	   for(Set linkSet:linkSets){
   			for(Object readyUrl:linkSet.toArray()){
   				readyUrlList.add((String) readyUrl);
   				
   			}
   		}   
       }
		
		
		int totalUrlSize = readyUrlList.size();
		if(totalUrlSize==0){
			return;
			
		}
	 
			
           

		
		int blockSize=AppUtil.getInfoExtractorConfig().getUrlBlockSize();

               // context.put(ContentExtractor.CONTENTEXTRACTOR_KEY, this);
				ParallelThreadExecutor parallelThreadExecutor=new UrlFetchParallelThreadExecutor();
			    
				parallelThreadExecutor.parallelExecuteThreadsForCollectionData(readyUrlList, blockSize, context);
					
			}
				
			
	    		
	    	








	
	@Override
	public void actionPerformed(InfoExtractorEvent event) {
		// TODO Auto-generated method stub
		if(!(event.getEventType()==EventType.CONTENTEXTRACTOR)){
			return;
		}
		Map<String,Object> context=(Map<String, Object>) event.getSource();
		//this.extract(context);
		//获取当前层
		//获取当前层链接列表
		//对当前层的链接列表进行内容抽取以及保存
		
	}
	@Override
	public void execute(HActionContext context) throws Exception {
		// TODO Auto-generated method stub
		this.extract(context);
	}

	

}
