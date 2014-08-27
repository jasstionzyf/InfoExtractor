package com.yufei.infoExtractor.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.index.Indexed;

import com.yufei.annotation.QueryEnable;
import com.yufei.pfw.entity.Entity;




/**
 * @author jasstion
  2013-5-10下午12:19:56
  仅仅表示从网站提取出来的有价值的链接信息
 *
 */
public class Relatedlink extends Entity {
	
	
	private static Log mLog = LogFactory.getLog(Relatedlink.class);
	private Long taskId=null;
	//唯一索引
	@Indexed(unique=true)
	private String link;
	private Integer status;
	private Integer depth;
	private String upLink;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	@QueryEnable(enable = true)
	private Date findTime=null;
	private Date updateTime=null;
	private String contentHashCode=null;
	private String site="";
	private String domain="";
	/**
	 * 是否被处理（比如：里面的属性的抽取,或者采集到的网页信息发生变化需要重新进行信息提取等）
	 */
	private boolean isProcessed=false;
	public boolean isProcessed() {
		return isProcessed;
	}
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	
	public String getContentHashCode() {
		return contentHashCode;
	}
	public void setContentHashCode(String contentHashCode) {
		this.contentHashCode = contentHashCode;
	}
	public Date getFindTime() {
		return findTime;
	}

	public void setFindTime(Date findTime) {
		this.findTime = findTime;
	}
    public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public String getUpLink() {
		return upLink;
	}

	public void setUpLink(String upLink) {
		this.upLink = upLink;
	}
	
  
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
	private String originalHtmlContent=null;
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOriginalHtmlContent() {
		return originalHtmlContent;
	}
	@XmlElement(name = "originalHtmlContent")
    public void setOriginalHtmlContent(String originalHtmlContent) {
		this.originalHtmlContent = originalHtmlContent;
	}
	
    


	


	

}