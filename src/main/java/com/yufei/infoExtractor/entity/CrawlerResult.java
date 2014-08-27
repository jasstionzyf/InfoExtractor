/**
 * 
 */
package com.yufei.infoExtractor.entity;

import com.yufei.pfw.entity.Entity;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-8-18 上午8:52:46
 */
@XmlRootElement
public class CrawlerResult {
private String taskName=null;
private List<Hotel> results=new ArrayList();
public String getTaskName() {
	return taskName;
}
@XmlElement
public void setTaskName(String taskName) {
	this.taskName = taskName;
}
public List<? extends Entity> getResults() {
	return results;
}
@XmlElementWrapper(name = "results")
@XmlElement(name = "result")
public void setResults(List<Hotel> results) {
	this.results = results;
}
}
