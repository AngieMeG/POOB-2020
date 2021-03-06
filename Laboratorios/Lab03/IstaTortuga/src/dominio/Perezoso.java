 

import java.awt.Color;
import java.util.*;

/**
 * Un pirata peresozo
 *
 * @author Angie Medina - Jose Perez
 * @version 29/9/20
 */
public class Perezoso extends Pirata{
    private int ordenesActuar;
    private int cantActue;
    private int defaultCantActue;
    /**
     * Constructor for objects of class Perezoso
     *@param isla Isla en la que se encuentra el pirata
     *@param name String con el nombre del pirata
     *@param posicionx Entero con la posicion en x inicial del pirata
     *@param posiciony Entero con la posicion en y inicial del pirata
     *@param cantActue Entero que indica la cantidad de veces que puede actuar el pirata antes de cansarse
     */
    public Perezoso(Isla isla,String name,int posicionx, int posiciony, int cantActue){
        super(isla, name, posicionx, posiciony);
        color = Color.BLUE;
        palabras = "";
        ordenesActuar = 0;
        this.cantActue = cantActue;
        defaultCantActue = cantActue;
    }
    
    /**
     * El pirata peresozo solo actua cuando se le ha ordenado que actue dos veces seguidas
     */
    public void actue(){
        if(cantActue==0){
            for(EnIsla i: super.getIsla().elementosCercanos(this)){
                if(i.forma().equals("Lago") && i.getColor().equals(Color.CYAN)) {
                    defaultCantActue++;
                    cantActue = defaultCantActue;
                    palabras ="";
                    break;
                }
            }
        }
        if (cantActue != 0){
            if (ordenesActuar == 1){
                ordenesActuar = 0;
                cantActue -= 1;
                super.actue();
            }
            else{
                ordenesActuar ++;
            }
        }
        else{
            pare();
        }
    }
    
    public void pare(){
        super.pare();
        if (cantActue == 0) palabras = "Estoy cansado";
    }
    
    /**
     * El pirata peresozo siempre elige parar cuando se le pide que decida
     */
    public void decida(){
        pare();
    }
    /**
      *@return Entero con la cantidad de acciones que podra realizar el pirata antes de cansarse	
      */
    public int getEstamina(){
        return cantActue;
    }
}
