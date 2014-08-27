package com.yufei.infoExtractor.extractor.propertyExtractor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.Relatedlink;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.extractor.ExtractorAction;
import com.yufei.infoExtractor.extractor.PropertyExtractor;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.HtmlUtil;
import com.yufei.utils.PatternUtils;
import com.yufei.utils.SequenceUUID;

/**
 * created by @author jasstion at 2012-10-29
 * 用于提取多媒体内容（比如：网页中的图片，视频等）
 * 
 * 
 * 
 */
public class MediaPropertyExtractor   extends PropertyExtractor implements ExtractorAction {
	private static final Log mLog = LogFactory.getLog(CommonPropertyExtractor.class);

	@Override
	public void extract(HActionContext context) {
		// TODO Auto-generated method stub
		PropertyMatch propertyMatch=(PropertyMatch) context.get("currentPropertyMatch");
		Relatedlink link = (Relatedlink) context.getTargetEntity();
		String htmlContent =link.getOriginalHtmlContent();
		String fieldName = propertyMatch.getProperty();
		// 分析出多媒体下载链接并且更根据此链接生成唯一的名字保存到系统指定的mediaDirectory目录中
	   

		String mediaUrl = PatternUtils.getStrByRegex(htmlContent, (String) propertyMatch.getMatch().get(PropertyMatch.regex_key));
		if (mediaUrl == null || mediaUrl.trim().equals("")) {
			//照片允许获取失误
			return;
		}
		CommonUtil.formatUrl(mediaUrl);
		String mediaSaveName = SequenceUUID.getInstance().getUUID()
				+ mediaUrl.substring(mediaUrl.lastIndexOf("."));
		
		try {
			
		    HtmlUtil.downLoadPicture(mediaUrl, mediaSaveName,AppUtil.getInfoExtractorConfig().getMediaDirectory());
		     CommonUtil.setPropertyForEntity(link, mediaSaveName,
						fieldName);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mLog.info("action for the " + fieldName + "wrong!");
			mLog.debug(ExceptionUtil.getExceptionDetailsMessage(e));
		}
		

	}





}
