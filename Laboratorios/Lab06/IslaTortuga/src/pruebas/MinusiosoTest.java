package pruebas;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dominio.Isla;
import dominio.Minusioso;

import java.awt.Color;

/**
 * The test class MinusiosoTest.
 *
 * @author  Angie Medina - Jose Perez
 * @version 1/10/20
 */
public class MinusiosoTest
{
    /**
     * Default constructor for test class MinusiosoTest
     */
    public MinusiosoTest()
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
    public void deberiaSerDeColorNaranja(){
        Isla islaTest = Isla.demeIsla();
        Minusioso r1 = new Minusioso(islaTest, "m1", 100, 100);
        Color color = Color.ORANGE;
        assertEquals(color, r1.getColor());
    }
    
    @Test
    public void deberiaSubir(){
        Isla islaTest = Isla.demeIsla();
        Minusioso r1 = new Minusioso(islaTest, "m1", 100, 0);
        int yi = r1.getPosicionY();
        for(int i=0;i<500;i++) r1.actue();
        assertTrue(yi<r1.getPosicionY());
    }

    @Test
    public void deberiaBajar(){
        Isla islaTest = Isla.demeIsla();
        Minusioso r1 = new Minusioso(islaTest, "m1", 100, 500);
        int yi = r1.getPosicionY();
        for(int i=0;i<500;i++) r1.actue();
        assertTrue(yi>r1.getPosicionY());
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
