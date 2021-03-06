import java.util.ArrayList;

public class Combo{
    public final static int COSTO_POR_OMISION = 10000;
    
    private String nombre;
    private int descuento;
    private ArrayList<Producto> productos;
    
    public Combo(String nombre, int descuento){
        this.nombre = nombre;
        this.descuento = descuento;
        productos= new ArrayList<Producto>();
    }

    public void adProducto(Producto p){
        productos.add(p);
    }
    
    /**
     * Calcula el precio de un combo
     * @return el precio del combo
     * @throws ComboExcepcion COMBO_VACIO, si no tiene productos; PRODUCTO_DESCONOCIDO, si hay un producto sin nombre; 
     * y PRECIO_DESCONOCIDO si un producto tiene error en el precio 
     */
    public int precio() throws ComboExcepcion{
       if (productos.size() == 0) throw new ComboExcepcion(ComboExcepcion.COMBO_VACIO);
       int precio = 0;
       for(Producto p : productos) precio += p.precio();
       return (int) precio * getDescuento()/100;
    }
    
    /**
     * Calcula el precio por omision
     * Para los productos con problemas se asume COSTO_POR_OMISION
     * @return el costo de un combo
     * @throws ComboExcepcion COMBO_VACIO, si no tiene productos
     */
    public int precioOmision() throws ComboExcepcion{
       if (productos.size() == 0) throw new ComboExcepcion(ComboExcepcion.COMBO_VACIO);
       int precio = 0;
       for(Producto p : productos) {
           try{
               precio += p.precio();    
           } catch(ComboExcepcion e){
               precio += COSTO_POR_OMISION;
           }
       }
       return (int) precio * getDescuento()/100;
    }   
    
    /**
     * Calcula el precio asumido
     * Se asume que el precio de los productos desconocidos es el del primer producto conocido
     * Se asume que el precio de los productos sin precio es el del ultimo producto 
     * Si el combo esta vacio el precio asumido es cero
     * @return el precio asumido de un prodcto
     */
    public int precioAsumido(){
       int primerPrecio = 0, ultimoPrecio = 0, precio = 0;
       for(Producto p : productos) {
           try{
               int precioProducto = p.precio();
               precio += precioProducto;
               if (primerPrecio == 0)primerPrecio = precioProducto;
               ultimoPrecio = precioProducto;
           } catch(ComboExcepcion e){
               if (e.getMessage().equals(ComboExcepcion.PRODUCTO_DESCONOCIDO)) precio += primerPrecio;
               else if(e.getMessage().equals(ComboExcepcion.PRECIO_DESCONOCIDO)) precio += ultimoPrecio;
           }
       }
       return (int) precio * getDescuento()/100;
    }
    
    /**
     * Retorna el descuento del producto
     * @return el descuento en forma decimal
     */
    public int getDescuento(){
        return 100 - descuento;
    }
}
