package Generated;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JAXB_Generator {

    private final static String PACKAGE_NAME = "Generated";
    private JAXBContext generator;
    private String conf_file;
    private GameDescriptor container;

    public JAXB_Generator(String file_name) throws JAXBException {
        generator = JAXBContext.newInstance(PACKAGE_NAME);
        conf_file=file_name;
    }

    public void GenerateFromXML() throws FileNotFoundException,JAXBException {

        if (generator != null) {
            Unmarshaller u = this.generator.createUnmarshaller();
            if (conf_file != null) {
                container = (GameDescriptor) u.unmarshal(new FileInputStream(this.conf_file));
            }
        }
    }

    GameDescriptor getContainer()
    {
        return this.container;
    }
}
