package dominio; 

import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Se busca mantener un cat醠ogo de los MOOC ofrecidos
 * por la decanatura en el per韔do intermedio a sus estudiantes
 * en el programa IEMOIS
 * @author Angie Medina - Jose Perez
 * @version 21/10/20
 *
 */
public class Iemois{
    private LinkedList <Mooc> cursos;



    public Iemois(){
        cursos = new LinkedList<Mooc>();
    }
    
    /**
     * Adiciona algunos cursos
     */
    public void adicione(){
    	try {
        	Mooc ejemplos[] = {
	        new Mooc("Databases","Bases de Datos", 
	        "En este curso aprender谩 las tecnolog铆as b谩sicas de las bases de datos. Las bases de datos son necesarias en todas las tecnolog铆as usadas por"+
	        "la mayor铆a de las personas todos los dias: sitios web, sistema de comunicaciones, sistemas bancarios, video juegos y cualquier otro software"+
	        "o herramienta tecnol贸gica que deba mantener informaci贸n persistente. Adem谩s de la persistencia, las bases de datos ofrecen otras propiedades"+
	        "que las hacen excepcionalmente 煤tiles y convenientes: confiabilidad, eficiencia, escalabiliad, control de concurrencia, abstracci贸n de datos"+
	        " y lenguajes de alto nivel",
	        "Stanford Lagunita", 13),        
            new Mooc("Introduction to Computer Science and Programming Using Python","Programaci贸n", 
	        "Este curso es el primero de una secuencia de dos cursos: Introduction to Computer Science and Programming Using Python,"+
	        " y Introduction to Computational Thinking and Data Science. Ellos fueron dise帽ado para ayudar a personas con poca experiencia en ciencias de"+
            " la computaci贸n y programaci贸n a pensar computacionalmente y a escribir programas para abordar problemas 煤tiles",
            "Edx", 9),          
            new Mooc("Introduction to Computational Thinking and Data Science","Ciencia de Datos", 
	        "Este curso aprender谩 c贸mo usar la computaci贸n para lograr una variedad de objetivos y le brindar谩 una breve introducci贸n a una variedad de"+
	        " t贸picos en soluci贸n computacional de problemas: visualizaci贸n, programaci贸n estoc谩stica, aprendizaje de m谩quina, etc",
            "Edx", 9),            
        	};
        	for(Mooc informacion : ejemplos) {
        		adicione(informacion);
        	}
    	} catch(Exception e) {
    		
    	}
        
    }
    
   
    
    /**
     * Consulta la informaci贸n de un curso
     * @return el curso
     */
    public Mooc getInformacion(String nombre,String distribuidor){
    	Mooc c = null;
    	for(int i = 0; i < cursos.size() && c == null; i++){
    	    if (cursos.get(i).getNombre().compareToIgnoreCase(nombre)==0 &&
                   (cursos.get(i).getDistribuidor().compareToIgnoreCase(distribuidor)==0)){
                   c=cursos.get(i);
                }
    	}
    	return c;
    }


    /**
     * Adiciona un nuevo curso
     * @throws IEMOISExcepcion SEMANA_TEXTO Cuando la semana no representa un numero
     * 						   NOMBRE_VACIO Cuando no se da un nombre para el curso
     */
    public void adicione(String nombre, String area,  String objetivo, String  distribuidor, String semanas) throws IEMOISExcepcion{
    	try {
    		Integer.parseInt(semanas.trim());
    	}catch(Exception e) {
    		throw new IEMOISExcepcion(IEMOISExcepcion.SEMANA_TEXTO);
    	}
    	adicione(new Mooc(nombre, area, objetivo, distribuidor, Integer.parseInt(semanas.trim())));
    }

    /**
     * Adiciona un nuevo cursos
     * @throws IEMOISExcepcion CURSO_REPETIDO Cuando el curso que se quiere adicionar ya se habia agregado antes
     */
    public void adicione(Mooc informacion) throws IEMOISExcepcion{
    	int i = 0;
    	while ((i < cursos.size()) && (cursos.get(i).getNombre().compareToIgnoreCase(informacion.getNombre()) < 0)){
    	    i++;
    	}
    	for (Mooc curso : cursos) {
    		if (curso.toString().equals(informacion.toString())) throw new IEMOISExcepcion(IEMOISExcepcion.CURSO_REPETIDO);
    	}
    	cursos.add(i,informacion);
    }
    

    
    /**
     * Consulta las cursos que inician con un prefijo
     * @param prefijo El prefijo a buscar
     * @return resultados, todos los cursos que inician con un prefijo
     */
    public ArrayList<Mooc> busque(String prefijo){
        ArrayList<Mooc> resultados = new ArrayList<Mooc>();
    	prefijo = prefijo.toUpperCase();
    	for(int i = 0; i < cursos.size(); i++){
    	    if(cursos.get(i).getNombre().toUpperCase().startsWith(prefijo)){
    	       resultados.add(cursos.get(i));
    	    }	
    	}
        return resultados;
    }

    /**
     * Consulta el numero de cursos
     * @return el numero de cursos actuales
     */
    public int numeroCursos(){
        return cursos.size();
    }


    /**
     * Consulta todos los cursos
     * @return allEntries, todos los cursos actuales vistos como cadenas
     */
    public String toString(){
	StringBuffer allEntries = new StringBuffer();
        for(Mooc informacion : cursos) {
            allEntries.append(informacion.toString().length()<=150? informacion:informacion.toString().substring(0,199)+"...");
            allEntries.append('\n');
            allEntries.append('\n');
        }
        return allEntries.toString();
    }
}
