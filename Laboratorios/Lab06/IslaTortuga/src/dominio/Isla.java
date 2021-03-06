
package dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


/**
 * Clase isla
 * @author Angie Medina - Jose Perez
 * @version 15/11/20
 */
public class Isla implements Serializable{
    public static final int MAXIMO = 500;
    private static Isla isla = null;

    /**
     * Devuelve la isla actual
     * @return isla, la isla actual
     */
    public static Isla demeIsla() {
        if (isla == null){
            isla = new Isla();
        }
        return isla;
    }

    /**
     * Crea una nueva isla
     */
    private static void nuevaIsla() {
        isla = new Isla();
    }   

    /**
     * Cambia de isla
     * @param d, la nueva isla que se quiere manejar
     */
    public static void cambieIsla(Isla d) {
        isla=d;
    }       

    private static ArrayList<EnIsla> elementos;
    private int tesoroPosX;
    private int tesoroPosY;
    private boolean encontraronTesoro;

    /**
     * Constructor isla
     */
    private Isla() {
        elementos= new ArrayList<EnIsla>();
        tesoroPosX = (int)(Math.random() * MAXIMO);
        tesoroPosY = (int)(Math.random() * MAXIMO);
        encontraronTesoro=false;
    }

    /**
     * Agrega algunos elementos a la isla actual
     */
    public void algunosEnIsla(){    
       Pirata jack = new Pirata(this, "Jack", 100, 20);
       Pirata elizabeth = new Pirata(this, "Elizabeth", 350, 50);
       adicione(jack);adicione(elizabeth);
       Rebelde henry = new Rebelde(this, "Henry", 50, 200);
       Rebelde corina = new Rebelde(this, "Corina", 450, 200);
       adicione(henry); adicione(corina);
       Palmera superiorDerecha = new Palmera(this, "superiorDerecha", 499, 480);
       Palmera superiorIzquierda = new Palmera(this, "superiorIzquiera", 1, 480);
       Palmera inferiorDerecha = new Palmera(this, "inferiorDerecha", 499, 1);
       Palmera inferiorIzquierda = new Palmera(this, "inferiorIzquiera", 1, 1);
       adicione(superiorDerecha);adicione(superiorIzquierda);
       adicione(inferiorDerecha); adicione(inferiorIzquierda);
       Minusioso ada = new Minusioso(this, "Ada", 100, 300);
       Minusioso turing = new Minusioso(this, "Turing", 250, 300);
       adicione(ada); adicione(turing);
       Perezoso angie = new Perezoso(this, "Angie", 100, 400, 2);
       Perezoso jose = new Perezoso(this, "Jose", 118, 100, 1);
       adicione(angie); adicione(jose);
       Lago lagoCacatua = new Lago(this,"lagoCacatua",100,100);
       Lago unilago = new Lago(this,"unilago",200,200);
       adicione(lagoCacatua);adicione(unilago);
    }  

    /**
     * Devuelve un elemento de la isla
     * @param n, el indice del elemento
     * @return h, el elemento solicitado en la isla
     */
    public EnIsla demeEnIsla(int n){
        EnIsla h=null;
        if (1<=n && n<=elementos.size()){
            h=elementos.get(n-1);
        }    
        return h; 
    }
    
    /**
     * Adiciona un elemento a la isla
     * @param e, el elemento a adicionar
     */
    public static void adicione(EnIsla e){
        elementos.add(e);
    }

    /**
     * Devuelve el numero de elementos que se encuantran en la isla
     * @return el numero de elementos en la isla
     */
    public int numeroEnIsla(){
        return elementos.size();
    }

    /**
     * Calcula si un elemento esta en la posicion del tesoro
     * @param e, el elemento al que se quiere verificar la posicion
     * @return si el elemento se encuentra en el tesoro
     */
    public boolean enTesoro(EnIsla e){
        boolean tesoro=(tesoroPosX==e.getPosicionX() && tesoroPosY==e.getPosicionY());
        encontraronTesoro = encontraronTesoro || tesoro;
        return tesoro;
    }     
    
    /**
     * Hace que todos los elementos de la isla actuen 
     */
    public void actuen(){
       for (int i = 1; i <= numeroEnIsla(); i++){
           demeEnIsla(i).actue();
       }
    }

    /**
     * Hace que todos los elementos de la isla paren
     */
    public void paren(){
       for (int i = 1; i <= numeroEnIsla(); i++){
           demeEnIsla(i).pare();
       }
    }    

    /**
     * Hace que todos los elementos de la isla decidan
     */
    public void decidan(){
        for (int i = 1; i <= elementos.size(); i++){
           demeEnIsla(i).decida();
       }
    }
    
    /**
      * Devuelve una lista con los elementos cercanos a un elemento dado en la isla
      * @param elemento elemento de tipo EnIsla
      * @return cercanos ArrayList de tipo EnIsla       
      */
    public ArrayList<EnIsla> elementosCercanos(EnIsla elemento){
        int x = elemento.getPosicionX(), y = elemento.getPosicionY();
        ArrayList<EnIsla> cercanos = new ArrayList<EnIsla>();
        for(EnIsla i: elementos){
            try{
                int auxX = i.getPosicionX(), auxY = i.getPosicionY();
                double distancia = Math.sqrt((x-auxX)*(x-auxX)+(y-auxY)*(y-auxY));
                if(distancia<=50) cercanos.add(i);
            }
            catch(Exception e){}
        }
        return cercanos;
    }
   /**
     * Limpia la lista elementos
     */
    public void clearElementos(){
        elementos.clear();
    }
    
    /**
	 * Abre, si es posible, el archivo especificado
	 * @param file el archivo a abrir
     * @throws IOException ERROR ABRIR Si ha ocurrido un error de algun tipo cuando se ha intentado abrir el archivo
	 * */
	public static void abra01(File file) throws IslaTortugaException{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Isla.cambieIsla((Isla) in.readObject());
			in.close();
		} catch(IOException | ClassNotFoundException e) {
			throw new IslaTortugaException(IslaTortugaException.ERROR_ABRIR);
		} 
	}
	
	/**
	 * Abre si es posible, el archivo especificado
	 * @param file el archivo que se desea abrir
	 * @throws IslaTortugaException - TIPO_ERRONEO Cuando el archivo que se quiere abrir no es .dat
	 * 								- ERROR_ABRIR Cuando se tuvo problema al intentar abrir el archivo
	 * 								- CLASE_NO_ENCONTRADA Cuando se guardo en el archivo una objeto de una clase que no se tiene en la aplicacion 
	 */
	public static void abra(File file) throws IslaTortugaException{
		try {
			if (!file.getCanonicalPath().endsWith(".dat")) {
				throw new IslaTortugaException(IslaTortugaException.TIPO_ERRONEO_DAT);
			}
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Isla.cambieIsla((Isla) in.readObject());
			in.close();
		} catch(IOException e) {		
			throw new IslaTortugaException(IslaTortugaException.ERROR_ABRIR);
		} catch(ClassNotFoundException e) {
			throw new IslaTortugaException(IslaTortugaException.CLASE_NO_ENCONTRADA);
		}
	}
	
	/**
	 * Guarda, si es posible, en el archivo especificado
	 * @param file el archivo a guardar
	 * @throws IslaTortugaException ERROR_SALVAR Cuando se presenta un error de algun tipo al intentar salvar en un archivo
	 * */
	public void guarde01(File file) throws IslaTortugaException{
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(this);
			out.close();
		} catch(IOException e) {
			throw new IslaTortugaException(IslaTortugaException.ERROR_SALVAR);
		}
	}
	
	/**
	 * Guarda, si es posible, en el archivo especificado
	 * @param file el archivo a guardar
	 * @throws IslaTortugaException - TIPO_ERRONEO Cuando el archivo en el que se quiere guardar no es .dat
	 * 								- ERROR_SALVAR Cuando se tuvo problema al intentar salvar el archivo 
	 */
	public void guarde(File file) throws IslaTortugaException{
		try {
			if (!file.getCanonicalPath().endsWith(".dat")) {
				throw new IslaTortugaException(IslaTortugaException.TIPO_ERRONEO_DAT);
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(this);
			out.close();
		} catch(IOException e) {
			throw new IslaTortugaException(IslaTortugaException.ERROR_SALVAR);
		}
	}
	
	
	/**
	 * Exporta los datos del archivo especificado
	 * @param file el archivo al que se va a exportar
	 * @throws IslaTortugaException -ERROR_EXPORTAR  Cuando se produjo algun problema a la hora de exportar al archivo
	 * */
	public void exporte01(File file) throws IslaTortugaException {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			for(int i = 1; i <= numeroEnIsla(); i++) {
				EnIsla element = demeEnIsla(i);
				pw.print(element.getClass().getSimpleName() + element.nombre() + " " + element.getPosicionX() + " " + element.getPosicionY());
				if(element.getClass().getSimpleName().equals("Perezoso")) pw.print(" "+ ((Perezoso)element).getEstamina());
				pw.println();
			}
			pw.close();
		}catch(IOException e){
			throw new IslaTortugaException(IslaTortugaException.ERROR_EXPORTAR);
		}
	}
	
	/**
	 * Exporta los datos del archivo especificado
	 * @param file el archivo al que se va a exportar
	 * @throws IslaTortugaException - TIPO_ERRONEO Cuando el archivo al que se quiere exportar no tiene extencion txt
	 * 								- ERROR_EXPORTAR  Cuando se produjo algun problema a la hora de exportar al archivo
	 * */
	public void exporte02(File file) throws IslaTortugaException {
		try {
			if (!file.getCanonicalPath().endsWith(".txt")) {
				throw new IslaTortugaException(IslaTortugaException.TIPO_ERRONEO_TXT);
			}
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			for(int i = 1; i <= numeroEnIsla(); i++) {
				EnIsla element = demeEnIsla(i);
				pw.print(element.getClass().getSimpleName() + element.nombre() + " " + element.getPosicionX() + " " + element.getPosicionY());
				if(element.getClass().getSimpleName().equals("Perezoso")) pw.print(" "+ ((Perezoso)element).getEstamina());
				pw.println();
			}
			pw.close();
		}catch(IOException e){
			throw new IslaTortugaException(IslaTortugaException.ERROR_EXPORTAR);
		}
	}
	
	/**
	 * Exporta los datos del archivo especificado
	 * @param file el archivo al que se va a exportar
	 * @throws IslaTortugaException - TIPO_ERRONEO Cuando el archivo al que se quiere exportar no tiene extencion txt
	 * 								- ERROR_EXPORTAR  Cuando se produjo algun problema a la hora de exportar al archivo
	 * */
	public void exporte03(File file) throws IslaTortugaException {
		try {
			if (!file.getCanonicalPath().endsWith(".txt")) {
				throw new IslaTortugaException(IslaTortugaException.TIPO_ERRONEO_TXT);
			}
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			for(int i = 1; i <= numeroEnIsla(); i++) {
				EnIsla element = demeEnIsla(i);
				pw.print(element.getClass().getSimpleName() + element.nombre() + " " + element.getPosicionX() + " " + element.getPosicionY());
				if(element.getClass().getSimpleName().equals("Perezoso")) pw.print(" "+ ((Perezoso)element).getEstamina());
				pw.println();
			}
			pw.close();
		}catch(IOException e){
			throw new IslaTortugaException(IslaTortugaException.ERROR_EXPORTAR);
		}
	}
	
	
	/**
	 * Exporta los datos del archivo especificado
	 * @param file el archivo al que se va a exportar
	 * @throws IslaTortugaException - TIPO_ERRONEO Cuando el archivo al que se quiere exportar no tiene extencion txt
	 * 								- ERROR_EXPORTAR  Cuando se produjo algun problema a la hora de exportar al archivo
	 * */
	public void exporte(File file) throws IslaTortugaException {
		try {
			if (!file.getCanonicalPath().endsWith(".txt")) {
				throw new IslaTortugaException(IslaTortugaException.TIPO_ERRONEO_TXT);
			}
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			for(int i = 1; i <= numeroEnIsla(); i++) {
				EnIsla element = demeEnIsla(i);
				pw.print(element.getClass().getSimpleName() + element.nombre() + " " + element.getPosicionX() + " " + element.getPosicionY());
				if(element.getClass().getSimpleName().equals("Perezoso")) pw.print(" "+ ((Perezoso)element).getEstamina());
				pw.println();
			}
			pw.close();
		}catch(IOException e){
			throw new IslaTortugaException(IslaTortugaException.ERROR_EXPORTAR);
		}
	}
	
	/**
	 * Importa los datos del archivo especificado
	 * @param file el archivo a importar
	 * @throws IslaTortugaException - ERROR_IMPORTAR  Cuando se produjo algun problema a la hora de importar un archiv
	 * 								
	 **/
	public static void importe01(File file) throws IslaTortugaException{
		try {
			Isla.cambieIsla(null);
			Isla.demeIsla();
			BufferedReader bIn = new BufferedReader(new FileReader(file));
			String line = bIn.readLine();
			while (line != null) {
				line = line.trim();
				if (line.equals("")) continue;
				String[] elements = line.split(" ");
				if(elements[0].equals("Pirata")) {
					Pirata p  = new Pirata(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(p);
				}
				else if(elements[0].equals("Minusioso")) { Minusioso m = new Minusioso(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(m);
				}
				else if(elements[0].equals("Palmera")) {
					Palmera p = new Palmera(Isla.demeIsla(),"",Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
					adicione(p);
				}
				else if(elements[0].equals("Perezoso")) {
					Perezoso p = new Perezoso(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), 
							Integer.parseInt(elements[3]), Integer.parseInt(elements[4]));
					adicione(p);
				}
				else if(elements[0].equals("Lago")) {
					Lago l = new Lago(Isla.demeIsla(), "", Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
					adicione(l);
				}
				else if(elements[0].equals("Rebelde")) {
					Rebelde r = new Rebelde(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(r);
				}
				line = bIn.readLine();
			}
			bIn.close();
		} catch(IOException e) {
			throw new IslaTortugaException(IslaTortugaException.ERROR_IMPORTAR);
		} 
	}
	
	/**
	 * Importa los datos del archivo especificado
	 * @param file el archivo a importar
	 * 								- TIPO_ERRONEO Cuando el archivo que se quiere importar no tiene extension txt
	 * 								- ARCHIVO_NO_ENCONTRADO Cuando el archivo que se queire importar no se encontro
	 * 								- ERROR_IMPORTAR  Cuando se produjo algun problema a la hora de importar un archivo
	 * 								- ERROR_INFORMACION Cuando alguna linea del archivo se sale de la estructura que se necesita para poder leer
	 **/
	public static void importe02(File file) throws IslaTortugaException{
		try {
			if (!file.getCanonicalPath().endsWith(".txt")) {
				throw new IslaTortugaException(IslaTortugaException.TIPO_ERRONEO_TXT);
			}
			Isla.cambieIsla(null);
			Isla.demeIsla();
			BufferedReader bIn = new BufferedReader(new FileReader(file));
			String line = bIn.readLine();
			while (line != null) {
				line = line.trim();
				if (line.equals("")) continue;
				String[] elements = line.split(" ");
				if(elements[0].equals("Pirata")) {
					Pirata p = new Pirata(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(p);
				}
				else if(elements[0].equals("Minusioso")) {
					Minusioso m = new Minusioso(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(m);
				}
				else if(elements[0].equals("Palmera")) {
					Palmera p = new Palmera(Isla.demeIsla(),"",Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
					adicione(p);
				}
				else if(elements[0].equals("Perezoso")) {
					Perezoso p = new Perezoso(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), 
							Integer.parseInt(elements[3]), Integer.parseInt(elements[4]));
					adicione(p);
				}
				else if(elements[0].equals("Lago")) {
					Lago l = new Lago(Isla.demeIsla(), "", Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
					adicione(l);
				}
				else if(elements[0].equals("Rebelde")) {
					Rebelde r = new Rebelde(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(r);
				}
				else {
					throw new IslaTortugaException(IslaTortugaException.CLASE_NO_ENCONTRADA);
				}
				line = bIn.readLine();
			}
			bIn.close();
		} catch(FileNotFoundException e) {
			throw new IslaTortugaException(IslaTortugaException.ARCHIVO_NO_ENCONTRADO);
		} catch(IOException e) {
			throw new IslaTortugaException(IslaTortugaException.ERROR_IMPORTAR);
		} catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
			throw new IslaTortugaException(IslaTortugaException.ERROR_INFORMACION);
		}
	}
	
	/**
	 * Importa los datos del archivo especificado
	 * @param file el archivo a importar
	 * 								- TIPO_ERRONEO Cuando el archivo que se quiere importar no tiene extension txt
	 * 								- ERROR_IMPORTAR  Cuando se produjo algun problema a la hora de importar un archivo
	 **/
	public static void importe03(File file) throws IslaTortugaException{
		int contLine = 1, j = 2;
		try {
			if (!file.getCanonicalPath().endsWith(".txt")) {
				throw new IslaTortugaException(IslaTortugaException.TIPO_ERRONEO_TXT);
			}
			Isla.cambieIsla(null);
			Isla.demeIsla();
			BufferedReader bIn = new BufferedReader(new FileReader(file));
			String line = bIn.readLine();
			while (line != null) {
				line = line.trim();
				if (line.equals("")) continue;
				String[] elements = line.split(" ");
				if(elements[0].equals("Pirata")) {
					try { for (j = 2; j < 4; j++) Integer.parseInt(elements[j]);} 
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Pirata p = new Pirata(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(p);
				}
				else if(elements[0].equals("Minusioso")) {
					try { for (j = 2; j < 4; j++) Integer.parseInt(elements[j]);}
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Minusioso m = new Minusioso(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(m);
				}
				else if(elements[0].equals("Rebelde")) {
					try { for (j = 2; j < 4; j++) Integer.parseInt(elements[j]);}
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Rebelde r = new Rebelde(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione(r);
				}
				else if(elements[0].equals("Perezoso")) {
					try { for (j = 2; j < 5; j++) Integer.parseInt(elements[j]);}
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Perezoso p = new Perezoso(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), 
							Integer.parseInt(elements[3]), Integer.parseInt(elements[4]));
					adicione(p);
				}
				else if(elements[0].equals("Palmera")) {
					try { for (j = 1; j < 3; j++) Integer.parseInt(elements[j]);}
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Palmera p = new Palmera(Isla.demeIsla(),"",Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
					adicione(p);
				}
				else if(elements[0].equals("Lago")) {
					try { for (j = 1; j < 3; j++) Integer.parseInt(elements[j]);}
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Lago l = new Lago(Isla.demeIsla(), "", Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
					adicione(l);
				}
				else {raiseError(contLine, elements[0], "It's not a defined class");}
				contLine++;
				line = bIn.readLine();
			}
			bIn.close();
		} catch(IOException e) {
			throw new IslaTortugaException(IslaTortugaException.ERROR_IMPORTAR);
		}
	}

	/**
	 * Importa los datos del archivo especificado
	 * @param file el archivo a importar
	 * 								- TIPO_ERRONEO Cuando el archivo que se quiere importar no tiene extension txt
	 * 								- ERROR_IMPORTAR  Cuando se produjo algun problema a la hora de importar un archivo
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NumberFormatException 
	 * @throws ClassNotFoundException 
	 **/
	public static void importe(File file) throws IslaTortugaException{
		int contLine = 1, j = 2;
		try {
			if (!file.getCanonicalPath().endsWith(".txt")) {
				throw new IslaTortugaException(IslaTortugaException.TIPO_ERRONEO_TXT);
			}
			Isla.cambieIsla(null);
			Isla.demeIsla();
			BufferedReader bIn = new BufferedReader(new FileReader(file));
			String line = bIn.readLine();
			while (line != null) {
				line = line.trim();
				if (line.equals("")) continue;
				String[] elements = line.split(" ");
				String name = elements[0];
				if(name.equals("Pirata") || name.equals("Minusioso") || name.equals("Rebelde")) {
					try { for (j = 2; j < 4; j++) Integer.parseInt(elements[j]);} 
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Class c = Class.forName("dominio." + elements[0]);
					Object o = c.getDeclaredConstructor(Isla.class, String.class, int.class, int.class).newInstance(
							Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
					adicione((EnIsla)o);
				}
				else if(name.equals("Perezoso")) {
					try { for (j = 2; j < 5; j++) Integer.parseInt(elements[j]);}
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Perezoso p = new Perezoso(Isla.demeIsla(), elements[1], Integer.parseInt(elements[2]), 
							Integer.parseInt(elements[3]), Integer.parseInt(elements[4]));
					adicione(p);
				}
				else if(name.equals("Palmera") || name.equals("Lago")) {
					try { for (j = 1; j < 3; j++) Integer.parseInt(elements[j]);}
					catch(NumberFormatException e) {raiseError(contLine, elements[j], "Cannot convert into a number");}
					catch(ArrayIndexOutOfBoundsException e) {raiseError(contLine, "", "Less data than it should");}
					Class c = Class.forName("dominio." + elements[0]);
					Object o = c.getDeclaredConstructor(Isla.class, String.class, int.class, int.class).newInstance(
							Isla.demeIsla(), "", Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
					adicione((EnIsla)o);
				}
				else {raiseError(contLine, elements[0], "It's not a defined class");}
				contLine++;
				line = bIn.readLine();
			}
			bIn.close();
		} catch(IOException e) {
			throw new IslaTortugaException(IslaTortugaException.ERROR_IMPORTAR);
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IslaTortugaException(IslaTortugaException.CLASE_NO_ENCONTRADA);
		}
	}
	
	public static void raiseError(int line, String word, String motive) throws IslaTortugaException {
		throw new IslaTortugaException("Error in line " + line +": '" + word + "' because " + motive);
	}
}

