import java.io.IOException;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mongodb.BasicDBObject;
import com.yufei.infoExtractor.pfw.InfoExtractorDao;
import com.yufei.infoExtractor.pfw.entity.News;
import com.yufei.infoExtractor.pfw.entity.Task;
import com.yufei.infoExtractor.util.AppUtil;

public class CommonTest {

/*	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	@Test
	public void testObjectFeather() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Object weibo=new Weibo();
		assertEquals("Weibo",weibo.getClass().getSimpleName());
		String temp="jasstion";
		weibo.getClass().getMethod("setUserName", String.class).invoke(weibo, temp);
       
		assertEquals(temp,BeanUtils.getProperty(weibo, "userName"));
		//濡備綍鍒ゆ柇涓�釜寮傚父鍦ㄤ竴涓紓甯稿垪琛ㄤ腑
		List<Object> exceptionsList=new ArrayList();
		exceptionsList.add(new Exception());
		Assert.assertFalse(exceptionsList.contains(new IOException()));
       IOException io=new IOException();
		exceptionsList.add(io);
		Assert.assertTrue(exceptionsList.contains(io));
	}
	@Test
	public void testListAddObj() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException{
	
		Object list=new ArrayList();
		Object weibo=new Weibo();
		((Weibo)weibo).setUserName("jasstion");
		System.out.print(list.getClass().getClasses().toString());
	    CommonUtil.getMethod(weibo.getClass(), "addContents").invoke(weibo, "contents");
		System.out.print(((Weibo)weibo).getContents().get(0));
		list.getClass().getMethod("add", Object.class).invoke(list, weibo);
		Assert.assertEquals(((Weibo)weibo).getUserName(), ((Weibo)(((List)list).get(0))).getUserName());
		
	}
	@Test
	public void testActionForUrl(){
		String url1="http://t.qq.com/doctorskin123",
			   url2="?mode=0&amp;id=111152011547895&amp;pi=2&amp;time=1336487469";
		String baseUrl=CommonUtil.getBaseUrl(url1);
		String fullUrl=CommonUtil.normalizeUrl(url2, baseUrl);
		Assert.assertEquals("http://t.qq.com/doctorskin123", baseUrl);
		Assert.assertEquals("http://t.qq.com/doctorskin123?mode=0&id=111152011547895&pi=2&time=1336487469", fullUrl);
	}
	
	public void testTrackCallHiberarchyOfMethodCall(){
		List list=new ArrayList();
		list.add("string");
		StackTraceElement[]  stackTraceElements=Thread.currentThread().getStackTrace();
		for(StackTraceElement stackTraceElement:stackTraceElements){
			System.out.print(stackTraceElement.toString()+"\n");
			
		}

		
	}
	@Test
	public void testXmlmarshalling() throws JAXBException{
		String taskName="renzhiqiangweiboqq";
		Task task=new Task();
		task.setStatus(Task.UN_RUN);
		task.setTaskExecuteJobFullName("com.rzx.crawler.job.WeiboTaskExecuteJob");
		task.setTaskName(taskName);
		task.setEntityFullName("com.rzx.crawler.pfw.entity.Weibo");
		//task.setPriority(1);
		Seedsite seedsite=new Seedsite();
		
		seedsite.setTaskName(taskName);
		seedsite.setDomain("t.qq.com");
		seedsite.setDepth(2);
		seedsite.setSiteName("http://t.qq.com/renzhiqiang");
		seedsite.setStatus(Seedsite.UN_CRAWLERED);
		seedsite.setRequestExecuteJs(true);
		task.getSeedsites().add(seedsite);
		Pattern pattern=new Pattern();


		List<PropertyMatch> propertyMatchs=new ArrayList<PropertyMatch>();
		String linkEntityRegex="http://t.qq.com/renzhiqiang";
		String linkLinksRegex="http://.*";
		propertyMatchs.add(new PropertyMatch("linkEntityRegex","http://t.qq.com/renzhiqiang"));
		propertyMatchs.add(new PropertyMatch("linkLinksRegex","http://.*"));
        propertyMatchs.add(new PropertyMatch("contents","msgCnt>(.*)</div>:pageBtn href=\"(.*?)\">涓嬩竴椤�));
		propertyMatchs.add(new PropertyMatch("contentsNumber", "骞挎挱.*?class=text_count.*?>(.*?)<"));

		propertyMatchs.add(new PropertyMatch("followingNumber", "鏀跺惉.*?class=text_count.*?>(.*?)<"));

		propertyMatchs.add(new PropertyMatch("followerNumber", "鍚紬.*?class=text_count.*?>(.*?)<"));

		propertyMatchs.add(new PropertyMatch("selfDescription", "class=summary>(.*?)<A"));

		propertyMatchs.add(new PropertyMatch("userName", "nickName = '(.*)'"));
		
		
		pattern.setTaskName(taskName);
		pattern.setPropertyMatches(propertyMatchs);
		task.getSeedsites().get(0).setPattern(pattern);
		CrawlerConfig crawlerConfig=new CrawlerConfig();
		crawlerConfig.getTasks().add(task);
		crawlerConfig.setTempDirectory("D://temp");
		File file=new File("c:\\dataCrawler.xml");
		JAXBContext jaxbContext=JAXBContext.newInstance(CrawlerConfig.class);
		Marshaller marshaller=jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(crawlerConfig, file);
		
		
	}
	//@Test
	public void testXmlUnmarshalling() throws JAXBException{


		File file=new File("c:\\dataCrawler.xml");
		JAXBContext jaxbContext=JAXBContext.newInstance(CrawlerConfig.class);
		Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
	    
		CrawlerConfig crawlerConfig=(CrawlerConfig) unmarshaller.unmarshal(file);
		Assert.assertNotNull(crawlerConfig.getTasks().get(0));
		
		
	}
	//@Test
	public void downloadUrl( ) throws  Exception{
		String downloadUrl="http://zhangmenshiting2.baidu.com/data2/music/931084/931084.mp3?xcode=5f8094cfb46ab33657e43892dd7828bb&mid=0.64891716865346";
		DataRepository dataRepository=(DataRepository) AppUtil.getBeanFromBeanContainer(DataRepository.class);
		URL url=new URL(downloadUrl);
		HttpURLConnection  httpUrlConnection= (HttpURLConnection) url.openConnection();
		InputStream source=httpUrlConnection.getInputStream();
		FileOutputStream out=new FileOutputStream(new File("c:\\test1.mp3"));
		out.write(IOUtils.translantStreamToByte(source));
		out.close();
		source.close();
		
		
		//dataRepository.getMongoOperations().createCollection("media");
		
	}
	@Test
	public void generateXmlForObject(){
		Weibo weibo=new Weibo();
		weibo.setUserName("jasstion");
	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	
	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	

	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	

	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	

	    weibo.getContents().add("dfsfdsdfsdfsdfsdfsf");	
	    CommonUtil.generateXmlForObject(weibo,Weibo.class);
	   

	}
	@Test
	public void calculateSimilartityTest(){
		List<Integer> data1=new ArrayList<Integer>();
		List<Integer> data2=new ArrayList<Integer>();
        int yu=10;
		int length=1000;
		Random random=new Random(500);
		for(int i=0;i<length;i++){
			data1.add( Math.abs(random.nextInt()%yu));
		}
		for(int i=0;i<length;i++){
			data2.add( Math.abs(random.nextInt()%yu));
		}
		for(int i=0;i<length;i++){
			System.out.print(data1.get(i)+" ");
		}
		System.out.print("\n");
		for(int i=0;i<length;i++){
			System.out.print(data2.get(i)+" ");
		}
		System.out.print("\n");
		double similartity=CommonUtil.calculateSimilartity(data1, data2);
		System.out.print(similartity);
		Assert.assertNotSame("", String.valueOf(similartity));

		
	}*/
public static void main(String[] args) throws IOException{
	
	/*String[] strs=new String[12];
	strs[0]="￥";
	strs[1]=".";
	for(int i=2;i<12;i++){
		strs[i]=String.valueOf(i-2);
		
	}
	for(String str:strs){
		BufferedImage bufferImage=new BufferedImage(200, 200, BufferedImage.TYPE_INT_BGR);
		Graphics g=bufferImage.getGraphics();
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, 200, 200);
		g.setColor(new Color(221,0,0));
		g.setFont(new Font(null, Font.PLAIN, 35));
		g.drawString(str,50, 100);
//		bufferImage.getGraphics().dispose();
		ImageIO.write(bufferImage, "png", new File("F:\\picturedemo\\"+str+".png"));

	}*/
	/*
BufferedImage bufferImage= ImageIO.read(new File("F:\\media\\test.png"));
int height=bufferImage.getHeight();
int width=bufferImage.getWidth(); 
int minX=bufferImage.getMinX();
int minY=bufferImage.getMinY();
int rgb8=0;
int[] colorValues=new int[3];
for(int i=minX;i<width;i++){
	for(int j=minY;j<height;j++){
	int pixel=bufferImage.getRGB(i, j);
	colorValues[0]=(pixel & 0Xff0000)>>16;
	colorValues[1]=(pixel & 0Xff00)>>8;
	colorValues[2]=(pixel & 0Xff);


	for(int ii:colorValues){
		System.out.print(ii+"  ,");
	}
	System.out.print("\n");
	//0开头的表示八进制，0X开头的表示十六进制，普通的表示十进制
	//bufferImage.setRGB(i, j,(int)(Math.pow(2, 24)-1));
	}
}
System.out.print("height:"+height+"     "+"width:"+width+"\n");
//ImageIO.write(bufferImage, "jpg", new File("F:\\media\\ddd1.jpg"));

	
	int rgb = getRGB(255, 255, 255);
	System.out.print(rgb+"\n");
	double d = Math.pow(2, 24)-1;
	System.out.print(d);
	System.out.print(rgb==d);*/
	/*BufferedImage bufferImage= ImageIO.read(new File("F:\\media\\test.png"));
//	bufferImage.
	int height=bufferImage.getHeight();
	int width=bufferImage.getWidth(); 
	int minX=bufferImage.getMinX();
	int minY=bufferImage.getMinY();
	
List<Point> points=new ArrayList<Point>();
	for(int i=minX;i<width;i++){
		for(int j=minY;j<height;j++){
		int pixel=bufferImage.getRGB(i, j);
		pixel=getRGB((pixel & 0Xff0000)>>16, (pixel & 0Xff00)>>8, 		(pixel & 0Xff));
		
		
		//System.out.print(pixel+"\n");
		if(pixel!=getRGB(255, 255, 255)){
			int[] a=CommonUtil.getRGBFromPixel(pixel);
			for(int i1:a){
				System.out.print(i1+" ,");
			}
			System.out.print("\n");
			points.add(new Point( i, j));
		}


		}
}
		Collections.sort(points);
		List a=new ArrayList();
		for(Point p:points){
			System.out.print(p.getX()+","+p.getY()+"\n");
			if(!a.contains(p.getX())){
				a.add(p.getX());
			}
		}
		for(Object ia:a){
			System.out.print(ia+"\n");
		}
		System.out.print("*************************");*/

	/*Entity entity=new Task();
	System.out.print(entity.getClass().getSimpleName());
		*/
/*	Collection collection=new ArrayList();
	collection.add("sfsfsdf");
	collection.add("sfsfsdf");
	collection.add("sfsfsdf");
	System.out.print(collection.iterator().next());

	Collection collection1=new ArrayList();
	collection1.add("test");
	
	collection.removeAll(collection);
	collection.addAll(collection1);
	System.out.print(collection.iterator().next());
Task task=new Task();
Annotation[] as = null;
try {
	Field declaredField = task.getClass().getDeclaredField("seedsites");
	as = declaredField.getAnnotations();
} catch (NoSuchFieldException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (SecurityException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
CollectionElementType element1Type=null;
for(Annotation a:as){
	element1Type=(CollectionElementType) a;
}
System.out.print(element1Type.type());
}
public static int getRGB(int red,int green,int blue){
	return (red<<16)+(green<<8)+(blue);
	//
	 
	 it.sauronsoftware.cron4j.Task  infoTask=new InfoExtractorCommonTask(null);
	boolean b = infoTask.getClass().isAssignableFrom(InfoExtractorCommonTask.class);
	System.out.print(b);*/
	
	//Hibernate-51cto博客圈
	//3370 3367
	InfoExtractorDao infoExtractorDao=(InfoExtractorDao) AppUtil.getBeanFromBeanContainer(InfoExtractorDao.class);
	List entitys=null;
	
	//where("title").is("Hibernate-51cto博客圈").
	//Criteria c1=new Criteria();

	//c1.where("score").is(3370);
	//c.andOperator(c1);
	
	
	AppUtil.startMongodb();
	System.exit(0);
	//query.getQueryObject().put("score","3370" );
//	Task task=infoExtractorDao.getDataRepositoryI().queryEntity("taskName", "jkhsdf", Task.class);
	/*entitys= infoExtractorDao.getDataRepositoryI().getMongoOperations().find(query(where("title").is("Hibernate-51cto博客圈")
            .and("score").is(3370).and("findTime").gt(task.getStartedTime())), News.class);	
	System.out.print(entitys.size());*/
	
}
}
