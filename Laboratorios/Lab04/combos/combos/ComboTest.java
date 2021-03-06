


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ComboTest{
    public ComboTest(){
    }
    
    @Before
    public void setUp(){
    }
    
    @Test
    public void deberiaCalcularElCostoDeUnCombo(){
        Combo c = new Combo("Rapido", 10);
        c.adProducto(new Producto("Coca Cola", 1000));
        c.adProducto(new Producto("Hamburguesa",8000));
        c.adProducto(new Producto("Papas", 1000));
        int precioEsperado = (int) 3000 * 90/100;
        try {
           assertEquals(9000,c.precio());
        } catch (ComboExcepcion e){
            fail("Lanzó excepcion");
        }    
    }    
    
    @Test
    public void deberiaLanzarExcepcionSiElComboNoTieneProductos(){
        Combo c = new Combo("Rapido", 10);
        try { 
           int precio = c.precio();
           fail("No lanzó excepcion");
        } catch (ComboExcepcion e) {
            assertEquals(ComboExcepcion.COMBO_VACIO,e.getMessage());
        }    
    }    
    
    
   @Test
    public void deberiaLanzarExcepcionSiNoSeConoceElPrecioDeUnProducto(){
        Combo c = new Combo("Rapido", 10);
        c.adProducto(new Producto("Coca Cola", 1000));
        c.adProducto(new Producto("Hamburguesa",8000));
        c.adProducto(new Producto("Papas", -1000));
        try { 
           int precio=c.precio();
           fail("No lanza la excepcion");
        } catch (ComboExcepcion e) {
            assertEquals(ComboExcepcion.PRECIO_DESCONOCIDO,e.getMessage());
        }    
    }     
    
   @Test
    public void deberiaLanzarExcepcionSiNoSeConoceUnProducto(){
        Combo c = new Combo("Rapido", 10);
        c.adProducto(new Producto("Coca Cola", 1000));
        c.adProducto(new Producto(null,8000));
        c.adProducto(new Producto("Papas", -1000));
        try { 
           int precio=c.precio();
           fail("No lanza la excepcion");
        } catch (ComboExcepcion e) {
            assertEquals(ComboExcepcion.PRODUCTO_DESCONOCIDO,e.getMessage());
        }    
    }  
    
   @Test
   public void deberiaCalcularElPrecioOmision(){
        Combo c = new Combo("Rapido", 10);
        c.adProducto(new Producto("Coca Cola", 1000));
        c.adProducto(new Producto("Hamburguesa",8000));
        c.adProducto(new Producto("Papas", 1000));
        int precio = 0;
        int precioEsperado = (int) 10000 * 90/100;
        try { 
           precio = c.precioOmision();
        } catch (ComboExcepcion e) {
           fail("Lanzo excepcion.");
        }    
        assertEquals(precioEsperado, precio);
   }    
   
   @Test
   public void deberiaLanzarExcepcionSiElComboNoTieneProductosPrecioOmision(){
        Combo c = new Combo("Rapido", 10);
        try { 
           int precio = c.precioOmision();
           fail("No lanzó excepcion");
        } catch (ComboExcepcion e) {
            assertEquals(ComboExcepcion.COMBO_VACIO,e.getMessage());
        }    
   }    
    
    
   @Test
    public void deberiaAsumirUnPrecioporOmision(){
        Combo c = new Combo("Rapido", 10);
        c.adProducto(new Producto(null, 1000));
        c.adProducto(new Producto("Hamburguesa", -1));
        c.adProducto(new Producto(null, -2));
        c.adProducto(new Producto("Papas", 2000));
        int precio = 0;
        int precioEsperado = (int) 32000 * 90/100;
        try { 
           precio = c.precioOmision();
        } catch (ComboExcepcion e) {
            fail("Lanza excepcion.");
        }    
        assertEquals(precioEsperado, precio);
    }     
    
   @Test
   public void deberiaCalcularElPrecioAsumido(){
        Combo c = new Combo("Rapido", 10);
        c.adProducto(new Producto("Coca Cola", 1000));
        c.adProducto(new Producto("Hamburguesa",8000));
        c.adProducto(new Producto("Papas", 1000));
        int precio = 0;
        int precioEsperado = (int) 10000 * 90/100;
        precio = c.precioAsumido();
        assertEquals(precioEsperado, precio);
   } 
   
   @Test
   public void deberiaTomarElPrimerPrecioEnLosDemasProductos(){
       Combo c = new Combo("Rapido", 10);
       c.adProducto(new Producto("Coca cola", 1000));
       c.adProducto(new Producto(null, 2000));
       c.adProducto(new Producto(null, 500));
       int precio = 0;
       int precioEsperado = (int) 3000 * 90/100;
       precio = c.precioAsumido();
       assertEquals(precioEsperado, precio);
   }
   
   @Test
   public void deberiaTomarElUltimoPrecioEnLosDemasProductos(){
       Combo c = new Combo("Rapido", 10);
       c.adProducto(new Producto("Coca cola", 1000));
       c.adProducto(new Producto("Papas", 2000));
       c.adProducto(new Producto("Hamburguesa", -1));
       c.adProducto(new Producto("Nachos", -2));
       int precio = 0;
       int precioEsperado = (int) 7000 * 90/100;
       precio = c.precioAsumido();
       assertEquals(precioEsperado, precio);
   }
    
   @Test
   public void deberiaTomarElUltimoOPrimerPrecioEnLosDemasProductos(){
       Combo c = new Combo("Rapido", 10);
       c.adProducto(new Producto("Coca cola", 1000));
       c.adProducto(new Producto("Papas", 2000));
       c.adProducto(new Producto(null, 3000));
       c.adProducto(new Producto("Nachos", -2));
       int precio = 0;
       int precioEsperado = (int) 6000 * 90/100;
       System.out.println(precioEsperado);
       precio = c.precioAsumido();
       assertEquals(precioEsperado, precio);
   }
   
   @After
   public void tearDown(){
   }
}
