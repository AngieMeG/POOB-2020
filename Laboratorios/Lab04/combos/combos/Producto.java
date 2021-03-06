public class Producto{
    private String nombre;
    private int precio;

  
    public Producto(String nombre, int precio){
        this.nombre = nombre;
        this.precio = precio;
    }    
    

    public int precio() throws ComboExcepcion{
       if (nombre == null || nombre.equals("")) throw new ComboExcepcion(ComboExcepcion.PRODUCTO_DESCONOCIDO);
       if (precio <= 0) throw new ComboExcepcion(ComboExcepcion.PRECIO_DESCONOCIDO);
       return precio;
    }
}
