package com.yufei.infoExtractor.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.yufei.entity.PaginationRule;
import com.yufei.entity.UrlParameter;
import com.yufei.infoExtractor.entity.ExternalLink;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.pfw.service.MongodbPfwService;
import com.yufei.pfw.service.PfwService;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.HtmlUtil;

public class ExternalMongodbLinksGetter implements ExternalLinksGetter {
	private static final Log mLog = LogFactory.getLog(ExternalMongodbLinksGetter.class);

PfwService pfwService=(PfwService) AppUtil.getBeanFromBeanContainer(MongodbPfwService.class);
	@Override
	public List<String> getExternalLinks() {
		// TODO Auto-generated method stub
		Map map=null;
		List<ExternalLink> externalLinks=pfwService.query(map, ExternalLink.class);
		List<String> readyUrls=new ArrayList<>();
		//&jumpto={}&totalPage=3
		List<String> pageUrlTemplates=new ArrayList<String>();
		
		for(ExternalLink externalLink:externalLinks){
			//50025135 测试 女装
			String link = externalLink.getLink();
			//if(link.contains("50025135")){
			
				
					String html=HtmlUtil.getHtmlContent(link, 5,1,false);
					if(CommonUtil.isEmptyOrNull(html)){
						mLog.info("针对目录链接"+link+"获取失败！");
						continue;
					}
					Document doc=null;
					doc = Jsoup.parse(html);
					Elements elements=doc.getElementsByClass("ui-page-next");
					String pageUrlTemplate=elements.attr("href");
					
					if(CommonUtil.isEmptyOrNull(pageUrlTemplate)){
						pageUrlTemplate=link;
					}
				    // pageUrlTemplate=pageUrlTemplate.replaceAll("n=[\\d]{0,}?&", "n=180");
					else{
				     pageUrlTemplate=pageUrlTemplate.replaceAll("(s=[\\d]{0,}?)&", "s={}&");
				     mLog.info("获取目录链接模板:"+pageUrlTemplate+"");
					}

					pageUrlTemplates.add(pageUrlTemplate);
					//q=%C4%DA%D2%C2


			}
		

		//}
		for(String pageUrlTemplate:pageUrlTemplates){
			if(pageUrlTemplate.contains("{}")){
				PaginationRule paginationRule = new PaginationRule();
			    paginationRule.setPaginationTemplate(pageUrlTemplate);
			    UrlParameter urlParameter=new UrlParameter();
			    urlParameter.setBegainPagNumber(0);
			    urlParameter.setPagNumberSpace(60);
			    urlParameter.setParameterType("Integer");
			    urlParameter.setParameterValue(String.valueOf(3));
			    paginationRule.getUrlParameters().add(urlParameter);
				List<String> urls=CommonUtil.generateUrlsByPaginationRule(paginationRule); 
				readyUrls.addAll(urls);
			}
			else{
				readyUrls.add(pageUrlTemplate)
;			}
		
			
		}
		return readyUrls;
	}
public static void main(String[] args){
	List<String> s=new ExternalMongodbLinksGetter().getExternalLinks();
	for(String str:s){
		mLog.info(str);
	}
}
}
