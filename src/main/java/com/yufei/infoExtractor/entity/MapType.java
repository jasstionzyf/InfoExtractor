package com.yufei.infoExtractor.entity;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


public class MapType {
	 @XmlElement(name ="entry")  
     public List<MapEntry> entryList = new ArrayList<MapEntry>();  
}
