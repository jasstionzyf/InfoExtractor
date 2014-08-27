package com.yufei.infoExtractor.extractor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.taoban.shopdata.linkGetter.ExternalLinksGetter;
import com.taoban.shopdata.linkGetter.ExternalMongodbLinksGetter;
import com.yufei.entity.Sp;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.core.InfoExtractionAction;
import com.yufei.infoExtractor.pfw.entity.Seedsite;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.pfw.service.PfwService;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.PatternUtils;

public class UrlAndContentExtractor extends Extractor implements ExtractorAction,InfoExtractionAction <HActionContext>{
	private static final Log mLog = LogFactory.getLog(UrlAndContentExtractor.class);

private HActionContext context=null;
static PfwService pfwService=(PfwService) AppUtil.getBeanFromBeanContainer(PfwService.class);

@Override
public void execute(HActionContext context) throws Exception {
	// TODO Auto-generated method stub
	this.extract(context);
}

@Override
public void extract(HActionContext context) {
	// TODO Auto-generated method stub
	//获取目录页
	//提取里面的商品列表（链接以及各种信息）
	this.context=context;
	Seedsite seedsite=context.getSeedsite();
	String externalLinkGetterClassName = seedsite.getExternalLinkGetterClassName();
	Set[] linkSet =context.getLinkSets();
	if(!CommonUtil.isEmptyOrNull(externalLinkGetterClassName)){
		try {
			ExternalLinksGetter externalLinksGetter=(ExternalLinksGetter) Class.forName(externalLinkGetterClassName)
					.newInstance();
			List<String> externalLinks=externalLinksGetter.getExternalLinks();
			
			linkSet[0].addAll(externalLinks);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mLog.info("初始化externalLinksGetter类失败，直接返回");
			return;
		}
	}
	
	if(!CommonUtil.isEmptyOrNull(linkSet[0])){
		Iterator<String> it=linkSet[0].iterator();
	while(it.hasNext()){
		parseLink(it.next());
	}
		

		
	}
	
}
private static String getHtmlContent(String url,int visitCount){
	String htmlContent=null;
	if(visitCount==0){
		mLog.info("针对链接:"+url+"的访问失败！");
		return null;
	}
	else{
		try {
			htmlContent = CommonUtil.getHtmlContent(url, false, 0, 3);
		   
			
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

private static void parseLink(String link){
	Document doc;
	String htmlTxt = null;
	try {
		htmlTxt = CommonUtil.getHtmlContent(link, false, 0, 3);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		htmlTxt=getHtmlContent(link, 3);
}
	if(CommonUtil.isEmptyOrNull(link)){
		htmlTxt=getHtmlContent(link, 3);

	}
	if(htmlTxt==null){
		return;
	}
	
		doc = Jsoup.parse(htmlTxt);
		//￥68.99 (4.34折) ￥159.00
	Elements productPriceElements = doc.getElementsByClass("productPrice");
	Elements productShopElements = doc.getElementsByClass("productShop");
	Elements productTitleElements=doc.getElementsByClass("productTitle");
	Elements productImgElements=doc.getElementsByClass("productImg");

	Elements productStatusElements = doc.getElementsByClass("productStatus");

	Sp sp=null;
	String productBody=null;
	//Element element:productPriceElements
	for(int i=0;i<productPriceElements.size();i++){
		Element element=productPriceElements.get(i);
		try{
		productBody=element.text();
		if(CommonUtil.isEmptyOrNull(productBody)){
			continue;
			
		}
		productBody=productBody.replaceAll("¥", "").replaceAll("\\(", "").replaceAll("\\)","");
		String[] strs=productBody.split(" ");
        if(strs.length!=3){
			continue;
		}
		String price=strs[0];
		String discount=strs[1];
		String oprice=strs[2];
		sp=new Sp();
		sp.setPrice(Double.parseDouble(strs[0].trim()));
        sp.setDiscount(Double.parseDouble(PatternUtils.getStrByRegex(discount, "(.*?)折")));
        sp.setOprice(Double.parseDouble(oprice));
		if(i<productShopElements.size()){
			Element shopElement=productShopElements.get(i);
			
			
				sp.setShopName(shopElement.text());
				
			
		}
		boolean isWan=false;
		//月销量:2753|累计评价:1.4万
		if(i<productStatusElements.size()){
			Element statusElement=productStatusElements.get(i);
			String text = statusElement.text();
             text=text.trim();
           
             text=text.replaceAll("万", "0000");
			if(!CommonUtil.isEmptyOrNull(text)){
				Integer orderNumber=0;
				try{
					orderNumber= Integer.parseInt(PatternUtils.getStrByRegex(text, "月销量:(.*?)\\|"));
				}catch(Exception e){
					
				}
				sp.setOrderedNumber(orderNumber);
				Integer reviews=0;
				String reviewStr = PatternUtils.getStrByRegex(text, "\\|累计评价:(.*)");
				
				reviewStr=reviewStr.replaceAll("万", "0000");

				try{
					reviews= Integer.parseInt(reviewStr);
				}catch(Exception e){


				}
				sp.setReviewCount(reviews);
				
				//设置link title//设置图片
				if(i<productTitleElements.size()){
					Element titleElement=productTitleElements.get(i);
					String title = titleElement.text();
					String url=PatternUtils.getStrByRegex(titleElement.html(), "href=\"(.*?)\"");
					sp.setLink(url);
					sp.setTitle(title);
					String pictureUrl=null;
					String productDetailContent=getHtmlContent(url,3);
					if(!CommonUtil.isEmptyOrNull(productDetailContent)){
						sp.setPictureUrl(PatternUtils.getStrByRegex(productDetailContent, "J_ImgBooth[\\s\\S\\r\\n]{0,}?src=\"(.*?)\""));
					}
					if(CommonUtil.isEmptyOrNull(sp.getPictureUrl())){
						
						if(i<productImgElements.size()){
							Element pictureElement=productImgElements.get(i);
							sp.setPictureUrl(PatternUtils.getStrByRegex(pictureElement.html(), "src=\"(.*?)\""));
						}
						
					}
				}
				
				

			}
			
				
			
		}
		
		}catch(Exception e){
			mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
		}
		pfwService.save(sp);
		
	}


	
}

public static void main(String[] args){
	ExternalLinksGetter externalLinksGetter=new ExternalMongodbLinksGetter();
	List<String> externalLinks=externalLinksGetter.getExternalLinks();
	if(!CommonUtil.isEmptyOrNull(externalLinks)){
		Iterator<String> it=externalLinks.iterator();
	while(it.hasNext()){
		parseLink(it.next());
	}
		

		
	}
	
}
}
