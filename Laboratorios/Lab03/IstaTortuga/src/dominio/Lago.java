 

import java.awt.Color;

public class Lago implements EnIsla
{
    private Isla isla;
    private String name;
    private int posicionx, posiciony;
    private Color color;
    private int actuar;
    private int enfriamiento;
    
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
    
    public void pare(){
        color = Color.BLUE;
    }
    
    public String forma(){
        return FORMAS[4];
    }
    
    /**
      * Retorna un entero con la cantidad de turnos que se debe esperar hasta que el lago se enfrie.
      *@return enfriamiento entero con la cantidad de turnos que se debe esperar hasta que el lago se enfrie.
       */
    public int getEnfriamiento(){
        return enfriamiento;
    }
}
