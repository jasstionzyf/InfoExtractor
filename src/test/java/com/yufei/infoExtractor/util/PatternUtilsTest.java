package com.yufei.infoExtractor.util;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yufei.infoExtractor.pfw.entity.Relatedlink;
import com.yufei.infoExtractor.pfw.entity.Weibo;
import com.yufei.infoExtractor.util.CommonUtil;
import com.yufei.infoExtractor.util.PatternUtils;

public class PatternUtilsTest {
	Map context=new HashMap();
	//init data for test
	//currentLink,currentHtmlContent,regexMap
	Weibo weibo=new Weibo();
	
	String currentHtmlContent=CommonUtil.getStringFromFile(new File("src/test/resources/html/qqweiboTest.html"));
	Map<String, String> reg=new HashMap<String, String>();
    
	
	
	

	@Before
	public void setUp() throws Exception {
		


	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testJudgeLinkType() {

	}

	@Test
	public void testGetStrByRegex() {
	}

	@Test
	public void testGetStrByPattern() {
	}

	@Test
	public void testParseLinksFormHtml() {
	}

	//@Test
	public void testSetPropertiesOnLink() {
		//PatternUtils.setPropertiesOnLink(context);
		Assert.assertEquals("宋奉宜醫師",weibo.getUserName());
	    Assert.assertNotNull(weibo.getContents().get(0));
		
	}


	@Test
	public void getStrByPattern(){
		reg.put("selfD", "广播.*?class=text_count.*?>(.*?)<!广播<STRONG>(.*?)</STRONG>");
		String selfD=PatternUtils.getStrByRegex(reg, currentHtmlContent, "selfD");
		Assert.assertNotNull(selfD);
		
	}
	@Test
	public void parseLinksFormHtmlTest(){
		
		
	}
	public  static void main(String[] args) throws IOException{
		String url="http://www.dianping.com/shop/2871976/review_all?pageno=2";
		String htmlContent = CommonUtil.getHtmlContent(null, false, url);
		List<String> comments=PatternUtils.getListStrByRegex(htmlContent, "<div class=\"comment-entry\">(.*?)</div>");
	System.out.print(htmlContent);
		System.out.println(comments.size());
	}
}
