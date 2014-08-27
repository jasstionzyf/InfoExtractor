package com.yufei.infoExtractor.entity;

import com.yufei.pfw.entity.Relatedlink;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Weibo extends Relatedlink {
    @Override
	public int hashCode() {
		
		int result = 1;
		/*final int prime = 31;
		result = prime * result
				+ ((contents == null) ? 0 : contents.hashCode());
		result = prime * result
				+ ((contentsNumber == null) ? 0 : contentsNumber.hashCode());
		result = prime * result
				+ ((followerNumber == null) ? 0 : followerNumber.hashCode());
		result = prime * result
				+ ((followingNumber == null) ? 0 : followingNumber.hashCode());
		result = prime * result
				+ ((selfDescription == null) ? 0 : selfDescription.hashCode());
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());*/
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
		Weibo other = (Weibo) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (contentsNumber == null) {
			if (other.contentsNumber != null)
				return false;
		} else if (!contentsNumber.equals(other.contentsNumber))
			return false;
		if (followerNumber == null) {
			if (other.followerNumber != null)
				return false;
		} else if (!followerNumber.equals(other.followerNumber))
			return false;
		if (followingNumber == null) {
			if (other.followingNumber != null)
				return false;
		} else if (!followingNumber.equals(other.followingNumber))
			return false;
		if (selfDescription == null) {
			if (other.selfDescription != null)
				return false;
		} else if (!selfDescription.equals(other.selfDescription))
			return false;
		if (sign == null) {
			if (other.sign != null)
				return false;
		} else if (!sign.equals(other.sign))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	private String sign;


	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {

		this.sign = sign;

	}

	public String getUserName() {
		return userName;
	}

	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getContents() {
		return contents;
	}



	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public void addContents(String content) {
		if (!contents.contains(content)) {
			contents.add(content);
		}

	}

	private String userName;
	private List<String> contents = new ArrayList();
	private String  selfDescription = null;
	private Integer followerNumber;
	private Integer followingNumber;
	private Integer contentsNumber;

	public String getSelfDescription() {
		return selfDescription;
	}

	
	public void setSelfDescription(String selfDescription) {
		this.selfDescription = selfDescription;
	}

	public Integer getFollowerNumber() {
		return followerNumber;
	}

	
	public void setFollowerNumber(Integer followerNumber) {
		this.followerNumber = followerNumber;
	}

	public Integer getFollowingNumber() {
		return followingNumber;
	}

	public void setFollowingNumber(Integer followingNumber) {
		this.followingNumber = followingNumber;
	}

	public Integer getContentsNumber() {
		return contentsNumber;
	}

	
	public void setContentsNumber(Integer contentsNumber) {
		this.contentsNumber = contentsNumber;
	}

}
