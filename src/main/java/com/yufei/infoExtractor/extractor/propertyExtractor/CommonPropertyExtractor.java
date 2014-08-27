package com.yufei.infoExtractor.extractor.propertyExtractor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.Relatedlink;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.extractor.ExtractorAction;
import com.yufei.infoExtractor.extractor.PropertyExtractor;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.PatternUtils;

/**
 * created by @author jasstion at 2012-10-29o
 * 直接通过正则表达式来匹配网页中的内容
 * 
 * 
 */
public class CommonPropertyExtractor   extends PropertyExtractor implements ExtractorAction {
	private static final Log mLog = LogFactory.getLog(CommonPropertyExtractor.class);


	@Override
	public void extract(HActionContext context) {
		// TODO Auto-generated method stub
		PropertyMatch propertyMatch=(PropertyMatch) context.get("currentPropertyMatch");
		Relatedlink link = (Relatedlink) context.getTargetEntity();
		String htmlContent =link.getOriginalHtmlContent();
		String fieldName = propertyMatch.getProperty();
		String regex=(String) propertyMatch.getMatch().get(PropertyMatch.regex_key);
		String value = PatternUtils.getStrByRegex(htmlContent,regex );
		// 如果匹配到空值则直接返回部队此链接进行处理认为此链接为非内容链接
	if (CommonUtil.isEmptyOrNull(value)) {
			mLog.info("对" + fieldName + "进行正则表达式匹配失败,处理的链接为："
					+ link.getLink());
			mLog.debug("对应正则表达式为：："+regex);
       
			//mLog.debug("内容为："+htmlContent);
			//return false;
		}
	//对于特殊价格(比如:12-23)
	value=value.replaceAll("[\\s\\r\\n]", "");
	if(value.matches("[\\d.]{0,}-[\\d.]{0,}")){
		mLog.info("处理特殊价格"+value+"");
		value=value.substring(0, value.indexOf("-"));
		
		
		
	}
		CommonUtil.setPropertyForEntity(link, value, fieldName);

	}





}
