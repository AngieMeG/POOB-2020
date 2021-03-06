package presentacion;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ConfigWindow extends JDialog{
    private JButton aplicar;
	private JButton colorSelect1;
	private JButton colorSelect2;
	private JButton colorSelect3;
	private Color color1;
	private Color color2;
	private Color mark;
	private Color[] colors;
	private JTextField widthTextField;
	private JTextField heightTextField;
	private int size;
	GridBagConstraints constraints;
	
	public ConfigWindow(JFrame parent, Color color1, Color color2, Color mark,int size) {
		super(parent,Dialog.ModalityType.DOCUMENT_MODAL);
		this.color1 = color1;
		this.color2 = color2;
		this.size = size;
		this.mark = mark;
		colors = new Color[] {color1, color2,mark};
		prepareElementos();
		prepareAcciones();
	}
	
	private void prepareElementos() {
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
	 	setTitle("Settings");
    	setSize(100, 220);
    	setResizable(false);
    	prepareColores();
    	prepareTamanos();
    	prepareAplicar();
    	setLocationRelativeTo(null);
	}
	private void prepareAcciones() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
    			salga();
    		}
		});
		
		aplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplique();
			}
		});
		
		colorSelect1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escojaColor(color1,0);
			}
		});
		
		colorSelect2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escojaColor(color2,1);
			}
		});
		
		colorSelect3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escojaColor(mark,2);
			}
		});
	}
	
	private void prepareColores() {
		TitledBorder panelTitle = new TitledBorder("Colors");
		panelTitle.setTitleColor(Color.GRAY.brighter());
		JPanel colores = new JPanel();
    	colores.setLayout(new GridBagLayout());
    	colores.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), panelTitle));
    	colores.setBackground(Color.BLACK);
    	constraints = new GridBagConstraints();
    	constraints.weighty = 1.0;
    	constraints.weightx = 1.0;
    	
    	JLabel color1 = new JLabel("Color 1:");
    	color1.setForeground(Color.GRAY.brighter());
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (color1, constraints);
    	
    	colorSelect1 = new JButton ("");
    	colorSelect1.setBackground(colors[0]);
    	constraints.gridx = 2;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (colorSelect1, constraints);
    	
    	JLabel color2 = new JLabel("Color 2:");
    	color2.setForeground(Color.GRAY.brighter());
    	constraints.gridx = 0;
    	constraints.gridy = 1;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (color2, constraints);
    	
    	colorSelect2 = new JButton ("");
    	colorSelect2.setBackground(colors[1]);
    	constraints.gridx = 2;
    	constraints.gridy = 1;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (colorSelect2, constraints); 
    	
    	JLabel mark = new JLabel("Mark:");
    	mark.setForeground(Color.GRAY.brighter());
    	constraints.gridx = 0;
    	constraints.gridy = 2;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (mark, constraints);
    	
    	colorSelect3 = new JButton ("");
    	colorSelect3.setBackground(colors[2]);
    	constraints.gridx = 2;
    	constraints.gridy = 2;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (colorSelect3, constraints); 
    	
    	add(colores);
	}
	
	private void prepareTamanos() {
		TitledBorder panelTitle = new TitledBorder("Size");
		panelTitle.setTitleColor(Color.GRAY.brighter());
		JPanel tamano = new JPanel();
		tamano.setBackground(Color.BLACK);
    	tamano.setLayout(new GridBagLayout());
    	tamano.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), panelTitle));
    	constraints = new GridBagConstraints();
    	
    	widthTextField = new JTextField(""+size);
    	widthTextField.setBackground(Color.BLACK);
    	widthTextField.setForeground(Color.GRAY.brighter());
    	widthTextField.setColumns(4);
    	widthTextField.setEditable(false);
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	tamano.add(widthTextField, constraints);
    	
    	JLabel separator = new JLabel("x");
    	separator.setBackground(Color.BLACK);
    	separator.setForeground(Color.gray.brighter());
    	constraints.gridx = 1;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	tamano.add(separator, constraints);
    	
    	heightTextField = new JTextField(""+size);
    	heightTextField.setBackground(Color.BLACK);
    	heightTextField.setForeground(Color.GRAY.brighter());
    	heightTextField.setColumns(4);
    	heightTextField.setEditable(false);
    	constraints.gridx = 2;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	tamano.add(heightTextField, constraints);
    	
    	add(tamano);
	}
	
	private void prepareAplicar() {
		JPanel aplicarPanel = new JPanel();
		aplicarPanel.setBackground(Color.BLACK);
    	aplicarPanel.setLayout(new GridLayout());
    	aplicarPanel.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), new TitledBorder("")));
    	aplicar = new JButton("Apply");
    	aplicarPanel.add(aplicar);
    	
    	add(aplicarPanel);
	}
	
	private void aplique() {
		if(color1.equals(color2)) {
			JOptionPane.showMessageDialog(this,
				    "Colors must be different",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
		else {
			colors = new Color[] {color1,color2,mark};
			salga();
		}	
	}
	
	private void escojaColor(Color defaultColor, int chooser) {
		if(chooser == 0) {
			color1 = JColorChooser.showDialog(this, "Pick a color", defaultColor);
			colorSelect1.setBackground(color1);
		}
		else if(chooser == 1) {
			color2 = JColorChooser.showDialog(this, "Pick a color", defaultColor);
			colorSelect2.setBackground(color2);
		}
		else {
			mark = JColorChooser.showDialog(this, "Pick a color", defaultColor);
			colorSelect3.setBackground(mark);
		}
	}
 
	private void salga() {
		setVisible(false);
		dispose();
	}

	public Color[] showDialog() {
		setVisible(true);
		return colors;
	}
}
