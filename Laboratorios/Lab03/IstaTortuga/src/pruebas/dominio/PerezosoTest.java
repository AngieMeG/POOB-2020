package dominio;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

/**
 * The test class PerezosoTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PerezosoTest
{
    /**
     * Default constructor for test class PerezosoTest
     */
    public PerezosoTest()
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
    public void deberiaSerDeColorAzul(){
        Isla islaTest = Isla.demeIsla();
        Perezoso pz1 = new Perezoso(islaTest, "pz1", 100, 100, 2);
        Color color = Color.BLUE;
        assertEquals(color, pz1.getColor());
    }
    
    @Test
    public void noDeberiaActuar(){
        Isla islaTest = Isla.demeIsla();
        Perezoso pz1 = new Perezoso(islaTest, "pz1", 100, 120, 2);
        pz1.actue();
        int posX1 = pz1.getPosicionX(), posY1 = pz1.getPosicionY();
        assertTrue((100 == posX1) && (120 == posY1));
    }
    
    @Test
    public void deberiaActuar(){
        Isla islaTest = Isla.demeIsla();
        Perezoso pz1 = new Perezoso(islaTest, "pz1", 100, 120, 2);
        pz1.actue();pz1.actue();
        int posX1 = pz1.getPosicionX(), posY1 = pz1.getPosicionY();
        assertTrue((100 != posX1) || (120 != posY1));
    }
    
    @Test
    public void noDeberiaActuarMas(){
        Isla islaTest = Isla.demeIsla();
	islaTest.clearElementos();
        boolean prop1, prop2;
        Perezoso pz1 = new Perezoso(islaTest, "pz1", 100, 100, 1);
        pz1.actue();pz1.actue();
        int posX1 = pz1.getPosicionX(), posY1 = pz1.getPosicionY();
        pz1.actue();pz1.actue();
        int posX2 = (Integer) pz1.getPosicionX(), posY2 = (Integer)pz1.getPosicionY();
        assertTrue((posX1 == posX2) && (posY1 == posY2));
    }
    
    @Test
    public void deberiaParar(){
        Isla islaTest = Isla.demeIsla();
        Perezoso pz1 = new Perezoso(islaTest, "pz1", 100, 120, 2);
        pz1.decida();
        int posX1 = pz1.getPosicionX(), posY1 = pz1.getPosicionY();
        assertTrue((100 == posX1) && (120 == posY1));
    }
    
    @Test
    public void deberiaVolverAActuar(){
        Isla islaTest = Isla.demeIsla();
        Perezoso pz1 = new Perezoso(islaTest, "pz1", 100, 120, 1);
        Lago lago1 = new Lago(islaTest, "l1", 100, 125);
        islaTest.adicione(pz1);
        islaTest.adicione(lago1);
        pz1.actue();pz1.actue();
        int x1 = pz1.getPosicionX(), y1 = pz1.getPosicionY();
        for(int i=0; i<=lago1.getEnfriamiento();i++) lago1.actue();
        pz1.actue();pz1.actue();
        int x2 = pz1.getPosicionX(), y2 = pz1.getPosicionY();
        assertFalse(x1==x2 && y1==y2);
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
