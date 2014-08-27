package com.yufei.infoExtractor.entity;

import com.yufei.annotation.CollectionElementType;
import com.yufei.pfw.entity.Entity;
import java.util.HashSet;

import java.util.Set;



public class TaskFingerprint extends Entity{
public TaskFingerprint() {
		super();
		// TODO Auto-generated constructor stub
	}
public TaskFingerprint(String taskName, Set<String> fingerprints) {
		super();
		this.taskName = taskName;
		this.fingerprints = fingerprints;
	}
private String taskName=null;
@CollectionElementType(type=String.class)
private Set<String> fingerprints=new HashSet<String>();
public String getTaskName() {
	return taskName;
}
public TaskFingerprint(String taskName) {
	super();
	this.taskName = taskName;
}
public void setTaskName(String taskName) {
	this.taskName = taskName;
}
public Set<String> getFingerprints() {
	return fingerprints;
}
public void setFingerprints(Set<String> fingerprints) {
	this.fingerprints = fingerprints;
}

}
