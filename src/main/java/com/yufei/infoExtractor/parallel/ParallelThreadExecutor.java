package com.yufei.infoExtractor.parallel;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.yufei.infoExtractor.context.HActionContext;

/**
 * @author jasstion
   2012-11-18
 *
 */
public interface ParallelThreadExecutor {
/**
 * @param ks集合数据
 * 将集合数据进行spit然后多线程的方式处理
 * context作为线程处理数据时候需要的额外信息
 */
public  void parallelExecuteThreadsForCollectionData(Collection ks,int blockSize,HActionContext context);
}
