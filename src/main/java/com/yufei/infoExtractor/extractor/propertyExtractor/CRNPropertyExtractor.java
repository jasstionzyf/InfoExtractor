package com.yufei.infoExtractor.extractor.propertyExtractor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.Relatedlink;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.extractor.ExtractorAction;
import com.yufei.infoExtractor.extractor.PropertyExtractor;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.infoExtractor.util.CustomerHtmlNode;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.HtmlUtil;

/**
 * created by @author jasstion at 2012-10-29
 * 专门用于将得到的网页信息进行去噪处理以及保存净化后网页中的图片信息
 */
public class CRNPropertyExtractor   extends PropertyExtractor implements ExtractorAction {
	private static final Log mLog = LogFactory.getLog(CRNPropertyExtractor.class);

	@Override
	public void extract(HActionContext context) {
		// TODO Auto-generated method stub
		PropertyMatch propertyMatch=(PropertyMatch) context.get("currentPropertyMatch");
		Relatedlink link = (Relatedlink) context.getTargetEntity();
		String htmlContent =link.getOriginalHtmlContent();
		String fieldName = propertyMatch.getProperty();
		String value=htmlContent;
        //对网页进行去噪处理（去除广告等于主题无关内容）
    	CustomerHtmlNode maxSoreCustomerHtmlNode=CustomerHtmlNode.removeHtmlNoise(htmlContent);
		value=maxSoreCustomerHtmlNode.getHtmlContent();
		int score=(int) maxSoreCustomerHtmlNode.getContentSimilarityScore();
        List<String> imagesUrl=HtmlUtil.getImageUrls(value);
        //make sure imagesUrl validate
        String normalImageUrl=null;
        for(String imageUrl:imagesUrl){
   	    imageUrl=CommonUtil.formatUrl(imageUrl);
   	    imageUrl=CommonUtil.normalizeUrl(imageUrl, link.getLink());
   	    
   	   
      }
       //下载主题相关内容中的图片
      List<String> imageFileNames=HtmlUtil.batchDowloadPcituresFormUrls(imagesUrl,AppUtil.getInfoExtractorConfig().getMediaDirectory());
      CommonUtil.setPropertyForEntity(link, imageFileNames, "picturesPath");
      //refactore the value
      //SRC=sfsdfsdf.jpg
        if(CommonUtil.isEmptyOrNull(value)){
       	 return ;
        }
        //clean html
        value=HtmlUtil.cleanHtml(value);
		CommonUtil.setPropertyForEntity(link, value, fieldName);
		//设置score
		CommonUtil.setPropertyForEntity(link, score, "score");

	}




}
