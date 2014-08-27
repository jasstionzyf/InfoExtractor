package com.yufei.infoExtractor.cache;

import java.lang.ref.WeakReference;

import java.util.HashMap;

import com.yufei.infoExtractor.LoggerCommon;

public class MemoryCache<K, V> extends LoggerCommon implements InfoExtractorCache<K, V> {
private HashMap< K, WeakReference<V>> weakHashMap=new HashMap<K,  WeakReference<V>>();


	public MemoryCache() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public void put(K k, V v) {
		// TODO Auto-generated method stub
		if(v==null){
			throw new IllegalArgumentException();
		}
		weakHashMap.put(k, new WeakReference<V>(v));
	}


	@Override
	public V get(K k) {
		// TODO Auto-generated method stub
		WeakReference<V> weakReference=weakHashMap.get(k);
		if(weakReference==null){
			return null;

		}
		return weakReference.get();
	}


	@Override
	public int size() {
		// TODO Auto-generated method stub
		
		return this.weakHashMap.size();
	}
//test
	

}
