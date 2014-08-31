package com.yufei.infoExtractor.component.repetition;

import java.util.BitSet;

import org.infinispan.commons.hash.MurmurHash3;

/**
 * @author jasstion
  2013-4-27下午12:44:31
  利用BitSet对大量数据完成去重
 *
 */
public class IsRepetiveBatSet implements IsRepetive{
	public static IsRepetive isRepetive=null;
	public static synchronized IsRepetive getInstance(){
		if(isRepetive==null){
			isRepetive=new IsRepetiveBatSet();
		}
		return isRepetive;
	}
	
	private IsRepetiveBatSet(){
		super();
		
	}
	 public IsRepetiveBatSet(int capacity) {
		// TODO Auto-generated constructor stub
		 max=capacity;
	}
	private  int max=(int) (Math.pow(2, 30)-1);
     private BitSet bitSetN=new BitSet(max);
     private BitSet bitSetP=new BitSet(max);
     private MurmurHash3 murmurHash3=new MurmurHash3();
     //以保存的元素个数
     private long existedNumber=0;
	@Override
	public synchronized boolean isRepetive(String identify) {
		// TODO Auto-generated method stub
		//如果是null提示不重复
		if(identify==null){
			return false;
		}
		int hashCode=murmurHash3.hash(identify);
		if(hashCode<0){
			if(bitSetN.get(Math.abs(hashCode))){
				return true;
				
			}
			bitSetN.set(Math.abs(hashCode));
			existedNumber+=1;
			return false;
		}
		else{
			if(bitSetP.get(hashCode)){
				return true;
				
			}
			bitSetP.set(hashCode);
			existedNumber+=1;
			return false;
		}


	}
public static void main(String[] args){
	String str="http://www.lefeng.com/?utm_source=bdppc1&utm_medium=cpc&utm_campaign=218&aid=6581&cid2=A%E5%93%81%E7%89%8C%E8%AF%8D_%E5%95%86%E5%93%81%E5%93%81%E7%89%8C-%E4%B9%90%E8%9C%82%E7%BD%91_%E9%94%99%E8%AF%8D&cid3=%E4%B9%90%E5%B3%B0&referer=http://www.baidu.com/baidu?word=%E4%B9%90%E5%B3%B0&amp;se=ichuner_1_";
	String str1="http://product.lefeng.com/product/53304.html";
	String str2="http://product.lefeng.com/product/123748.html";
	String str3="http://product.lefeng.com/product/155962.html";
	String str4="http://www.lefeng.com/?utm_source=bdppc1&utm_medium=cpc&utm_campaign=218&aid=6581&cid2=A%E5%93%81%E7%89%8C%E8%AF%8D_%E5%95%86%E5%93%81%E5%93%81%E7%89%8C-%E4%B9%90%E8%9C%82%E7%BD%91_%E9%94%99%E8%AF%8D&cid3=%E4%B9%90%E5%B3%B0&referer=http://www.baidu.com/baidu?word=%E4%B9%90%E5%B3%B0&amp;se=ichuner_1_";
IsRepetive isRepetive=new IsRepetiveBatSet();
	System.out.print(isRepetive.isRepetive(str));
	System.out.print(isRepetive.isRepetive(str1));
	System.out.print(isRepetive.isRepetive(str2));
	System.out.print(isRepetive.isRepetive(str3));
	System.out.print(isRepetive.isRepetive(str4));
	
	while(true){
		
	}

}
}
