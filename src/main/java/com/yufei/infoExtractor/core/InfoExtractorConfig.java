package com.yufei.infoExtractor.core;
import java.io.File;
import java.io.Serializable;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zhaoyufei zhao-0244@qq.com
 *系统运行配置信息和系统具体任务无关
 * created at 2012-9-2 上午11:29:24
 */
@XmlRootElement
public class InfoExtractorConfig implements Serializable{

	public  InfoExtractorConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

/**
 * 用于设置各个指标对于某部分网页内容是主题相关的权重
 */
private SubjectSimilarityWeight subjectSimilarityWeight;

private String isExtractorContent;
	
private String fileSavedDirectory;
private Integer passedScore=null;
private String taskConfigFileDir=null;

public String getTaskConfigFileDir() {
	return taskConfigFileDir;
}
@XmlElement
public void setTaskConfigFileDir(String taskConfigFileDir) {
	this.taskConfigFileDir = taskConfigFileDir;
}
public Integer getPassedScore() {
	return passedScore;
}
@XmlElement
public void setPassedScore(Integer passedScore) {
	this.passedScore = passedScore;
}
public String getFileSavedDirectory() {
	return fileSavedDirectory;
}
@XmlElement
public void setFileSavedDirectory(String fileSavedDirectory) {
	this.fileSavedDirectory = fileSavedDirectory;
}
public String getIsExtractorContent() {
	return isExtractorContent;
}
@XmlElement
public void setIsExtractorContent(String isExtractorContent) {
	this.isExtractorContent = isExtractorContent;
}
public SubjectSimilarityWeight getSubjectSimilarityWeight() {
	return subjectSimilarityWeight;
}
@XmlElement
public void setSubjectSimilarityWeight(
		SubjectSimilarityWeight subjectSimilarityWeight) {
	this.subjectSimilarityWeight = subjectSimilarityWeight;
}

private String tempDirectory=null;
public String getTempDirectory() {
	File file=new File(tempDirectory);
	if(!file.exists()){
    	file.mkdirs();
    }
	
	return tempDirectory;
}
@XmlElement
public void setTempDirectory(String tempDirectory) {
	this.tempDirectory = tempDirectory;
}
private String mediaDirectory=null;
public String getMediaDirectory() {
	File file=new File(mediaDirectory);
	if(!file.exists()){
    	file.mkdirs();
    }
	return mediaDirectory;
}
@XmlElement(name="mediaDirectory")
public void setMediaDirectory(String mediaDirectory) {
	this.mediaDirectory = mediaDirectory;
}

private Double passedSimilarityValue=new Double(0.00);

	public Double getPassedSimilarityValue() {
		return passedSimilarityValue;
	}
	@XmlElement(name="passedSimilarityValue")
    public void setPassedSimilarityValue(Double passedSimilarityValue) {
		this.passedSimilarityValue = passedSimilarityValue;
	}
	private Integer urlBlockSize;






	public Integer getUrlBlockSize() {
		return urlBlockSize;
	}
	@XmlElement(name="urlBlockSize")
    public void setUrlBlockSize(Integer urlBlockSize) {
		this.urlBlockSize = urlBlockSize;
	}
}
