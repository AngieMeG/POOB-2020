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
     * Constructor for objects of class Perezoso
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
    
    public int getEstamina(){
        return cantActue;
    }
    
    public static void test(){
        Isla islaTest = Isla.demeIsla();
        Perezoso pz1 = new Perezoso(islaTest, "pz1", 100, 100, 1);
        ArrayList<Integer> coord = new ArrayList<Integer>(), ans = new ArrayList<Integer>();
        pz1.actue();pz1.actue();
        int posX1 = pz1.getPosicionX(), posY1 = pz1.getPosicionY();
        ans.add(posX1);ans.add(posY1);
        pz1.actue();pz1.actue();
        int posX2 = pz1.getPosicionX(), posY2 = pz1.getPosicionY();
        coord.add(posX2);coord.add(posY2);
        System.out.println(coord);
        System.out.println(ans);
    }
}
