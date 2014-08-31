package com.yufei.infoExtractor.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jndi.JndiObjectTargetSource;

import com.yufei.infoExtractor.core.InfoExtractorConfig;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;
/*import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;*/

public class AppUtil {
private static Log mLog = LogFactory.getLog(AppUtil.class);
public final static String stringSplitSymbol=",";
private static ApplicationContext appContext=null;
private static Properties crawlerProperties=null;
public static String defaultCrawlerDateFilePath="classpath:conf/infoExtractorConfig.xml";
final static String defaultSpringConfigPath="conf/app.xml";
final static String cacheConf="conf/cache.xml";
public final static String defaultUrlCache="UrlCache";
static InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(AppUtil.cacheConf);





/*public static Object get(ServletContext app,String bean){
	
	return ((BeanFactory)app.getAttribute("beanFactory")).getBean(bean);
	
	
}*/
//BeanFactory beanFactory = new ClassPathXmlApplicationContext("com/fz/life/core/config/BeanContainer.xml");

//init linkaction

public static Object getBeanFromBeanContainer(String name){
	return getAppContext().getBean(name);
	
}
public static Object getBeanFromBeanContainer(Class c){
	return getAppContext().getBean(c);
	
}//初始化各种类型连接处理对象


public static Object getEjb(String name){
	return ((JndiObjectTargetSource)getAppContext().getBean(name)).getTarget();
	
}

private static ApplicationContext getAppContext(){
	appContext=appContext== null?new ClassPathXmlApplicationContext(defaultSpringConfigPath):appContext;
	return appContext;
	
	
}
private static Properties getProperties(String path){
	Properties properties=new Properties();
	
	InputStream in=null;
	try {
		URL resource=Thread.currentThread().getContextClassLoader().getResource(path);
        
		
		properties.load(resource.openStream());
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return properties;
	
}
@Deprecated
public static String getCrawlerProperty(String key){
	/*if(crawlerProperties==null){
		crawlerProperties=getProperties(AppConstants.CRAWLER_PROPERTIES);
	}
	return crawlerProperties.getProperty(key);*/
	return null;
	
} 


public static InfoExtractorConfig  getInfoExtractorConfig(){
	
	
	
	
	   InputStream in = null;
		
			in =Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/infoExtractorConfig.xml");
		
	InfoExtractorConfig infoExtractorConfig=null;
	
	JAXBContext jaxbContext;
	try {
		jaxbContext = JAXBContext.newInstance(InfoExtractorConfig.class);
		Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
	    infoExtractorConfig=(InfoExtractorConfig) unmarshaller.unmarshal(in);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		mLog.error("reading infoExtractorConfig wrong!");
		throw new RuntimeException(e);
	} finally{
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
		}
	}
	
	return infoExtractorConfig;
	
}

private static boolean  mongodbHasStarted=false;
public static void startMongodb(){
	StringBuffer command=new StringBuffer();
	String fileStore=AppUtil.getInfoExtractorConfig().getFileSavedDirectory();
	command.append("cmd /c start ");
	fileStore=fileStore.replace("//", "\\");
	command.append(""+fileStore+"db\\mongodb\\bin\\mongod");
		command.append("  --dbpath \""+fileStore+"db\\filestore\"");
	if(!mongodbHasStarted){
		
		try {
			//celan the lock
			File dbDataPath=new File(""+fileStore+"db//filestore//");
			if(!dbDataPath.exists()){
				dbDataPath.mkdirs();
			}
			for(File file:dbDataPath.listFiles()){
				if(file.getName().contains("mongod.lock")){
					file.delete();
					break;
				}
			}
			Process p=Runtime.getRuntime().exec(command.toString());
			mongodbHasStarted=true;
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

































































































}
