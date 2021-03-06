package dominio;
import java.awt.Color;
import java.io.Serializable;
import java.util.Random;

public interface EnIsla{
    String[] FORMAS = new String[]{"Persona", "Circulo", "Cuadrado", "Palmera","Lago"};

    Random r = new Random(1);

    int getPosicionX();
    int getPosicionY();
    Color getColor();
    void actue();    
    void pare();
    String nombre();

    /**
     * Decide aleatoriamente si actua o para
     */
    default void decida(){
        if (r.nextBoolean()){
            actue();
        }else{
            pare();
        }
    }     
    
    /**
     * Brinda la forma que tiene el elemento
     * @return la forma que tiene el elemento
     */
    default String forma(){
        return FORMAS[0];
    }

    /**
     * Devuelve el mensaje del elemento
     * @return el mensaje del elemento
     */
    default String mensaje(){
        return toString();
    }

    /**
     * Verifica si el objeto puede moverse dada una direccion
     * @param direccion, la direccion a la que se quiere mover. Validos 'S'(Sur), 'N'(Norte), 'E'(Este), 'O'(Oeste)
     * @return si el elemnto se puede mover en la direccion que quiere
     */
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
