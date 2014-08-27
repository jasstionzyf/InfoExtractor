package com.yufei.infoExtractor.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jasstion Email:zhao-0244@qq.com
 *2013-5-3 下午8:27:56
 */
@XmlRootElement
public class InfoExtractorCfg {
	public InfoExtractorCfg() {
		super();
		
	}
	private Pattern pattern;
	public Pattern getPattern() {
		return pattern;
	}
	@XmlElement
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
}
