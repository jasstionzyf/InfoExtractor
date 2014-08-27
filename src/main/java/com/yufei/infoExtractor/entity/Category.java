/**
 * 
 */
package com.yufei.infoExtractor.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.yufei.entity.Entity;

/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-8-23 下午6:03:22
 */
@XmlRootElement
public class Category  extends Entity{
	
private String categoryId=null;
private String parentCategoryId=null;
private String name=null;
private String description=null;

private List<? extends Category> subCategories=null;


public List<? extends Category> getSubCategories() {
	return subCategories;
}
@XmlElementWrapper(name="subCategories")
@XmlElement(name="category")
public void setSubCategories(List<? extends Category> subCategories) {
	this.subCategories = subCategories;
}
public String getCategoryId() {
	return categoryId;
}
public void setCategoryId(String categoryId) {
	this.categoryId = categoryId;
}
public String getParentCategoryId() {
	return parentCategoryId;
}
public void setParentCategoryId(String parentCategoryId) {
	this.parentCategoryId = parentCategoryId;
}
@Override
public String toString() {
	return "Category [categoryId=" + categoryId + ", parentCategoryId="
			+ parentCategoryId + ", name=" + name + ", description="
			+ description + super.toString()+"]";
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
			+ ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	Category other = (Category) obj;
	if (description == null) {
		if (other.description != null)
			return false;
	} else if (!description.equals(other.description))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	return true;
}
}
