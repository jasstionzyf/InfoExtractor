import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class Root {

    private Map<String, String> mapProperty;

    public Root() {
        mapProperty = new HashMap<String, String>();
    }
    @XmlJavaTypeAdapter  (MapAdapter.class)
    public Map<String, String> getMapProperty() {
        return mapProperty;
    }
  
    public void setMapProperty(Map<String, String> map) {
        this.mapProperty = map;
    }
    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(Root.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Root root = (Root) unmarshaller.unmarshal(new File("F:\\jcr\\xmlMap.xml"));
        System.out.println(root.getMapProperty().size());
      /*  Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(root, System.out);*/
    }
}
