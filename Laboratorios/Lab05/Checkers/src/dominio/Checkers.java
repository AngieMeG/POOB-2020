package dominio;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Random;

public class Checkers {
	
	private char[][] tablero;
	private int actualPlayer;
	private int[] tokens;
	private int[] moves;
	private int winner;
    /*
     * Constructor.
     * @param size Tamano del tablero.
     * @throws CheckersException VALUE_OUT_OF_LIMITS Si el valor es menor a cuatro. 
     */
    public Checkers(int size) throws CheckersException{
    	if(size < 4 || size > 26) throw new CheckersException(CheckersException.VALUE_OUT_OF_LIMITS);
    	tablero = new char[size][size];
    	for(int i = 0; i < size; i++) {
    		Arrays.fill(tablero[i], ' ');	
    	}
    	int j = 1;
    	for(int i = 0; i < size; i++) {
    		tablero[j][i] = '1';
    		j = (j+1)%2;
    	}
    	j = size % 2 == 0 ? 0 : 1;
    	for(int i = 0; i < size; i++) {
    		tablero[size-1-j][i] = '0';
    		j = (j+1)%2;
    	}
    	
    	if(size >= 8) {
    		for(int i = 1; i < size; i += 2) {
        		tablero[2][i]='1';
        	}
    		for(int i = size % 2 == 0 ? 0 : 1; i < size; i+=2) {
        		tablero[size-3][i]='0';
        	}
    	}
    	actualPlayer = 0;
    	tokens = new int[] {(int)3*size/2,(int)3*size/2};
    	moves = new int[] {0,0};
    	winner = -1;
    	
    }
    
    /*
     * Cosntructor
     * @param tablero la matriz que representa la posicion de las fichas
     * @param actualPlayer el jugador actual. 0 si es el jugador 1 si es la maquina
     * @param tokens el numero de fichas que tiene cada jugador. Primero el jugador y luego la maquina
     * @param moves la cantidad de movimentos que ha realzado cada jugador. Primero el jugador y luego la maquina
     */
    public Checkers(char[][] tablero, int actualPlayer, int[] tokens, int[] moves) {
    	this.tablero = tablero;
    	this.actualPlayer = actualPlayer;
    	this.tokens = tokens;
    	this.moves = moves;
    	winner = -1;
    }
    
    /**
     * Mueve una ficha del jugador actual en la direccion indicada
     * @param fila Entero que indica la fila del tablero. [1 .. tama_o del tablero]
     * @param columna Entero que indica la columna del tablero.[1 .. tama_o del tablero]
     * @param direction Indica la direccion a la que se quiere mover la ficha ["NE","NW","SE","SW"]
     * @return Retorna si algun jugador gano la partida.
     * @throws CheckersException MOVE_NOT_VALID Si el movimiento a realizar por la ficha no es valido.
     *                           VALUE_OUT_OF_LIMITS Si el numero ingresado es mayor al tamano del tablero o un numero negativo.
     *                           NO_MOVE no hay movimiento posible 
     */
    public boolean move(int row, int column, int rowMove, int columnMove) throws CheckersException{
    	row--;
    	column--;
    	rowMove--;
    	columnMove--;
    	if(row < 0 || column < 0 || rowMove < 0|| columnMove < 0) {
    		throw new CheckersException(CheckersException.VALUE_OUT_OF_LIMITS);
    	}
    	if(row > tablero.length || column > tablero.length || 
    			rowMove > tablero.length || columnMove > tablero.length ) {
    		throw new CheckersException(CheckersException.VALUE_OUT_OF_LIMITS);
    	}
    	ArrayList<ArrayList<Integer>> posibleMoves = posibleMoves(row,column);
    	ArrayList<ArrayList<Integer>> posibleJumps = posibleJumps(row,column);
    	boolean isAMove = posibleMoves.contains(new ArrayList<Integer>(Arrays.asList(rowMove,columnMove)));
    	boolean isAJump = posibleJumps.contains(new ArrayList<Integer>(Arrays.asList(rowMove,columnMove)));	
    	int totalMoves = posibleMoves.size()+posibleJumps.size();
        if(totalMoves == 0) {
        	throw new CheckersException(CheckersException.NO_MOVE);
        }
        if(!isAMove && !isAJump) {
        	throw new CheckersException(CheckersException.MOVE_NO_VALID);
        }
        if(isAJump) {
        	jump(row, column, rowMove, columnMove);
        }
    	tablero[rowMove][columnMove] = actualPlayer == 0 ? '0' : '1';
    	tablero[row][column] = ' ';
    	moves[actualPlayer] += 1;
    	actualPlayer = (actualPlayer+1)%2;
    	if(actualPlayer==1) move();
    	return isAWinner();
    }
    
    /**
     * Calcula los posibles movimientos o saltos que puede realizar la ficha
     * @param row Entero que indica la fila del tablero. [1 .. tama_o del tablero]
     * @param column Entero que indica la columna del tablero.[1 .. tama_o del tablero]
     * @return los posibles movimientos o saltos que puede realizar la ficha
     */
    public ArrayList<ArrayList<Integer>> posibleMovesAndJumps(int row, int column) {
    	ArrayList<ArrayList<Integer>> moves = posibleMoves(row, column);
    	moves.addAll(posibleJumps(row, column));
    	return moves;
    }
    
    /**
     * Calcula los posibles movimientos que pueda realizar la ficha
     * @param row Entero que indica la fila del tablero. [1 .. tama_o del tablero]
     * @param column Entero que indica la columna del tablero.[1 .. tama_o del tablero]
     * @return los posibles movimientos que pueda realizar la ficha
     */
    private ArrayList<ArrayList<Integer>> posibleMoves(int row, int column){
    	int move = actualPlayer == 0 ? -1 : 1;
    	boolean inRange =  row + move >= 0 && row + move <= tablero.length - 1;
    	ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
		if (column < tablero.length - 1 && inRange && tablero[row+move][column+1]==' ') {
			moves.add(new ArrayList<Integer>(Arrays.asList(row+move,column + 1)));
			}
		if (column>0 && inRange && tablero[row+move][column-1]==' ') {
			moves.add(new ArrayList<Integer>(Arrays.asList(row+move,column-1)));
			}
		return moves;
    }
    
    /**
     * Calcula los posibles saltos que pueda realizar una ficha
     * @param row Entero que indica la fila del tablero. [1 .. tama_o del tablero]
     * @param column Entero que indica la columna del tablero.[1 .. tama_o del tablero]
     * @return los posibles saltos que pueda realizar una ficha
     */
    private ArrayList<ArrayList<Integer>> posibleJumps(int row, int column){
    	int move = actualPlayer == 0 ? -1 : 1;
    	int nextPlayer = (actualPlayer + 1) % 2;
    	boolean inRange;
    	Deque<int[]> stack = new ArrayDeque<int[]>();
    	ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();  
    	stack.push(new int[] {row,column});
    	while(!stack.isEmpty()) {
    		int[] pos = stack.pop();
    		inRange = pos[0]+2*move>=0 && pos[0]+2*move<=tablero.length-1;
    		if(pos[1]<tablero.length-2 && inRange &&
    				(""+tablero[pos[0]+move][pos[1]+1]).equals(""+nextPlayer) && tablero[pos[0]+2*move][pos[1]+2]==' ') {
    			int[] toPush = new int[] {pos[0]+2*move,pos[1]+2};
    			stack.push(toPush);
    			moves.add(new ArrayList<Integer>(Arrays.asList(toPush[0],toPush[1])));
    		}
    		if(pos[1]>1 && inRange &&
    				(""+tablero[pos[0]+move][pos[1]-1]).equals(""+nextPlayer) && tablero[pos[0]+2*move][pos[1]-2]==' ') {
    			int[] toPush = new int[] {pos[0]+2*move,pos[1]-2};
    			stack.push(toPush);
    			moves.add(new ArrayList<Integer>(Arrays.asList(toPush[0],toPush[1])));
    		}
    	}
    	return moves;
    }
    
    /**
     * Salta y come a una ficha contraria
     * @param row Entero que indica la fila del tablero. [1 .. tama_o del tablero]
     * @param column Entero que indica la columna del tablero.[1 .. tama_o del tablero]
     * @param rowMove Entero que indica la fila del tablero [1 .. tama_o del tablero] a la que se va a saltar
     * @param columnMove Entero que indica la columna del tablero [1 .. tama_o del tablero] a la que se va a saltar
     */
    private void jump(int row, int column, int rowMove, int columnMove) {
    	int move = actualPlayer==0?1:-1;
    	int nextPlayer = (actualPlayer+1)%2;
    	boolean inRange;
    	Deque<int[]> queue = new ArrayDeque<int[]>();
    	Deque<String> seq = new ArrayDeque<String>();
    	String path = "";
    	seq.push("");
    	queue.push(new int[] {rowMove,columnMove});
    	while(!queue.isEmpty()) {
    		String last = seq.pollLast();
    		int[] pos = queue.pollLast();
    		inRange = pos[0]+2*move>=0 && pos[0]+2*move<=tablero.length-1;
    		if(pos[1]<tablero.length-2 && inRange && (""+tablero[pos[0]+move][pos[1]+1]).equals(""+nextPlayer) && 
    				(tablero[pos[0]+2*move][pos[1]+2]==' ' || (pos[0]+2*move==row && pos[1]+2==column))) {
    			if(pos[0]+2*move==row && pos[1]+2==column) {
    				path = last+"l";
    				break;
    			}
    			queue.push(new int[] {pos[0]+2*move,pos[1]+2});
    			seq.push(last+"l");
    		}
    		if(pos[1]>1 && inRange && (""+tablero[pos[0]+move][pos[1]-1]).equals(""+nextPlayer) && 
    				(tablero[pos[0]+2*move][pos[1]-2]==' ') || (pos[0]+2*move==row && pos[1]-2==column)){
    			if(pos[0]+2*move==row && pos[1]-2==column) {
    				path = last+"r";
    				break;
    			}	
    			queue.push(new int[] {pos[0]+2*move,pos[1]-2});
    			seq.push(last+"r");
    		}
    	}
    	eat(row,column,path);
    }
    
    public boolean isAWinner() {
    	boolean win = false;
    	if(winner == -1) {
    		if(tokens[(actualPlayer+1)%2] == 0){
    			win = true;
    			winner = actualPlayer;
    		}else if(!canMove()) {
    			winner = (actualPlayer+1)%2;
    		}
    		int tokens = 0;
    		for(int i=0; i<tablero.length; i++) {
    			if((""+tablero[0][i]).equals(""+actualPlayer)) tokens++;
    		}
    		if(tokens>=tablero.length-1) {
    			winner = actualPlayer;
    		}
    	}else win = true;
    	return win;
    }
    
    /**
     * Se come a una ficha contraria
     * @param row Entero que indica la fila del tablero. [1 .. tama_o del tablero]
     * @param column Entero que indica la columna del tablero.[1 .. tama_o del tablero]
     * @param path Camino que debe seguirse para comer una o varias fichas
     */
    private void eat(int row, int column, String path) {
    	int move = actualPlayer==0?-1:1;
    	int[] pos = new int[] {row,column};
    	for(int i=0; i<path.length();i++) {
    		char s = path.charAt(path.length()-1-i);
       		if(s=='l') {
    			tablero[pos[0]+move][pos[1]-1] = ' ';
    			pos[0] = pos[0]+2*move; pos[1] = pos[1]-2;
    		}
    		else {
    			tablero[pos[0]+move][pos[1]+1] = ' ';
    			pos[0] = pos[0]+2*move; pos[1] = pos[1]+2;
    		}
    	}
    	tokens[(actualPlayer+1)%2] -= path.length();
    }
    
    /**
     * Mueve una ficha del jugador actual automaticamente
     * @return Retorna si el jugador gano la partida.
     * @throws CheckersException NO_MOVE no hay movimiento posible. 
     */
    public boolean move() throws CheckersException{
    	if(canMove()) {
    		ArrayList<ArrayList<Integer>> tokens = allTokens();
    		HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> moves = allPosibleMoves();
    		HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> jumps = allPosibleJumps();
	    	Random r = new Random();
	    	boolean moved = false;
	    	while(!moved) {
	    		ArrayList<Integer> ini = tokens.get(r.nextInt(tokens.size()));
	    		ArrayList<ArrayList<Integer>> toJump = jumps.get(ini);
	    		ArrayList<ArrayList<Integer>> toMove = moves.get(ini);
	    		if(toJump.size()>0) {
	    			ArrayList<Integer> fin = toJump.get(r.nextInt(toJump.size()));
		    		move(ini.get(0)+1,ini.get(1)+1,fin.get(0)+1,fin.get(1)+1);
		    		moved = true;
	    		}
	    		else if (toMove.size()>0) {
		    		ArrayList<Integer> fin = toMove.get(r.nextInt(toMove.size()));
		    		move(ini.get(0)+1,ini.get(1)+1,fin.get(0)+1,fin.get(1)+1);
		    		moved = true;
	    		}
	    	}	
	    }
        return isAWinner() ;
    }
    
    private HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> allPosibleMoves() {
    	ArrayList<ArrayList<Integer>> tokens = allTokens();
    	HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> posibleMoves = new HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>>();
    	for(ArrayList<Integer> pos: tokens) {
    		posibleMoves.put(pos, posibleMoves(pos.get(0),pos.get(1)));
    		}
    	return posibleMoves;
    }
    
    private HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> allPosibleJumps() {
    	ArrayList<ArrayList<Integer>> tokens = allTokens();
    	HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> posibleJumps = new HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>>();
    	for(ArrayList<Integer> pos: tokens) {
    		posibleJumps.put(pos, posibleJumps(pos.get(0),pos.get(1)));
    		}
    	return posibleJumps;
    }
    
    private ArrayList<ArrayList<Integer>>allTokens() {
    	ArrayList<ArrayList<Integer>> tokens = new ArrayList<ArrayList<Integer>>();
    	for(int i=0;i<tablero.length;i++) {
    		for(int j=0; j<tablero.length; j++) {
    			if((""+tablero[i][j]).equals(""+actualPlayer)) {
    				ArrayList<Integer> aux = new ArrayList<Integer>();
    				aux.add(i); aux.add(j);
    				tokens.add(aux);
    			}
    		}
    	}
    	return tokens;
    }
    
    
    /**
     * Retorna la informacion de la partida
     * @return char[][] La informacion de la partida.
     */
    public char[][] consult() {
    	return tablero;  
    }
    
    /**
     * Consulta el numero de movimientos realizados por cada jugador
     * @return El numero de movimientos que ha realizado cada jugador. Primero el jugador y luego la maquina.
     */
    public int[] consultMoves(){ 
        return moves;
    }
    
    /**
     * Consulta las fichas comidas por cada jugador
     * @return El numero de fichas que han sido comidas por cada jugador. Primero el jugador y luego la maquina.
     */
    public int[] eatenTokens(){
        return (new int[] {(int) 3*(tablero.length)/2-tokens[1],(int)3*(tablero.length)/2-tokens[0]});
    }
    
    /**
     * Consulta el numero de fichas en el tablero
     * @return El numero de fichas actualmente en el tablero por cada jugador. Primero el jugador y luego la maquina.
     */
    public int[] tokens() {
    	return tokens;
    }
    
    /**
     * Verifica si la ficha que se selecciono si es una ficha del jugador actual
     * @param row Entero que indica la fila del tablero. [1 .. tama_o del tablero]
     * @param column Entero que indica la columna del tablero.[1 .. tama_o del tablero]
     * @return Si la ficha en el tablero es del jugador actual
     */
    public boolean seleccione(int row, int col) {
    	row--;
    	col--;
    	return (""+tablero[row][col]).equals(""+actualPlayer);
    }
    
    public int winner() {
    	return winner;
    }
    
    private boolean canMove() {
		HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> moves = allPosibleMoves();
		HashMap<ArrayList<Integer>,ArrayList<ArrayList<Integer>>> jumps = allPosibleJumps();
    	boolean canMove = false;
    	for(ArrayList<ArrayList<Integer>> i: moves.values()) {
    		if(i.size()>0) {
    			canMove = true;
    			break;
    			}
    	}for(ArrayList<ArrayList<Integer>> i: jumps.values()) {
    		if(i.size()>0 || canMove) {
    			canMove = true;
    			break;
    			}
    	}
    	return canMove;
    }
}