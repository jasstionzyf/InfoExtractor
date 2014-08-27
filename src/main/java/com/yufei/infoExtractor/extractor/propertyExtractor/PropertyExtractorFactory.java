package com.yufei.infoExtractor.extractor.propertyExtractor;

import java.util.HashMap;
import java.util.Map;

import com.mchange.v2.log.MLog;
import com.yufei.infoExtractor.entity.PropertyMatch;
import com.yufei.infoExtractor.extractor.PropertyExtractor;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;

public class PropertyExtractorFactory {
	static Map<String,String>  matchTypePropertyExtractors=new HashMap<String, String>();
	static{
		matchTypePropertyExtractors.put(PropertyMatch.matchType_common, "com.yufei.infoExtractor.extractor.propertyExtractor.CommonPropertyExtractor");
		matchTypePropertyExtractors.put(PropertyMatch.matchType_collection, "com.yufei.infoExtractor.extractor.propertyExtractor.CollectionPropertyExtractor");
		matchTypePropertyExtractors.put(PropertyMatch.matchType_collection_na_assembleUrl, "com.yufei.infoExtractor.extractor.propertyExtractor.CollectionPropertyExtractor");
		matchTypePropertyExtractors.put(PropertyMatch.matchType_collection_na_regUrl, "com.yufei.infoExtractor.extractor.propertyExtractor.CollectionPropertyExtractor");
		matchTypePropertyExtractors.put(PropertyMatch.matchType_collection_ua_assembleUrl, "com.yufei.infoExtractor.extractor.propertyExtractor.CollectionPropertyExtractor");
		matchTypePropertyExtractors.put(PropertyMatch.matchType_collection_ua_regUrl, "com.yufei.infoExtractor.extractor.propertyExtractor.CollectionPropertyExtractor");

		matchTypePropertyExtractors.put(PropertyMatch.matchType_FilterContent, "com.yufei.infoExtractor.extractor.propertyExtractor.CRNPropertyExtractor");

		matchTypePropertyExtractors.put(PropertyMatch.matchType_media, "com.yufei.infoExtractor.extractor.propertyExtractor.MediaPropertyExtractor");

		matchTypePropertyExtractors.put(PropertyMatch.matchType_pictureNumber, "com.yufei.infoExtractor.extractor.propertyExtractor.PictureNumberPropertyExtractor");



		
	}
public static PropertyExtractor getPropertyExtractor(PropertyMatch propertyMatch){
	if(CommonUtil.isEmptyOrNull(propertyMatch.getMatchType())){
		throw new  IllegalArgumentException();
	}
	PropertyExtractor propertyExtractor=null;
	String className=matchTypePropertyExtractors.get(propertyMatch.getMatchType());
	if(className==null){
		MLog.info("not support PropertyExtractor! the fieldName is "+propertyMatch.getProperty()+"; the matchType is "+propertyMatch.getMatchType());
		return null;
	}
	try {
		propertyExtractor=(PropertyExtractor) Class.forName(className).newInstance();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		MLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
	}
	return propertyExtractor;
}
}
