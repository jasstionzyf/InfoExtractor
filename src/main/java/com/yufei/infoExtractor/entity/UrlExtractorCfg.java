package com.yufei.infoExtractor.entity;

import com.yufei.pfw.entity.PaginationRule;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author jasstion
 *
 */
/**
 * @author jasstion Email:zhao-0244@qq.com
 *2013-5-3 下午8:21:13
 */
@XmlRootElement
public class UrlExtractorCfg {
	public UrlExtractorCfg() {
		super();
		
	}

	private final  PaginationRule paginationRule=null;
	
	private Pattern pattern;
	public Pattern getPattern() {
		return pattern;
	}
	@XmlElement
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	} 

	
	/**
	 * 如果等于-1表示全网抓取
	 */
	private Integer depth;



	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public PaginationRule getPaginationRule() {
		return paginationRule;
	}




	   
}
