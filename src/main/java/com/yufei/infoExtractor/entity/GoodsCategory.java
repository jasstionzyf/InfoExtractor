/**
 * 
 */
package com.yufei.infoExtractor.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zhaoyufei zhao-0244@qq.com
 *
 * created at 2012-8-23 下午6:12:17
 */
@XmlRootElement
public class GoodsCategory extends Category {

	@Override
	public String toString() {
		return "GoodsCategory [toString()=" + super.toString() + "]";
	}


}
