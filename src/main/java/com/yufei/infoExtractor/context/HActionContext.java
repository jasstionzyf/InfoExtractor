package com.yufei.infoExtractor.context;

import java.util.Set;

import com.yufei.component.repetition.IsRepetive;
import com.yufei.infoExtractor.entity.Seedsite;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.task.InfoExtractorCommonTask;
import com.yufei.infoExtractor.util.FactoryUtil;
import com.yufei.pfw.entity.Entity;

/**
 * @author jasstion
   2013-1-9
 *作为针对网站信息采集流程的上下文
 */ 
public class HActionContext extends BaseContext<InfoExtractorCommonTask>{
private  Seedsite seedsite=null;
/**
 * 需要加工的实体
 */
private Entity targetEntity=null;

private IsRepetive  isRepetive=FactoryUtil.createRepetiveJudge();
private Integer currentDepth=0;
public Integer getCurrentDepth() {
	return currentDepth;
}
public void setCurrentDepth(Integer currentDepth) {
	this.currentDepth = currentDepth;
}
private Set<String> linkFingerPrints=null;
public HActionContext(final Seedsite seedsite) {
	super();
	this.seedsite = seedsite;
}

public HActionContext() {
	super();
	// TODO Auto-generated constructor stub
}
public Entity getTargetEntity() {
	return targetEntity;
}
public void setTargetEntity(Entity targetEntity) {
	this.targetEntity = targetEntity;
}
public Seedsite getSeedsite() {
	return seedsite;
}
public Task task=null;
public Task getTask() {
	return task;
}
public void setTask(Task task) {
	this.task = task;
}

public IsRepetive getIsRepetive() {
	return isRepetive;
}
public void setIsRepetive(IsRepetive isRepetive) {
	this.isRepetive = isRepetive;
}
}
