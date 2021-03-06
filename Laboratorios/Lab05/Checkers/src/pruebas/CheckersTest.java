package pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import dominio.Checkers;
import dominio.CheckersException;

/*
 * Clase de pruebas para checkers
 * @author Angie Medina - Jose Perez
 * @version 5/11/20
 */

public class CheckersTest{
	public CheckersTest() {} 
	
	@Test
	public void deberiaCrearTableroSegunTamaño() throws CheckersException {
		int size = 10;
		try {
			Checkers ch = new Checkers(size);
			assertEquals(size, ch.consult().length);
		}catch(CheckersException e) {
			fail("Lanzo excepcion");
		}
	}
	
	@Test
	public void deberiaCrearFichas() {
		int size = 8;
		try {
			Checkers ch = new Checkers(size);
			assertTrue(3*size/2 == ch.tokens()[0] && 3*size/2 == ch.tokens()[1]);
		}catch(CheckersException e) {
			fail("Lanzo excepcion");
		}
	}
	
	@Test
	public void deberiaLanzarExcepcionPorTamaño() throws CheckersException {
		int size = 2;
		try {
			new Checkers(size);
			fail("No lanzo excepcion");
		} catch(CheckersException e) {
			assertEquals(e.getMessage(), CheckersException.VALUE_OUT_OF_LIMITS);
		}
	}
	
	@Test
	public void deberiaMoverLaFicha(){
		int size = 8;
		try {
			Checkers ch = new Checkers(size);
			char[][] numBoard = ch.consult();
			ArrayList<ArrayList<Character>> before = new ArrayList<>(); 
			for (int i = 0; i < numBoard.length; i++) {
				ArrayList<Character> temp = new ArrayList<>();
				for (int j = 0; j < numBoard.length; j++) temp.add(numBoard[i][j]);
				before.add(temp);
			}
			ch.move(size - 2, 3, size - 3, 4);
			numBoard = ch.consult();
			ArrayList<ArrayList<Character>> after = new ArrayList<>(); 
			for (int i = 0; i < numBoard.length; i++) {
				ArrayList<Character> temp = new ArrayList<>();
				for (int j = 0; j < numBoard.length; j++) temp.add(numBoard[i][j]);
				after.add(temp);
			}
			assertFalse(before.toString().equals(after.toString()));
		}catch(CheckersException e) {
			fail("Lanzo excepcion");
		}
	}
	
	@Test
	public void deberiaLanzarExcepccionPorTamañoMove() {
		int size = 8;
		try {
			Checkers ch = new Checkers(size);
			ch.move(size + 3, 3, 5, 6);
			fail("No lanzo excepccion");
		}catch(CheckersException e) {
			assertEquals(e.getMessage(), CheckersException.VALUE_OUT_OF_LIMITS);
		}
	}
	
	@Test
	public void deberiaLanzarExcepcionNoMovimientoNoPosible() {
		int size = 8;
		try {
			Checkers ch = new Checkers(size);
			ch.move(size - 2, 1, 1, 1);
			fail("No lanzo excepccion");
		}catch(CheckersException e) {
			assertEquals(e.getMessage(), CheckersException.MOVE_NO_VALID);
		}
	}
	
	@Test
	public void deberiaLanzarExcepcionNoHayMovimientos(){
		int size = 8;
		try {
			Checkers ch = new Checkers(size);
			ch.move(size, 1, 1, 1);
			fail("No lanzo excepccion");
		}catch(CheckersException e) {
			assertEquals(e.getMessage(), CheckersException.NO_MOVE);
		}
	}
	
	@Test
	public void deberiaTenerPosiblesMovimientosYSaltos() {
		int size = 8;
		try {
			Checkers ch = new Checkers(size);
			assertTrue(ch.posibleMovesAndJumps(size - 2, size - 2).size() > 0);
		} catch(CheckersException e) {
			fail("Lanzo excepcion");
		}
	}
	
	@Test
	public void deberianTenerMovimientos(){
		int size = 8;
		try {
			Checkers ch = new Checkers(size);
			ch.move(6, 1, 5, 2);
			int[] moves = ch.consultMoves();
			assertTrue(moves[0] > 0 && moves[1] > 0);
		}catch(CheckersException e) {
			fail("Lanzo excepcion");
		}
	}
	
	@Test
	public void deberiaEmpezarElJugador() {
		int size = 8;
		try {
			Checkers ch = new Checkers(size);
			assertTrue(ch.seleccione(size - 2, 3));
		}catch(CheckersException e) {
			fail("Lanzo excepcion");
		}
		
	}
		
		
}
