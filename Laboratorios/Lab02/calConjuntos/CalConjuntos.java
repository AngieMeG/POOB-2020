import java.util.Stack;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
/** Calculadora.java
 * Representa una calculadora de conjuntos
 * @author ESCUELA 2020-2
 */
    
public class CalConjuntos{

    private Stack<Conjunto> operandos;
    private int cantOperandos;
    private boolean ultimaAccion;
    private Conjunto universal;

    
    /**
     * Constructor de CalConjuntos
     */
    public CalConjuntos(){
        operandos = new Stack<Conjunto> ();
        cantOperandos = 0;
        ultimaAccion = true;
        universal = new Conjunto();
    }
    
    /**
     * Adiciona un conjunto a la calculadora
     * @param dConjunto un array String que contiene el conjunto a adicionar
     */
    public void adicione(String [] dConjunto){       
        operandos.push(new Conjunto(dConjunto));
        cantOperandos ++;
        universal = universal.union(operandos.peek());
        ultimaAccion = true;
    }
    
    /**
     * Elimina el ultimo operando de la calculadora
     */
    public void elimine(){
        if (cantOperandos > 0){
            operandos.pop();
            cantOperandos --;
            ultimaAccion = true;
        }
        else{
            //Toolkit.getDefaultToolkit().beep(); 
            //JOptionPane.showMessageDialog(null,"No hay suficientes operandos" ,"Error",JOptionPane.ERROR_MESSAGE);
            ultimaAccion = false;
        }
    }
    
    /**
     * Duplica el ultimo elemento el numero de veces indicada
     * @param times el numero de veces a duplicar el ultimo elemento
     */
    public void duplique(int times){
        Conjunto aDuplicar = operandos.pop();
        cantOperandos --;
        for (int i = 0; i < times + 1; i++){
            operandos.push(aDuplicar);
            cantOperandos ++;
        }
    }
    
    /**
     * Retorna la cantidad de operandos que estan en la calculadora
     * @return el numero de operandos que estan en la calculadora
     */
    public int cardinal(){
        ultimaAccion = true;
        return cantOperandos;
    }
  
    /**
     * Retorna la representacion como cadena del conjunto del tope de la pila
     * @return la representacion como cadena del conjunto del tope de la pila
     */
    public String consulte(){
        Conjunto topePila = operandos.pop();
        operandos.add(topePila);
        String cadenaTopePila = topePila.toString();
        ultimaAccion = true;
        return "{" + cadenaTopePila + "}";
    }
    
    /**
     * Opera los dos ultimos conjuntos
     * @param operacion, valido 'U' (union), 'I' (Interseccion), '-' (Diferencia), '_' (Diferencia simétrica), 'x' (Producto)
     */
    public void opere(char operacion){
        Conjunto conjunto1 = operandos.pop();
        Conjunto conjunto2 = new Conjunto();
        if(operacion!='c') conjunto2 = operandos.pop();
        switch (operacion){
            case 'U':
                this.adicione(conjunto2.union(conjunto1).toString().split(","));
                ultimaAccion = true;
                break;
            case 'I':
                this.adicione(conjunto2.interseccion(conjunto1).toString().split(","));
                ultimaAccion = true;
                break;
            case '-':
                this.adicione(conjunto2.diferencia(conjunto1).toString().split(","));
                ultimaAccion = true;
                break;
            case '_':
                this.adicione(conjunto2.diferenciaSimetrica(conjunto1).toString().split(","));
                ultimaAccion = true;
                break;
            case 'x':
                this.adicione(conjunto2.producto(conjunto1).toString().split(","));
                ultimaAccion = true;
                break;
            case 'c':
                this.adicione(universal.diferencia(conjunto1).toString().split(","));
                ultimaAccion = true;
                break;
            default:
                //Toolkit.getDefaultToolkit().beep(); 
                //JOptionPane.showMessageDialog(null,"Operacion invalida" ,"Error",JOptionPane.ERROR_MESSAGE);
                ultimaAccion = false;
        }
    }
    
    /**
     * Retorna si la ultima acción realizada por la calculadora fue exitosa o no
     * @return true si la ultima accion se pudo realizar
     *         false en caso contrario
     */
    public boolean ok(){
        return ultimaAccion;
    }
}
    