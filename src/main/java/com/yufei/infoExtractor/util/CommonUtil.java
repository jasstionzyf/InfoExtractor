package com.yufei.infoExtractor.util;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.core.InfoExtractorConfig;
import com.yufei.infoExtractor.io.AbstractHttpDataRetriever;
import com.yufei.infoExtractor.io.DataRetriever;
import com.yufei.infoExtractor.io.DataRetrieverFactory;
import com.yufei.infoExtractor.io.DataRetrieverFeatures;
import com.yufei.infoExtractor.pfw.entity.Entity;
import com.yufei.infoExtractor.pfw.entity.GoodsCategory;
import com.yufei.infoExtractor.pfw.entity.PropertyMatch;
import com.yufei.infoExtractor.pfw.entity.Relatedlink;
import com.yufei.infoExtractor.pfw.mongodb.DataRepository;
import com.yufei.infoExtractor.util.sequence.SequenceUUID;

/**
 * @author zhao-0244@qq.com to do some common function so they should be static
 *         methods is thread safe and common used
 */
public class CommonUtil{
	private static Log mLog = LogFactory.getLog(CommonUtil.class);

	// 扩展词典新增词元
	public synchronized static void addExtTerm(String term) {

	}

	public static void setPropertyForEntity(Entity entity, Object value,
			String propertyName) {

		try {
			//
			BeanUtils.setProperty(entity, propertyName, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mLog.info(e.getMessage());
		}
	}



	public static boolean isMediaType(String fieldName) {
		/*
		 * if(obj instanceof Collection){ return true; } else{ return false; }
		 */
		if (fieldName.endsWith("Media")) {
			return true;

		} else
			return false;

	}

	public static String getStringFromFile(File file) {
		String str = null;

		byte[] bytes;

		if (file != null) {
			try {
				bytes = IOUtils
						.translantStreamToByte(new FileInputStream(file));
				str = IOUtils.translantByteStreamToString(bytes, "utf-8");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				mLog.debug(file.getAbsoluteFile() + ": not found!");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				mLog.debug(e.getMessage());
			}
		}

		return str;

	}

	// 获取此类以及子类的所有方法
	public static Method getMethod(Class obj, String methodName) {
		Method[] methods = obj.getMethods();
		for (Method method1 : methods) {
			if (method1.getName().equals(methodName)) {

				return method1;

			}
		}
		return null;
	}

	public static String getBaseUrl(String urlStr) {
		urlStr = CommonUtil.formatUrl(urlStr);
		String baseUrl = null;
		if (urlStr.startsWith("http")) {
			if (urlStr.contains("?")) {
				baseUrl = urlStr.substring(0, urlStr.indexOf("?"));
			}

		}
		return baseUrl;

	}

	public static int getCharNumber(char ch, String sourceString) {
		int charNumber = 0;
		char[] charArray = sourceString.toCharArray();
		for (char ch1 : charArray) {
			if (ch1 == ch) {
				charNumber++;
			}
		}
		return charNumber;

	}

	// 替换制定位置的某个字符
	public static String replaceByIndex(String sourceStr, String targetString,
			String[] values) {
		StringBuffer stringBuffer = new StringBuffer();
		String[] strs = sourceStr.split(targetString);
		int position = 0;
		for (String str : strs) {
			if (position < values.length) {
				stringBuffer.append(str).append(values[position]);
			} else {
				stringBuffer.append(str);
			}
			position += 1;
		}

		return stringBuffer.toString();
	}

	// 截取某个特定的字符串更具某个字符出现的位置
	/**
	 * @param ch
	 *            :截取子字符串要参考的字符
	 * @param position
	 *            ：字符第几次出现
	 * @param sourceString
	 * @return 目标字符串中的以零开始，position表示的位置的子字符串
	 */
	public static String subString(char ch, int position, String sourceString) {
		StringBuffer buffer = new StringBuffer();
		int tempPosition = 0;
		char[] chars = sourceString.toCharArray();
		for (char ch1 : chars) {
			if (tempPosition == position) {
				break;
			}
			buffer.append(ch1);
			if (ch1 == ch) {
				tempPosition++;
			}
		}
		return buffer.toString();
	}

	/**
	 * @param url
	 *            ：需要处理的url
	 * @param upUrl
	 *            :需要处理的url所在html页面的链接地址
	 * @return：经过处理过的完整的url
	 */
	public static String normalizeUrl(String url, String upUrl) {
		url = CommonUtil.formatUrl(url);
		upUrl = CommonUtil.formatUrl(upUrl);

		String returnUrl = null;
		if (url.startsWith("http")) {
			returnUrl = url;

		} else {
			if (url.startsWith("/") || url.startsWith("../")) {

				if (CommonUtil.getCharNumber('/', upUrl) <= 3) {
					if (upUrl.contains("?")) {
						returnUrl = upUrl.substring(0, upUrl.lastIndexOf('?'))
								+ url;

					} else {
						if (CommonUtil.getCharNumber('/', upUrl) < 3) {

							returnUrl = upUrl + "/" + url;

						} else {
							returnUrl = upUrl + url;
						}
					}

				}

				else {
					url = url.replace("../", "");
					url = url.replaceFirst("/", "");
					returnUrl = CommonUtil.subString('/', 3, upUrl) + url;
				}

			}

			else {
				returnUrl = upUrl + url;
			}

		}//

		return returnUrl;

	}

	public static String formatUrl(String url) {
		String[] filterdStrs = { "amp;", " " };
		for (String str : filterdStrs) {
			url = url.replace(str, "");
		}
		return url;

	}

	public static String getHtmlContent(String url) throws IOException {
		String htmlStr = null;
		DataRetriever dataRetriever = DataRetrieverFactory
				.createDataRetriever(new DataRetrieverFeatures(true, null));
		dataRetriever.setUrl(new URL(url));
		dataRetriever.connect();
		htmlStr = ((AbstractHttpDataRetriever) dataRetriever).getHtmlContent();
		dataRetriever.disconnect();

		return htmlStr;

	}

	public static String getHtmlContent(Map context, String url)
			throws IOException {
		String protocolType = (String) context.get("protocolType");
		Boolean requestExecuteJs = (Boolean) context.get("requestExecuteJs");

		String htmlStr = null;
		DataRetriever dataRetriever = DataRetrieverFactory
				.createDataRetriever(new DataRetrieverFeatures(true, protocolType));
	/*	DataRetriever dataRetriever = (DataRetriever) context
				.get("dataRetriever");
		if (dataRetriever == null) {
			throw new RuntimeException(
					"dataRetriever not set in current context!");
		}*/
		dataRetriever.setUrl(new URL(url));
		dataRetriever.connect();
		htmlStr = ((AbstractHttpDataRetriever) dataRetriever).getHtmlContent();
		context.put("redirectUrl", dataRetriever.getUrl().toString());
		dataRetriever.disconnect();

		return htmlStr;

	}

	public static String getHtmlContent(Map context) throws IOException {
		Relatedlink link = (Relatedlink) context.get("currentLink");
		String protocolType = (String) context.get("protocolType");
		Boolean requestExecuteJs = (Boolean) context.get("requestExecuteJs");

		String htmlStr = null;

		DataRetriever dataRetriever = (DataRetriever) context
				.get("dataRetriever");
		if (dataRetriever == null) {
			throw new RuntimeException(
					"dataRetriever not set in current context!");
		}
		dataRetriever.setUrl(new URL(link.getLink()));
		dataRetriever.connect();
		htmlStr = ((AbstractHttpDataRetriever) dataRetriever).getHtmlContent();
		dataRetriever.disconnect();

		return htmlStr;

	}

	public static String upFirstChar(String str) {

		char temp = str.charAt(0);
		temp = Character.toUpperCase(temp);
		str = str.replaceFirst(String.valueOf(str.charAt(0)),
				String.valueOf(temp));
		return str;

	}

	public static String downFirstChar(String str) {

		char temp = str.charAt(0);
		temp = Character.toLowerCase(temp);
		str = str.replaceFirst(String.valueOf(str.charAt(0)),
				String.valueOf(temp));
		return str;

	}

	public static String cleanHtml(String about) {
		// TODO Auto-generated method stub
		// 禁止取出换行，制表符以及空格等
		String regexs[] = { "\"", "\'", "<.*?>", "'", "<", ">", "nbsp",
				"[\\s\\r\\n]{1,}" };
		Pattern p = null;
		Matcher match = null;
		for (String regex : regexs) {
			p = Pattern.compile(regex, Pattern.DOTALL);
			match = p.matcher(about);
			if (match.find()) {
				about = match.replaceAll("");
			}
		}
		return about.trim();
		// return about.replaceAll("<.*?>", "");
	}

	public static boolean isValidUrl(String url) {
		// TODO Auto-generated method stub
		/*
		 * if(!url.contains("?")&&!url.startsWith("http")){ return false; }
		 */
		if (url.endsWith(".js") || url.endsWith(".css")) {
			return false;
		}
		return true;
	}

	public static List<String> convertToList(String[] strs) {
		List list = new ArrayList<String>();
		for (String str : strs) {
			list.add(str);
		}
		return list;
	}

	// remove file or remove directory
	public static void deleteFile(File file) {
		if (file.isFile()) {
			file.delete();
		}
		if (file.isDirectory()) {
			// first delete all the sub files
			for (File file1 : file.listFiles()) {
				deleteFile(file1);

			}
			// at last delete directory
			file.delete();
		}
	}

	// remove all the files related with svn
	public static void cleanSvnFolder(String directory) {
		File file = new File(directory);
		File[] files = file.listFiles();
		for (File tempFile : files) {
			if (tempFile.isDirectory()) {
				if (tempFile.getName().endsWith(".svn")) {
					CommonUtil.deleteFile(tempFile);
				} else {
					cleanSvnFolder(tempFile.getAbsolutePath() + "\\");
				}

			}

		}
	}

	// 将java文件中的所有特定字符串替换成指定的字符串
	@Deprecated
	public static void batchReplaceTextUnderFile(String directory)
			throws Exception, Exception {
		final String[] acceptFileNames = { ".java" };
		File file = new File(directory);
		File[] files = file.listFiles();
		for (File tempFile : files) {
			if (tempFile.isDirectory()) {
				batchReplaceTextUnderFile(tempFile.getAbsolutePath() + "\\");

			} else {
				if (tempFile.getName().endsWith(".java")) {
					String className = null;
					File file1 = tempFile;
					String classFullName = tempFile.getName();
					className = classFullName.substring(0,
							classFullName.indexOf("."));
					file1 = new File(directory + classFullName);
					String fileContent = IOUtils.translantByteStreamToString(
							IOUtils.translantStreamToByte(new FileInputStream(
									file1)), "utf-8");
					Pattern pattern = Pattern.compile(
							"public[\\s]{1,}enum[\\s]{1,}(.*?)[\\s]{1,}\\{",
							Pattern.CASE_INSENSITIVE);
					List<Pattern> patterns = new ArrayList<Pattern>();
					patterns.add(pattern);
					List<String> classNames = new ArrayList<String>();
					classNames = PatternUtils.getListStrByPattern(patterns,
							fileContent);
					for (String className1 : classNames) {
						String regex = "public [\\s]{0,}" + className1;

						fileContent = fileContent.replaceAll(regex, "private "
								+ className1);
					}

					file.delete();
					FileOutputStream ref = new FileOutputStream(directory
							+ classFullName);
					ref.write(fileContent.getBytes());
				}
			}
		}

	}

	public static void generateXmlForObject(Object obj, Class cla) {
		String tempDirectory = AppUtil.getInfoExtractorConfig()
				.getTempDirectory();
		String fileName = cla.getName() + System.currentTimeMillis() + ".xml";

		File file = new File(tempDirectory + fileName);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(cla);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(obj, file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}
	public  static Object getObjectFromXml(InputStream in,Class cla) {

		Object obj=null;
		//URL resource=Thread.currentThread().getContextClassLoader().getResource(AppUtil.defaultCrawlerDateFilePath);//new ClassPathResource(AppUtil.defaultCrawlerDateFilePath);
	//	InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(AppUtil.defaultCrawlerDateFilePath);
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(cla);
			Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
		    obj= unmarshaller.unmarshal(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mLog.error("reading Xml wrong!");
			throw new RuntimeException(e);
		} 
		
		return obj;

	}
	public static String native2Ascii(String str) {

		char[] chars = str.toCharArray();

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < chars.length; i++) {

			sb.append(char2Ascii(chars[i]));

		}

		return sb.toString();

	}

	static String PREFIX = "\\u";

	private static String char2Ascii(char c) {

		if (c > 255) {

			StringBuilder sb = new StringBuilder();

			sb.append(PREFIX);

			int code = (c >> 8);

			String tmp = Integer.toHexString(code);

			if (tmp.length() == 1) {

				sb.append("0");

			}

			sb.append(tmp);

			code = (c & 0xFF);

			tmp = Integer.toHexString(code);

			if (tmp.length() == 1) {

				sb.append("0");

			}

			sb.append(tmp);

			return sb.toString();

		} else {

			return Character.toString(c);

		}

	}

	public static String getStrFromInputStream(InputStream inputStream,
			String defaultEncoding) {
		if (inputStream == null) {
			return null;
		}
		return IOUtils.translantByteStreamToString(
				IOUtils.translantStreamToByte(inputStream), defaultEncoding);

	}

	public static void main(String[] args) throws Exception, IOException {
		/*
		 * try { CommonUtil.batchReplaceTextUnderFile("E:\\data\\java\\");
		 * //("D:\\work\\crawler-maven\\src\\main\\java\\com\\rzx\\crawler\\core\\"
		 * );// //CommonUtil.cleanSvnFolder("E:\\data\\java\\"); } catch
		 * (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * File file1=new File("D:\\acsii.txt"); String
		 * fileContent=IOUtils.translantByteStreamToString
		 * (IOUtils.translantStreamToByte(new FileInputStream(file1)),"utf-8");
		 * fileContent=CommonUtil.ascii2Native(fileContent);
		 * 
		 * file1.delete(); FileOutputStream ref=new
		 * FileOutputStream("D:\\acsii.txt"); ref.write(fileContent.getBytes());
		 * //test the CountDownLatch demo how to coordinate different threads
		 * final Weibo weibo=new Weibo(); weibo.setName("sdfsfd"); final
		 * CountDownLatch count1=new CountDownLatch(5); final CountDownLatch
		 * count2=new CountDownLatch(5); final Thread thread1=new Thread(new
		 * Runnable() {
		 * 
		 * @Override public void run() { try { count1.await(); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block throw
		 * new RuntimeException(e); } // TODO Auto-generated method stub for(int
		 * i=0;i<8;i++){ System.out.print(i+"\n"); } weibo.setName("jasstion");
		 * System.out.print(weibo.getName()); } }); Thread thread2=new
		 * Thread(new Runnable() {
		 * 
		 * @Override public void run() { try { count2.await(); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block throw
		 * new RuntimeException(e); } try { thread1.join(); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block throw
		 * new RuntimeException(e); } // TODO Auto-generated method stub for(int
		 * i=9;i<17;i++){ System.out.print(i+"\n"); } } }); Thread thread3=new
		 * Thread(new Runnable() {
		 * 
		 * @Override public void run() { /*try { thread1.join(); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block throw
		 * new RuntimeException(e); } // TODO Auto-generated method stub
		 * System.out.print("this text should be the last print!"+"\n"); int
		 * i=5; while(i!=0){ i--; count1.countDown();
		 * 
		 * count2.countDown(); }
		 * 
		 * 
		 * } }); thread2.start(); thread1.start(); thread3.start();
		 */

		/*
		 * //test the httpClient to deal with the redirect DefaultHttpClient
		 * httpclient = new DefaultHttpClient(); HttpContext localContext = new
		 * BasicHttpContext(); HttpGet httpget = new
		 * HttpGet("http://bj.58.com/zpshengchankaifa/9489314658442x.shtml");
		 * HttpResponse response = httpclient.execute(httpget, localContext);
		 * HttpHost target = (HttpHost) localContext.getAttribute(
		 * ExecutionContext.HTTP_TARGET_HOST); HttpUriRequest req =
		 * (HttpUriRequest) localContext.getAttribute(
		 * ExecutionContext.HTTP_REQUEST); System.out.println("Target host: " +
		 * target); System.out.println("Final request URI: " + req.getURI()); //
		 * relative URI (no proxy used)
		 * System.out.println("Final request method: " + req.getMethod());
		 * System
		 * .out.print(CommonUtil.getStrFromInputStream(response.getEntity()
		 * .getContent(), "utf-8")); String str=CommonUtil.getStringFromFile(new
		 * File("src/test/resources/html/qqweiboTest.html")); //换行符
		 * str=CommonUtil.removeJSAndCss(str);
		 * str=CommonUtil.removeChineseChar(str);
		 * 
		 * 
		 * String str1=CommonUtil.getStringFromFile(new
		 * File("src/test/resources/html/links.html")); //换行符
		 * str1=CommonUtil.removeJSAndCss(str1);
		 * str1=CommonUtil.removeChineseChar(str1); Map<String,Integer>
		 * fq_str=new HashMap<String, Integer>(), fq_str1=new HashMap<String,
		 * Integer>(); StringTokenizer str_tokenizer=new
		 * StringTokenizer(str," "); StringTokenizer str1_tokenizer=new
		 * StringTokenizer(str1," "); String temp=null;
		 * while(str_tokenizer.hasMoreTokens()){ temp=str_tokenizer.nextToken();
		 * System.out.print(temp+"\n"); if(!fq_str.containsKey(temp)){
		 * fq_str.put(temp, 1); } else{ fq_str.put(temp, fq_str.get(temp)+1); }
		 * 
		 * 
		 * 
		 * } Iterator<String> iterator=fq_str.keySet().iterator();
		 * while(iterator.hasNext()){ fq_str1.put(iterator.next(), 0); }
		 * while(str1_tokenizer.hasMoreTokens()){
		 * temp=str1_tokenizer.nextToken(); System.out.print(temp+"\n");
		 * if(fq_str1.containsKey(temp)){ fq_str1.put(temp,
		 * fq_str1.get(temp)+1); } } List<Integer> data_str=new
		 * ArrayList(fq_str.values()); List<Integer> data_str1=new
		 * ArrayList(fq_str1.values());
		 * System.out.print(CommonUtil.calculateSimilartity(data_str,
		 * data_str1));
		 */
		// 测试判断网页链接相似度
		/*
		 * String url1="http://t.nuomi.com/beijing-25-0-0-0",
		 * url4="http://www.nuomi.com/youhui/chuantong/beijing/",
		 * url5="http://t.nuomi.com/deal/elong7995.html?p=022-2",
		 * url6="http://t.nuomi.com/beijing-0-0-0-0/1",
		 * url2="http://t.nuomi.com/beijing-0-0-0-0/2",//目标
		 * url3="http://t.nuomi.com/deal/elong20377.html?p=022-52";//目标
		 * System.out.print(CommonUtil.calculateSimilartity(((List[])CommonUtil.
		 * getVectorofString
		 * (url1,url2))[0],((List[])CommonUtil.getVectorofString
		 * (url1,url2))[1])+"\n");
		 * System.out.print(CommonUtil.calculateSimilartity
		 * (((List[])CommonUtil.getVectorofString
		 * (url1,url3))[0],((List[])CommonUtil
		 * .getVectorofString(url1,url3))[1])+"\n");
		 * 
		 * System.out.print(CommonUtil.calculateSimilartity(((List[])CommonUtil.
		 * getVectorofString
		 * (url4,url2))[0],((List[])CommonUtil.getVectorofString
		 * (url4,url2))[1])+"\n");
		 * System.out.print(CommonUtil.calculateSimilartity
		 * (((List[])CommonUtil.getVectorofString
		 * (url4,url3))[0],((List[])CommonUtil
		 * .getVectorofString(url4,url3))[1])+"\n");
		 * System.out.print(CommonUtil.
		 * calculateSimilartity(((List[])CommonUtil.getVectorofString
		 * (url5,url2))
		 * [0],((List[])CommonUtil.getVectorofString(url5,url2))[1])+"\n");
		 * System.out.print(CommonUtil.calculateSimilartity(((List[])CommonUtil.
		 * getVectorofString
		 * (url5,url3))[0],((List[])CommonUtil.getVectorofString
		 * (url5,url3))[1])+"\n");
		 * System.out.print(CommonUtil.calculateSimilartity
		 * (((List[])CommonUtil.getVectorofString
		 * (url6,url2))[0],((List[])CommonUtil
		 * .getVectorofString(url6,url2))[1])+"\n");
		 * System.out.print(CommonUtil.
		 * calculateSimilartity(((List[])CommonUtil.getVectorofString
		 * (url6,url3))
		 * [0],((List[])CommonUtil.getVectorofString(url6,url3))[1])+"\n");
		 */
		// 经错测试0.95比较好
		/*
		 * String url1="http://t.qq.com/zhaoyufei",
		 * url4="http://t.qq.com/you86886/follower",
		 * url5="http://t.qq.com/home_userinfo.php?u=you86886", url6=
		 * "http://t.qq.com/vqu_cc?mode=0&amp;id=60901117445464&amp;pi=3&amp;time=1343931063"
		 * , url2="http://t.qq.com/zhao-0244";//目标
		 * System.out.print(CommonUtil.calculateSimilartity
		 * (((List[])CommonUtil.getVectorofString
		 * (url1,url2))[0],((List[])CommonUtil
		 * .getVectorofString(url1,url2))[1])+"\n");
		 * 
		 * System.out.print(CommonUtil.calculateSimilartity(((List[])CommonUtil.
		 * getVectorofString
		 * (url4,url2))[0],((List[])CommonUtil.getVectorofString
		 * (url4,url2))[1])+"\n");
		 * System.out.print(CommonUtil.calculateSimilartity
		 * (((List[])CommonUtil.getVectorofString
		 * (url5,url2))[0],((List[])CommonUtil
		 * .getVectorofString(url5,url2))[1])+"\n");
		 * System.out.print(CommonUtil.
		 * calculateSimilartity(((List[])CommonUtil.getVectorofString
		 * (url6,url2))
		 * [0],((List[])CommonUtil.getVectorofString(url6,url2))[1])+"\n");
		 * //经错测试0.95比较好
		 */

		/*
		 * String
		 * str="http://nuomi.xnimg.cn/upload/deal/2012/08/V_L/89811-1.jpg";
		 * System.out.print(str.substring(str.lastIndexOf(".")));
		 * CommonUtil.setPropertyForEntity(weibo , "test", "sign"); String
		 * url="http://t.nuomi.com/beijin"
		 * ,url1="http:/hotel/detail/853858.html";
		 * System.out.print(CommonUtil.normalizeUrl(url1, url));
		 * CommonUtil.dataBaseRefresh(); DataRepository
		 * dataRepository=(DataRepository)
		 * AppUtil.getBeanFromBeanContainer(DataRepository.class);
		 * 
		 * 
		 * Map queryObject=new HashMap();
		 * 
		 * 
		 * 
		 * List<Task>
		 * list1=dataRepository.getMongoOperations().findAll(Task.class);
		 * for(Task task:list1){
		 * 
		 * System.out.print(task.toString()+"\n");
		 * 
		 * 
		 * } //验证反射机制调用方法的时候如何处理方法内部跑出的异常 /*Weibo weibo=new Weibo();
		 * 
		 * 
		 * String md5 =
		 * EncryptUtil.md5("http://nuomi.xnimg.cn/upload/deal/2012/08/V_L/89811-1"
		 * ); System.out.print(md5);
		 * 
		 * //GZIPOutputStream gzipOutputStream=new GZIPOutputStream();
		 */
		/*
		 * String chinese=
		 * "这两个向量形式就是用TFIDF算出来的，很容易看出两篇文章不是同一篇，为什么？因为他们的特征权重根本不一样，所以说权重代表的是差别，而不是优劣"
		 * ; Map<String,Integer> map=CommonUtil.getVectorofString(chinese);
		 * 
		 * String temp=null; List<Map.Entry<String, Integer>> entries=new
		 * ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		 * Collections.sort(entries,new Comparator<Entry<String, Integer>>() {
		 * 
		 * @Override public int compare(Entry<String, Integer> o1, Entry<String,
		 * Integer> o2) { // TODO Auto-generated method stub
		 * if(o1.getValue()-o2.getValue()>0){ return -1;
		 * 
		 * } return 1; } }); Iterator<String> iterator=map.keySet().iterator();
		 * 
		 * while(iterator.hasNext()){ temp=iterator.next();
		 * System.out.print(temp+"出现的次数是："+map.get(temp)+"\n");
		 * 
		 * } for(Entry entry:entries){
		 * System.out.print(entry.getKey()+" 出现的次数是："+entry.getValue()+"\n"); }
		 */
		// 测试replaceByIndex
		String[] strs = { "o", "u", "i" }, str1 = { "s", "s" };
		String test = "zha{}y{}fe{}";
		/*
		 * PaginationRule paginationRule=new PaginationRule();
		 * paginationRule.setPaginationTemplate(test); List<UrlParameter>
		 * list=new ArrayList<UrlParameter>(); list.add(new UrlParameter(2,
		 * "String", "s,s")); list.add(new UrlParameter(1, "String", "o,u,i"));
		 * 
		 * 
		 * list.add(new UrlParameter(3, "Integer", "1"));
		 * 
		 * paginationRule.setUrlParameters(list); List<String>
		 * uls=CommonUtil.generateUrlsByPaginationRule(paginationRule);
		 * for(String url:uls){ System.out.print(url+"\n"); }
		 */
		// Thread.currentThread().sleep(10000);
		// test.split("{}");

		/*
		 * List<String[]> list = new ArrayList(); String[] str1 = { "1", "2" },
		 * str2 = { "100", "198", "100", "198", "100", "198", "100", "198" },
		 * str3 = { "1", "2" }, str4 = { "100", "198", "100", "198", "100",
		 * "198", "100", "198" }; list.add(str1); list.add(str2);
		 * list.add(str3); list.add(str4); List<String[]> listStr =
		 * CommonUtil.getDKL(list); for (String[] strs1 : listStr) { for (String
		 * str : strs1) { System.out.print(str+"\n");
		 * 
		 * } System.out.print(strs1.length+"\n");
		 * 
		 * } System.out.print(listStr.size() + "\n");
		 * 
		 * //exception 测试 try{ throw new RuntimeException("this is a test!");
		 * }catch(Exception e){ e.printStackTrace(); mLog.info(e.getMessage());
		 * mLog.info(ExceptionUtil.getExceptionDetailsMessage(e)); }
		 */

		/*
		 * String dianying=
		 * "[方庄] 博纳国际影城仅售26元，价值100元方庄店单人观影票1张！2D/3D通兑，不限时段，国内外大片一网打尽，全场劲爆通兑，尽享五星级豪华影城。博纳国际影城(方庄店)"
		 * ; String jiudian="仅售178元，价值268元的主题房套餐！主题房1晚+免费宽带（房间均配有台式电脑）+免费停车！";
		 * String test1="仅售90元，价值120元的北京奥体中心店4小时房套餐！4小时商务间B+免费宽带+免费停车+免费拨打市话！";
		 * StringBuffer targetStr=new StringBuffer(); DataRepository
		 * dataRepository=(DataRepository)
		 * AppUtil.getBeanFromBeanContainer(DataRepository.class);
		 */

		/*
		 * Map queryObject=new HashMap(); List<Hotel>
		 * list=dataRepository.getMongoOperations().findAll(Hotel.class); int
		 * num=0; List<String> demoTexts=new ArrayList(); for(Hotel hotel:list){
		 * if
		 * (hotel.getDescripton()!=null&&!hotel.getDescripton().trim().equals(""
		 * )){ demoTexts.add(hotel.getDescripton()+hotel.getArea().replaceAll(
		 * "[\\n\\s\\r]{0,}", ""));
		 * //mLog.info(hotel.getDescripton()+hotel.getArea()+"\n"); } }
		 * Map<String,Double> map=CommonUtil.getEigenvector(demoTexts);
		 * mLog.info("抽取的字符特征信息如下：\n"); Iterator<String>
		 * it=map.keySet().iterator(); while (it.hasNext()) { String
		 * key=it.next(); mLog.info(key+": 出现的次数为"+map.get(key)); for(int
		 * i=0;i<Math.rint(map.get(key));i++){ targetStr.append(key); }
		 * 
		 * }
		 */

		// 生城targetStr

		// System.out.print(CommonUtil.calculateSimilartity(CommonUtil.getVectorofString(dianying,
		// test1)[0], CommonUtil.getVectorofString(dianying, test1)[1])+"\n");
		// System.out.print(CommonUtil.calculateSimilartity(CommonUtil.getVectorofString(jiudian,
		// test1)[0], CommonUtil.getVectorofString(jiudian, test1)[1])+"\n");
		// String cat1="mt>[\S\n\s\r]{0,}?<H2>(.*)</H2>";
		/*
		 * CommonUtil.extractCategoryInfo(); List<GoodsCategory>
		 * goodsCategories=
		 * dataRepository.getMongoOperations().findAll(GoodsCategory.class);
		 * //CommonUtil.generateXmlForObject(goodsCategories, cla);
		 * GoodsCategory goodCategoryTopList=new GoodsCategory();
		 * List<GoodsCategory> topCategories=(List<GoodsCategory>)
		 * dataRepository.queryListEntity("parentCategoryId", "0",
		 * GoodsCategory.class); List<GoodsCategory> level1Categories=null;
		 * List<GoodsCategory> level2Categories=null; for(GoodsCategory
		 * goodC:topCategories){ level1Categories=(List<GoodsCategory>)
		 * dataRepository.queryListEntity("parentCategoryId",
		 * goodC.getCategoryId(), GoodsCategory.class);
		 * 
		 * for(GoodsCategory goodC1:level1Categories){
		 * level2Categories=(List<GoodsCategory>)
		 * dataRepository.queryListEntity("parentCategoryId",
		 * goodC1.getCategoryId(), GoodsCategory.class);
		 * goodC1.setSubCategories(level2Categories); }
		 * goodC.setSubCategories(level1Categories); }
		 * 
		 * goodCategoryTopList.setSubCategories(topCategories);
		 * 
		 * CommonUtil.generateXmlForObject(goodCategoryTopList,
		 * GoodsCategory.class); /* 503c8ed32ff6e2c8ee58c970
		 * 503c8ed42ff6e2c8ee58c971 DataRepository
		 * dataRepository=(DataRepository)
		 * AppUtil.getBeanFromBeanContainer(DataRepository.class); GoodsCategory
		 * entity=new GoodsCategory(); entity.setId("503c8ed32ff6e2c8ee58c970");
		 * dataRepository.removeEntity(entity);
		 * entity.setId("503c8ed42ff6e2c8ee58c971");
		 * dataRepository.removeEntity(entity); GoodsCategory
		 * entity1=(GoodsCategory) dataRepository.queryEntity("categoryId",
		 * "6c8dec307f00000101d132722c88fe45", GoodsCategory.class);
		 */

		/*
		 * //dataRepository.updateEntity("503c8ed42ff6e2c8ee58c971",
		 * GoodsCategory.class, "name", "大家电"); DataRepository
		 * dataRepository=(DataRepository)
		 * AppUtil.getBeanFromBeanContainer(DataRepository.class);
		 * 
		 * List<GoodsCategory>
		 * goodsCategories=dataRepository.getMongoOperations(
		 * ).findAll(GoodsCategory.class);
		 * 
		 * for(GoodsCategory good:goodsCategories){
		 * System.out.print(good.toString()+"\n"); }
		 * System.out.print(goodsCategories.size());
		 */
		/*
		 * DataRepository dataRepository=(DataRepository)
		 * AppUtil.getBeanFromBeanContainer(DataRepository.class);
		 * 
		 * Parser parser=new Parser(); parser.setInputHTML(currentHtmlContent);
		 * NodeFilter filter =new NodeClassFilter(Div.class);//过滤器 NodeList list
		 * = parser.extractAllNodesThatMatch(filter);//过滤所有链接 Div div=null; //m
		 * mt mc for(Node node:list.toNodeArray()){ div=(Div)node;
		 * if(div.getAttribute("class")==null){ continue; }
		 * if(div.getAttribute("class").equals("m")){ GoodsCategory
		 * goodsCategory1=null; for(Node node1:div.getChildren().toNodeArray()){
		 * if(((Div)node1).getAttribute("class").equals("mt")){ goodsCategory1
		 * =new GoodsCategory(); String name1 =
		 * CommonUtil.cleanHtml(node1.getText()); System.out.print(name1);
		 * goodsCategory1.setName(name1); dataRepository.save(goodsCategory1);
		 * goodsCategory1=(GoodsCategory) dataRepository.queryEntity("name",
		 * name1, GoodsCategory.class);
		 * 
		 * }//处理一个category1
		 * if(((Div)node1).getAttributeEx("class").equals("mc")){
		 * 
		 * for(Node node2:node1.getChildren().toNodeArray()){
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * }//
		 * 
		 * 
		 * 
		 * }
		 */
		/*
		 * List list1=null,list2=new ArrayList(); list2.add("sdfsf");
		 * System.out.print(CommonUtil.isEmptyOrNull(list1));
		 * System.out.print(CommonUtil.isEmptyOrNull(list2));
		 */
		// CommonUtil.cleanSvnFolder("D:\\infoExtractor-maven\\src\\test");
		// D:\infoExtractor-maven\src\test
		// Field field=Task.class.getDeclaredField("pattern");
		// System.out.print(CommonUtil.isCustomerObjType(field));
		/*
		 * Date date=new Date(); String str="sdfsf";
		 * System.out.print(((Object)str).toString());
		
		List<Seedsite> seedsites = new ArrayList<Seedsite>();
		List<Class<?>> interfaces = CommonUtil.getALlInterfaces(seedsites
				.getClass());
		for (Class cla : interfaces) {
			System.out.print(cla.getName() + "\n");
		}
		System.out.print(CommonUtil.isCollection(Task.class)); */
	/*	 DataRepository dataRepository=(DataRepository)AppUtil.getBeanFromBeanContainer(DataRepository.class);
		 News testNews=dataRepository.queryEntity("id", "508800721ad52a232e9975d3", News.class);
		 //System.out.print(testNews.getTitle()+"\n");
		//System.out.print(testNews.getContent()+"\n");
 //System.out.print(CommonUtil.cleanHtml(testNews.getContent()));
  Parser parser=new Parser(); 
  String currentHtmlContent=testNews.getContent();
  parser.setInputHTML(currentHtmlContent);
  NodeIterator elements=parser.elements();
  CustomerHtmlNode rootCustomerMode=new CustomerHtmlNode();
HtmlUtil.createCustomerHtmlNode(elements.nextNode(),rootCustomerMode, 0);
CustomerHtmlNode maxSoreCustomerHtmlNode=rootCustomerMode.getCustomerHtmlNodeWithMaxScore();//HtmlUtil.getCustomerHtmlNodeByDepthAndOrderNum(rootCustomerMode,0,0);
//System.out.print(rootCustomerMode.getHtmlContent()+"\n");
System.out.print(maxSoreCustomerHtmlNode.getHtmlContent()+"\n");
System.out.print(maxSoreCustomerHtmlNode.getContentSimilarityScore()+"\n");*/
Map map=new HashMap();
boolean result=false;
map.put("result", result);
result=true;
System.out.print(map.get("result"));


/*
System.out.print(rootCustomerMode.getSubCustomerHtmlNode().get(0).getSubCustomerHtmlNode().get(0).getHtmlSegmentStatistics().getPercentageOfContent());
System.out.print(rootCustomerMode.getSubCustomerHtmlNode().get(0).getSubCustomerHtmlNode().get(0).getHtmlContent());*/

}
	
 /* String removeJSAndCss = HtmlUtil.removeJSAndCss(currentHtmlContent);
  System.out.print("***************************************");
  System.out.print(HtmlUtil.removeHtmlTag(removeJSAndCss));
*/


		
	
	
/**
 * @param htmlContent
 * @return
 * 计算出一个html网页中内容在整个网页中的百分比
 */
public double getPercentageOfReallyCInHtml(String htmlContent){
	double  percentage = 0;
	int totalCount=0,contentCount=0;
	totalCount=htmlContent.length();
	htmlContent=htmlContent.replaceAll("<.*>", "");
	
	return percentage;
}
	public static PropertyMatch getPropertyMatchFromListByPropertyName(String fieldName,
List<PropertyMatch> propertyMatchs) {
	PropertyMatch propertyMatch =null;
	for(PropertyMatch pro:propertyMatchs){
		if(pro.getProperty().equalsIgnoreCase(fieldName)){
			propertyMatch=pro;
			break;
		}
	}
	return propertyMatch;
}

	/**
	 * 用于从网页中抽取某个网站的分类（比如：京东商城网站的商品分类，某论坛的文章分类等等） 目前仅仅支持三级目分类目录
	 */
	private static void extractCategoryInfo() {
		DataRepository dataRepository = (DataRepository) AppUtil
				.getBeanFromBeanContainer(DataRepository.class);
		dataRepository.getMongoOperations().dropCollection(GoodsCategory.class);
		String currentHtmlContent = CommonUtil.getStringFromFile(new File(
				"src/test/resources/html/jingdongcategory.html"));
		currentHtmlContent = CommonUtil.cleanHtml(currentHtmlContent);
		String[] records = currentHtmlContent.split("[\\s\\r\\n]{1,}");
		GoodsCategory goodsCategory1 = null;
		GoodsCategory goodsCategory2 = null;
		GoodsCategory goodsCategory3 = null;
		String goodCategoryId1 = null, goodCategoryId2 = null;
		// 小心此操作
		// dataRepository.getMongoOperations().dropCollection(GoodsCategory.class);
		for (String str : records) {
			if (str.contains("！")) {
				goodsCategory1 = new GoodsCategory();
				goodsCategory1.setName(str);
				goodsCategory1.setCategoryId(SequenceUUID.getInstance()
						.getUUID());
				goodsCategory1.setParentCategoryId("0");
				dataRepository.save(goodsCategory1);
				goodsCategory1 = (GoodsCategory) dataRepository.queryEntity(
						"name", str, GoodsCategory.class);

			}
			if (!str.contains("！") && !str.contains("，")) {
				goodsCategory2 = new GoodsCategory();
				goodsCategory2.setName(str);
				goodsCategory2.setCategoryId(SequenceUUID.getInstance()
						.getUUID());

				goodsCategory2.setParentCategoryId(goodsCategory1
						.getCategoryId());
				dataRepository.save(goodsCategory2);
				goodsCategory2 = (GoodsCategory) dataRepository.queryEntity(
						"name", str, GoodsCategory.class);

			}
			if (str.contains("，") && !str.contains("！")) {
				// 三级
				String[] stres = str.split("，");
				for (String str0 : stres) {
					goodsCategory3 = new GoodsCategory();
					goodsCategory3.setCategoryId(SequenceUUID.getInstance()
							.getUUID());

					goodsCategory3.setParentCategoryId(goodsCategory2
							.getCategoryId());
					goodsCategory3.setName(str0);
					dataRepository.save(goodsCategory3);
				}

			}

		}
	}

	/**
	 * @param data1
	 * @param data2
	 * @return 两个向里的相似度
	 * @用于比较两个向量的相似度（根据向量夹角余弦值来比较）
	 */
	public static double calculateSimilartity(List<Float> data1,
			List<Float> data2) {
		if (data1 == null || data2 == null || data1.size() != data2.size()) {
			throw new IllegalArgumentException();
		}
		double similartity = 0.00;
		Float value1 = 0.00f, value2 = 0.00f, value3 = 0.00f;
		for (int i = 0; i < data1.size(); i++) {
			value1 += data1.get(i).floatValue() * data2.get(i).floatValue();
			value2 += data1.get(i) * data1.get(i);
			value3 += data2.get(i) * data2.get(i);
		}
		similartity = value1 / Math.sqrt(value2 * value3);

		return similartity;
	}

	public static double calculateSimilartityByInteger(List<Integer> data1,
			List<Integer> data2) {
		if (data1 == null || data2 == null || data1.size() != data2.size()) {
			throw new IllegalArgumentException();
		}
		double similartity = 0.00;
		Float value1 = 0.00f, value2 = 0.00f, value3 = 0.00f;
		for (int i = 0; i < data1.size(); i++) {
			value1 += data1.get(i).floatValue() * data2.get(i).floatValue();
			value2 += data1.get(i) * data1.get(i);
			value3 += data2.get(i) * data2.get(i);
		}
		similartity = value1 / Math.sqrt(value2 * value3);

		return similartity;
	}
	/**
	 * @param sourceStr
	 * @return
	 * @计算一个文本中每个字符出现的次数组成的向量
	 */
	public static Map<String, Integer> getVectorofString(String sourceStr) {
		char[] chars = sourceStr.toCharArray();
		Map<String, Integer> charCountMap_str = new HashMap<String, Integer>();
		String temp = null;
		for (char ch : chars) {
			temp = String.valueOf(ch);
			if (charCountMap_str.get(temp) == null) {
				charCountMap_str.put(temp, 1);
			} else {
				charCountMap_str.put(temp, charCountMap_str.get(temp) + 1);

			}
		}

		return charCountMap_str;
	}

	/**
	 * @param str
	 *            ：与目标字符串相似性比较的字符串
	 * @param targetStr
	 *            ：目标字符串
	 * @return：得到两个带比较字符串的向量
	 */
	public static List<Integer>[] getVectorofString(String str, String targetStr) {
		List<Integer>[] lists = new ArrayList[2];
		if (str == null || targetStr == null) {
			throw new RuntimeException();
		}
		Map<String, Integer> charCountMap_str = new HashMap<String, Integer>();
		Map<String, Integer> charCountMap_targetStr = new HashMap<String, Integer>();

		char[] targetChars = targetStr.toCharArray();
		char[] chars = str.toCharArray();

		String temp = null;
		// get all chars across str and targetStr
		char[] allChars = new StringBuffer(str).append(targetStr).toString()
				.toCharArray();
		for (char ch : allChars) {
			temp = String.valueOf(ch);

			charCountMap_str.put(temp, 0);
			charCountMap_targetStr.put(temp, 0);

		}

		for (char ch : chars) {
			temp = String.valueOf(ch);

			charCountMap_str.put(temp, charCountMap_str.get(temp) + 1);

		}
		for (char ch : targetChars) {
			temp = String.valueOf(ch);

			charCountMap_targetStr.put(temp,
					charCountMap_targetStr.get(temp) + 1);

		}
		// 设置一些重要特征的权重
		if (charCountMap_targetStr.get("/") != null
				&& charCountMap_str.get("/") != null) {
			if (charCountMap_targetStr.get("/") != charCountMap_str.get("/")) {
				charCountMap_targetStr.put("/", 0);
				charCountMap_str.put("/", 100);
			}

		}
		lists[0] = new ArrayList<Integer>(charCountMap_str.values());
		lists[1] = new ArrayList<Integer>(charCountMap_targetStr.values());

		return lists;
	}

	public static String removeSpace(String str) {
		/*
		 * str=str.replaceAll("\r", ""); str=str.replaceAll("\n", "");
		 * str=str.replaceAll(" ", "");
		 */
		return str;
	}

	/**
	 * @param savedDirectory
	 * @param saveFileName
	 *            :务必使全名（即使要有格式）
	 * @param input
	 * @throws IOException
	 */
	public static void makeFile(String savedDirectory, String saveFileName,
			InputStream input) throws IOException {
		BufferedImage srcImage;

		srcImage = ImageIO.read(input);

		ImageIO.write(srcImage, "JPEG", new File(savedDirectory + saveFileName));
		input.close();
		/*
		 * File file=new File(savedDirectory+saveFileName); if(!file.exists()){
		 * file.createNewFile(); }
		 * 
		 * FileOutputStream fileOutputStream=new FileOutputStream(file); byte[]
		 * buffer=new byte[512]; while(input.read(buffer)!=-1){
		 * fileOutputStream.write(buffer);
		 * 
		 * } fileOutputStream.close();
		 */
	}
	public static void makeFile(String savedDirectory, String saveFileName,
			String input) throws IOException {
		if(isEmptyOrNull(savedDirectory)||isEmptyOrNull(saveFileName)||isEmptyOrNull(input)){
			throw new IllegalArgumentException();
		}
		 File file=new File(savedDirectory+saveFileName);
		 if(!file.exists()){
		 file.createNewFile(); }
		 byte[] bytes=input.getBytes();
		 FileOutputStream fileOutputStream=new FileOutputStream(file);
		 byte[] buffer=new byte[512]; 
	     fileOutputStream.write(bytes);
	     fileOutputStream.close();
		 
	}
	/**
	 * @param strs
	 *            ：要参与笛卡尔积运算的数组
	 * @return：一个包含所有可能组合数组的集合
	 */
	public static List<String[]> getDKL(List<String[]> strs) {

		if (strs.size() > 1) {
			String[] temp = new String[strs.get(0).length * strs.get(1).length];
			int index0 = 0;
			// 继续递归
			for (String str0 : strs.get(0)) {

				for (String str1 : strs.get(1)) {
					temp[index0] = str0 + "@" + str1;
					index0 += 1;

				}

			}

			strs.remove(0);
			strs.remove(0);

			strs.add(0, temp);
			return getDKL(strs);
		} else {
			List<String[]> returnStrs = new ArrayList<String[]>();
			String[] temp0 = null;
			for (String s : strs.get(0)) {

				temp0 = s.split("@");
				returnStrs.add(temp0);
			}

			return returnStrs;
		}
	}

	/**
	 * @param listStr
	 *            ：需要提取特征信息的一系列文本内容组成的集合(提取的特征可能是词元也可能是字符（对于中文）)
	 * @return：返回一个特征信息向量（比如：所有文档中频繁出现的字符的出现次数(求平均值)）可以抽象一个向量来表示
	 */
	public static Map<String, Double> getEigenvector(List<String> demoTexts) {
		DecimalFormat df2 = new DecimalFormat("###.00");

		// 所有样本文本信息中共同出现的字符的次数（字符为key,次数为value）
		Map<String, Double> vectorMap = new HashMap<String, Double>();
		// 务必确定集合中与demoTexts的顺序是一致的
		List<Map<String, Integer>> charNumberMaps = new ArrayList<Map<String, Integer>>();
		for (String str : demoTexts) {
			charNumberMaps.add(CommonUtil.getVectorofString(str));
		}
		boolean charIsAllOwned = false;
		float i = 0;
		// 随即取出一个样本文件中的charNumberMap
		Map<String, Integer> tempMap = charNumberMaps.get(0);
		mLog.info("抽取字符的文本内容为：" + demoTexts.get(0));
		Iterator<String> charIterator = tempMap.keySet().iterator();
		String ch;
		Double chCount = null;

		float chSumCount = 0, mapNumber = demoTexts.size();
		while (charIterator.hasNext()) {
			charIsAllOwned = false;
			chSumCount = 0;
			// 判断是否charIsAllOwned为true
			i = 0;
			ch = charIterator.next();
			for (Map<String, Integer> map : charNumberMaps) {
				if (map.keySet().contains(ch)) {
					i += 1;
					chSumCount += map.get(ch);

				}

				//

			}
			if (i / mapNumber > 0.8) {
				charIsAllOwned = true;
			}
			if (charIsAllOwned) {
				chCount = (double) (chSumCount / mapNumber);
				if (ch.equals("酒") || ch.equals("房")) {
					vectorMap.put(ch, 100.0);
				} else {
					vectorMap.put(ch, 1.0);
					// Double.valueOf(df2.format(chCount)));
				}

			}
		}

		return vectorMap;
	}
  
	public static String convertHtmlCharacter(String htmlStr) {
		String[] character = { "<", ">", "&" }, updateStrs = { "&lt;", "&gt;",
				"&amp;" };

		int length = character.length;
		for (int i = 0; i < length; i++) {
			htmlStr = htmlStr.replaceAll(character[i], updateStrs[i]);

		}
		return htmlStr;
	}

	public static boolean isEmptyOrNull(Collection collection) {
		return collection == null || collection.size() == 0 ? true : false;
	}

	public static boolean isEmptyOrNull(String str) {
		return str == null || str.trim().equals("") ? true : false;
	}

	public static boolean ifFieldHasGetAndSetMethod(Class classz, Field field) {

		Method[] methods = classz.getMethods();
		String getMethodName = null, setMethodName = null;
		int i = 0;
		getMethodName = "get" + CommonUtil.upFirstChar(field.getName());
		setMethodName = "set" + CommonUtil.upFirstChar(field.getName());
		for (Method method : methods) {

			if (getMethodName.equals(method.getName())
					|| setMethodName.equals(method.getName())) {
				i += 1;
			}
			if (i == 2) {
				return true;
			}
		}

		return false;
	}

	private static void getInterfaceOfCla(Class cla, List<Class<?>> interfaces) {
		Class<?>[] interfaces1 = cla.getInterfaces();
		if (interfaces1.length == 0) {
			return;
		} else {
			for (Class class1 : interfaces1) {
				interfaces.add(class1);
				getInterfaceOfCla(class1, interfaces);
			}

		}

	}

	/**
	 * @param clas
	 * @return get all the super interfaces of one class
	 */

	public static List<Class<?>> getALlInterfaces(Class clas) {
		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		getInterfaceOfCla(clas, interfaces);

		return interfaces;
	}
public static Field getFieldByFieldName(Class classz,String fieldName){
	
	 List<Field> fields=CommonUtil.getAllDeclareFields(classz);
	 for(Field field0:fields){
		 if(field0.getName().equalsIgnoreCase(fieldName)){
			 return field0;
		 }
	 }
	return null;
	
}
	
	/**
	 * @param classz
	 * @return
	 *  获取所有一个类的所有熟属性包括父类(仅仅限于有get方法)
	 */
	public static List<Field> getAllDeclareFields(Class classz) {
		List<Field> fields = new ArrayList();
		Field[] fields1 = null;

		for (; classz != Object.class; classz = classz.getSuperclass()) {
			fields1 = classz.getDeclaredFields();

			for (Field field : fields1) {
				// 判断是否有get或者set方法
				if (CommonUtil.ifFieldHasGetAndSetMethod(classz, field)) {
					fields.add(field);

				}

			}

		}

		return fields;
	}

	/**
	 * @param resultList
	 * @throws Exception
	 */
	public static Object[] sort(Object[] results) throws Exception {
		// TODO Auto-generated method stub
		if (results == null) {
			throw new IllegalArgumentException();
		}
		try {
			Arrays.sort(results);
		} catch (Exception ex) {
			// do nothing
		}

		return results;
	}

	/**
	 * @param taskConfig
	 * @param class1
	 * @param path
	 */
	public static void generateXmlForObject(Object obj, Class cla, String path) {
		// TODO Auto-generated method stub
		String tempDirectory = path;
		String fileName = cla.getName() + System.currentTimeMillis() + ".xml";

		File file = new File(tempDirectory + fileName);

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(cla);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(obj, file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param type
	 * @return 判断是否是用户自定义的实体类（此种类型的类往往需要用户操作比如：浏览内容以及编辑里面的内容）
	 */
	public static boolean isCustomerObjType(Class type) {
		if (type.isPrimitive()) {
			return false;
		}

		boolean b1 = isCollection(type) || type.isAssignableFrom(String.class)
				|| type.isAssignableFrom(Date.class);
		boolean b2 = type.isAssignableFrom(Double.class)
				|| type.isAssignableFrom(Character.class)
				|| type.isAssignableFrom(Long.class)
				|| type.isAssignableFrom(Float.class)
				|| type.isAssignableFrom(Integer.class) || type.isEnum();

		if (b1 || b2) {
			return false;
		}
		return true;
	}

	/**
	 * 判断某个属性是否是用户自定义的对象类型
	 * 
	 * @return
	 */
	public static boolean isCustomerObjType(Field property) {
		Class<?> type = property.getType();
		if (type.isPrimitive()) {
			return false;
		}

		boolean b1 = isCollection(type) || type.isAssignableFrom(String.class)
				|| type.isAssignableFrom(Date.class);
		boolean b2 = type.isAssignableFrom(Character.class)
				|| type.isAssignableFrom(Long.class)
				|| type.isAssignableFrom(Float.class)
				|| type.isAssignableFrom(Integer.class) || type.isEnum();

		if (b1 || b2) {
			return false;
		}
		return true;

	}

	public static boolean isCollection(Class<?> type) {
		if (type.getInterfaces().length == 0) {
			return false;
		}

		/*
		 * if(type.getInterfaces()[0].isAssignableFrom(Collection.class)){
		 * return true; }
		 */
		List<Class<?>> interfaces = CommonUtil.getALlInterfaces(type);
		if (interfaces.contains(Collection.class)) {
			return true;
		}
		return false;

	}

	/**
	 * @param pixel
	 * @return 从像素值获取r,g,b
	 */
	public static int[] getRGBFromPixel(int pixel) {
		int[] colorValues = new int[3];
		colorValues[0] = (pixel & 0Xff0000) >> 16;
		colorValues[1] = (pixel & 0Xff00) >> 8;
		colorValues[2] = (pixel & 0Xff);
		return colorValues;

	}

	/**
	 * @param red
	 * @param green
	 * @param blue
	 * @return 从r,g,b计算像素值
	 */
	public static int getPixelFromRGB(int red, int green, int blue) {
		return (red << 16) + (green << 8) + (blue);

	}

	public static double getFiguresSimilartity() {
		double similartity = 0.00;
		return similartity;
	}
	// 對一個圖像的像素進行縮放以及平移
	
	//不同类别对象和字符串之间的转换
	
	/**
	 * @param targetCla
	 * @param value
	 * @return
	 * 主要用于将表格中编辑过的单元格中的字符串对象转换成其原始对象（单元格一经编辑此单元格中保存的对象类型也就发生了变化，不起之前类型如何，都会变成字符串类型
	 * 因此在保存的时候需要进行转换）
	 */
	public static   Object   objectConvert(Class targetCla,Object value){
		if(value==null){
			return null;
			
		}
		mLog.debug("此次转换目标类为："+targetCla.getName()+";要转换的值的类型为："+value.getClass().getName());
		Object object=value;
		if(targetCla.isAssignableFrom(String.class)&&value.getClass().isAssignableFrom(String.class)){
	     object=value;
	}
	if(Double.class.isAssignableFrom(targetCla)&&value.getClass().isAssignableFrom(String.class)){
			object=Double.parseDouble((String)value);
		}
	if(Integer.class.isAssignableFrom(targetCla)&&value.getClass().isAssignableFrom(String.class)){
			object=Integer.valueOf((String)value);
	}
	if(Long.class.isAssignableFrom(targetCla)&&value.getClass().isAssignableFrom(String.class)){
		object=Long.valueOf((String)value);
}
		return object;
		
	}
}
