package dominio;



import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.awt.Color;

/**
 * The test class PalmeraTest.
 *
 * @author  Angie Medina
 * @version 1/10/20
 */
public class PalmeraTest
{
    /**
     * Default constructor for test class PalmeraTest
     */
    public PalmeraTest()
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
    public void deberiaSerVerde(){
        Isla islaTest = Isla.demeIsla();
        Palmera pa1 = new Palmera(islaTest, "pa1", 100, 100);
        Color color = Color.GREEN;
        assertEquals(color, pa1.getColor());
    }
    
    @Test
    public void deberiaSerVerdeOscura(){
        Isla islaTest = Isla.demeIsla();
        Palmera pa1 = new Palmera(islaTest, "pa1", 100, 100);
        pa1.actue();
        Color color = Color.GREEN.darker();
        assertEquals(color, pa1.getColor());
    }
    
    @Test
    public void deberiaSerVerdeClara(){
        Isla islaTest = Isla.demeIsla();
        Palmera pa1 = new Palmera(islaTest, "pa1", 100, 100);
        pa1.pare();
        System.setProperty("brighterGreen", "#7fff68");
        Color brighterGreen = Color.getColor("brighterGreen");
        assertEquals(brighterGreen, pa1.getColor());
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
