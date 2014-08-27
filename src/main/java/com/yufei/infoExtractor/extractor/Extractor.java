/**
 * 
 */
package com.yufei.infoExtractor.extractor;

import com.yufei.infoExtractor.context.HActionContext;

/**
 * @author zhaoyufei zhao-0244@qq.com
 *信息抽取抽象类（仅仅负责初级数据的获取）
 * created by zhaoyufei at 2012-8-7 下午4:13:16
 */
public abstract class Extractor implements ExtractorAction{
public abstract void extract(HActionContext context);







}
