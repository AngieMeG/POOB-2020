package dominio;

import java.awt.Color;
import java.io.Serializable;

/**
 * Clase lago, este puede estar en la isla y recarga la estamina de los piratas cuando esta se les acaba
 * @author Angie Medina - Jose Perez
 * @version 15/11/20
 */
public class Lago implements EnIsla, Serializable{
    private Isla isla;
    private String name;
    private int posicionx, posiciony;
    private Color color;
    private int actuar;
    private int enfriamiento;
    
    /**
     * Constructor.
     * @param isla, la isla en la que se quiere colocar el lago
     * @param name, el nombre que va a tener el algo
     * @param posicionx, la posicion en el eje x en la que se va a encontrar el lago
     * @param posiciony, la posicion en el eje y en la que se va a encontrar el lago
     */
    public Lago(Isla isla,String name,int posicionx, int posiciony){
        this.isla = isla;
        this.name = name;
        this.posicionx = posicionx;
        this.posiciony = posiciony;
        color = Color.BLUE;
        actuar = 0;
        enfriamiento = r.nextInt(9)+1;
    }
    
    public int getPosicionX(){
        return posicionx;
    }
    
    public int getPosicionY(){
        return posiciony;
    }
    
    public Color getColor(){
        return color;
    }
    
    /**
     * Si se llego al tiempo de enfriamiento, el alog se enfria volviendose de un azul cian
     */
    public void actue(){
        if(actuar==enfriamiento) {
            color = Color.CYAN;
            actuar = 0;
        }
        else{
            pare();
            actuar++;
        }
    }   
    
    /**
     * Cuando se le dice al lago que pare este se vuelve de color azul oscuro
     */
    public void pare(){
        color = Color.BLUE;
    }
    
    /**
     * Retorna la forma que tiene el lago
     */
    public String forma(){
        return FORMAS[4];
    }
    
    /**
      * Retorna un entero con la cantidad de turnos que se debe esperar hasta que el lago se enfrie.
      * @return enfriamiento entero con la cantidad de turnos que se debe esperar hasta que el lago se enfrie.
      */
    public int getEnfriamiento(){
        return enfriamiento;
    }
    
    public String getName(){
        return name;
    }

	@Override
	public String nombre() {
		return "";
	}
}
