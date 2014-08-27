package com.yufei.infoExtractor.listener;

import java.util.EventListener;

/**
 * @author jasstion
   2012-12-3
 *用于监听任务执行过程中相关状态的改变
 */
public interface InfoExtractorCommonTaskListener extends EventListener {
public  void urlFetchedNumberChanged(InfoExtractorEvent e);
public  void urlExtractedNumberChanged(InfoExtractorEvent e);

}
