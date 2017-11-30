import Generated.JAXB_Generator;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class Tester {
   public static void main(String[] args)
   {
       JAXB_Generator generator= null;
       try {
           generator = new JAXB_Generator("C:\\Users\\israe\\Google Drive\\Study\\JavaCourse\\TexasHoldem\\Engine\\Resource\\ex1-basic.xml");
       } catch (JAXBException e) {
           e.printStackTrace();
       }
       try {
          generator.GenerateFromXML();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (JAXBException e) {
           e.printStackTrace();
       }
   }
}
