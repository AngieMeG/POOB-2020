import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;
/**
 * @author ECI, 2018
 *
 */
public class Conjunto{
    
    private ArrayList<String> elementos;
    
    /**
     * Constructor del conjunto
     */
  
    public Conjunto () {
        elementos = new ArrayList <>();
        
    }

     /**
     * Constructor del conjunto dado un array de elementos
     */
    public Conjunto(String [] elementos){
        this.elementos = new ArrayList <>();
        for (int i = 0; i < elementos.length; i++){
            boolean flag = true;
            for(String elemento : this.elementos){
               if(elemento.equals(elementos[i].toUpperCase().replaceAll(" ",""))){
                    flag = false;
                    break;
                }
             }
            if (flag){this.elementos.add(elementos[i].toUpperCase().replaceAll(" ",""));}
        }
        
    }
    
    /**
     * Calcula el cardinal del conjunto
     * @return cardinal, el numero de elementos que tiene el conjunto
     */    
    public int cardinal() {
       return elementos.size();
    }

    /**
     * Verifica si un elemento pertenece al conjunto
     * @param elemento, el elemento a verificar
     * @return true, si el elemento pertenece al conjunto
     *         false, si no pertenece
     */      
    public boolean pertenece(String elemento){
       boolean flag = false;
       for (String e : elementos){
           if (e.equals(elemento.toUpperCase().replaceAll(" ", ""))){
               flag = true;
               break;
           }
       }
       return flag;
    }
    
    /**
     * Compara este conjunto con otro
     * @param c el conjunto a comparar
     * @return true, si los conjuntos son iguales
     *         false, si no
     */
    private boolean equals (Conjunto c) {
        int cont = 0;
        for (String elemento :  elementos){
            if (c.pertenece(elemento)){
                cont ++;
            }
            else{break;}
        }
        return (cont == elementos.size() && cont == c.cardinal()) ? true : false;
    }

    /** 
     * Compara si este conjunto es igual a otro 
     * @param o, debe ser el conjunto resultante
     */
    @Override
    public boolean equals (Object o) {
            return this.equals ((Conjunto) o);
    }
    
    /**
     * Compara si este conjunto esta contenido en otro
     * @param c el conjunto a comparar
     */
    public boolean contenido(Conjunto c){
        int cont = 0;
        for (String elemento :  elementos){
            if (c.pertenece(elemento)){
                cont ++;
            }
            else{break;}
        }
        return cont == elementos.size() ? true : false;
    }
    
    /**
     * Hace la operacion de union sobre dos conjuntos
     * @param c el otro conjunto a operar
     * @return el conjunto resultante
     */
    public Conjunto union(Conjunto c){
        Conjunto nuevo = new Conjunto();
        nuevo.elementos.addAll(this.elementos);
        for (String elemento : c.getElementos()){
            if (!nuevo.elementos.contains(elemento)){
                nuevo.elementos.add(elemento);
            }
        }
        return nuevo;
    }
    
    /**
     * Hace la operacion de interseccion entre dos conjuntos
     * @param c el otro conjunto a operar
     * @return el conjunto resultante
     */
    public Conjunto interseccion(Conjunto c){
       Conjunto nuevo = new Conjunto();
       for (String elemento : c.getElementos()){
           if (elementos.contains(elemento)){
               nuevo.elementos.add(elemento);
           }
       }
       return nuevo;        
    }
    
    /**
     * Hace la operacion de diferencia entre dos conjuntos
     * @param c el otro conjunto a operar
     * @return el conjunto resultante
     */
    public Conjunto diferencia(Conjunto c){
       Conjunto nuevo = new Conjunto();
       ArrayList<String> elementosC = c.getElementos();
       for (String elemento : elementos){
           if (!elementosC.contains(elemento)){
               nuevo.elementos.add(elemento);
           }
       }
       return nuevo;        
    }    
    
    /**
     * Hace la operacion de diferencia simetrica entre dos conjuntos
     * @param c el conjunto a operar
     * @return el conjunto resultante
     */
    public Conjunto diferenciaSimetrica(Conjunto c){
       Conjunto nuevo = new Conjunto();
       nuevo.elementos.addAll(this.diferencia(c).getElementos());
       nuevo.elementos.addAll(c.diferencia(this).getElementos());
       return nuevo;        
    }    
    
    /**
     * Hace la operacion de producto entre dos conjuntos
     * @param c el conjunto con el que operar
     * @return el conjunto resultante
     */
    public Conjunto producto(Conjunto c){
       Conjunto nuevo = new Conjunto();
       for (String elemento : elementos){
           for (String elementoC : c.getElementos()){
               nuevo.elementos.add("("+elemento +" - "+ elementoC+")");
           }
       }
       return nuevo;
    }            
        
    /** 
     * Retorna una cadena que describe este conjunto con los elementos en mayusculas, entre corchetes, ordenados alfabeticamente y separados por coma
     */
    @Override
    public String toString () {
          String[] nuevo = new String[this.cardinal()];
          int cont = 0;
          for (int i = 0; i < this.cardinal(); i++){
              nuevo[i] = elementos.get(i);
              cont += elementos.get(i).length() + 2;
          }
          cont = cont>0 ? cont:2;
          Arrays.sort(nuevo);
          String s = Arrays.toString(nuevo).substring(1, cont-1);
          return s;
    }
    
    public ArrayList<String> getElementos(){
        return this.elementos;
    }

}
