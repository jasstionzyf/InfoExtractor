package com.yufei.infoExtractor.listener;

import it.sauronsoftware.cron4j.TaskExecutor;


import it.sauronsoftware.cron4j.TaskExecutorListener;

public abstract class AbstractTaskExecutorListener implements TaskExecutorListener {

	@Override
	public void completenessValueChanged(TaskExecutor arg0, double arg1) {
		// TODO Auto-generated method stub
        
	}

	@Override
	public void executionPausing(TaskExecutor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executionResuming(TaskExecutor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executionStopping(TaskExecutor arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executionTerminated(TaskExecutor arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void statusMessageChanged(TaskExecutor arg0, String arg1) {
		// TODO Auto-generated method stub

	}

}
