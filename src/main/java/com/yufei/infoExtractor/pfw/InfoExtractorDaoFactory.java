package com.yufei.infoExtractor.pfw;

import com.yufei.infoExtractor.util.AppUtil;

public class InfoExtractorDaoFactory {
public static InfoExtractorDao getInfoExtractorDao(){
	return (InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
	
}
}
