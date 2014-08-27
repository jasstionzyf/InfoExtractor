package com.yufei.infoExtractor.util;

import static org.junit.Assert.*;


import org.junit.Assert;
import org.junit.Test;

import com.yufei.infoExtractor.core.InfoExtractorConfig;

public class AppUtilTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	@Test
    public void testGetInfoExtractorConfig(){
	InfoExtractorConfig infoExtractorConfig=AppUtil.getInfoExtractorConfig();
	Assert.assertTrue(infoExtractorConfig.getSubjectSimilarityWeight()!=null);
	Assert.assertTrue(infoExtractorConfig.getSubjectSimilarityWeight().getCharNumberPercentage()==250);
	Assert.assertTrue(infoExtractorConfig.getSubjectSimilarityWeight().getCharNumber()==400);
    }
}
