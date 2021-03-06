package dominio;

public class IEMOISExcepcion extends Exception{
	static public final String NO_NOMBRE = "No se ingresó el nombre del curso.";
	static public final String SEMANA_TEXTO = "Las semanas deberian ser solo un numero.";
	static public final String CURSO_REPETIDO = "El curso ingresado ya se habia añadido antes.";
	
	public IEMOISExcepcion(String mensaje) {
		super(mensaje);
	}
}
