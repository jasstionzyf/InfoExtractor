package com.yufei.infoExtractor.cache;

public interface InfoExtractorCache <K,V>{
public  V get(K k);
public void put(K k,V v);
public int size();
}
