package dominio;  

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * Ofrece el registro de un curso
 * @author Angie Medina - Jose Perez
 * @version 21/10/20
 *
 */
public class Registro{
    public static String nombre="mooc";
    
    public static void registre(Exception e){
        try{
            Logger logger=Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file = new FileHandler(nombre+".log",true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE,e.toString(),e);
            file.close();
        }catch (Exception oe){
            oe.printStackTrace();
            System.exit(0);
        }
    }
}
    
