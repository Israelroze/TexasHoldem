import Generated.JAXB_Generator;

public class Tester {
   public static void main(String[] args)
   {
       JAXB_Generator generator=new JAXB_Generator("C:\\Users\\israe\\Google Drive\\Study\\JavaCourse\\TexasHoldem\\Engine\\Resource\\ex1-basic.xml");
      System.out.println(generator.GenerateFromXML());
   }
}
