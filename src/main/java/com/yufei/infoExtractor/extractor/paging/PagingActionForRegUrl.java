package com.yufei.infoExtractor.extractor.paging;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.entity.ProxyServer;
import com.yufei.entity.Relatedlink;
import com.yufei.infoExtractor.context.BaseContext;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.PatternUtils;

public class PagingActionForRegUrl implements PagingAction {
private HActionContext context;
private static Log mLog = LogFactory.getLog(PagingActionForRegUrl.class);
public static Integer defaultpageNumber = 3;
List<String> collectionValues=new ArrayList<String>();
String pageUrlRegex =null;
String contentRegex=null;

Integer pageSize=null;

Integer totalRecorderNumber=null;
String fieldName=null;
Relatedlink link=null;
	public BaseContext getContext() {
	return context;
}

public void setContext(HActionContext context) {
	this.context = context;
}

	public PagingActionForRegUrl(HActionContext context) {
	super();
	this.context = context;
}

	@Override
	public void action() throws Exception {
		// TODO Auto-generated method stub
		//action begain
		this.pageAction();
		//when action finished 
		Integer currentPageNumber = (Integer) context.get("currentPageNumber");
		String currentPageContent = (String)context.get("currentPageContent");
	    if(currentPageNumber!=null){
			context.remove(currentPageNumber);
		}
		if(currentPageContent!=null){
			context.remove(currentPageContent);
		}
		//action end
		//after action remove the previous variables
		
		context.remove("pageUrlRegex");
		context.remove("contentRegex");

		context.remove("pageSize");

		context.remove("totalRecorderNumber");
		context.remove("currentFieldName");

	}

	private void pageAction() throws Exception {

		Integer currentPageNumber = (Integer) context.get("currentPageNumber");
		if (currentPageNumber == null) {
			context.put("currentPageNumber", 1);
			currentPageNumber = (Integer) context.get("currentPageNumber");
		} else {
			context.put("currentPageNumber", currentPageNumber + 1);
		}
		extracted();

		if (currentPageNumber == (defaultpageNumber - 1)) {
			return;

		}
		pageAction();

	}

	private void extracted()
			throws MalformedURLException, IOException, IllegalAccessException,
			InvocationTargetException {
		Relatedlink link=(Relatedlink) context.getTargetEntity();
		// currentCollectionName
		// currentContentRegex
		
		String currentPageContent = null;
		mLog.debug(currentPageContent);

		// 判断currentPageContent存在与否，如果存在则表示不是第一次递归，如果不存在则表示第一次递归
		// 如果第一次递归，必须设置currentPageContent属性
		boolean isFirstRecursion = context.get("currentPageContent") == null;
		if (isFirstRecursion) {
			String htmlContent =((Relatedlink)context.getTargetEntity()).getOriginalHtmlContent();
			context.put("currentPageContent", htmlContent);
		} else {
			currentPageContent = (String) context.get("currentPageContent");
			/*if (mLog.isDebugEnabled()) {
				mLog.debug("开始对网页内容：" + currentPageContent + "进行分页提取信息");

			}*/
			String pageUrl = PatternUtils.getStrByRegex(currentPageContent,
					pageUrlRegex,false);
			pageUrl=pageUrl.trim();

			if (mLog.isDebugEnabled()) {
				mLog.debug("根据正则表达式：" + pageUrlRegex + "匹配网页得到的下一页Url地址是："
						+ pageUrl);

			}
			if (pageUrl == null) {
				return;
			}
			pageUrl = CommonUtil.normalizeUrl(pageUrl,
					CommonUtil.getBaseUrl(link.getLink()));
			ProxyServer protocolType =context.getSeedsite().getDataRetrieverFeatures().getProxy();
			Boolean requestExecuteJs =context.getSeedsite().getDataRetrieverFeatures().getRequestExecuteJs();
			//默认采用浏览器去获取网页内容，这样就可以直接执行相关的ajax
			if (requestExecuteJs == null) {
				requestExecuteJs = true;
			}
			
			currentPageContent = CommonUtil.getHtmlContent(protocolType, requestExecuteJs, pageUrl);
			currentPageContent=CommonUtil.removeSpace(currentPageContent);
			if (mLog.isDebugEnabled()) {
				mLog.debug("请求" + pageUrl + "得到的网页内容是：" + currentPageContent);

			}
			context.put("currentPageContent", currentPageContent);

		}
		// 设置value
		currentPageContent = (String) context.get("currentPageContent");

		List<String> values = PatternUtils.getListStrByRegex(
				currentPageContent, this.contentRegex);
		for (String value : values) {
			//目前暂且的约定：翻书需要分页获取的属性都以s结尾并且该实体类必须新增一个方法方法名是:add+属性名（第一个字符大写）
			try {
				CommonUtil.getMethod(link.getClass(),
						"add" + CommonUtil.upFirstChar(fieldName))
						.invoke(link, value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				mLog.info("在对实体对象进行fieldName："+fieldName+"进行设置值的时候出错，错误信息是"+ExceptionUtil.getExceptionDetailsMessage(e)+"");
			
		}
		
	}
		if (mLog.isDebugEnabled()) {
			if (values.size() == 0)
				mLog.debug("根据正则表达式：" + contentRegex + "匹配网页错误！");

		}

		
	}


}
