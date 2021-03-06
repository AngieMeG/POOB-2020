package dominio;

import java.awt.Color;

/**
 * Un pirata que es bastante minusioso
 *
 * @author Angie Medina - Jose Perez
 * @version 1/10/20
 */
public class Minusioso extends Pirata{
	
    private String movimiento;
    
    /**
     * Crea un pirata minusioso en la posicion <i>(posicionx, posiciony)</i>
     * @param isla, la isla en la que se va a encontrar
     * @param name, nombre del pirata
     * @param posicionx, coordenada x de la posicion
     * @param posiciony, coordenada y de la posicion
     */
    public Minusioso(Isla isla,String name,int posicionx, int posiciony){
        super(isla, name, posicionx, posiciony);
        color = Color.ORANGE;
        palabras = "";
        movimiento = "++";
    }
    
    /**
     * Actua de manera que eventualmente va a llegar al tesoro de la isla
     */
    @Override
    public void actue(){
        char d = movimiento.charAt(0)=='+'?'E':'O';
        movimiento();
        if(puedeMoverse(d)){
            muevase(d);
        }
        else{
            d = movimiento.charAt(1)=='+'?'N':'S';
            if(puedeMoverse(d)){
                muevase(d);
            }
            else{
                d = d=='N'?'S':'N';
                muevase(d);
            }   
            movimiento = (movimiento.charAt(0)=='+'?"-":"+")+(d=='N'?"+":"-");
        }
    }
    
    /**
     * Cuando para habla mucho
     */
    @Override
    public void pare(){
        super.pare();
        if(r.nextBoolean()){
            palabras = "Soy un pirata minusioso! Y... Estoy cansado. No puedo descansar hasta encontrar el tesoro, estoy seguro de que est? cerca, puedo"+
            " sentirlo, sera mio... mi precioso... *murmullos ininteligibles*";
        }
        else palabras = "Soy un pirata minusioso! Y... debo encontrar el tesoro, se que estoy cerca";
    }
}
