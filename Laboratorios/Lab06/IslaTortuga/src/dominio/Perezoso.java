package dominio;

import java.awt.Color;
import java.util.*;

/**
 * Un pirata peresozo
 *
 * @author Angie Medina - Jose Perez
 * @version 1/10/20
 */
public class Perezoso extends Pirata{
    private int ordenesActuar;
    private int cantActue;
    private int defaultCantActue;
    
    /**
     * Crea un pirata perezoso en la posicion <i>(posicionx, posiciony)</i>
     * @param isla, la isla en la que se va a encontrar
     * @param name, nombre del pirata
     * @param posicionx, coordenada x de la posicion
     * @param posiciony, coordenada y de la posicion
     * @param cantActue, la cantidad de veces que puede actuar antes de que se canse
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
    
    /**
     * El pirata para siempre que se le pide
     */
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
     * Retorna la cantidad de veces que puede actuar antes de cansarce
     */
    public int getEstamina(){
        return cantActue;
    }
}
