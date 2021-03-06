package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dominio.EnIsla;
import dominio.Isla;
import dominio.IslaTortugaException;
import dominio.Persona;

public class IslaGUI extends JFrame{

    private JPanel botones;
    private JScrollPane contenedor;
    private JButton botonAccion;
    private JButton botonAprisa;
    private JButton botonCorten;
    private JButton botonDecision;  

    private FotoIsla foto;
    
	/*Barra Menu*/
	private JMenuBar barraMenu;
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveAsItem;
	private JMenuItem exportAsItem;
	private JMenuItem importItem;
	private JMenuItem exitItem;
	private JMenuItem configuracionItem;
	private JMenuItem helpItem;
	private JMenuItem aboutItem;
	private JMenu opciones;
	private JMenu archivo;	
	private JMenu help;

    
    private IslaGUI() {
        super("Isla Tortuga");
        try {
            Isla.demeIsla().algunosEnIsla();     
            elementos();
            acciones();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void elementos() throws Exception {

        setLayout(new BorderLayout());    
        contenedor = new JScrollPane();

        foto= new FotoIsla();
        contenedor.getViewport().add(foto);

        botones=new JPanel(new GridLayout(1,4));
        botonAccion=new JButton("Actuen");
        botonAprisa=new JButton("Aprisa");
        botonCorten=new JButton("Paren");
        botonDecision=new JButton("Decidan"); 
        botones.add(botonAccion);
        botones.add(botonAprisa);
        botones.add(botonCorten);
        botones.add(botonDecision); 

        add(contenedor,BorderLayout.CENTER);
        add(botones,BorderLayout.SOUTH);

        barraMenu = new JMenuBar();
        archivo = new JMenu("File");
        newItem = new JMenuItem("New");
    	openItem = new JMenuItem("Open");
    	saveAsItem = new JMenuItem("Save as");
    	exportAsItem = new JMenuItem("Export as");
    	importItem = new  JMenuItem("Import");
    	exitItem = new JMenuItem("Exit");
    	archivo.add(newItem);
    	archivo.addSeparator();
    	archivo.add(openItem);
    	archivo.add(saveAsItem);
    	archivo.addSeparator();
    	archivo.add(exportAsItem);
    	archivo.add(importItem);
    	archivo.addSeparator();
    	archivo.add(exitItem);
    	barraMenu.add(archivo);
    	setJMenuBar(barraMenu);
    	
    	
        pack();
        setSize(Isla.MAXIMO+100,Isla.MAXIMO+135);

        
        setResizable(false);
    }

    private void acciones(){
        ActionListener oyenteBotonAccion=new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    actuen();
                }   
            };  
        botonAccion.addActionListener(oyenteBotonAccion);

        ActionListener oyenteBotonAprisa=new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    aprisa();
                }   
            };  
        botonAprisa.addActionListener(oyenteBotonAprisa);  

        ActionListener oyenteBotonCorten=new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    paren();
                }   
            };  
        botonCorten.addActionListener(oyenteBotonCorten);

        ActionListener oyenteBotonDecision=new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    decidan();
                }   
            }; 
        botonDecision.addActionListener(oyenteBotonDecision);
        
        prepareMetodosMenu();
    }   

    private void prepareMetodosMenu() {
        newItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionNuevo();
    		}
    		
    	});
        
        openItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionAbra();
    		}
    		
    	});
    	
    	saveAsItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionGuardeComo();
    		}
    		
    	});
    	
    	exportAsItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionExportar();
    		}
    	});
    	
    	importItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionImportar();
    		}
    	});
    	
    	exitItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionSalga();
    		}
    		
    	});
        
    }
    
    private void opcionNuevo() {
    	Isla.cambieIsla(null);
    	Isla.demeIsla();
    	actualice();
    }
    
    private void opcionAbra(){
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("."));
    	try {
    		int seleccion = fileChooser.showOpenDialog(this);
    		File file = new File("");
    		if (seleccion == JFileChooser.APPROVE_OPTION) {
    			file = fileChooser.getSelectedFile();
    		}
    		Isla.demeIsla().abra(file);
    		actualice();
    	}catch (IslaTortugaException e) {
    		raiseError(e.getMessage());
    	}
    	
    }
    
    private void opcionGuardeComo(){
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("."));
    	fileChooser.setFileFilter(new FileNameExtensionFilter("DAT File","dat"));
    	try {
        	int seleccion = fileChooser.showSaveDialog(this);
        	File file = new File("");
        	if(seleccion == JFileChooser.APPROVE_OPTION) {
        		file = fileChooser.getSelectedFile();
        	}
    		Isla.demeIsla().guarde(file);
    	}catch (IslaTortugaException e) {
    		raiseError(e.getMessage());
    	}
    }
    
    private void opcionExportar() {
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("."));
    	fileChooser.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
    	try {
    		int seleccion = fileChooser.showSaveDialog(this);
    		File file = new File("");
    		if (seleccion == JFileChooser.APPROVE_OPTION) {
    			file = fileChooser.getSelectedFile();
    		}
    		Isla.demeIsla().exporte(file);
    	}catch (IslaTortugaException e) {
    		raiseError(e.getMessage());
    	}
    }
    
    private void opcionImportar() {
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("."));
    	try {
    		int seleccion = fileChooser.showOpenDialog(this);
    		File file = new File("");
    		if (seleccion == JFileChooser.APPROVE_OPTION) {
    			file = fileChooser.getSelectedFile();
    		}
    		Isla.demeIsla().importe(file);
    		actualice();
    	} catch (IslaTortugaException e) {
    		raiseError(e.getMessage());
    	}
    }
    
    private void opcionSalga() {
    	System.exit(0);
    }
    
    
    private void actuen(){
        Isla.demeIsla().actuen();
        actualice();
    }

    private void aprisa(){
        for(int i=0; i<99; i++){
            actuen();
        }
    }    

    private void paren(){       
        Isla.demeIsla().paren();
        actualice();
    }       

    private void decidan(){       
        Isla.demeIsla().decidan();
        actualice();
    }   

    private void actualice(){
        foto.actualice();
    }

    
    private void salir(){
        dispose();
        System.exit(0);
    }   

    private void raiseError(String message) {
    	Toolkit.getDefaultToolkit().beep();
    	JOptionPane.showMessageDialog(null, message,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        IslaGUI gui = new IslaGUI();
        gui.setVisible(true);
    }   

    @SuppressWarnings("serial")
	class FotoIsla extends JComponent {
        
        public void actualice(){
            repaint();
        }

        public void paintComponent(Graphics g){
            int x,y;
            g.setFont(new Font("TimesRoman", Font.PLAIN, 8)); 

            for (int i=1; i<=Isla.demeIsla().numeroEnIsla(); i++) {

                EnIsla e=Isla.demeIsla().demeEnIsla(i);
                x=e.getPosicionX(); 
                y=Isla.MAXIMO-e.getPosicionY();  

                g.setColor(e.getColor()); 
                

                if (e.forma().equals("Persona")){
                    humano(g,(Persona)e,x,y);
                    g.drawString(e.mensaje(),x+20,y+10);
                } else  if (e.forma().equals("Circulo")){
                    g.fillOval(x+10,y+0,20,20);
                    g.drawString(e.mensaje(),x+20,y+10);
                } else  if (e.forma().equals("Cuadrado")){
                    g.fillRect(x,y,20,20);
                    g.drawString(e.mensaje(),x+20,y+10);
                } else if (e.forma().equals("Palmera")){
                    g.fillOval(x + 11, y + 0, 8, 35);
                    g.fillArc(x, y - 20, 30, 30, 0, -60);
                    g.fillArc(x, y - 20, 30, 30, 70, -40);
                    g.fillArc(x, y - 20, 30, 30, 100, 40);
                    g.fillArc(x, y - 20, 30, 30, 240, -60);
                } else if (e.forma().equals("Lago")){
                    g.setColor(Color.GREEN);
                    g.fillOval(x - 13, y - 18, 8, 24);
                    g.fillArc(x - 22, y - 30, 25, 25, 0, -60);
                    g.fillArc(x - 22, y - 30, 25, 25, 70, -40);
                    g.fillArc(x - 22, y - 30, 25, 25, 100, 40);
                    g.fillArc(x - 22, y - 30, 25, 25, 240, -60);
                    g.fillOval(x + 13, y - 18, 8, 24);
                    g.fillArc(x + 4, y - 30, 25, 25, 0, -60);
                    g.fillArc(x + 4, y - 30, 25, 25, 70, -40);
                    g.fillArc(x + 4, y - 30, 25, 25, 100, 40);
                    g.fillArc(x + 4, y - 30, 25, 25, 240, -60);
                    g.setColor(e.getColor()); 
                    g.fillOval(x-13, y + 0, 36, 10);
                }
            }
            super.paintComponent(g);
        }

        public void humano(Graphics g, Persona e,int x, int y){
            int pos;
            g.setColor(Color.PINK);
            g.fillOval(x+10,y+0,10,10);/*cabeza*/
            g.setColor(e.getColor()); 
            g.drawLine(x+10+5,y+10,x+10+5,y+10+20);

            pos=e.getPosicionBrazo('I');
            if (pos==Persona.ARRIBA){
                g.drawLine(x+10+5,y+10+5,x+10+15,y+10);/*brazo izq arriba*/
            } else if (pos==Persona.FRENTE){
                g.drawLine(x+10+5,y+10+5,x+10+15,y+10+5);/*brazo izq al frente*/
            } else {
                g.drawLine(x+10+5,y+10+5,x+10+15,y+10+10);/*brazo izq abajo*/
            }

            pos=e.getPosicionBrazo('D');
            if (pos==Persona.ARRIBA){
                g.drawLine(x+10+5,y+10+5,x+5,y+10);/*brazo der arriba*/
            } else if  (pos==Persona.FRENTE){
                g.drawLine(x+10+5,y+10+5,x+5,y+10+5);/*brazo der al frente*/
            } else{
                g.drawLine(x+10+5,y+10+5,x+5,y+10+10);/*brazo der abajo*/
            }

            g.drawLine(x+10+5,(y+15)+10+5,x+10+15,(y+15)+10+15);
            g.drawLine(x+10+5,(y+15)+10+5,x+5,(y+15)+10+15);

            pos=e.getPosicionPierna('D');
            if (pos==Persona.ARRIBA){
                g.drawLine(x+5,(y+15)+10+15,x+5+10,(y+15)+10+15);/*pierna der arriba*/
            } else if  (pos==Persona.FRENTE){
                g.drawLine(x+5,(y+15)+10+15,x+5-10,(y+15)+10+15+5);/*pierna der al frente*/
            } else{
                g.drawLine(x+5,(y+15)+10+15,x+5,(y+15)+10+15+10);/*pierna der abajo*/
            }

            pos=e.getPosicionPierna('I');
            if (pos==Persona.ARRIBA){
                g.drawLine(x+10+15,(y+15)+10+15,x+10+15-10,(y+15)+10+15);/*pierna izq arriba*/
            }else if  (pos==Persona.FRENTE){
                g.drawLine(x+10+15,(y+15)+10+15,x+10+15+10,(y+15)+10+15+5);/*pierna izq al frente*/
            }else {
                g.drawLine(x+10+15,(y+15)+10+15,x+10+15,(y+15)+10+15+10);/*piernaizqabajo*/
            }
        }
    }
}



