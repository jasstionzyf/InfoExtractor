/**
 * 
 */
package com.yufei.infoExtractor.extractor;

import com.yufei.infoExtractor.context.HActionContext;

/**
 * @author zhaoyufei zhao-0244@qq.com
 *根据property对应的配置的规则来从指定的内容中抽取信息并进行处理
 * created at 2012-8-30 上午11:43:49
 */
public abstract class PropertyExtractor extends Extractor {

	/* (non-Javadoc)
	 * @see com.rzx.crawler.extractor.Extractor#extract(java.util.Map)
	 */
	@Override
	public abstract void extract(HActionContext context) ;
	
	

}
