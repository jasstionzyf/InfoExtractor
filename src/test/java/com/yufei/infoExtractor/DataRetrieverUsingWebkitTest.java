package com.yufei.infoExtractor;

import static org.junit.Assert.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.yufei.infoExtractor.io.AbstractHttpDataRetriever;
import com.yufei.infoExtractor.io.HttpDataRetriever;
import com.yufei.infoExtractor.io.DataRetrieverFeatures;
import com.yufei.infoExtractor.io.impl.HttpDataRetrieverUsingWebkit;

public class DataRetrieverUsingWebkitTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetContent() {
	HttpDataRetriever	dataRetriever=new HttpDataRetrieverUsingWebkit(new DataRetrieverFeatures(true, null));
		try {
			dataRetriever.setUrl(new URL("http://rate.taobao.com/user-rate-e8ebe6dc6e36c1c64bf9d7ce83375740.htm"));
			dataRetriever.connect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(dataRetriever.getHtmlContent());
	//	Assert.assertTrue(((AbstractHttpDataRetriever)dataRetriever).getHtmlContent().contains("下一页"));
	}

}
