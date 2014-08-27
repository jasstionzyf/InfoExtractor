/**
 * 
 */
package com.yufei.infoExtractor.core;

import it.sauronsoftware.cron4j.Task;

import java.util.HashMap;

import com.yufei.infoExtractor.context.BaseContext;
import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.task.InfoExtractorCommonTask;

/**
 * @author jasstion
   2013-1-21
 *表示InfoExtractor平台中的一个行为，完成具体的一个功能，并且依赖于上下文环境（比如：HActionContext）
 *并且这些功能是可配置的以及可以其他行为进行组合一起来完成某项具体的任务
 *所有的action都有一个actionType以及此action属于那个actionGroup
 *actionType抽象的表明一个action的职责类型，比如：信息抽取，信息处理，信息保存等等
 *actionGroup表示某些一系列特定的action组合起来形成一个group
 */
public interface InfoExtractionAction <C extends BaseContext>{
	
	public void execute(C context) throws Exception;



}
