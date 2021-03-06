import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;
/**
 * The test class calConjuntosTest.
 *
 * @author  Tatiana Medina - Jose Perez
 * @version 2/08/20
 */
public class calConjuntosTest
{
    private CalConjuntos calculadora;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        calculadora = new CalConjuntos();
    }

    // Pruebas del primer ciclo: creacion, adicion, consulte
    @Test
    public void deberiaCrearCalculadoraVacia(){
        assertEquals(0,calculadora.cardinal());
    }
    
    @Test
    public void deberiaAdicionarUnOperando(){
        String[] conjunto1 = {"uno", "dos", "tres"};
        calculadora.adicione(conjunto1);
        assertEquals(1, calculadora.cardinal());
    }
    
    @Test
    public void deberiaPoderExpresarElUltimoOperandoComoCadena(){
        String[] conjunto1 = {"A","B", "C"};
        calculadora.adicione(conjunto1);
        assertEquals("{A, B, C}", calculadora.consulte());
    }
    
    @Test
    public void deberiaMantenerSuCardinalAlConsultar(){
        String[] conjunto1 = {"A","B", "C"};
        calculadora.adicione(conjunto1);
        calculadora.consulte();
        assertEquals(1, calculadora.cardinal());
    }
    
    @Test
    public void deberiaRealizarceLaAccion(){
        String[] conjunto1 = {"uno", "dos", "tres"};
        calculadora.adicione(conjunto1);
        assertTrue(calculadora.ok());
    }
    
    @Test
    public void noDeberiaRealizarceLaAccion(){
        calculadora.elimine();
        assertFalse(calculadora.ok());
    }
    
    @Test
    public void deberiaTenerTamaño1(){
        String[] conjunto1 = {"rojo","verde", "azul"};
        calculadora.adicione(conjunto1);
        assertEquals(1, calculadora.cardinal());
    }
    
    @Test
    public void noDeberiaOperarConjuntos(){
        String[] dCaso1 = {"uno", "dos", "tres"};
        calculadora.adicione(dCaso1);
        calculadora.adicione(dCaso1);
        calculadora.opere('a');
        assertFalse(calculadora.ok());
    }
    
    // Pruebas del segundo ciclo: Union e Interseccion
    @Test
    public void noDeberiaHaberElementosRepetidos(){
        String[] dCaso1 = {"uno", "dos", "tres"};
        calculadora.adicione(dCaso1);
        String[] dCaso2 = {"uno", "cuatro", "cinco"};
        calculadora.adicione(dCaso2);
        calculadora.opere('U');
        assertEquals("{CINCO, CUATRO, DOS, TRES, UNO}",calculadora.consulte());
    }
    
    @Test
    public void deberiaTenerLosElementosQueSeRepitanEnLosConjuntos(){
        String[] dCaso1 = {"uno", "dos", "tres"};
        String[] dCaso2 = {"uno","dos","cuatro", "cinco"};
        calculadora.adicione(dCaso1); calculadora.adicione(dCaso2);
        calculadora.opere('I');
        assertEquals("{DOS, UNO}", calculadora.consulte());
    }
    
    // Pruebas del tercer ciclo: Diferencia y Diferencia simetrica
    @Test
    public void noDeberiaTenerLosElementosCompartidosConElOtroConjunto(){
        String[] dCaso1 = {"A", "B", "D", "e"};
        String[] dCaso2 = {"A","B","c"};
        calculadora.adicione(dCaso1); calculadora.adicione(dCaso2);
        calculadora.opere('-');
        assertEquals("{D, E}",calculadora.consulte());
    }
    
    @Test
    public void noDeberiaTenerLaInterseccionDeLosConjuntos(){
        String[] dCaso1 = {"A", "B", "D", "e"};
        String[] dCaso2 = {"A","B","c"};
        calculadora.adicione(dCaso1); calculadora.adicione(dCaso2);
        calculadora.opere('_');
        assertEquals("{C, D, E}", calculadora.consulte());
    }
    
    // Pruebas del cuarto ciclo: Elimine y duplique
    @Test
    public void deberiaTenerUnTamañoMenorPorUno(){
        String[] conjunto1 = {"uno", "dos", "tres"};
        calculadora.adicione(conjunto1);
        int cardinalAntes = calculadora.cardinal();
        calculadora.elimine();
        assertEquals(cardinalAntes - 1, calculadora.cardinal());
    }
    
    @Test
    public void deberiaAumentarElTamañoEnN(){
        String[] conjunto1 = {"uno", "dos", "tres"};
        calculadora.adicione(conjunto1);
        int cardinalAntes = calculadora.cardinal();
        calculadora.duplique(3);
        assertEquals(cardinalAntes + 3, calculadora.cardinal());
    }
    
    // Pruebas del quinto ciclo: Producto
    @Test
    public void deberiaTenerComoCardinalLaMultiplicacionDeLasCardinalidades(){
        String[] dCaso1 = {"A", "B", "D", "e"};
        Conjunto caso1 = new Conjunto(dCaso1);
        String[] dCaso2 = {"A","B","c"};
        Conjunto caso2 = new Conjunto(dCaso2);
        calculadora.adicione(dCaso1); calculadora.adicione(dCaso2);
        calculadora.opere('x');
        Conjunto producto = new Conjunto(calculadora.consulte().replace("{","").replace("}","").split(","));
        assertEquals(caso1.cardinal()*caso2.cardinal(), producto.cardinal());
    }
    
    // Pruebas del sexto ciclo: Complemento
    @Test
    public void noDeberiaTenerLosElementosCompartidosConElPrimerConjunto(){
        String[] dCaso1 = {"A", "B", "D", "e","f","g","h","i"};
        String[] dCaso2 = {"A","B","c"};
        calculadora.adicione(dCaso1); calculadora.adicione(dCaso2);
        calculadora.opere('c');
        assertEquals("{D, E, F, G, H, I}",calculadora.consulte());
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
