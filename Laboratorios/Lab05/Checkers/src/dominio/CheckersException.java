package dominio;

@SuppressWarnings("serial")
public class CheckersException extends Exception{
	
	public static final String VALUE_OUT_OF_LIMITS = "The value is lower than 4 or is greater than the board's size. ";
	public static final String NO_MOVE = "There is no posible move for this token.";
	public static final String MOVE_NO_VALID = "The movement you want to performed is not valid";
	public CheckersException(String mensaje) {
		super(mensaje);
	}
	
	

}
