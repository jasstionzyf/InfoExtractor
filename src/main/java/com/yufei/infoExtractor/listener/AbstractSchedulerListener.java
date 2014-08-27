package com.yufei.infoExtractor.listener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import it.sauronsoftware.cron4j.SchedulerListener;
import it.sauronsoftware.cron4j.TaskExecutor;
public abstract class AbstractSchedulerListener implements SchedulerListener {
 
	@Override
	public abstract void taskFailed(TaskExecutor arg0, Throwable arg1);

	

	@Override
	public abstract void taskLaunching(TaskExecutor arg0);

	@Override
	public abstract  void taskSucceeded(TaskExecutor arg0);

}
