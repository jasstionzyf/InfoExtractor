package com.yufei.infoExtractor.entity;

import com.yufei.annotation.CollectionElementType;
import com.yufei.annotation.QueryEnable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;


import com.yufei.infoExtractor.core.InfoActionConfig;
import com.yufei.pfw.entity.Entity;


public class Task extends Entity {
	private Pattern pattern=null;
	//一直没有启动过刚创建
	public final static Integer UN_RUN=0; 
	//正在执行
	public final static Integer IS_RUNNING=1; 
	//正常执行完毕
	public final static Integer IS_RUNED=2; 
	//被挂起
	public final static Integer IS_SUSPENG=3;
	//强行终止任务的执行
	public final static Integer IS_STOPED=4;
	public final  static Integer IS_FAILED=5;
	//任务执行定时表达式
	private String cron=null;
	public String getCron() {
		return cron;
	}
	@XmlElement
    public void setCron(String cron) {
		this.cron = cron;
	}

	// Field
	//定义每次任务执行间隔周期，当前任务借宿之后暂停此变量指定的时间之后重新开始启动,分钟计，默认为：60*12（半天）
    private Long intervalPeriodTime;

	
	public Long getIntervalPeriodTime() {
		return intervalPeriodTime;
	}
	public void setIntervalPeriodTime(Long intervalPeriodTime) {
		this.intervalPeriodTime = intervalPeriodTime;
	}
    @QueryEnable(enable=true)
	private String taskName;
	
	private Integer status=UN_RUN;
	private Date startedTime;
	private Date endTime;
	
	private String entityFullName=null;
	
	private String taskExecuteJobFullName;
	@CollectionElementType(type=InfoActionConfig.class)
    private List<InfoActionConfig> actionConfigs=new ArrayList<InfoActionConfig>();
	
	public List<InfoActionConfig> getActionConfigs() {
		return actionConfigs;
	}
	@XmlElementWrapper(name="actionConfigs")
	@XmlElement(name="actionConfig")
	public void setActionConfigs(List<InfoActionConfig> actionConfigs) {
		this.actionConfigs = actionConfigs;
	}

	
	

	public String getEntityFullName() {
		return entityFullName;
	}
	

	/*public List<String> getExtractors() {
		return infoExtractorActions;
	}
	@XmlElementWrapper(name="extractors")
	@XmlElement(name="extractor")
	public void setExtractors(List<String> extractors) {
		this.extractors = extractors;
	}*/
	@XmlElement
	public void setEntityFullName(String entityFullName) {
		this.entityFullName = entityFullName;
	}
	
	
	

	public String getTaskExecuteJobFullName() {
		return taskExecuteJobFullName;
	}
	@XmlElement
	public void setTaskExecuteJobFullName(String taskExecuteJobFullName) {
		this.taskExecuteJobFullName = taskExecuteJobFullName;
	}
	@CollectionElementType(type=Seedsite.class)
	private List<Seedsite> seedsites=new ArrayList<Seedsite>();
	//1:low,2:common,3:high;default :common
	/*@XmlElement
	private Integer priority=2;

	// Constructors

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}*/

	public List<Seedsite> getSeedsites() {
		
		return seedsites;
	}
	@XmlElementWrapper(name="seedsites")
	@XmlElement(name="seedsite")
	public void setSeedsites(List<Seedsite> seedsites) {
		this.seedsites = seedsites;
	}

	/** default constructor */
	public Task() {
		super();
		//this.setStatus(Task.UN_RUN);
	}


	public String getTaskName() {
		return taskName;
	}
	@XmlElement
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	

	public Integer getStatus() {
		return status;
	}
	@XmlElement
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStartedTime() {
		return startedTime;
	}
	
	public void setStartedTime(Date startedTime) {
		this.startedTime = startedTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityFullName == null) ? 0 : entityFullName.hashCode());
		result = prime * result
				+ (int) (intervalPeriodTime ^ (intervalPeriodTime >>> 32));
		result = prime * result
				+ ((seedsites == null) ? 0 : seedsites.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime
				* result
				+ ((taskExecuteJobFullName == null) ? 0
						: taskExecuteJobFullName.hashCode());
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
	

		if (taskName == null) {
			if (other.taskName != null)
				return false;
		} else if (!taskName.equals(other.taskName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Task [cron=" + cron + ", intervalPeriodTime="
				+ intervalPeriodTime + ", taskName=" + taskName + ", status="
				+ status + ", startedTime=" + startedTime + ", endTime="
				+ endTime + ", entityFullName=" + entityFullName
				+ ", taskExecuteJobFullName=" + taskExecuteJobFullName
				+ ", seedsites=" + seedsites + "]";
	}
	


    
	

	

}