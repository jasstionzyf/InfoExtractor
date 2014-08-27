package com.yufei.infoExtractor.entity;

import java.io.Serializable;

public class InfoExtractTaskStatistics implements Serializable{
private String taskName=null;
	public String getTaskName() {
	return taskName;
}
public void setTaskName(String taskName) {
	this.taskName = taskName;
}
//任务运行状态
private Integer taskStatus=0;
//url解析的数目
private Integer urlFetchedNumber=0;
//url采集的数目
private Integer urlExtractedNumber=0;
public Integer getTaskStatus() {
	return taskStatus;
}
public void setTaskStatus(Integer taskStatus) {
	this.taskStatus = taskStatus;
}
public Integer getUrlFetchedNumber() {
	return urlFetchedNumber;
}
public void setUrlFetchedNumber(Integer urlFetchedNumber) {
	this.urlFetchedNumber = urlFetchedNumber;
}
public Integer getUrlExtractedNumber() {
	return urlExtractedNumber;
}
public void setUrlExtractedNumber(Integer urlExtractedNumber) {
	this.urlExtractedNumber = urlExtractedNumber;
}

}
