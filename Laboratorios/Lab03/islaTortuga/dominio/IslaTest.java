package dominio;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * The test class IslaTest.
 *
 * @author  Angie Medina - Jose Perez
 * @version 1/10/20
 */
public class IslaTest
{
    /**
     * Default constructor for test class IslaTest
     */
    public IslaTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        
    }
    
    @Test
    public void deberiaDarUnaIsla(){
        Isla islaTest = Isla.demeIsla();
        assertTrue(islaTest != null);
    }
    
    @Test
    public void deberiaAdicionarLosElementos(){
        Isla islaTest = Isla.demeIsla();
        int cont = islaTest.numeroEnIsla();
        Pirata p1 = new Pirata(islaTest, "p1", 100, 100);
        Pirata p2 = new Pirata(islaTest, "p2", 150, 150);
        islaTest.adicione(p1); islaTest.adicione(p2);
        assertEquals(cont + 2, islaTest.numeroEnIsla());
    }
    
    @Test
    public void deberiaDarLosElementosAgregados(){
        Isla islaTest = Isla.demeIsla();
        int cont = islaTest.numeroEnIsla();
        Pirata p1 = new Pirata(islaTest, "p1", 100, 100);
        Pirata p2 = new Pirata(islaTest, "p2", 150, 150);
        islaTest.adicione(p1); islaTest.adicione(p2);
        assertTrue(p2.equals(islaTest.demeEnIsla(cont+2)));
    }
    
    @Test
    public void deberiaMoverLosElementos(){
        Isla islaTest = Isla.demeIsla();
        int cont = islaTest.numeroEnIsla();
        Pirata p1 = new Pirata(islaTest, "p1", 100, 100);
        islaTest.adicione(p1);
        islaTest.actuen();
        Pirata last = (Pirata) islaTest.demeEnIsla(cont+1);
        int posX2 = last.getPosicionX(), posY2 = last.getPosicionY();
        assertTrue((100 != posX2) || (100 != posY2));
    }
    
    @Test
    public void noDeberiaMoverLosElementos(){
        Isla islaTest = Isla.demeIsla();
        int cont = islaTest.numeroEnIsla();
        Pirata p1 = new Pirata(islaTest, "p1", 100, 100);
        islaTest.adicione(p1);
        islaTest.paren();
        Pirata last = (Pirata) islaTest.demeEnIsla(cont+1);
        int posX2 = last.getPosicionX(), posY2 = last.getPosicionY();
        assertTrue((100 == posX2) || (100 == posY2));
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
