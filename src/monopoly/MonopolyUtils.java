package monopoly;


import gameLogic.GameManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


/**
 * this class is a collection of utilities needed in the monopoly game web service
 * @author Dana Akerman
 */
public class MonopolyUtils 
{
    
    // constants
    //---------------------------------------------------------------------------------
    
    public static final String FILES_FOLDER = "resources/files/";
    
    // functions
    //---------------------------------------------------------------------------------
    
    /**
     * converts an xml file to an object holding the xml data genereted by JAXB
     * @param fileName the name of the xml file
     * @return the generated Monopoly object or null if filename is null or 
     * if theres is a problem with JAXB unmarshalling
     */
    public static generated.Monopoly createObjectFromXml(String fileName)
    {
        if(fileName == null)
        {
            return null;
        }
        
        try 
        {
            InputStream is = GameManager.class.getClassLoader().getResourceAsStream(FILES_FOLDER + fileName);
            generated.Monopoly temp = new generated.Monopoly();
            JAXBContext jc;
            jc = JAXBContext.newInstance(generated.Monopoly.class);
            Unmarshaller u;
            u = jc.createUnmarshaller();
            temp = (generated.Monopoly)u.unmarshal(is);
            return temp;
        } 
        catch (JAXBException e) 
        {
            return null;
        }
    }
    
    //---------------------------------------------------------------------------------
    
    /**
     * converts a text file to string
     * @param fileName the file name
     * @return a string thay represents the text file
     */
    public static String textFileToString(String fileName)
    {
        InputStream is = null;
        try 
        {
            is = MonopolyGame.class.getClassLoader().getResourceAsStream(FILES_FOLDER + fileName);
            return new Scanner(is).useDelimiter("\\Z").next();
        } 
        finally 
        {
            if (is != null) 
            {
                try 
                {
                    is.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    //---------------------------------------------------------------------------------
    
    /**
     * makes the thread sleep
     * @param millies the time of sleep in milliseconds
     */
    public static void sleep(long millies) 
    {
        try 
        {
            Thread.sleep(millies);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
}
