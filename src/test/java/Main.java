import com.yufei.annotation.QueryEnable;
import com.yufei.infoExtractor.LoggerCommon;
import com.yufei.infoExtractor.entity.Hotel;
import com.yufei.utils.CommonUtil;
import java.io.IOException;


/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2010 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

public class Main extends LoggerCommon {

	public Main() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		/*InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
		List<Hotel> unProcessedLinks=null;
		Map queryMap=new HashedMap();
		Task task=infoExtractorDao.getDataRepositoryI().queryEntity("taskName", "hotel", Task.class);
		queryMap.put("taskId", task.getId());
	//	infoExtractorDao.getDataRepositoryI().getMongoOperations().dropCollection(TaskFingerprint.class);
		List<TaskFingerprint> taskFingerprints=(List<TaskFingerprint>) infoExtractorDao.getDataRepositoryI().getMongoOperations().findAll(TaskFingerprint.class);
		unProcessedLinks= infoExtractorDao.getDataRepositoryI().queryListEntity("taskId", task.getId(), Hotel.class);//infoExtractorDao.getDataRepositoryI().queryAllValuesOfField("link",Hotel.class, queryMap);

		unProcessedLinks= infoExtractorDao.getDataRepositoryI().queryListEntity("taskId", task.getId(), Hotel.class);//infoExtractorDao.getDataRepositoryI().queryAllValuesOfField("link",Hotel.class, queryMap);
	int size=0;
		for(Hotel hotel:unProcessedLinks){
			if(CommonUtil.isEmptyOrNull(hotel.getHotelName())){
				size++;
				System.out.print(hotel.getLink()+"\n");
			}
		}
		System.out.print(size);
		
		try {
			String savePath=AppUtil.getInfoExtractorConfig().getFileSavedDirectory();
		//	FileUtil.makeExcelFileForTaskResult(unProcessedLinks,savePath+"test\\", Hotel.class);
			List<String> fieldOrders=new ArrayList<String>();
			
			fieldOrders.add("hotelName");
			fieldOrders.add("city");

			fieldOrders.add("leftTimeAvailabled");

			fieldOrders.add("orderedNumber");

			fieldOrders.add("area");

			fieldOrders.add("price");
			fieldOrders.add("oPrice");

			fieldOrders.add("discount");

			fieldOrders.add("pictureNameMedia");
			List<String> headers=new ArrayList<String>();
			headers.add("酒店名称");
			headers.add("城市");

			headers.add("剩余抢购天数");

			headers.add("订单数目");

			headers.add("地址");

			headers.add("价格");

			headers.add("原价");

			headers.add("折扣");

			headers.add("酒店照片");

			




			
		      FileUtil.gernerateExcelFromList(unProcessedLinks, fieldOrders, savePath+"test\\", headers);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*System.out.print(unProcessedLinks.size());
		String html=CommonUtil.getHtmlContent("", true, "http://www.lashou.com/hotel/deal/7353332.html");
		System.out.print(html);*/
		/*CacheManager singletonManager = CacheManager.newInstance("src/main/resources/conf/cache.xml");
	//	singletonManager.addCache("testCache");
		Cache test = singletonManager.getCache("UrlCache");
		test.put(new Element("test", "test"));
		test.put(new Element("test1", "test1"));
		test.put(new Element("test2", "test2"));	
		test.put(new Element("test3", "test3"));
		
		System.out.print(test.get("test3").getValue());
	 
		
		 *    String url="http://go.microsoft.com/fwlink/?linkid=69157";
	    EncryptUtil.md5(url);
	    new Main().getM_log().info(EncryptUtil.md5(url));
		 * 
		 * // Prepares the task.
		MyTask task = new MyTask();
		
		// Creates the scheduler.
       
		Scheduler scheduler = new Scheduler();
	    
		System.out.print(scheduler.isDaemon());
		scheduler.setDaemon(true);
		// Schedules the task, once every minute.
		String taskId=scheduler.schedule("* * * * *", task);
	    //获取所有运行的task的各种信息
		
		// Starts the scheduler.
		scheduler.start();
		Task task0=scheduler.getTask(taskId);
		System.out.print(task0.canBePaused());
		task0.execute(null);
		
		TaskExecutor[] taskExecutors=scheduler.getExecutingTasks();
		if(taskExecutors.length==0){
			System.out.print("no active executing task \n");

		}
		for(TaskExecutor taskExecutora:taskExecutors){
			System.out.print(taskExecutora.getStatusMessage()+"\n");
			
		}
		// Stays alive for five minutes.
		try {
			Thread.sleep(5L * 60L * 1000L);
		} catch (InterruptedException e) {
			;
		}
		// Stops the scheduler.
		scheduler.stop();
		double totalBytes=0;
	
	    	
	          
	    }
	    System.out.print(1024*1024*1024/(48304*16));
		CacheFactory<Integer,WeakReference<News>> cacheFactory=CacheFactory.getInstance();
		InfoExtractorCache<Integer,WeakReference<News>>  cache=cacheFactory.createCache(CacheFactory.MemoryCacheType);
		WeakReference<News> byteReference=null;
     

	    for (int i = 0; i < 1000900; i++) {  
	        byteReference=new WeakReference<News>(new News());
	        cache.put(i, byteReference);  
	        
	 
	   //     System.gc();  
	    
	  
	  
	    }  

	
	 
	    News bytes=null;
	    for (int i = 0; i < 1000900; i++) {  
	    	byteReference=cache.get(i);
	    	if(byteReference!=null){
	    		 bytes=byteReference.get();
		    	 if(bytes!=null){
		    		    System.out.print(bytes+"\n");

		    	 }
	    	}
	    
	
	}*/
//		System.out.print(EncryptUtil.md5("http://www.baidu.com/s?wd=java spring hibernate&pn=230&tn=baiduhome_pg&ie=utf-8&rsv_page=1"));
	/*String libPath=System.getProperty("java.library.path");

	System.out.print(HtmlUtil.encoderUrl("赵宇飞,猪八戒"));
	String[] strs={"赵宇飞,猪八戒","123"};
	for(int i=0;i<strs.length;i++){
		strs[i]=HtmlUtil.encoderUrl(strs[i]);
	}
	for(String str:strs){
		System.out.print(str+"\n");
	}
		AppUtil.defaultCrawlerDateFilePath="file:D:\\infoExtractorConfig.xml";
	InfoExtractorConfig infoExtractorConfig=AppUtil.getInfoExtractorConfig();
	System.out.print(infoExtractorConfig.getTempDirectory());
		InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
 
		News news=infoExtractorDao.getDataRepositoryI().queryEntity("id", "50be82f6ae81d569ec79a276", News.class);
		String originalHtmlContent = news.getOriginalHtmlContent();
		System.out.print(originalHtmlContent+"\n*************************************************\n");
		System.out.print(new String(originalHtmlContent.getBytes("gbk"),"utf-8"));
		AppUtil.defaultCrawlerDateFilePath="file:D:\\infoExtractorConfig.xml";
	InfoExtractorConfig infoExtractorConfig=AppUtil.getInfoExtractorConfig();
	System.out.print(infoExtractorConfig.getTempDirectory());

		 news=infoExtractorDao.getDataRepositoryI().queryEntity("id", "50be82f6ae81d569ec79a276", News.class);
		 originalHtmlContent = news.getOriginalHtmlContent();
		System.out.print(originalHtmlContent+"\n*************************************************\n");
		System.out.print(new String(originalHtmlContent.getBytes("gbk"),"utf-8"));
		String tr=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.print(System.getProperty("user.dir"));
		  System.out.println("用户的账户名称："+System.getProperty("user.name"));   
		   System.out.println("用户的主目录："+System.getProperty("user.home"));   
		   System.out.println("用户的当前工作目录："+System.getProperty("user.dir")); 
		   System.out.println("文件分隔符："+System.getProperty("file.separator"));   //在 unix 系统中是＂／＂   
		
		Set<Weibo> weibos=new HashSet<Weibo>();
		Weibo e1 = new Weibo();
		Weibo e2 = new Weibo();
        e1.setDepth(3);
        e2.setDepth(4);
		weibos.add(e1);
		weibos.add(e2);
		//weibos.add(null);
		//weibos.add(null);

System.out.print(weibos.size());
		Weibo weibo=new Weibo();
		weibo.setSign("%");
		CommonUtil.generateXmlForObject(weibo, Weibo.class, "F:\\jcr\\");
		Weibo weibo1=(Weibo) CommonUtil.getObjectFromXml(new FileInputStream(new File("F:\\jcr\\test%.xml")), Weibo.class);
		System.out.print(weibo1.getSign());*/
		//System.out.print("dfsdfsdf"+"\n"+"sdfsdfs");
	//String url1 = "http://user.qzone.qq.com/625070193/blog/1345306023";
	//String c=CommonUtil.getHtmlContent(null, true, url1);
   // System.out.print(c);
//System.out.print(HtmlUtil.decodeUrl(url1));
		/*System.out.println(InfoActionType.EXPORT.toString());
		InfoActionConfig infoActionConfig=new InfoActionConfig();
		infoActionConfig.setInfoActionClassFullClassName("fsdfsdf");
		infoActionConfig.setInfoActionType(InfoActionType.EXPORT);
		try {
			System.out.print(PropertyUtils.getProperty(infoActionConfig,"infoActionType"));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				//
				InfoActionConfig infoActionConfig2=(InfoActionConfig) CommonUtil.getObjectFromXml(new FileInputStream(new File("F:\\tasks\\test.xml")), InfoActionConfig.class);
				CommonUtil.generateXmlForObjectA(infoActionConfig, InfoActionConfig.class, "F:\\tasks\\test.xml");*/
	//对于可查询注解的测试
		QueryEnable   queryEnable= (QueryEnable) CommonUtil.getFieldByFieldName(Hotel.class, "hotelName").getAnnotation(QueryEnable.class);;
		//elementType.getField(jTable1.getColumnName(col)).getAnnotation(CollectionElementType.class);
		QueryEnable   queryEnable1= (QueryEnable) CommonUtil.getFieldByFieldName(Hotel.class, "city").getAnnotation(QueryEnable.class);;

		System.out.println(queryEnable.enable());
		System.out.println(queryEnable1!=null);
		//System.out.println(CommonUtil.fieldIsQueryEnable( CommonUtil.getFieldByFieldName(Hotel.class, "hotelName")));
		//System.out.println(CommonUtil.fieldIsQueryEnable( CommonUtil.getFieldByFieldName(Hotel.class, "city")));

	
	}
}
