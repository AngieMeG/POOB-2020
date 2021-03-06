package dominio; 
 
/**
 * Curso ofrecidos por la decanatura en el periodo intermedio
 * @author Angie Medina - Jose Perez
 * @version 21/10/20
 *
 */
public class Mooc{
    private String nombre;
    private String area;   
    private String objetivo;
    private String distribuidor;
    private int semanas;  

 
    public Mooc(String nombre, String area,  String objetivo, String  distribuidor, int semanas) throws IEMOISExcepcion{
    	if (nombre.trim().equals("")) throw new IEMOISExcepcion(IEMOISExcepcion.NO_NOMBRE);
        this.nombre = nombre.trim();
        this.area = area.trim();
        this.objetivo = objetivo.trim();
        this.distribuidor = distribuidor.trim();
        this.semanas = semanas;        
    }
    
    /**
     * Devuelve el nombre del curso
     * @return el nombre del curso
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Devuelve el area de enfasis del curso
     * @return el area del curso
     */
    public String getArea(){
        return area;
    }

    /**
     * Devuelve el objetivo del curso
     * @return el objetivo del curso
     */
    public String getObjetivo(){
        return objetivo;
    }
    
    /**
     * Devuelve la entidad que distribuye el curso
     * @return la distribuidora del curso
     */
    public String getDistribuidor(){
        return distribuidor;
    }    
    
    /**
     * Devuelve las semanas que dura el curso
     * @return el numero de semans que dura el curso
     */
    public int getSemanas(){
        return semanas;
    }
    
    /**
     * Devuelve la información del curso en forma de cadena
     * @return la información del curso en forma de cadena
     */
    public String toString(){
        return nombre + "\n" + area + "\n"+ distribuidor + "\nSemanas:" + semanas+"\n" +objetivo ;
    }

}
