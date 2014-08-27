package com.yufei.infoExtractor.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

import com.yufei.infoExtractor.action.InfoActionType;

public class InfoActionConfig{
private String infoActionClassFullClassName=null;
private InfoActionType infoActionType=null;
public String getInfoActionClassFullClassName() {
	return infoActionClassFullClassName;
}
public  InfoActionConfig() {
	super();
	// TODO Auto-generated constructor stub
}
@XmlElement(name="infoActionClassFullClassName")
public void setInfoActionClassFullClassName(String infoActionClassFullClassName) {
	this.infoActionClassFullClassName = infoActionClassFullClassName;
}
public InfoActionType getInfoActionType() {
	return infoActionType;
}
@XmlElement(name="infoActionType")

public void setInfoActionType(InfoActionType infoActionType) {
	this.infoActionType = infoActionType;
}
}
