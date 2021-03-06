package pruebas;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dominio.Isla;
import dominio.Rebelde;

import java.awt.Color;

/**
 * The test class RebeldeTest.
 *
 * @author  Angie Medina - Jose Perez
 * @version 1/10/20
 */
public class RebeldeTest
{
    /**
     * Default constructor for test class RebeldeTest
     */
    public RebeldeTest(){
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
    }
    
    @Test
    public void deberiaSerDeColorRojo(){
        Isla islaTest = Isla.demeIsla();
        Rebelde r1 = new Rebelde(islaTest, "r1", 100, 100);
        Color color = Color.RED;
        assertEquals(color, r1.getColor());
    }
    
    @Test
    public void noDeberiaActuar(){
        Isla islaTest = Isla.demeIsla();
        Rebelde r1 = new Rebelde(islaTest, "r1", 100, 120);
        r1.actue();
        int posX1 = r1.getPosicionX(), posY1 = r1.getPosicionY();
        assertTrue((100 == posX1) && (120 == posY1));
    }
    
    @Test
    public void deberiaActuar(){
        Isla islaTest = Isla.demeIsla();
        Rebelde r1 = new Rebelde(islaTest, "r1", 100, 120);
        r1.actue(); r1.actue(); r1.actue();
        int posX1 = r1.getPosicionX(), posY1 = r1.getPosicionY();
        assertTrue((100 != posX1) || (120 != posY1));
    }
    
    @Test
    public void deberiaActuarSiOrdenanParar(){
        Isla islaTest = Isla.demeIsla();
        Rebelde r1 = new Rebelde(islaTest, "r1", 100, 120);
        r1.pare();
        int posX1 = r1.getPosicionX(), posY1 = r1.getPosicionY();
        assertTrue((100 != posX1) || (120 != posY1));
    }
    
    @Test
    public void deberiaParar(){
        Isla islaTest = Isla.demeIsla();
        Rebelde r1 = new Rebelde(islaTest, "r1", 100, 120);
        r1.actue(); r1.actue(); r1.actue();
        int posX1 = r1.getPosicionX(), posY1 = r1.getPosicionY();
        r1.decida();
        int posX2 = r1.getPosicionX(), posY2 = r1.getPosicionY();
        assertTrue((posX1 == posX2) && (posY1 == posY2));
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown(){
    }
}
