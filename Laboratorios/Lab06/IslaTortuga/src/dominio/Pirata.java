package dominio;

import java.awt.Color;

/**
 * Clase pirata, este puede estar en la isla
 * @author Angie Medina - Jose Perez
 * @version 15/11/20
 *
 */
public class Pirata extends Persona implements EnIsla{

   private Isla isla;   
   protected String palabras;

   /**
    * Crea un pirata en la posicion <i>(posicionx, posiciony)</i>
    * @param isla, la isla en la que se va a encontrar
    * @param name, nombre del pirata
    * @param posicionx, coordenada x de la posicion
    * @param posiciony, coordenada y de la posicion
    */
   public Pirata(Isla isla,String name,int posicionx, int posiciony){
        super(name,posicionx,posiciony);
        this.isla=isla;
        color=Color.BLACK;
        palabras="¡Buscando!";
        
   }

   /**
    * Hace que el pirata pare
    */
   public void pare(){
        muevaBrazo('I','P'); 
        muevaPierna('I','P');
        muevaBrazo('D','P'); 
        muevaPierna('D','P');       
        palabras="";
   }

   /**
    * Mueve, si es posible, al pirata 
    */
   public final void muevase(char d){
        if (! isla.enTesoro(this)){
            super.muevase(d);
        }    
   }

   /**
    * Hace que el pirata actue
    */
   public void actue(){
        char d;
        movimiento();
        do{
            d="NSEO".charAt((int)(Math.random() * 4));
        } while (! puedeMoverse(d));
        muevase(d);
   }
   
   /**
    * Mueve las extremidades del pirata
    */
   protected void movimiento(){
        if (getPosicionBrazo('I') == ABAJO && getPosicionBrazo('D') == ABAJO){
            muevaBrazo('I','S'); 
            muevaPierna('I','S');
        } else if  (getPosicionBrazo('I') == FRENTE){
            muevaBrazo('I','S'); 
            muevaPierna('I','S');
        } else if (getPosicionBrazo('I') == ARRIBA){
            muevaBrazo('I','B'); 
            muevaPierna('I','B');
            muevaBrazo('I','B'); 
            muevaPierna('I','B');
            muevaBrazo('D','S'); 
            muevaPierna('D','S');
        }else if (getPosicionBrazo('D') == FRENTE){
            muevaBrazo('D','S'); 
            muevaPierna('D','S');
            muevaBrazo('D','S'); 
            muevaPierna('D','S');
            muevaBrazo('I','B'); 
            muevaPierna('I','B');
        }else if (getPosicionBrazo('D') == ARRIBA){
            muevaBrazo('D','B'); 
            muevaPierna('D','B');
            muevaBrazo('D','B'); 
            muevaPierna('D','B');
            muevaBrazo('I','S'); 
            muevaPierna('I','S');
        } 
   }
   
   /**
    * Retorna el mensaje del pirata
    */
   public final String mensaje() {
        return super.mensaje()+": "+(isla.enTesoro(this) ? "¡ENCONTRE EL TESORO! ": palabras);
   }

   /**
    * Retorna la isla en la que se encuentra
    */
   protected Isla getIsla(){
       return isla;
   }
}

