package com.yufei.infoExtractor.extractor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yufei.infoExtractor.context.HActionContext;
import com.yufei.infoExtractor.extractor.entity.Point;
import com.yufei.infoExtractor.util.AppUtil;
import com.yufei.utils.CommonUtil;
import com.yufei.utils.ExceptionUtil;

/**
 * created by @author jasstion at 2012-9-21 extractor the number from a picture
 */
public class PictureNumExtractor extends Extractor {
	private static final class YComparator implements Comparator<Point> {
		@Override
		public int compare(Point o1, Point o) {
			// TODO Auto-generated method stub
			if(o1.getY()>o.getY()){
				return 1;
			}
			if(o1.getY()<o.getY()){
				return -1;
				
			}
			else{
				if(o1.getX()>o.getX()){
					return 1;
				}
				else{
					return -1;	
				}
				
			}


		}
	}




	private static final Log mLog = LogFactory.getLog(PictureNumExtractor.class);

	private Map<String, List<Point>> demoPoints = new HashMap<String, List<Point>>();

	
	/**
	 * @param backgroundPixel
	 * @param file
	 * @return
	 * @throws IOException
	 * backgroundPixel为-1的话默认取边缘的像素的颜色作为背景
	 */
	public static List<Point> getImagePoints(int backgroundPixel, File file)
			throws IOException {
		List<Point> points = new ArrayList<Point>();
		BufferedImage bufferImage = null;

		bufferImage = ImageIO.read(file);
	
		int height = bufferImage.getHeight();
		int width = bufferImage.getWidth();
		int minX = bufferImage.getMinX();
		int minY = bufferImage.getMinY();
		//取邊框像素作為背景色像素
		if(backgroundPixel==-1){
			backgroundPixel= bufferImage.getRGB(minX, minY);
			backgroundPixel = CommonUtil.getPixelFromRGB((backgroundPixel & 0Xff0000) >> 16,
					(backgroundPixel & 0Xff00) >> 8, (backgroundPixel & 0Xff));
		}
		
		for (int i = minX; i < width; i++) {
			for (int j = minY; j < height; j++) {
				int pixel = bufferImage.getRGB(i, j);
				pixel = CommonUtil.getPixelFromRGB((pixel & 0Xff0000) >> 16,
						(pixel & 0Xff00) >> 8, (pixel & 0Xff));
				if (pixel != backgroundPixel) {
					 
					points.add(new Point(new Float(i), new Float(j)));
					
				}

			}
		}
		Collections.sort(points);


		return points;
	}
	public static List<Point> getDemoPoint(int backgroundPixel, InputStream image)
			throws IOException {
		List<Point> points = new ArrayList<Point>();
		BufferedImage bufferImage = null;

		bufferImage = ImageIO.read(image);
	
		int height = bufferImage.getHeight();
		int width = bufferImage.getWidth();
		int minX = bufferImage.getMinX();
		int minY = bufferImage.getMinY();
		//取邊框像素作為背景色像素
		if(backgroundPixel==-1){
			backgroundPixel= bufferImage.getRGB(minX, minY);
			backgroundPixel = CommonUtil.getPixelFromRGB((backgroundPixel & 0Xff0000) >> 16,
					(backgroundPixel & 0Xff00) >> 8, (backgroundPixel & 0Xff));
		}
		
		for (int i = minX; i < width; i++) {
			for (int j = minY; j < height; j++) {
				int pixel = bufferImage.getRGB(i, j);
				pixel = CommonUtil.getPixelFromRGB((pixel & 0Xff0000) >> 16,
						(pixel & 0Xff00) >> 8, (pixel & 0Xff));
				if (pixel != backgroundPixel) {
					 
					points.add(new Point(new Float(i), new Float(j)));
					
				}

			}
		}
		Collections.sort(points);


		return points;
	}
	/**
	 * @param folder
	 *            文件夹名称,里面的每个图片里面包含一个单独的数字
	 * @throws IOException
	 */
	
	private void initDemoPoints(String folder) throws IOException {
		File file = new File(folder);
		File[] pictures = file.listFiles();
		String number=null;
		List<Point> demoPoint=null;
		for(File picture:pictures){
			number=picture.getName().substring(0, picture.getName().lastIndexOf("."));
			
				demoPoint = getImagePoints(-1, picture);
				demoPoints.put(number, demoPoint);
			
		}

	}
public String extractNumberFormPicture(File file){
	try {
		initDemoPoints("F:\\picturedemo\\lefeng\\");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		mLog.info(ExceptionUtil.getExceptionDetailsMessage(e1));
	}
	StringBuffer result=new StringBuffer();
	List<Point> imagePoints = null;
	try {
		imagePoints = getImagePoints(-1, file);
	} catch (IOException e) {
		// TODO Auto-generated catch block
	mLog.info(ExceptionUtil.getExceptionDetailsMessage(e));
	}
	List<List<Point>> list=PictureNumExtractor.splitIntoImagePoints(imagePoints);
	mLog.info("图片此次被分割成："+list.size()+"部分");
	
	for(List<Point> points:list){
		result.append(extractNumber(points));
	}
	return result.toString();
}
	private String extractNumber(List<Point> points) {
	// TODO Auto-generated method stub
		String tempNumber=null;
		String currentNumber=null;
		double currentSimilarity=-1;
		double tempSimilartity=0;
		//number,points
		Iterator< String> demoNumbers=demoPoints.keySet().iterator();
	  while (demoNumbers.hasNext()) {
		  tempNumber=demoNumbers.next();
		  tempSimilartity=calculateTwoShapSimilartityByCoordinate(points,demoPoints.get(tempNumber) );
		if(currentSimilarity!=-1){
			if(new BigDecimal(tempSimilartity).setScale(7, BigDecimal.ROUND_HALF_UP).doubleValue()>new BigDecimal(currentSimilarity).setScale(7, BigDecimal.ROUND_HALF_UP).doubleValue()){
				currentSimilarity=tempSimilartity;
				currentNumber=tempNumber;
			}
		}
		else{
			currentSimilarity=tempSimilartity;
			currentNumber=tempNumber;
		}
	}
		
		
	    return currentNumber;
}
	@Override
	public void extract(HActionContext context) {
		// TODO Auto-generated method stub
		String numberPictureName=(String) context.get("numberPictureName");
		List<Point> 	points=null;
		try {
			points=getImagePoints(-1, new File(AppUtil.getInfoExtractorConfig().getTempDirectory()+numberPictureName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mLog.info("Error when extract picture form images! "+ExceptionUtil.getExceptionDetailsMessage(e));
		}
      
	}
/**
	 * @param points
	 * @return 將一張圖片中獲取到的數字座標簡單的分割成單獨的幾個數字座標
	 */
	public static List<List<Point>> splitIntoImagePoints(List<Point> points) {
		// 排序
		Collections.sort(points);
		List<List<Point>> imagePoints = new ArrayList<List<Point>>();
		//先进后出
		//Stack<String> stack = new Stack<String>();
		//先进先出
		Queue<String> quene=new LinkedList<String>();
		for (int i = 0; i < points.size(); i++) {
			if (i == (points.size() - 1)) {
				break;
			}
			if (points.get(i).getX() .floatValue()!= points.get(i + 1).getX().floatValue()) {
				if ((points.get(i).getX() + 1) != points.get(i + 1).getX().intValue()) {
					quene.add(String.valueOf(i));
				}
			}
	
		}
		int end = points.size();
		int begain = 0;
		while (!quene.isEmpty()) {
	
		    end=Integer.parseInt(quene.poll())+1;
			imagePoints.add(points.subList(begain, end));
			begain=end;
	
		}
		//添加最后一个
		imagePoints.add(points.subList(begain, end));
	
		return imagePoints;
	}
/**
 * @param figurePoints
 * @param ratioNumber
 * 对图像的各个坐标按照一定比例进行缩小
 */
public static void ratioFigure(List<Point> figurePoints,Float  ratioX,Float radioY){
	for(Point point:figurePoints){
		point.setX(point.getX()*ratioX);
		point.setY(point.getY()*radioY);
	}
	
}
/**
 * @param figurePoints
 * @param x 正数表示像右移动 
 * @param y 正数表示向下移动
 */
public static void moveFigure(List<Point> figurePoints,Float x,Float y){
	for(Point point:figurePoints){
		point.setX(point.getX()+x);
		point.setY(point.getY()+y);
	}
}



public double calculateTwoShapSimilartityByCoordinate(List<Point> points1,List<Point> points2){
	double similartity = 0;
	StringBuffer str=new StringBuffer();
	if(points2.size()==0||points1.size()==0){
		return 0;
	}
	/*for(Point point:points1){
		str.append((point.getX()+":"+point.getY()+" ,"));
	}
	mLog.info(str.toString());
	str=new StringBuffer();
	for(Point point:points2){
		str.append((point.getX()+":"+point.getY()+" ,"));
		}
	mLog.info(str.toString());*/
	//对图形进行缩放
	Float xLength1=points1.get(points1.size()-1).getX()-points1.get(0).getX();
	Float xLength2=points2.get(points2.size()-1).getX()-points2.get(0).getX();
	Float yHeight1=null;
	Float  yHeight2=null;
	//对y坐标进行排序
	Collections.sort(points1,new YComparator());
	Collections.sort(points2,new YComparator());
	yHeight1=points1.get(points1.size()-1).getY()-points1.get(0).getY();
	yHeight2=points2.get(points2.size()-1).getY()-points2.get(0).getY();
	
	//对x进行排序

/*	if(xLength1*yHeight1>xLength2*yHeight2){
		ratioFigure(points1,xLength2/ xLength1, yHeight2/yHeight1);
		}
	else{
		ratioFigure(points2, xLength1/xLength2, yHeight1/yHeight2);
	}*/
	ratioFigure(points2, xLength1/xLength2, yHeight1/yHeight2);

	Collections.sort(points1);
	Collections.sort(points2);
	/*str=new StringBuffer();
	for(Point point:points1){
		str.append((point.getX()+":"+point.getY()+" ,"));
	}
	mLog.info(str.toString());
	str=new StringBuffer();
	for(Point point:points2){
		str.append((point.getX()+":"+point.getY()+" ,"));
		}
	mLog.info(str.toString());*/
	//平移

	float  x1=points1.get(0).getX();
	float  x2=points2.get(0).getX();
	Collections.sort(points1,new YComparator());
	Collections.sort(points2,new YComparator());
	float y1=points1.get(0).getY();
	float y2=points2.get(0).getY();
	
	moveFigure(points2, x1-x2, y1-y2);
	Collections.sort(points1);
	Collections.sort(points2);
	/* str=new StringBuffer();
	for(Point point:points1){
		str.append((point.getX()+":"+point.getY()+" ,"));
	}
	mLog.info(str.toString());
	str=new StringBuffer();
	for(Point point:points2){
		str.append((point.getX()+":"+point.getY()+" ,"));
		}
	mLog.info(str.toString());*/
   List<Float> data1 = null;
	List<Float> data2 = null;
	data1=new ArrayList<Float>();
	data2=new ArrayList<Float>();
	int i=0;
	List<Point> accordedPoints=null;
	//判断哪个图形坐标多，然后以坐标较少的那个图形为标准来得到相应的另一个图形的坐标
/*   if(points1.size()<points2.size()){
	   accordedPoints=accordedShapCoordinates(points1, points2);
		for(Point point:points1){
			data1.add(point.getY());
		}
		for(Point point:accordedPoints){
			data2.add(point.getY());
		}
			similartity=CommonUtil.calculateSimilartity(data1, data2);
   }
   else{
	   accordedPoints=accordedShapCoordinates(points2, points1);
			for(Point point:points2){
				data1.add(point.getY());
			}
			for(Point point:accordedPoints){
				data2.add(point.getY());
			}
			
				similartity=CommonUtil.calculateSimilartity(data1, data2);
				mLog.info(similartity);
   }*/
	 accordedPoints=accordedShapCoordinates(points1, points2);
		for(Point point:points1){
			data1.add(point.getY());
		}
		for(Point point:accordedPoints){
			data2.add(point.getY());
		}
	 similartity=CommonUtil.calculateSimilartity(data1, data2);
		//mLog.info(similartity);
	return similartity;
	
}
	public static void main(String[] args) throws IOException {
		// 各个数字图像的坐标集合
		// 指定的图片，然后从指定的图片中分割出来各个可能的数字的坐标集合；
		// 将各个坐标集合和所有的样本坐标集合进行相似度计算
		// 如果匹配失败则将匹配失败的坐标集合在逐个分离开来然后哦单个在进行匹配

//.substring(0, result.lastIndexOf('.'))
long be=System.currentTimeMillis();
		/*for(int i=3;i<6;i++){
			String testPicture="F:\\media\\0"+i+".png";
			String number=null;
			String result=new PictureNumExtractor().extractNumberFormPicture(new File(testPicture));
			System.out.print(result+"\n");	
		}*/
String testPicture="D:\\data\\119.png";
String number=null;
String result=new PictureNumExtractor().extractNumberFormPicture(new File(testPicture));
System.out.print(result+"\n");	
		System.out.print(System.currentTimeMillis()-be);
	}
public static List<Float>[] getYFeatch(List<Point> ponits1,List<Point> points2){
	List<Float>[] floats=new ArrayList[2];
	floats[0]=new ArrayList<Float>();
	floats[1]=new ArrayList<Float>();
	if(CommonUtil.isEmptyOrNull(ponits1)||CommonUtil.isEmptyOrNull(points2)||(ponits1.size()>=points2.size())){
		throw new IllegalArgumentException();
	}
	for(Point  point:ponits1){
		  floats[0].add(point.getY());
		  floats[1].add(getSimilarityPointY(points2,point));
	}
	return floats;
	
}
public static Float getSimilarityPointY(List<Point> points2, Point point) {
	// TODO Auto-generated method stub
	Float y=null;
	Point  similarityPoint=null;
	double disTemp=0,previousDis=-1;
	for(Point pointTemp:points2){
	//	points2.remove(pointTemp);
		disTemp=getDis(pointTemp, point);
		if(previousDis==-1){
			previousDis=disTemp;
			similarityPoint=pointTemp;
		}
		else{
			if(previousDis>disTemp){
				similarityPoint=pointTemp;
				previousDis=disTemp;
			}
		}
	}
	
	return similarityPoint.getY();
}
public static double getDis(Point  point1,Point point2){
	double dis = 0;
	double a=point1.getX()-point2.getX();
	double b=point1.getY()-point2.getY();
	dis=Math.sqrt(a*a+b*b);
	return dis;
}
/**
 * @param demoShap
 * @param targetShap
 * @return
 * 两个图形的坐标集合，其中targetShap的坐标要比demoShap的坐标分布稠密一些，通过demoShap来讲targetShap的坐标进行统一化，即抽取targetShap中的坐标
 * 和demoShap中的坐标一一对应
 */
public static List<Point>  accordedShapCoordinates(List<Point> demoShap,List<Point> targetShap){
	List<Point> resultShap=new ArrayList<Point>();
	//对应点规则：x相差最小，然后是y相差最小，同时将匹配到的坐标全部移除再继续选择
	List<Integer> indexs=null;
	Point tempPoint=null;

	for(Point point:demoShap){
		 indexs=new ArrayList<Integer>();
		tempPoint=getSimilarityPoint(targetShap, point);
		resultShap.add(tempPoint);
	
	}

	return resultShap;
	
}

/**
 * @param targetShap
 * @param point
 * @return
 * 从targetShap中获取与point最接近的point同时将targetShap中的x<=此次获取的point的x坐标的所有point移除
 */
private static Point getSimilarityPoint(List<Point> targetShap,Point point){

	
	
	
	
	Point  similarityPoint=null;
	double disTemp=0,previousDis=-1;
	for(Point pointTemp:targetShap){
		disTemp=getDis(pointTemp, point);
		if(previousDis==-1){
			previousDis=disTemp;
			similarityPoint=pointTemp;
		}
		else{
			if(previousDis>disTemp){
				similarityPoint=pointTemp;
				previousDis=disTemp;
			}
		}
	}


	
	
	return similarityPoint;
}


}
