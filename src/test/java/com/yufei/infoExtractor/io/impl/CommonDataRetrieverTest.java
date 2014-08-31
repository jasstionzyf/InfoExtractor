package com.yufei.infoExtractor.io.impl;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ibm.icu.text.CharsetDetector;
import com.yufei.dataget.dataretriver.CommonDataRetriever;
import com.yufei.dataget.dataretriver.HttpDataRetriever;
import com.yufei.utils.IOUtils;


public class CommonDataRetrieverTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
	HttpDataRetriever datar=new CommonDataRetriever(null);
	try {
       byte[] bytes=null;		
		String url="http://www.baidu.com/link?url=bed99b09f3231513406dbe09f497a989cfe5db95200099a225d88982fe894b353955bffd5f8db48862f710a169f3d74458b65d43a96e94aafd50f6f61e2859568c3474d4c8f1e285fc3773f0c4fece5c654724412ac48371b66a1f0ea3a4c13dcd454228114ad3cffe07c3fe830b63e328fe2ff594aa9c25572a4edd0ef15caaa060ae4278d44f7cb752afa2388384e8a2cdb752b44730a7eb515a4e607efb30e05d847881ef463448df02b5c2f1aad524a5505699ed2a4581b766bf1dd46bfc9bc1340324d542e23df7c61356c0022c325c08bda3a7b38fde4d8c2e771d91f3b99b60b24e8bc2147c1f9abe1beb639117c592df3f71e19a7a3a01d1d10cae9193470aa7bd988da4e32d991baa69d62021da9148b4b0f2a518a024fa64524421a9a043da8217d594cd4353cab7bc0b189a23dcd0a9e9bba4ce1e89ab06ac7675783d2b1c7a276354be22043c5b0a058d410f89123be7df9dec2782629969ebac5573358cc3c0659257bfcf547131cdc0ebb0773dbb1416254fd865f4583a20e12cfc66b73acdaf18b3";
			datar.setUrl(new URL(url));
			
		datar.connect();
		bytes=IOUtils.translantStreamToByte(datar.getContent());



		
		CharsetDetector detector = new CharsetDetector();
	/*	byte[] tbutes=new byte[1024*];
		int i=0;
		for(byte b:tbutes){
			i++;
			b=bytes[i];
		}*/
        /*detector.setText(bytes);
       // CharsetMatch matche = detector.detect();
        CharsetMatch[] matches=detector.detectAll();
      for(CharsetMatch matche:matches){
        	System.out.print(matche.getConfidence());
        	System.out.print(matche.getName());
        	System.out.print("\n");

        }*/
        
		//byte[] bytes = str.getBytes(Charset.forName("GBK"));
		System.out.print(new String(bytes,Charset.forName("UTF-8")));
		
    		datar.disconnect();

		 
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

}
