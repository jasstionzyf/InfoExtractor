package com.yufei.infoExtractor.entity;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class MapEntry {
	  @XmlAttribute  
      public String key;  
      @XmlValue  
      public String value;  
}
