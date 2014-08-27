import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name ="root")  
public class HashMapTest {  
   
    @XmlJavaTypeAdapter(HashMapAdapter.class)  
    public Map<String, String> map = new HashMap<String, String>();  
   
    public static class HashMapAdapter extends XmlAdapter<MapType, Map<String, String>> {  
        @Override  
        public MapType marshal(Map<String, String> map) {  
            MapType mapType = new MapType();  
            for (Entry<String, String> entry : map.entrySet()) {  
                MapEntry mapEntry = new MapEntry();  
                mapEntry.key = entry.getKey();  
                mapEntry.value = entry.getValue();  
                mapType.entryList.add(mapEntry);  
            }  
            return mapType;  
        }  
        @Override  
        public Map<String, String> unmarshal(MapType type) throws Exception {  
            Map<String, String> map = new HashMap<String, String>();  
            for (MapEntry entry : type.entryList) {   
                map.put(entry.key, entry.value);  
            }  
            return map;  
        }  
    }  
   
    public static class MapType {  
        @XmlElement(name ="entry")  
        public List<MapEntry> entryList = new ArrayList<MapEntry>();  
    }  
   
    public static class MapEntry {  
        @XmlAttribute  
        public String key;  
        @XmlValue  
        public String value;  
    }  
      
    public static void main(String args[]) throws Exception {  
        HashMapTest mp = new HashMapTest();  
        mp.map.put("key1", "value1");  
        mp.map.put("key2", "value2");  
   
        JAXBContext jc = JAXBContext.newInstance(HashMapTest.class);  
        Marshaller m = jc.createMarshaller();  
        m.marshal(mp, System.out);  
    }  
} 