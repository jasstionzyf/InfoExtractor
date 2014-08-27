package com.yufei.infoExtractor.util;

/**
 * created by @author jasstion at 2012-10-26
 * 表示一个Html标签所有文字的总数，html标记字符的总数以及单纯文字的总数
 */
public class HtmlSegmentStatistics {
	@Override
	public String toString() {
		return "HtmlSegmentStatistics [totalCharNum=" + totalCharNum
				+ ", htmlTagCharNum=" + htmlTagCharNum + ", contentCharNum="
				+ contentCharNum + ", percentageOfContent="
				+ percentageOfContent + "]";
	}
	private int totalCharNum;
	private int htmlTagCharNum;
	private int contentCharNum;
	public HtmlSegmentStatistics(int totalCharNum, int htmlTagCharNum,
			int contentCharNum) {
		super();
		this.totalCharNum = totalCharNum;
		this.htmlTagCharNum = htmlTagCharNum;
		this.contentCharNum = contentCharNum;
	
	}
	public int getTotalCharNum() {
		return totalCharNum;
	}
	public void setTotalCharNum(int totalCharNum) {
		this.totalCharNum = totalCharNum;
	}
	public int getHtmlTagCharNum() {
		return htmlTagCharNum;
	}
	public void setHtmlTagCharNum(int htmlTagCharNum) {
		this.htmlTagCharNum = htmlTagCharNum;
	}
	public int getContentCharNum() {
		return contentCharNum;
	}
	public void setContentCharNum(int contentCharNum) {
		this.contentCharNum = contentCharNum;
	}
	public double getPercentageOfContent() {
		return  (double)this.getContentCharNum()/(double)getTotalCharNum();
	}
	public void setPercentageOfContent(double percentageOfContent) {
		this.percentageOfContent = percentageOfContent;
	}
	private double  percentageOfContent;

}
