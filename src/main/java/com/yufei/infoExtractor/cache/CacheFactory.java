package com.yufei.infoExtractor.cache;

public class CacheFactory<K,V> {
	public final static String  MemoryCacheType="MemoryCacheType";
	private static CacheFactory cacheFactory=null;
	private CacheFactory(){
		super();
	}
public   InfoExtractorCache< K, V> createCache(String cacheType){
	if(cacheType.equals(MemoryCacheType)){
		return new MemoryCache<K, V>();

	}
	return null;
}
public static CacheFactory getInstance(){
	if(cacheFactory==null){
		cacheFactory=new  CacheFactory();

	}
	return cacheFactory ;
}

}
