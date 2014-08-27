package com.yufei.infoExtractor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.yufei.infoExtractor.core.impl.InfoExtractorM;

public class LoggerCommon {
private Log m_log=null;


	public LoggerCommon() {
		super();
		this.m_log = LogFactory.getLog(this.getClass());
		
		// TODO Auto-generated constructor stub
	}


	public Log getM_log() {
		return m_log;
	}


	public void setM_log(Log m_log) {
		this.m_log = m_log;
	}

}
