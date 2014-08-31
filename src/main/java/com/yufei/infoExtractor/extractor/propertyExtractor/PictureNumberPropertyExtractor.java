package com.yufei.infoExtractor.extractor.propertyExtractor;

import com.yufei.dataget.utils.HtmlUtil;
import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.entity.Relatedlink;
import com.yufei.infoExtractor.extractor.ExtractorAction;
import com.yufei.infoExtractor.extractor.PictureNumExtractor;
import com.yufei.infoExtractor.extractor.PropertyExtractor;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.PatternUtils;
import com.yufei.utils.SequenceUUID;

/**
 * created by @author jasstion at 2012-10-29
 * 用于提取网页中某个图片中数字的处理（比如：某些网站里面的价格图片）
 */
public class PictureNumberPropertyExtractor  extends PropertyExtractor  implements ExtractorAction {
	private static final Log mLog = LogFactory.getLog(PictureNumberPropertyExtractor.class);

	@Override
	public void extract(HActionContext context) {
		// TODO Auto-generated method stub
		PropertyMatch propertyMatch=(PropertyMatch) context.get("currentPropertyMatch");
		Relatedlink link = (Relatedlink) context.getTargetEntity();
		String htmlContent =link.getOriginalHtmlContent();
		String fieldName = propertyMatch.getProperty();		
		String value=null;
		String mediaUrl = PatternUtils.getStrByRegex(htmlContent, (String) propertyMatch.getMatch().get(PropertyMatch.regex_key));
		if (mediaUrl == null || mediaUrl.trim().equals("")) {
			//照片允许获取失误
			return;
		}
		CommonUtil.formatUrl(mediaUrl);
		String mediaSaveName = SequenceUUID.getInstance().getUUID()
				+ mediaUrl.substring(mediaUrl.lastIndexOf("."));
		context.put("mediaUrl", mediaUrl);
		context.put("mediaSaveName", mediaSaveName);
		try {
		HtmlUtil.downLoadPicture(mediaUrl, mediaSaveName,AppUtil.getInfoExtractorConfig().getMediaDirectory());
			 value=new PictureNumExtractor().extractNumberFormPicture(new File(AppUtil.getInfoExtractorConfig().getMediaDirectory()+mediaSaveName));
			CommonUtil.setPropertyForEntity(link, value,
					fieldName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mLog.info("action for the " + fieldName + "wrong!");
			mLog.debug(ExceptionUtil.getExceptionDetailsMessage(e));
		}
	}




}
