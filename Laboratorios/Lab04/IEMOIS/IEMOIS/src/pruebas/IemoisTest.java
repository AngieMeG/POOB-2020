package pruebas;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import dominio.IEMOISExcepcion;
import dominio.Iemois;
import dominio.Mooc;

/**
 * La clase de pruebas para Ieomis
 * @author Angie Medina - Jose Perez
 * @version 21/10/20
 *
 */
public class IemoisTest {
	public IemoisTest() {}
	
	@Test
	public void deberiaAdicionarElMoocEspecifico() throws IEMOISExcepcion {
		Iemois ie = new Iemois();
		try {
			Mooc mooc = new Mooc("Prendiendo a aprender", "aprendizaje", 
				"Este curso te brinda acceso a invaluables técnicas de aprendizaje utilizadas " +
				"por expertos. Aprenderemos cómo el cerebro utiliza dos modos de aprendizaje" + 
				"muy distintos y cómo encapsula la información", "coursera", 4);
			ie.adicione(mooc);
			assertTrue(mooc.toString().equals(ie.getInformacion("Prendiendo a aprender", "coursera").toString()));
		}catch(Exception e) {
			fail("Lanzo excepcion");
		}
		
	}
	
	@Test
	public void deberiaFallarAlAdicionarNombreVacio() throws IEMOISExcepcion{
		Iemois ie = new Iemois();
		try {
			Mooc m1 = new Mooc("", "aprendizaje", 
				"Este curso te brinda acceso a invaluables técnicas de aprendizaje utilizadas " +
				"por expertos. Aprenderemos cómo el cerebro utiliza dos modos de aprendizaje" + 
				"muy distintos y cómo encapsula la información", "coursera", 4);
			ie.adicione(m1);
			fail("No lanzo excepcion");
		} catch(IEMOISExcepcion e) {
			assertTrue(e.getMessage().equals(IEMOISExcepcion.NO_NOMBRE));
		}
		
	}
	
	@Test
	public void deberiaFallarAlAdicionarSemanaTexto() throws IEMOISExcepcion{
		Iemois ie = new Iemois();
		try {
			ie.adicione("Aprendiendo a aprender", "Aprendizaje", 
					"Este curso te brinda acceso a invaluables técnicas de aprendizaje utilizadas " +
					"por expertos. Aprenderemos cómo el cerebro utiliza dos modos de aprendizaje" + 
					"muy distintos y cómo encapsula la información" , "Course", "varias");
			fail("No lanzo la excepcion");
		} catch(IEMOISExcepcion e) {
			assertTrue(e.getMessage().equals(IEMOISExcepcion.SEMANA_TEXTO));
		}
	}
	
	@Test
	public void deberiaFallarAlAdicionarRepetido() throws IEMOISExcepcion{
		Iemois ie = new Iemois();
		try {
			Mooc m1 = new Mooc("Aprendiendo a aprender", "aprendizaje", 
				"Este curso te brinda acceso a invaluables técnicas de aprendizaje utilizadas " +
				"por expertos. Aprenderemos cómo el cerebro utiliza dos modos de aprendizaje" + 
				"muy distintos y cómo encapsula la información", "coursera", 4);
			Mooc m2 = new Mooc("Aprendiendo a aprender", "aprendizaje", 
					"Este curso te brinda acceso a invaluables técnicas de aprendizaje utilizadas " +
					"por expertos. Aprenderemos cómo el cerebro utiliza dos modos de aprendizaje" + 
					"muy distintos y cómo encapsula la información", "coursera", 4);

			ie.adicione(m1); ie.adicione(m2);
			fail("No lanzo excepcion");
		} catch(IEMOISExcepcion e) {
			assertTrue(e.getMessage().equals(IEMOISExcepcion.CURSO_REPETIDO));
		}
	}
	
	@Test
	public void deberiaTenerCiertoNumeroDeMoocs() throws IEMOISExcepcion{
		Iemois ie = new Iemois();
		try {
			Mooc m1 = new Mooc("Databases","Bases de Datos", 
		        "En este curso aprendera¡ las tecnologias bÃ¡sicas de las bases de datos.",
		    	        "Stanford Lagunita", 13);
			Mooc m2 = new Mooc("Prendiendo a aprender", "aprendizaje", 
				"Este curso te brinda acceso a invaluables técnicas de aprendizaje utilizadas " +
				"por expertos.", "coursera", 4);
			ie.adicione(m1); ie.adicione(m2);
			assertEquals(2, ie.numeroCursos());
		}catch(IEMOISExcepcion e) {
			fail("Lanzo excepcion");
		}
	}
	
	@Test
	public void deberiaListarTodosLosMooc() throws IEMOISExcepcion{
		Iemois ie = new Iemois();
		StringBuffer desire = new StringBuffer();
		try {
			Mooc m1 = new Mooc("Databases","Bases de Datos", 
		        "En este curso aprendera¡ las tecnologias bÃ¡sicas de las bases de datos.",
		    	        "Stanford Lagunita", 13);
			Mooc m2 = new Mooc("Prendiendo a aprender", "aprendizaje", 
				"Este curso te brinda acceso a invaluables técnicas de aprendizaje utilizadas " +
				"por expertos.", "coursera", 4);
			ie.adicione(m1); ie.adicione(m2);
			desire.append(m1.toString()); desire.append("\n\n");
			desire.append(m2.toString());desire.append("\n\n");
			assertTrue(desire.toString().equals(ie.toString()));
		} catch(IEMOISExcepcion e) {
			fail("Lanzo excepcion");
		}
	}
	
	@Test
	public void deberiaListarLosMoocInciadosPorPrendiendo() throws IEMOISExcepcion{
		Iemois ie = new Iemois();
		ArrayList<Mooc> desire = new ArrayList<Mooc>();
		try {
			Mooc m1 = new Mooc("Databases","Bases de Datos", 
		        "En este curso aprendera¡ las tecnologias bÃ¡sicas de las bases de datos.",
		    	        "Stanford Lagunita", 13);
			Mooc m2 = new Mooc("Prendiendo a aprender", "aprendizaje", 
				"Este curso te brinda acceso a invaluables técnicas de aprendizaje utilizadas " +
				"por expertos.", "coursera", 4);
			ie.adicione(m1); ie.adicione(m2);
			desire.add(m2);
			assertTrue(desire.equals(ie.busque("Prendiendo")));
		} catch(Exception e) {
			fail("Lanzo excepcion");
		}
	}
	
	@Test
	public void noDeberiaListarMooc() throws IEMOISExcepcion{
		Iemois ie = new Iemois();
		ArrayList<Mooc> desire = new ArrayList<Mooc>();
		try {
			assertTrue(desire.equals(ie.busque("Prendiendo")));
		} catch(Exception e) {
			fail("Lanzo excepcion");
		}
	}
	
}

