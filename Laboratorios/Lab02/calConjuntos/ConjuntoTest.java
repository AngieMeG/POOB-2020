import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author   ECI
 * @version 2020-2
 */
public class ConjuntoTest
{
 
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
        
    }
 
    @Test    
    public void deberiaPasar(){
        assertTrue(true);
    }
    
    @Test
    public void deberiaFallar(){
        assertTrue(false);
    }

    @Test    
    public void deberiaErrar(){
        int x = 0/0;
    }

    @Test
    public void deberiaCrearConjuntosVacios(){
        String [] dVacio={};
        Conjunto vacio=new Conjunto();
        assertEquals(0,vacio.cardinal());
        vacio=new Conjunto(dVacio);
        assertEquals(0,vacio.cardinal());
    }
    
    @Test
    public void deberiaCrearLosConjuntosDelTama_oIndicado(){
        String[]  dSiglas= {"POOB","MBDA"};
        Conjunto siglas=new Conjunto(dSiglas);
        assertEquals(2,siglas.cardinal());
    }

    @Test
    public void deberiaCrearLosConjuntoConLosElementosDados(){
        String[]  dSiglas={"POOB","MBDA"};
        Conjunto siglas=new Conjunto(dSiglas);
        assertEquals(2,siglas.cardinal());
        assertTrue(siglas.pertenece("POOB"));
        assertTrue(siglas.pertenece("MBDA"));
        assertFalse(siglas.pertenece("AYED"));
    }
    
    @Test
    public void noDeberiaSerSensibleAMayusculas(){
        String [] dSiglas={"POOB","MBDA","poob","Poob"};
        Conjunto siglas=new Conjunto(dSiglas);
        assertEquals(2,siglas.cardinal());
        assertTrue(siglas.pertenece("POOB"));
        assertTrue(siglas.pertenece("poob"));
        assertTrue(siglas.pertenece("PooB"));
    }
    
    
    @Test
    public void deberiaCrearConjuntosQuitandoElementosRepetidos(){
        String [] dSiglas={"TPRO","POOB","MBDA","TPRO"};
        Conjunto siglas=new Conjunto(dSiglas);
        assertEquals(3,siglas.cardinal());    
    }    
    
    @Test
    public void deberiaCrearConjuntosEliminandoEspaciosInnecesarios(){
        String [] dMaterias={"Programacion orientada a objetos","Modelos y bases de datos","Modelos y bases de datos       ","Modelos y                  bases de datos"};
        Conjunto materias=new Conjunto(dMaterias);
        assertEquals(2,materias.cardinal());
        assertTrue(materias.pertenece(dMaterias[0]));
        assertTrue(materias.pertenece(dMaterias[1]));
        assertTrue(materias.pertenece(dMaterias[2]));
    }    
    
    @Test
    public void deberiaPoderExpresarUnConjuntoComoCadena(){
        String [] dCaso={"uno","dos","Tres","Cuatro"};
        Conjunto caso=new Conjunto(dCaso);
        String resultado="{CUATRO, DOS, TRES, UNO}";
        assertEquals(resultado,"{"+caso.toString()+"}"); 
    }
   
    // Prueba Union y interseccion
    @Test
    public void noDeberiaHaberElementosRepetidos(){
        String[] dCaso1 = {"uno", "dos", "tres"};
        Conjunto caso1 = new Conjunto(dCaso1);
        String[] dCaso2 = {"uno", "cuatro", "cinco"};
        Conjunto caso2 = new Conjunto(dCaso2);
        assertEquals("{CINCO, CUATRO, DOS, TRES, UNO}","{"+caso1.union(caso2).toString()+"}");
    }

    @Test
    public void deberiaTenerComoCardinalidadLaSumaDeLasCardinalidades(){
        String[] dCaso1 = {"A", "B", "D", "e"};
        Conjunto caso1 = new Conjunto(dCaso1);
        String[] dCaso2 = {"F","G","h"};
        Conjunto caso2 = new Conjunto(dCaso2);
        assertEquals(caso1.cardinal() + caso2.cardinal(),caso1.union(caso2).cardinal());
    }
    
    @Test
    public void deberiaTenerLosElementosQueSeRepitanEnLosConjuntos(){
        String[] dCaso1 = {"uno", "dos", "tres"};
        Conjunto caso1 = new Conjunto(dCaso1);
        String[] dCaso2 = {"uno","dos","cuatro", "cinco"};
        Conjunto caso2 = new Conjunto(dCaso2);
        assertEquals("{DOS, UNO}","{" + caso1.interseccion(caso2).toString() + "}");
    }
    
    // Prueba diferencia y diferencia simetrica
    @Test
    public void noDeberiaTenerLosElementosCompartidosConElOtroConjunto(){
        String[] dCaso1 = {"A", "B", "D", "e"};
        Conjunto caso1 = new Conjunto(dCaso1);
        String[] dCaso2 = {"A","B","c"};
        Conjunto caso2 = new Conjunto(dCaso2);
        assertEquals("{D, E}","{"+caso1.diferencia(caso2).toString()+"}");
    }
    
    @Test
    public void noDeberiaTenerLaInterseccionDeLosConjuntos(){
        String[] dCaso1 = {"A", "B", "D", "e"};
        Conjunto caso1 = new Conjunto(dCaso1);
        String[] dCaso2 = {"A","B","c"};
        Conjunto caso2 = new Conjunto(dCaso2);
        assertEquals("{C, D, E}","{"+caso1.diferenciaSimetrica(caso2).toString()+"}");
    }
    
    // Prueba producto
    @Test
    public void deberiaTenerComoCardinalLaMultiplicacionDeLasCardinalidades(){
        String[] dCaso1 = {"A", "B", "D", "e"};
        Conjunto caso1 = new Conjunto(dCaso1);
        String[] dCaso2 = {"A","B","c"};
        Conjunto caso2 = new Conjunto(dCaso2);
        assertEquals(caso1.cardinal()*caso2.cardinal(), caso1.producto(caso2).cardinal());
    
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
