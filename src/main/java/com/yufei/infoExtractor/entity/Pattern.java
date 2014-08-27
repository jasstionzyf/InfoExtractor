package com.yufei.infoExtractor.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.yufei.entity.Entity;
import com.yufei.infoExtractor.annotation.CollectionElementType;

/**
 * @author zhaoyufei
 *采集信息实体对应的正则表达式，每个实体对应一个，通过sign关联
 */
@XmlRootElement
public class Pattern extends Entity {
	@Override
	public String toString() {
		StringBuffer str =new StringBuffer("Pattern [taskName="+ taskName);
		for(PropertyMatch propertyMatch:propertyMatches){
			str.append(propertyMatch.toString());
			
		}
		str.append("]");
		return str.toString();
	}



	


/*private Map<String,String> matches=new HashMap<String, String>();
public Map<String, String> getMatches() {
	return matches;
}

@XmlTransient
public void setMatches(Map<String, String> matches) {
	this.matches = matches;
}*/
@CollectionElementType(type=PropertyMatch.class)
private List<PropertyMatch> propertyMatches=new ArrayList<PropertyMatch>();
public List<PropertyMatch> getPropertyMatches() {
	return propertyMatches;
}
@XmlElementWrapper(name="propertyMatches")
@XmlElement(name="propertyMatch")
public void setPropertyMatches(List<PropertyMatch> propertyMatches) {
	this.propertyMatches = propertyMatches;
}
public String getTaskName() {
	return taskName;
}
@XmlElement
public void setTaskName(String taskName) {
	this.taskName = taskName;
}

private String taskName;




/*public void synchronizeRegexMap(){
	for(PropertyMatch propertyMatch:propertyMatches){
		if(propertyMatch.getProperty().equals("linkEntityRegex")||propertyMatch.getProperty().equals("linkLinksRegex")||propertyMatch.getProperty().equals("linkFilterRegex")){
			matches.put((String)LinkTypeMap.get(propertyMatch.getProperty()), CommonUtil.removeSpace(propertyMatch.getMatch()));
			
		}
		else{
		matches.put(propertyMatch.getProperty(), CommonUtil.removeSpace(propertyMatch.getMatch()));
		}
	}
}*/
}

