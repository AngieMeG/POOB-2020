
 

import java.util.*;

public class Isla{
    public static final int MAXIMO = 500;
    private static Isla isla = null;

    public static Isla demeIsla() {
        if (isla==null){
            isla=new Isla();
        }
        return isla;
    }

    private static void nuevaIsla() {
        isla=new Isla();
    }   

    public static void cambieIsla(Isla d) {
        isla=d;
    }       

    private ArrayList<EnIsla> elementos;
    private int tesoroPosX;
    private int tesoroPosY;
    private boolean encontraronTesoro;

    private Isla() {
        elementos= new ArrayList<EnIsla>();
        tesoroPosX = (int)(Math.random() * MAXIMO);
        tesoroPosY = (int)(Math.random() * MAXIMO);
        encontraronTesoro=false;
    }

    public void algunosEnIsla(){    
       Pirata jack = new Pirata(this, "Jack", 100, 20);
       Pirata elizabeth = new Pirata(this, "Elizabeth", 350, 50);
       adicione(jack);adicione(elizabeth);
       Rebelde henry = new Rebelde(this, "Henry", 50, 200);
       Rebelde corina = new Rebelde(this, "Corina", 450, 200);
       adicione(henry); adicione(corina);
       Palmera superiorDerecha = new Palmera(this, "superiorDerecha", 499, 480);
       Palmera superiorIzquierda = new Palmera(this, "superiorIzquiera", 1, 480);
       Palmera inferiorDerecha = new Palmera(this, "inferiorDerecha", 499, 1);
       Palmera inferiorIzquierda = new Palmera(this, "inferiorIzquiera", 1, 1);
       adicione(superiorDerecha);adicione(superiorIzquierda);
       adicione(inferiorDerecha); adicione(inferiorIzquierda);
       Minusioso ada = new Minusioso(this, "Ada", 100, 300);
       Minusioso turing = new Minusioso(this, "Turing", 250, 300);
       adicione(ada); adicione(turing);
       Perezoso angie = new Perezoso(this, "Angie", 100, 400, 2);
       Perezoso jose = new Perezoso(this, "Jose", 118, 100, 1);
       adicione(angie); adicione(jose);
       Lago lagoCacatua = new Lago(this,"lagoCacatua",100,100);
       Lago unilago = new Lago(this,"unilago",200,200);
       adicione(lagoCacatua);adicione(unilago);
    }  

    public EnIsla demeEnIsla(int n){
        EnIsla h=null;
        if (1<=n && n<=elementos.size()){
            h=elementos.get(n-1);
        }    
        return h; 
    }
    
    public void adicione(EnIsla e){
        elementos.add(e);
    }

    public int numeroEnIsla(){
        return elementos.size();
    }

    public boolean enTesoro(EnIsla e){
        boolean tesoro=(tesoroPosX==e.getPosicionX() && tesoroPosY==e.getPosicionY());
        encontraronTesoro = encontraronTesoro || tesoro;
        return tesoro;
    }     
    
    public void actuen(){
       for (int i = 1; i <= numeroEnIsla(); i++){
           demeEnIsla(i).actue();
       }
    }

    public void paren(){
       for (int i = 1; i <= numeroEnIsla(); i++){
           demeEnIsla(i).pare();
       }
    }    

    public void decidan(){
        for (int i = 1; i <= elementos.size(); i++){
           demeEnIsla(i).decida();
       }
    }
    
    /**
      * Devuelve una lista con los elementos cercanos a un elemento dado en la isla
      * @param elemento elemento de tipo EnIsla
      * @return cercanos ArrayList de tipo EnIsla       
      */
    public ArrayList<EnIsla> elementosCercanos(EnIsla elemento){
        int x = elemento.getPosicionX(), y = elemento.getPosicionY();
        ArrayList<EnIsla> cercanos = new ArrayList<EnIsla>();
        for(EnIsla i: elementos){
            try{
                int auxX = i.getPosicionX(), auxY = i.getPosicionY();
                double distancia = Math.sqrt((x-auxX)*(x-auxX)+(y-auxY)*(y-auxY));
                if(distancia<=50) cercanos.add(i);
            }
            catch(Exception e){}
        }
        return cercanos;
    }
   /**
     * Limpia la lista elementos
     */
    public void clearElementos(){
        elementos.clear();
    }
}
