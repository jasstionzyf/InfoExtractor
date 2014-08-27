package com.yufei.infoExtractor.util;

import com.yufei.dataget.utils.HtmlUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.yufei.infoExtractor.core.SubjectSimilarityWeight;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;

/**
 * created by @author jasstion at 2012-10-27
 */
public class CustomerHtmlNode   implements Serializable,Comparable<CustomerHtmlNode>{
	private static Log mLog = LogFactory.getLog(CustomerHtmlNode.class);

	public CustomerHtmlNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String  htmlContent=null;
	private HtmlSegmentStatistics htmlSegmentStatistics=null;
	private List<CustomerHtmlNode> subCustomerHtmlNode=new ArrayList<CustomerHtmlNode>();
	private CustomerHtmlNode topCustomerHtmlNode;
	private int depth=0;
	private int orderNum=0;
	private double contentSimilarityScore=0.0;
	
	public double getContentSimilarityScore() {
		return contentSimilarityScore;
	}

	public void setContentSimilarityScore(double contentSimilarityScore) {
		this.contentSimilarityScore = contentSimilarityScore;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public CustomerHtmlNode(String htmlContent ) {
		super();
		this.htmlContent=htmlContent;
		initHtmlSegmentStatistics();
	//	this.contentSimilarityScore=calculateContentSimilarityScore();
	}

	public  void initHtmlSegmentStatistics() {
		// TODO Auto-generated method stub
		setHtmlSegmentStatistics(getHtmlSegmentStatistics(htmlContent));
	}


	/** 
	 * @param html
	 * @return
	 * 选择与主题最为相关的网页内容
	 */
	public static CustomerHtmlNode removeHtmlNoise(String html){
		//String result=null;
		  Parser parser=new Parser(); 
		  CustomerHtmlNode maxSoreCustomerHtmlNode=null;
		  try {
			  //首先删除脚本和CSS
			  html=HtmlUtil.removeJSAndCss(html);
			  parser.setInputHTML(html);
	         NodeIterator elements=parser.elements();
		  CustomerHtmlNode rootCustomerMode=new CustomerHtmlNode();
		  Node tempNode=null;
		  Node currentMaxNode=null;
		  String tagName=null;
		  //获取（html）中最大的节点
		 while(elements.hasMoreNodes()){
			 tempNode=elements.nextNode();
			 if(currentMaxNode!=null){
				 if(tempNode.toHtml().length()>currentMaxNode.toHtml().length()){
						currentMaxNode=tempNode;
					} 
			 }
			
			 if(currentMaxNode==null){
				 currentMaxNode=tempNode;
			 }
		 }
		if(currentMaxNode==null){
			mLog.debug("在对网页内容进行去噪处理的时候发生严重错误，请检查网页内容是否正确，此次处理直接忽略！");
		}
		createCustomerHtmlNode(currentMaxNode,rootCustomerMode, 0);
		maxSoreCustomerHtmlNode=rootCustomerMode.getCustomerHtmlNodeWithMaxScore();//HtmlUtil.getCustomerHtmlNodeByDepthAndOrderNum(rootCustomerMode,0,0);
      //  result=maxSoreCustomerHtmlNode.getHtmlContent();
      // result= cleanHtml(result);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
			}
		return maxSoreCustomerHtmlNode;
		
	}
/**
 * @param node
 * @param rootHtmlNode
 * @param depth
 * 创建一个树，树中的每个节点都有两个属性：depth,orderNum(同一个节点的子节点的位置序列号，从零开始)
 */
public static  void createCustomerHtmlNode(Node node,CustomerHtmlNode rootHtmlNode,int depth){
	NodeList  nodes=null;
	CustomerHtmlNode nextCustomerHtmlNode=null;


	if(node==null){
		throw new IllegalArgumentException();
	}
	//根节点
	if(rootHtmlNode.getHtmlContent()==null){
		rootHtmlNode.setHtmlContent(node.toHtml());
	   rootHtmlNode.initHtmlSegmentStatistics();
		rootHtmlNode.setTopCustomerHtmlNode(null);
		depth=0;
		rootHtmlNode.setDepth(depth);
		rootHtmlNode.setOrderNum(0);
		nodes=node.getChildren();
		int preListSize=0;
		depth++;
		int orderNum=-1;
		//乱码的时候会出现
		if(nodes==null){
			return;
		}
		for(int i=0;i<nodes.size();i++){
		//	preListSize+=nodes.elementAt(i)
			orderNum++;
			nextCustomerHtmlNode=new CustomerHtmlNode(nodes.elementAt(i).toHtml());
			nextCustomerHtmlNode.setTopCustomerHtmlNode(rootHtmlNode);
			nextCustomerHtmlNode.setDepth(depth);
			nextCustomerHtmlNode.setOrderNum(orderNum);
			rootHtmlNode.getSubCustomerHtmlNode().add(nextCustomerHtmlNode);
			createCustomerHtmlNode(nodes.elementAt(i),nextCustomerHtmlNode,depth);
		}
		
	}
	//mLog.info(node.toHtml()+"\n");
	//mLog.info("***********************************************"+"\n");
	//mLog.info(rootHtmlNode.getHtmlSegmentStatistics().getPercentageOfContent()+"\n");
	
	if(node.getChildren()==null||node.getChildren().size()==0){
		
		return;

	}
	else{
		nodes=node.getChildren();
		int preListSize=0;
		depth++;
		int orderNum=-1;
		for(int i=0;i<nodes.size();i++){
		//	preListSize+=nodes.elementAt(i)
			orderNum++;
			nextCustomerHtmlNode=new CustomerHtmlNode(nodes.elementAt(i).toHtml());
			nextCustomerHtmlNode.setTopCustomerHtmlNode(rootHtmlNode);
			nextCustomerHtmlNode.setDepth(depth);
			nextCustomerHtmlNode.setOrderNum(orderNum);
			rootHtmlNode.getSubCustomerHtmlNode().add(nextCustomerHtmlNode);
			createCustomerHtmlNode(nodes.elementAt(i),nextCustomerHtmlNode,depth);
		}
		
	}
	rootHtmlNode.setContentSimilarityScore(rootHtmlNode.calculateContentSimilarityScore());
//	mLog.info(node.toHtml()+"\n");
	 //  mLog.info(rootHtmlNode.getContentSimilarityScore());

	//mLog.info("***********************************************"+"\n");

}

public static HtmlSegmentStatistics getHtmlSegmentStatistics(String htmlContent){
	HtmlSegmentStatistics htmlSegmentStatistics=null;
	int totalCount=htmlContent.length();
	int contentCharNum;
	int htmlCharNum;
	String plainText=HtmlUtil.removeJSAndCss(htmlContent);
	plainText=HtmlUtil.removeHtmlTag(plainText);
	contentCharNum=plainText.length();
	htmlCharNum=totalCount-contentCharNum;
	htmlSegmentStatistics=new HtmlSegmentStatistics(totalCount, htmlCharNum, contentCharNum);
	return htmlSegmentStatistics;
}

/**
 * @param rootCustomerHtmlNode
 * @param depth：树结构中的深度，root节点的深度为0
 * @param orderNum：某一深度上，某个特定index位置的元素（index也是从零开始）
 * @return
 * 获取某个深度某个位置的元素
 */
public static CustomerHtmlNode getCustomerHtmlNodeByDepthAndOrderNum(CustomerHtmlNode rootCustomerHtmlNode,int depth,int orderNum){
	CustomerHtmlNode result=null;
List<CustomerHtmlNode> list=new ArrayList<CustomerHtmlNode>();

	convertRootCustomerHtmlNodeToList(rootCustomerHtmlNode, list);
	Collections.sort(list);
	result=list.get(list.size()-1);
	return result;
}
public static void convertRootCustomerHtmlNodeToList(CustomerHtmlNode customerHtmlNode,List<CustomerHtmlNode> list){
	list.add(customerHtmlNode);
	int size = customerHtmlNode.getSubCustomerHtmlNode().size();
	if(size!=0){
		for(int i=0;i<size;i++){
			convertRootCustomerHtmlNodeToList(customerHtmlNode.getSubCustomerHtmlNode().get(i),list);
		}
	}
	else{
		return;
	}
}
	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public HtmlSegmentStatistics getHtmlSegmentStatistics() {
		return htmlSegmentStatistics;
	}


	@Override
	public String toString() {
		return "CustomerHtmlNode [htmlContent=" + htmlContent
				+ ", htmlSegmentStatistics=" + htmlSegmentStatistics
				+ ", subCustomerHtmlNode=" + subCustomerHtmlNode
				+ ", topCustomerHtmlNode=" + topCustomerHtmlNode + ", depth="
				+ depth + ", orderNum=" + orderNum + "]";
	}

	public void setHtmlSegmentStatistics(HtmlSegmentStatistics htmlSegmentStatistics) {
		this.htmlSegmentStatistics = htmlSegmentStatistics;
	}
	public List<CustomerHtmlNode> getSubCustomerHtmlNode() {
		return subCustomerHtmlNode;
	}
	public void setSubCustomerHtmlNode(List<CustomerHtmlNode> subCustomerHtmlNode) {
		this.subCustomerHtmlNode = subCustomerHtmlNode;
	}
	public CustomerHtmlNode getTopCustomerHtmlNode() {
		return topCustomerHtmlNode;
	}
	public void setTopCustomerHtmlNode(CustomerHtmlNode topCustomerHtmlNode) {
		this.topCustomerHtmlNode = topCustomerHtmlNode;
	}
public double calculateContentSimilarityScore(){
	double score=0;
	SubjectSimilarityWeight subjectSimilarityWeight = AppUtil.getInfoExtractorConfig().getSubjectSimilarityWeight();
	score+=this.getHtmlSegmentStatistics().getPercentageOfContent()*subjectSimilarityWeight.getCharNumberPercentage();
	if(this.getHtmlSegmentStatistics().getContentCharNum()<=subjectSimilarityWeight.getCharNumber()){
		return 0;
	}
	//score+=this.getHtmlSegmentStatistics().getContentCharNum()>subjectSimilarityWeight.getCharNumber()?25:0;
	score+=this.getSubCustomerHtmlNode().size()*subjectSimilarityWeight.getSubCustomerHtmlNodes();
	/*double subNodeContentAveragePercentage=0;
	for(int i=0;i<this.getSubCustomerHtmlNode().size();i++){
		subNodeContentAveragePercentage+=this.getSubCustomerHtmlNode().get(i).getHtmlSegmentStatistics().getPercentageOfContent();
	}
	subNodeContentAveragePercentage=subNodeContentAveragePercentage/this.getSubCustomerHtmlNode().size();
	score+=subNodeContentAveragePercentage*100*0.25;*/
	return score;
}
/**
 * @return
 * 获取以此节点为根节点的与最为可能是主题内容的节点
 */
public  CustomerHtmlNode getCustomerHtmlNodeWithMaxScore(){
	CustomerHtmlNode result=null;
	List<CustomerHtmlNode> list=new ArrayList<CustomerHtmlNode>();

		convertRootCustomerHtmlNodeToList(this, list);
		Collections.sort(list);
		result=list.get(list.size()-1);
		list=null;
return result;

	    
		
}

@Override
public int compareTo(CustomerHtmlNode o) {
	// TODO Auto-generated method stub
	if(this.getContentSimilarityScore()>o.getContentSimilarityScore()){
		return 1;
	}
	else if(this.getContentSimilarityScore()==o.getContentSimilarityScore()){
		return 0;
	}
	else if (this.getContentSimilarityScore()<o.getContentSimilarityScore()){
		return -1;
	}
	return 0;

}
public static void main(String[] args){
	try {
		String html=CommonUtil.getHtmlContent("http://www.cqkxzx.net/a/201304/0403Z9592013.html", false);
	   CustomerHtmlNode cu= CustomerHtmlNode.removeHtmlNoise(html);
	   System.out.print(cu.getDepth()+":"+cu.getOrderNum()+"\n");
	   System.out.print(cu.getHtmlContent()+"\n");
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
