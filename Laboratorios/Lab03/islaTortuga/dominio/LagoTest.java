package dominio;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;
import java.util.*;

/**
 * The test class PalmeraTest.
 *
 * @author  Angie - Medina
 * @version 1/10/20
 */
public class LagoTest
{
    /**
     * Default constructor for test class PalmeraTest
     */
    public LagoTest()
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
    public void deberiaSerAzul(){
        Isla isla = Isla.demeIsla();
        Lago Lago1 = new Lago(isla, "Lago1", 100, 100);
        Color color = Color.BLUE;
        assertEquals(color, Lago1.getColor());
    }
    
    @Test
    public void deberiaSerAzulClaro(){
        Isla isla = Isla.demeIsla();
        Lago Lago1 = new Lago(isla, "Lago1", 100, 100);
        for(int i=0; i<=Lago1.getEnfriamiento();i++){
            Lago1.actue();
        }
        Color color = Color.CYAN;
        assertEquals(color, Lago1.getColor());
    }
    
    @Test
    public void deberiaSerAzulClaroCadaNTurnos(){
        ArrayList<String> colors = new ArrayList<String>(), ans = new ArrayList<String>();
        Isla isla = Isla.demeIsla();
        boolean esAzulClaro = true;
        Lago Lago1 = new Lago(isla, "Lago1", 100, 100);
        for(int i=0; i<3; i++){
            for(int j=0; j<=Lago1.getEnfriamiento();j++){
                Lago1.actue();
            }
            if(!Lago1.getColor().equals(Color.CYAN)) esAzulClaro = false;
        }
        assertEquals(true,esAzulClaro);
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
