package com.yufei.infoExtractor.extractor.propertyExtractor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.extractor.PropertyExtractor;
import com.yufei.infoExtractor.extractor.paging.PagingAction;
import com.yufei.infoExtractor.util.FactoryUtil;

/**
 * created by @author jasstion at 2012-10-29
 * 用于某些集合属性的提取处理（比如：某些新闻中的评论信息）
 * 
 */
public class CollectionPropertyExtractor  extends PropertyExtractor{
	private static final Log mLog = LogFactory.getLog(CollectionPropertyExtractor.class);

	@Override
	public void extract(HActionContext context) {
		// TODO Auto-generated method stub
	PropertyMatch propertyMatch=(PropertyMatch) context.get("currentPropertyMatch");
	/*Relatedlink link =(Relatedlink) context.getTargetEntity();
		String htmlContent = link.getOriginalHtmlContent();
		String fieldName = propertyMatch.getProperty();
		Map<String,Object> fieldRegex=propertyMatch.getMatch();

	


		if(PropertyMatch.matchType_collection_na_assembleUrl.equals(propertyMatch.getMatchType())){
			//<!---评论内容正则表达式:分页template:每页的记录数:总评论数目正则匹配表达式:begainPagNumber:pagNumberSpace-->
			String contentRegex=(String) fieldRegex.get("contentRegex");
			String pageUrlTemplate=null;
			Integer    pageSize=0;
			String totalRecorderNumberRegex=null;
			Integer begainPagNumber=null;
			Integer pagNumberSpace=null;
			
            //根据这些参数将采集出集合信息
			context.put("pageUrlTemplate", pageUrlTemplate);
			context.put("contentRegex", contentRegex);

			context.put("pageSize", pageSize);
			context.put("begainPagNumber", begainPagNumber);
			context.put("pagNumberSpace", pagNumberSpace);

			context.put("totalRecorderNumberRegex", totalRecorderNumberRegex);
			context.put("currentFieldName", fieldName);
			

			
		   
		}*/
		PagingAction pagingAction=FactoryUtil.createPagingAction(propertyMatch.getMatchType(), context);
		try {
			pagingAction.action();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mLog.info("在对"+propertyMatch.getProperty()+"进行抽取的时候发生错误，错误信息是:"+e.getMessage()+"");
		throw new RuntimeException(e);
		}
       
	}




}
