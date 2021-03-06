package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Thread;
import java.io.*;

import dominio.*;

public class IslaGUI extends JFrame{

    private JPanel botones;
    private JScrollPane contenedor;
    private JButton botonAccion;
    private JButton botonAprisa;
    private JButton botonCorten;
    private JButton botonDecision;  

    private FotoIsla foto;
    
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

        WindowListener w = new WindowAdapter() { 
                public void windowClosing(WindowEvent e) {
                    salir();
                }
            };  
        this.addWindowListener(w);

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

    
    public static void main(String[] args) {
        IslaGUI gui = new IslaGUI();
        gui.setVisible(true);
    }   

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



