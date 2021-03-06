package dominio;
import java.awt.Color;
import java.util.Random;

public interface EnIsla{
    String[] FORMAS = new String[]{"Persona", "Circulo", "Cuadrado", "Palmera","Lago"};

    Random r = new Random(1);

    int getPosicionX();
    int getPosicionY();
    Color getColor();
    void actue();    
    void pare();


    default void decida(){
        if (r.nextBoolean()){
            actue();
        }else{
            pare();
        }
    }     
    
    default String forma(){
        return FORMAS[0];
    }

    default String mensaje(){
        return toString();
    }

    default boolean puedeMoverse(char direccion) {
        boolean puede=false;
        int posX = getPosicionX();
        int posY = getPosicionY();
        switch(direccion){
            case 'S' : puede = (posY+1 < Isla.MAXIMO);
            break;
            case 'E' : puede = (posX+1 < Isla.MAXIMO);
            break;
            case 'N' : puede = (posY-1 >= 0);
            break;
            case 'O' : puede = (posX-1 >= 0);
            break; 
        } 
        return puede;
    }
}
