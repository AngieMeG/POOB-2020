package dominio;

import java.awt.Color;

public class Pirata extends Persona implements EnIsla{

   private Isla isla;   
   protected String palabras;

   public Pirata(Isla isla,String name,int posicionx, int posiciony){
        super(name,posicionx,posiciony);
        this.isla=isla;
        color=Color.BLACK;
        palabras="¡Buscando!";
        
   }

   public void pare(){
        muevaBrazo('I','P'); 
        muevaPierna('I','P');
        muevaBrazo('D','P'); 
        muevaPierna('D','P');       
        palabras="";
   }

   public final void muevase(char d){
        if (! isla.enTesoro(this)){
            super.muevase(d);
        }    
   }

   public void actue(){
        char d;
        movimiento();
        do{
            d="NSEO".charAt((int)(Math.random() * 4));
        } while (! puedeMoverse(d));
        muevase(d);
   }
   
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
   
   public final String mensaje() {
        return super.mensaje()+": "+(isla.enTesoro(this) ? "¡ENCONTRE EL TESORO! ": palabras);
   }

   protected Isla getIsla(){
       return isla;
   }
}

