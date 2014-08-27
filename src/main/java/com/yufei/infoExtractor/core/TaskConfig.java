/**
 * 
 */
package com.yufei.infoExtractor.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.yufei.infoExtractor.entity.Task;


/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-9-2 上午11:40:59
 */
@XmlRootElement(name="taskConfig")
public class TaskConfig implements Serializable{
private List<Task> tasks=new ArrayList<Task>();
	

	public List<Task> getTasks() {
		return tasks;
	}
	@XmlElementWrapper(name="tasks")
	@XmlElement(name="task")
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	

}
