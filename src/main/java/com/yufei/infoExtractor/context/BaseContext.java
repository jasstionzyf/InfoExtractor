package com.yufei.infoExtractor.context;

import it.sauronsoftware.cron4j.Task;

import java.util.HashMap;

public class BaseContext <T extends Task> extends HashMap{
	T infoExtractorCommonTask=null;

	public T getInfoExtractorCommonTask() {
		return infoExtractorCommonTask;
	}

	public void setInfoExtractorCommonTask(T infoExtractorCommonTask) {
		this.infoExtractorCommonTask = infoExtractorCommonTask;
	}

}
