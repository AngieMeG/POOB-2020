
/**
 * Clase de excepciones para el proyecto
 *
 * @author Angie Medina - Jose Perez
 * @version 10/09/20
 */
public class ComboExcepcion extends Exception{
    public static final String COMBO_VACIO = "El combo no tiene productos.";
    public static final String PRECIO_DESCONOCIDO = "El precio del producto es desconocido.";
    public static final String PRODUCTO_DESCONOCIDO = "Este producto es desconocido.";
    
    /**
     * Genera una excepcion con el mensaje dado
     * @param message mensaje de la excepción lanzada
     */
    public ComboExcepcion(String message){
        super(message);
    }
}
