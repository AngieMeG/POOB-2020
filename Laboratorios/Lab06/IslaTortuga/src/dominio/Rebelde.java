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
     * Crea un pirata rebelde en la posicion <i>(posicionx, posiciony)</i>
     * @param isla, la isla en la que se va a encontrar
     * @param name, nombre del pirata
     * @param posicionx, coordenada x de la posicion
     * @param posiciony, coordenada y de la posicion
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
