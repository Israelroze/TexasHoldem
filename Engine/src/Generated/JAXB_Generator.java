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

    public JAXB_Generator(String file_name)
    {
        try {
            generator = JAXBContext.newInstance(PACKAGE_NAME);
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
        conf_file=file_name;
    }

    public String GenerateFromXML()
    {
        try {
            if(generator!=null) {
                Unmarshaller u = this.generator.createUnmarshaller();
                if(conf_file!=null) {
                    try{
                       container=(GameDescriptor)u.unmarshal(new FileInputStream(this.conf_file));
                    } catch (FileNotFoundException e) {
                        return "xml file not found, error:"+e.getMessage();
                    }
                    return "ok";
                }
                else
                {
                    return "null stream";
                }
            }
            else
            {
                return "null obj";
            }
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }
        return "error";
    }

    GameDescriptor getContainer()
    {
        return this.container;
    }
}
