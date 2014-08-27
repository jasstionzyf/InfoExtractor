package com.yufei.infoExtractor.core;

import java.util.List;


/**
 * @author jasstion
 * 下午5:21:48
 * 专门用于获取已有的链接（可能是人工配置的）
 */
public interface ExternalLinksGetter {
	public List<String> getExternalLinks();


}
