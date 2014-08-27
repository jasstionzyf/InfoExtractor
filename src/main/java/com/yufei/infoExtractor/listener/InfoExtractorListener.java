/**
 * 
 */
package com.yufei.infoExtractor.listener;

/**
 * @author zhaoyufei
 *
 */
import java.util.EventListener;
import java.util.EventObject;
public interface InfoExtractorListener extends EventListener{
	public void actionPerformed(InfoExtractorEvent event);

}
