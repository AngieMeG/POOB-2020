package dominio;

import java.awt.Color;

/**
 * Un Pirata que no hace caso a lo que se le ordena
 *
 * @author Angie Medina - Jose Perez
 * @version 1/10/20
 */
public class Rebelde extends Pirata{
    
    private int ordenesActuar;
    private String ultimaAccion;
    /**
     * Constructor for objects of class Rebelde
     */    
    public Rebelde(Isla isla,String name,int posicionx, int posiciony){
        super(isla, name, posicionx, posiciony);
        color = Color.RED;
        ordenesActuar = 0;
        ultimaAccion = "Ninguna";
        palabras = "Soy rebelde";
    }
    
    /**
     * El pirata rebelde solo actuara cuando se le hayan dado 3 instrucciones de actuar seguidas
     */
    public void actue(){
        if (ordenesActuar == 2){
            setUltimaAccion("Actue");
            ordenesActuar = 0;
            super.actue();
        }
        else{
            ordenesActuar ++;
        }
    }
    
    /**
     * El pirata rebelde actua si le piden que pare
     */
    public void pare(){
        ordenesActuar = 0;
        setUltimaAccion("Actue");
        super.actue();
    }
    
    /**
     * El pirata rebelde siempre para si su ultima accion fue actuar, y si no decide normalmente
     */
    public void decida(){
        ordenesActuar = 0;
        if (ultimaAccion.equals("Actue")){
            setUltimaAccion("Pare");
            super.pare();
        }
        else{
            /*setUltimaAccion("Decida");
            super.decida();*/
            if (r.nextBoolean()){
                setUltimaAccion("Actue");
                super.actue();
            }else{
                setUltimaAccion("Pare");
                super.pare();
            }
        }
    }
    
    /**
     * Modifica la ultima accion realizada por el pirata
     */
    public void setUltimaAccion(String accion){
        ultimaAccion = accion;
    }
}
