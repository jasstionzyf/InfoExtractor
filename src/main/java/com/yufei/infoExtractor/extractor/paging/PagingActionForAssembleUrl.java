package com.yufei.infoExtractor.extractor.paging;

import com.yufei.dataget.entity.PaginationRule;
import com.yufei.dataget.entity.UrlParameter;
import com.yufei.dataget.utils.HtmlUtil;
import com.yufei.infoExtractor.context.BaseContext;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.entity.Relatedlink;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
import com.yufei.utils.PatternUtils;

/**
 * @author jasstionon
 * 2013-2-3
 * 用于处理这样一种分页提取情景：
 * 评论对应的链接可以组装并且请求方式是get形式的
 * 另外对与一些headers以及cookies的要求可有可无
 */
public class PagingActionForAssembleUrl implements PagingAction {
	private static Log mLog = LogFactory
			.getLog(PagingActionForAssembleUrl.class);
	public static Integer defaultpageNumber = 3;
	List<String> collectionValues = new ArrayList<String>();
	String pageUrlTemplate = null;
	String contentRegex = null;
	Integer pagNumberSpace = null;
	Integer begainPagNumber = null;
	Integer pageSize = null;

	String totalRecorderNumberRegex = null;
	String fieldName = null;
	Relatedlink link = null;
	private HActionContext context;

	public BaseContext getContext() {
		return context;
	}

	public void setContext(HActionContext context) {
		this.context = context;

	}

	@Override
	public void action() throws Exception {
		// TODO Auto-generated method stub
		// make pageUrls according to the urlPagetemplate

		PaginationRule paginationRule = new PaginationRule();
		paginationRule.setPaginationTemplate(pageUrlTemplate);
		String totalRecorderNumberStr = PatternUtils.getStrByRegex(
				link.getOriginalHtmlContent(), totalRecorderNumberRegex, false);
		if (CommonUtil.isEmptyOrNull(totalRecorderNumberStr)) {
			mLog.info("get the totalRecorderNumber using the regex:"
					+ totalRecorderNumberRegex + " from the link :"
					+ link.getLink() + " appear error!");
			return;
		}
		Integer totalRecorderNumber = Integer.parseInt(totalRecorderNumberStr);
		String pageNumber = null;
		pageNumber = String.valueOf(totalRecorderNumber / pageSize
				+ (totalRecorderNumber % pageSize == 0 ? 0 : 1));
		UrlParameter urlParameter = new UrlParameter(1, "Integer", pageNumber,
				begainPagNumber, pagNumberSpace);
		paginationRule.getUrlParameters().add(urlParameter);
		List<String> geratedUrls = HtmlUtil
				.generateUrlsByPaginationRule(paginationRule);
		List<String> tempComments = null;
		String tempHtmlContent = null;
		for (String url : geratedUrls) {
			tempHtmlContent = HtmlUtil.getHtmlContent(context.getSeedsite().getDataRetrieverFeatures().getProxy(), false, url);
			tempComments = PatternUtils.getListStrByRegex(tempHtmlContent,
					contentRegex);
			for (String value : tempComments) {
				try {
					CommonUtil.getMethod(link.getClass(),
							"add" + CommonUtil.upFirstChar(fieldName)).invoke(
							link, value);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					mLog.info("在对实体对象进行fieldName：" + fieldName
							+ "进行设置值的时候出错，错误信息是"
							+ ExceptionUtil.getExceptionDetailsMessage(e) + "");

				}
			}
		}

		
	}

	public PagingActionForAssembleUrl(HActionContext context) {
		super();
		this.context = context;
		PropertyMatch propertyMatch=(PropertyMatch) context.get("currentPropertyMatch");

		pageUrlTemplate = (String) propertyMatch.getMatch().get("pageUrlTemplate");
		contentRegex = (String)  propertyMatch.getMatch().get("contentRegex");

		pageSize = Integer.parseInt((String) propertyMatch.getMatch().get("pageSize"));

		totalRecorderNumberRegex = (String)  propertyMatch.getMatch()
				.get("totalRecorderNumberRegex");
		fieldName = propertyMatch.getProperty();
		begainPagNumber = Integer.parseInt(  (String) propertyMatch.getMatch().get("begainPagNumber"));
		pagNumberSpace = Integer.parseInt(  (String) propertyMatch.getMatch().get("pagNumberSpace"));
		link = (Relatedlink) context.getTargetEntity();
	}

	public String getPageUrlTemplate() {
		return pageUrlTemplate;
	}

	public void setPageUrlTemplate(String pageUrlTemplate) {
		this.pageUrlTemplate = pageUrlTemplate;
	}

}
