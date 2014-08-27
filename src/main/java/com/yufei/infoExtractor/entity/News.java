package com.yufei.infoExtractor.entity;

import com.yufei.pfw.entity.Relatedlink;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class News  extends Relatedlink implements Comparable<News>{

 /**
 * 经过特定处理之后的文本内容
 */
private String content=null;


private String url=null;
 private Integer score;
 private List<String> picturesPath=new ArrayList<String>();
public List<String> getPicturesPath() {
	return picturesPath;
}
@XmlElementWrapper(name = "pictures")
@XmlElement(name = "picture")
public void setPicturesPath(List<String> picturesPath) {
	this.picturesPath = picturesPath;
}
public Integer getScore() {
	return score;
}
public void setScore(Integer score) {
	this.score = score;
}
public String getContent() {
	return content;
}
@XmlElement
public void setContent(String content) {
	this.content = content;
}
public String getUrl() {
	return url;
}
@XmlElement
public void setUrl(String url) {
	this.url = url;
}
@Override
public int compareTo(News o) {
	// TODO Auto-generated method stub
	if(this.score>o.getScore()){
		return -1;
	}
	if(this.score==o.getScore()){
		return 0;
	}
	else{
		return 1;
	}
}

}
