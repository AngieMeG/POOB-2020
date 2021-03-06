package dominio;

import java.awt.Color;

public class Palmera implements EnIsla{
    private Isla isla;
    private String name;
    private int posicionx, posiciony;
    private Color color;
    
    public Palmera(Isla isla,String name,int posicionx, int posiciony){
        this.isla = isla;
        this.name = name;
        this.posicionx = posicionx;
        this.posiciony = posiciony;
        color = Color.GREEN;
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
        color = Color.GREEN.darker();
    }   
    
    public void pare(){
        System.setProperty("brighterGreen", "#7fff68");
        Color brighterGreen = Color.getColor("brighterGreen");
        color = brighterGreen;
    }
    
    @Override
    public void decida(){
        for (int i = 0; i < 2; i++){
            if (r.nextBoolean()){
                actue();
            }else{
                pare();
            }   
        }
    }
    
    public String forma(){
        return FORMAS[3];
    }
}
