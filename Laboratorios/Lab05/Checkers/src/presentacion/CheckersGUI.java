package presentacion; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dominio.Checkers;
import dominio.CheckersException; 

@SuppressWarnings({ "serial" })
public class CheckersGUI extends JFrame{
	
	/*Variables de control*/
	private boolean guardado;
	private boolean colorChange;
	private File actualFile;
	private String about;
	private String howTo;
	private final ImageIcon player1Icon = new ImageIcon("src/presentacion/resources/whiteToken.png");
	private final ImageIcon player2Icon = new ImageIcon("src/presentacion/resources/blackToken.png");
	private final ImageIcon fondo = new ImageIcon("src/presentacion/resources/fondo.png");
	private final ImageIcon playButtonIco = new ImageIcon("src/presentacion/resources/play.png");
	private final ImageIcon configButtonIco = new ImageIcon("src/presentacion/resources/config.png");
	private final ImageIcon backButtonIco = new ImageIcon("src/presentacion/resources/back.png");
	private final ImageIcon applyButtonIco = new ImageIcon("src/presentacion/resources/apply.png");
	private final File font = new File("resources/8-BIT.TTF");
	
	/*Barra Menu*/
	private JMenuBar barraMenu;
	private JMenuItem openItem;
	private JMenuItem saveAsItem;
	private JMenuItem saveItem;
	private JMenuItem exitItem;
	private JMenuItem configuracionItem;
	private JMenuItem helpItem;
	private JMenuItem aboutItem;
	private JMenu opciones;
	private JMenu archivo;	
	private JMenu help;
	
	
	/*Checkers*/
	Checkers checkers;
	int size;
	
	/*Start Panel*/
	private JBackground startPanel;
	private JButton play;
	private JButton settings;
	
	/*Config Panel*/
	private JPanel configPanel;
	
	private JButton aplicar;
	private JButton colorSelect1;
	private JButton colorSelect2;
	private JButton colorSelect3;
	private JButton backConfig;
	private Color[] colors;
	private JTextField widthTextField;
	private JTextField heightTextField;
	
	/*Game Panel*/
	private JPanel gamePanel;
	private Color color1;
	private Color color2;
	private Color mark;
	
	/*Tablero*/
	private JButton backGame;
	private JMenuItem restartItem;
	private JButton[][] boardSquares;
    private JPanel board;
    private int[] selectedToken;
    	
    /*Stats*/
    /*Player 1*/
    private JTextField player1Tokens;
    private JTextField player1eatenTokens;
    private JTextField player1Move;
    /*Player 2*/
    private JTextField player2Tokens;
    private JTextField player2eatenTokens;
    private JTextField player2Move;
	
    /**/
    
    public CheckersGUI(){
        prepareElementos();
        prepareAcciones();
    }

    private void prepareElementos(){
    	addFont();
    	setTitle("Checkers");
    	setIconImage(player2Icon.getImage());
    	size = 8;
    	color1 = Color.BLACK;
    	color2 = Color.WHITE;
    	mark = new Color(24,30,113);
    	colors = new Color[] {color1,color2,mark};
    	colorChange = false;
    	cargueInfo();
    	preparePaneles();
    	prepareElementosMenu();
    	setMinimumSize(new Dimension(560,610));
    	setSize(new Dimension(560,610));
    	setLocationRelativeTo(null);
    	setUIFont(new javax.swing.plaf.FontUIResource("8-bit Operator+ SC",Font.BOLD,12));
    	UIManager.put("TitledBorder.border", new LineBorder( Color.GRAY.darker(), 1));
    } 	
    
    private void prepareAcciones(){
    	guardado = true;
    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	
    	/*Window Actions*/
    	addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
    			salga();
    		}
		});
    	
    	addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
    			SwingUtilities.invokeLater(new Runnable() {
    				public void run() {
    					try {
    						refresque();
    					}catch(Exception e) {}
    				}
    			});
    		}
		});
    	
    	/*BarraMenu Actions*/
    	openItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			abra();
    		}
    		
    	});
    	
    	saveItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			guarde();
    		}
    		
    	});
    	
    	saveAsItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			guardeComo();
    		}
    		
    	});
    	
    	exitItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			salga();
    		}
    		
    	});
    	
    	configuracionItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                configure();
       	    }
    	});
    	
    	restartItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                reinicie();
       	    }
    	});
    	
    	helpItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                muestreInfo(howTo,"How To Play");
       	    }
    	});
    	
    	aboutItem.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			muestreInfo(about, "About Checkers");
       	    }
    	});
    	
    	/*StartPanel Actions*/
    	
    	play.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                cargueNewGamePanel();
       	    }
    	});
    	
    	settings.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			cargueConfigPanel();
       	    }
    	});
    	
    	/*ConfigPanel Actions*/
    	
    	aplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplique();
			}
		});
		
		colorSelect1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escojaColor(colors[0],0);
			}
		});
		
		colorSelect2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escojaColor(colors[1],1);
			}
		});
		
		colorSelect3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				escojaColor(colors[2],2);
			}
		});
		
		backConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carguePanel(configPanel,startPanel);
			}
		});
		
		widthTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}
			
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			public void changedUpdate(DocumentEvent e) {
				cambieTexto();
			}
		});
    	
    }
    
    private void prepareAccionesGame() {
    	
    	/*GamePanel Actions*/
    	for(int i = 0; i<boardSquares.length; i++) {
    		for(int j = 0; j<boardSquares.length; j++) {
    			boardSquares[i][j].setName(""+(j+1)+" "+(i+1));
    			boardSquares[i][j].addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					seleccione(e);
    				}
    			});
    		}
    	}
    	
    	backGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToIni();
			}
		});
    	
    }
    
    private void preparePaneles() {
    	prepareStartPanel();
    	prepareConfigPanel();
    	add(startPanel);
    	setResizable(false);
    }
    
    private void backToIni() {
    	size = 8;
		color1 = Color.BLACK;
		color2 = Color.WHITE;
		saveItem.setEnabled(false);
    	saveAsItem.setEnabled(false);
    	if(guardado) {
    		setSize(getMinimumSize());
    		carguePanel(gamePanel,startPanel);
		}else {
			int result = JOptionPane.showConfirmDialog(this, "Do you want to save your changes?", "Exit Application",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				if(guarde()) {
					setSize(getMinimumSize());
					carguePanel(gamePanel,startPanel);
					guardado = true;
				}
			}else {
				setSize(getMinimumSize());
				carguePanel(gamePanel,startPanel);
				guardado = true;	
			}
		}
    	setLocationRelativeTo(null);
    }
    
    private void prepareStartPanel() {
    	startPanel = new JBackground(fondo);    	
    	startPanel.setLayout(new GridBagLayout());
    	Border emptyBorder = BorderFactory.createEmptyBorder();
    	play = new JButton();
    	play.setBackground(Color.BLACK);
    	play.setBorder(emptyBorder);
    	Image resizedIco = playButtonIco.getImage();
    	resizedIco = resizedIco.getScaledInstance(70, 46, java.awt.Image.SCALE_SMOOTH );
    	play.setIcon(new ImageIcon(resizedIco));
    	settings = new JButton();
    	settings.setBackground(Color.BLACK);
    	settings.setBorder(emptyBorder);
    	resizedIco = configButtonIco.getImage();
    	resizedIco = resizedIco.getScaledInstance(70, 46, java.awt.Image.SCALE_SMOOTH );
    	settings.setIcon(new ImageIcon(resizedIco));
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	startPanel.add(play,constraints);
    	constraints.gridx = 1;
    	JLabel separator = new JLabel("<html><font color='black'>____________</font></html>");
    	startPanel.add(separator,constraints);
    	constraints.gridx = 2;
    	startPanel.add(settings,constraints);
    }
    
    private void prepareGamePanel(){
    	gamePanel = new JPanel();
    	gamePanel.setLayout(new BorderLayout());
    	gamePanel.setBackground(Color.BLACK);
    	boardSquares = new JButton[size][size];
    	prepareElementosTablero();
    	prepareElementosStats();
    	prepareAccionesGame();
    	
    }
    
    private void prepareConfigPanel() {
    	configPanel = new JPanel();
    	configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.PAGE_AXIS));
    	configPanel.setBackground(Color.BLACK);
    	prepareColores();
    	prepareTamanos();
    	prepareAplicar();
    }
    
    private void prepareColores() {
    	Border emptyBorder = BorderFactory.createEmptyBorder();
    	Font font = new Font("8-bit Operator+ SC",Font.BOLD,12);
		JPanel colores = new JPanel();
		colores.setOpaque(false);
    	colores.setLayout(new GridBagLayout());
    	TitledBorder panelTitle = new TitledBorder("Colors");
    	panelTitle.setTitleColor(Color.GRAY.brighter());
    	colores.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), panelTitle));
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.weighty = 1.0;
    	constraints.weightx = 1.0;
    	
    	JTextField color1 = new JTextField("Color 1:");
    	color1.setEditable(false);
    	color1.setBorder(emptyBorder);
    	color1.setFont(font);
    	color1.setBackground(Color.BLACK);
    	color1.setForeground(Color.GRAY.brighter());
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add(color1, constraints);
    	
    	colorSelect1 = new JButton("");
    	colorSelect1.setBackground(colors[0]);
    	constraints.gridx = 2;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (colorSelect1, constraints);
    	
    	JTextField color2 = new JTextField("Color 2:");
    	color2.setEditable(false);
    	color2.setBorder(emptyBorder);
    	color2.setFont(font);
    	color2.setBackground(Color.BLACK);
    	color2.setForeground(Color.GRAY.brighter());
    	constraints.gridx = 0;
    	constraints.gridy = 1;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add(color2, constraints);
    	
    	colorSelect2 = new JButton ("");
    	colorSelect2.setBackground(colors[1]);
    	constraints.gridx = 2;
    	constraints.gridy = 1;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (colorSelect2, constraints); 
    	
    	JTextField mark = new JTextField("Mark:");
    	mark.setEditable(false);
    	mark.setBorder(emptyBorder);
    	mark.setFont(font);
    	mark.setBackground(Color.BLACK);
    	mark.setForeground(Color.GRAY.brighter());
    	constraints.gridx = 0;
    	constraints.gridy = 2;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add(mark, constraints);
    	
    	colorSelect3 = new JButton ("");
    	colorSelect3.setBackground(colors[2]);
    	constraints.gridx = 2;
    	constraints.gridy = 2;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	colores.add (colorSelect3, constraints); 
    	
    	configPanel.add(colores);
	}
	
	private void prepareTamanos() {
		Border emptyBorder = BorderFactory.createEmptyBorder();
    	Font font = new Font("8-bit Operator+ SC",Font.BOLD,12);
		JPanel tamano = new JPanel();
    	tamano.setLayout(new GridBagLayout());
    	TitledBorder panelTitle = new TitledBorder("Size");
    	panelTitle.setTitleColor(Color.GRAY.brighter()); 
    	tamano.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), panelTitle));
    	GridBagConstraints constraints = new GridBagConstraints();
    	tamano.setOpaque(false);
    	
    	widthTextField = new JTextField("8");
    	widthTextField.setColumns(4);
    	widthTextField.setFont(font);
    	widthTextField.setBackground(Color.BLACK);
    	widthTextField.setForeground(Color.GRAY.brighter());
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	tamano.add(widthTextField, constraints);
    	
    	JTextField separator = new JTextField(" x ");
    	separator.setColumns(2);
    	separator.setFont(font);
    	separator.setBackground(Color.BLACK);
    	separator.setForeground(Color.GRAY.brighter());
    	separator.setBorder(emptyBorder);
    	constraints.gridx = 1;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	tamano.add(separator, constraints);
    	
    	heightTextField = new JTextField("8");
    	heightTextField.setColumns(4);
    	heightTextField.setEditable(false);
    	heightTextField.setFont(font);
    	heightTextField.setBackground(Color.BLACK);
    	heightTextField.setForeground(Color.GRAY.brighter());
    	constraints.gridx = 2;
    	constraints.gridy = 0;
    	constraints.gridwidth = 1;
    	constraints.gridheight = 1;
    	tamano.add(heightTextField, constraints);
    	
    	configPanel.add(tamano);
	}
	
	private void prepareAplicar() {
		Border emptyBorder = BorderFactory.createEmptyBorder();
		JPanel botones = new JPanel();
		botones.setOpaque(false);
		GridBagConstraints constraints = new GridBagConstraints();
    	botones.setLayout(new GridBagLayout());
    	aplicar = new JButton();
    	aplicar.setBorder(emptyBorder);
    	Image resizedBackButton = applyButtonIco.getImage().getScaledInstance(70, 46, java.awt.Image.SCALE_SMOOTH );
    	aplicar.setIcon(new ImageIcon(resizedBackButton));
    	aplicar.setBackground(Color.BLACK);
    	botones.add(aplicar);
    	backConfig = new JButton();
    	backConfig.setBorder(emptyBorder);
    	resizedBackButton = backButtonIco.getImage().getScaledInstance(70, 46, java.awt.Image.SCALE_SMOOTH );
    	backConfig.setIcon(new ImageIcon(resizedBackButton));
    	backConfig.setBackground(Color.BLACK);
    	constraints.gridx = 1;
    	botones.add(new JLabel("   "));
    	constraints.gridx = 2;
    	botones.add(backConfig);
    	configPanel.add(botones);
	}
	
	private void aplique() {
		int size = 0;
		try {
			size = Integer.parseInt(widthTextField.getText());
			checkers = new Checkers(size);
			if(colors[0].equals(colors[1]) || colors[1].equals(colors[2]) || colors[0].equals(colors[2])) {
				raiseError("Colors must be different.");
			}
			else {
				color1 = colors[0];
				color2 = colors[1];
				mark = colors[2];
				this.size = size;
				carguePanel(configPanel,startPanel);
			}
		}catch(Exception e){
			if(e.getMessage().equals(CheckersException.VALUE_OUT_OF_LIMITS)) {
				raiseError("The value is lower than 4 or is greater than 26.");
			}else raiseError("Board's size must be a integer.");
		}
		
	}
	
	private void escojaColor(Color defaultColor, int chooser) {
		if(chooser == 0) {
			colors[0] = JColorChooser.showDialog(this, "Pick a color", defaultColor);
			colorSelect1.setBackground(colors[0]);
		}
		else if(chooser == 1){
			colors[1] = JColorChooser.showDialog(this, "Pick a color", defaultColor);
			colorSelect2.setBackground(colors[1]);
		}
		
		else {
			colors[2] = JColorChooser.showDialog(this, "Pick a color", defaultColor);
			colorSelect3.setBackground(colors[2]);
		}
	}
    
	public final void prepareElementosTablero() {
        board = new JPanel(new GridLayout(0, size+2)){
        	    @Override
        	    public final Dimension getPreferredSize() {
        	        Dimension d = super.getPreferredSize();
        	        Dimension prefSize = null;
        	        Component c = getParent();
        	        if (c == null) {
        	            prefSize = new Dimension(
        	                    (int)d.getWidth(),(int)d.getHeight());
        	        } else if (c!=null &&
        	                c.getWidth()>d.getWidth() &&
        	                c.getHeight()>d.getHeight()) {
        	            prefSize = c.getSize();
        	        } else {
        	            prefSize = d;
        	        }
        	        int w = (int) prefSize.getWidth();
        	        int h = (int) prefSize.getHeight();
        	        int s = (w>h ? h : w);
        	        return new Dimension(s,s);
        	    }
        };
        board.setBackground(Color.BLACK);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(Color.BLACK);
        boardConstrain.add(board);
        gamePanel.add(boardConstrain,BorderLayout.CENTER);
        añadaBotonesTablero();
        lleneTablero();
        
    }
    
	public final void añadaBotonesTablero() {
    	board.setBorder(new LineBorder(Color.BLACK));
    	Border emptyBorder = BorderFactory.createEmptyBorder();
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < boardSquares.length; ii++) {
            for (int jj = 0; jj < boardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                b.setBorder(emptyBorder);
                if ((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(color2);
                } else {
                    b.setBackground(color1);
                }
                boardSquares[jj][ii] = b;
            }
        }
    }
    
    public final void lleneTablero() {
    	//fill the chess board
    	JLabel identifier;
        board.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < size; ii++) {
        	identifier = new JLabel(Character.toString((char) ii+65),SwingConstants.CENTER);
        	identifier.setForeground(Color.gray.brighter());
            board.add(identifier);
        }
        board.add(new JLabel(""));
        // fill the black non-pawn piece row
        for (int ii = 0; ii < size; ii++) {
            for (int jj = 0; jj < size; jj++) {
                switch (jj) {
                    case 0:
                    	identifier = new JLabel("" + (ii+1),SwingConstants.CENTER);
                    	identifier.setForeground(Color.gray.brighter());
                        board.add(identifier);
                    default:
                        board.add(boardSquares[jj][ii]);
                }
            }
            identifier = new JLabel("" + (ii+1),SwingConstants.CENTER);
            identifier.setForeground(Color.gray.brighter());
            board.add(identifier);
        }
        board.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < size; ii++) {
        	identifier = new JLabel(Character.toString((char) ii+65),SwingConstants.CENTER);
        	identifier.setForeground(Color.gray.brighter());
            board.add(identifier);
        }
        board.add(new JLabel(""));
    }
    
    private void prepareElementosMenu() {
    	barraMenu = new JMenuBar();
    	prepareFileMenu();
    	prepareOptionMenu();
    	prepareHelpMenu();
    }
    
    private void prepareFileMenu() {	
    	archivo = new JMenu("File");
    	openItem = new JMenuItem("Open");
    	saveItem = new JMenuItem("Save");
    	saveAsItem = new JMenuItem("Save as");
    	exitItem = new JMenuItem("Exit");
    	saveItem.setEnabled(false);
    	saveAsItem.setEnabled(false);
    	archivo.add(openItem);
    	archivo.addSeparator();
    	archivo.add(saveItem);
    	archivo.add(saveAsItem);
    	archivo.addSeparator();
    	archivo.add(exitItem);
    	barraMenu.add(archivo);
    	setJMenuBar(barraMenu);
    }
    
    private void prepareOptionMenu() {
    	opciones = new JMenu("Options");
    	configuracionItem = new JMenuItem("Settings");
    	restartItem = new JMenuItem("Restart");
    	opciones.add(configuracionItem);
    	opciones.add(restartItem);
    	
    }
    
    private void prepareHelpMenu() {
    	help = new JMenu("Help");
    	helpItem = new JMenuItem("How to play");
    	aboutItem = new JMenuItem("About Checkers");
    	help.add(helpItem);
    	help.add(aboutItem);
    	barraMenu.add(help);
    }
    
    private void prepareElementosStats() {
    	Border emptyBorder = BorderFactory.createEmptyBorder();  
    	player1Tokens = new JTextField("0");
    	player1Tokens.setForeground(Color.GRAY.brighter());
    	player1Tokens.setHorizontalAlignment(SwingConstants.CENTER);
    	player1Tokens.setBorder(emptyBorder);
    	player1Tokens.setEditable(false);
    	player1Tokens.setBackground(Color.BLACK);
    	player1eatenTokens = new JTextField("0");
    	player1eatenTokens.setForeground(Color.GRAY.brighter());;
    	player1eatenTokens.setHorizontalAlignment(SwingConstants.CENTER);
    	player1eatenTokens.setBorder(emptyBorder);
    	player1eatenTokens.setEditable(false);
    	player1eatenTokens.setBackground(Color.BLACK);
    	player1Move = new JTextField("0");
    	player1Move.setForeground(Color.GRAY.brighter());
    	player1Move.setHorizontalAlignment(SwingConstants.CENTER);
    	player1Move.setBorder(emptyBorder);
    	player1Move.setEditable(false);
    	player1Move.setBackground(Color.BLACK);
    	JPanel playerPanel = prepareStats("Player",player1Icon,player1Tokens,player1eatenTokens,player1Move);
    	playerPanel.setForeground(Color.GRAY.brighter());
    	JPanel backButtonPanel = new JPanel();
    	backButtonPanel.setLayout(new GridBagLayout());
    	backGame = new JButton();
    	backGame.setBorder(emptyBorder);
    	Image resizedBackButton = backButtonIco.getImage().getScaledInstance(60, 36, java.awt.Image.SCALE_SMOOTH );
    	backGame.setIcon(new ImageIcon(resizedBackButton));
    	backGame.setBackground(Color.BLACK);
    	backButtonPanel.setBackground(Color.BLACK);
    	backButtonPanel.add(backGame);
    	playerPanel.add(backButtonPanel);
    	playerPanel.setBackground(Color.BLACK);
    	playerPanel.setForeground(Color.GRAY.brighter());
    	gamePanel.add(playerPanel,BorderLayout.WEST);
    	player2Tokens = new JTextField("0");
    	player2Tokens.setForeground(Color.GRAY.brighter());
    	player2Tokens.setHorizontalAlignment(SwingConstants.CENTER);
    	player2Tokens.setBorder(emptyBorder);
    	player2Tokens.setEditable(false);
    	player2Tokens.setBackground(Color.BLACK);
    	player2eatenTokens = new JTextField("0");
    	player2eatenTokens.setForeground(Color.GRAY.brighter());
    	player2eatenTokens.setHorizontalAlignment(SwingConstants.CENTER);
    	player2eatenTokens.setBorder(emptyBorder);
    	player2eatenTokens.setEditable(false);
    	player2eatenTokens.setBackground(Color.BLACK);
    	player2Move = new JTextField("0");
    	player2Move.setForeground(Color.GRAY.brighter());
    	player2Move.setHorizontalAlignment(SwingConstants.CENTER);
    	player2Move.setBorder(emptyBorder);
    	player2Move.setEditable(false);
    	player2Move.setBackground(Color.BLACK);
    	JPanel player2Panel = prepareStats("Computer",player2Icon,player2Tokens,player2eatenTokens,player2Move);
    	player2Panel.setBackground(Color.BLACK);
    	gamePanel.add(player2Panel,BorderLayout.EAST);
    }
    
    private JPanel prepareStats(String title,ImageIcon ico,JTextField tokens,JTextField eatenTokens,JTextField move) {
    	Border emptyBorder = BorderFactory.createEmptyBorder();
    	TitledBorder panelTitle = new TitledBorder(title);
    	panelTitle.setTitleColor(Color.GRAY.brighter());
    	JPanel stats = new JPanel();
    	stats.setLayout(new GridLayout(3,2));
    	stats.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), panelTitle));
    	JPanel info = new JPanel();
    	info.setBackground(Color.BLACK);
    	panelTitle = new TitledBorder("Stats");
    	panelTitle.setTitleColor(Color.GRAY.brighter());
    	info.setBorder(new CompoundBorder(new EmptyBorder(5,5,5,5), panelTitle));
    	info.setLayout(new GridLayout(3,2));
    	info.setForeground(Color.GRAY.brighter());
    	JTextField tokensText = new JTextField("Tokens");
    	tokensText.setEditable(false);
    	tokensText.setBorder(emptyBorder);
    	tokensText.setBackground(Color.BLACK);
    	tokensText.setForeground(Color.GRAY.brighter());
    	info.add(tokensText);
    	info.add(tokens);
    	JTextField eatenTokensText = new JTextField("Eaten Tokens");
    	eatenTokensText.setEditable(false);
    	eatenTokensText.setBorder(emptyBorder);
    	eatenTokensText.setBackground(Color.BLACK);
    	eatenTokensText.setForeground(Color.GRAY.brighter());
    	info.add(eatenTokensText);
    	info.add(eatenTokens);
    	JTextField movesText = new JTextField("Moves");
    	movesText.setEditable(false);
    	movesText.setBorder(emptyBorder);
    	movesText.setBackground(Color.BLACK);
    	movesText.setForeground(Color.GRAY.brighter());
    	info.add(movesText);
    	info.add(move);
    	stats.add(info);
    	JPanel piece = new JPanel();
    	piece.setBackground(Color.BLACK);
    	piece.setLayout(new GridBagLayout());
    	JLabel token = new JLabel();
    	token.setIcon(ico);
    	piece.add(token);
    	stats.add(info);
    	stats.add(piece); 	
    	return stats;
    }
    
    private void abra() {
    	JFileChooser fileChooser = new JFileChooser();
    	int seleccion = fileChooser.showOpenDialog(this);
    	if(seleccion == JFileChooser.APPROVE_OPTION) {
    		File file = fileChooser.getSelectedFile();
    		try {
    			ArrayList<String> data = leaArchivo(file);
    			size = Integer.parseInt(data.get(0));
    			String[] aux = new String[]{data.get(1).split(" ")[0],data.get(1).split(" ")[1]};
    			color1 = new Color(Integer.parseInt(aux[0]));
    			color2 = new Color(Integer.parseInt(aux[1]));
    			aux = new String[] {data.get(2).split(" ")[0],data.get(2).split(" ")[1]};
    			int[] moves = new int[] {Integer.parseInt(aux[0]),Integer.parseInt(aux[1])};
    			aux = new String[] {data.get(3).split(" ")[0],data.get(3).split(" ")[1]};
    			int[] tokens = new int[] {Integer.parseInt(aux[0]),Integer.parseInt(aux[1])};
    			char[][] board = new char[size][size];
    			for(int i=0;i<size;i++) {
    				String row = data.get(i+4); 
    				for(int j=0;j<size;j++) {
    					board[i][j] = row.charAt(j);
    				}
    			}
    			int actualPlayer = moves[0]>moves[1]?1:0;
    			checkers = new Checkers(board,actualPlayer,tokens,moves);
    			cargueGamePanel();
    		}catch(Exception e) {
    			raiseError("Corrupt File");
    		}
    	}
    }
    
    private boolean guarde() {
    	boolean guardado = false;
    	try {
    		if(actualFile!=null) {
	    		FileWriter writer = new FileWriter(actualFile);
	    		writer.write(prepareSave());
	    		writer.close();
	    		this.guardado = true;
    		}
    		else {
    			guardado = guardeComo();
    		}
    	}catch(Exception e) {}
    	return guardado;
    }
    
    private boolean guardeComo() {
    	boolean guardado = false;
    	JFileChooser fileChooser = new JFileChooser();
    	int seleccion = fileChooser.showSaveDialog(this);
    	if(seleccion == JFileChooser.APPROVE_OPTION) {
    		File file = fileChooser.getSelectedFile();
    		try {
    			File newFile = new File(file.getPath()+".ck");
    			if(!newFile.createNewFile()) {
    				Toolkit.getDefaultToolkit().beep();
    	    		int result = JOptionPane.showConfirmDialog(this,newFile.getName()+" already exist" , 
    	    				"Save as Confirmation", 0, JOptionPane.WARNING_MESSAGE);
    	    		if(result == JOptionPane.YES_OPTION) {
    	    			actualFile = newFile;
    	    			guarde();
    	    			guardado = true;
    	    		}
    			}else {
    				actualFile = newFile;
	    			guarde();
    			}
    		}
    		catch(Exception e) {}
    	}
    	return guardado;
    }
   
    private void salga() {
    	if(!guardado) {
			int result = JOptionPane.showConfirmDialog(this, "Do you want to save your changes?", "Exit Application",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				if(guarde()) System.exit(0);
			}else System.exit(0);
		}else System.exit(0);
    }
    
    private void refresque(){
    	if(colorChange) {
    		cambieColor();
    		colorChange = false;
    	}
    	drawPieces();
    	actualiceStats(checkers.tokens(),checkers.eatenTokens(),checkers.consultMoves());
    }
    
    private void cambieColor() {
    	for (int i = 0; i < boardSquares.length; i++) {
            for (int j = 0; j < boardSquares[i].length; j++) {
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                	boardSquares[i][j].setBackground(color2);
                } else {
                	boardSquares[i][j].setBackground(color1);
                }
            }
    	}
    }
    private void drawPieces() {
    	/*Consultar checkers*/
    	char[][] positions = checkers.consult();
    	JButton button = boardSquares[0][0]; 
    	ImageIcon image = player1Icon;
    	Image whiteResizedImage = image.getImage().getScaledInstance(button.getWidth()-8, button.getWidth()-8, java.awt.Image.SCALE_SMOOTH );
    	image = player2Icon;
    	Image blackResizedImage = image.getImage().getScaledInstance(button.getWidth()-8, button.getWidth()-8, java.awt.Image.SCALE_SMOOTH );
    	ImageIcon whiteResizedIco = new ImageIcon(whiteResizedImage);
    	ImageIcon blackResizedIco = new ImageIcon(blackResizedImage);
    	for (int i = 0; i < boardSquares.length; i++) {
            for (int j = 0; j < boardSquares[i].length; j++) {
                if (positions[i][j]=='0') {
                	boardSquares[j][i].setIcon(whiteResizedIco);
                }else if (positions[i][j]=='1') {
                	boardSquares[j][i].setIcon(blackResizedIco);
                }
                else boardSquares[j][i].setIcon(null);
            }
    	}
    }
    
    
    private void configure() {
    	ConfigWindow configDialog = new ConfigWindow(this,color1,color2,mark,size);
    	Color colors[] = configDialog.showDialog();
    	if(!color1.equals(colors[0]) || !color2.equals(colors[1]) || !mark.equals(colors[2])) {
    		colorChange = true;
    		color1 = colors[0];
        	color2 = colors[1];
        	mark = colors[2];
        	refresque();
    	}else colorChange = false;
    	
    }
    
    private void reinicie() {
    	if(checkers.winner()==-1) {
    		if(!guardado) {
    		int result = JOptionPane.showConfirmDialog(this, "Do you want to save your changes?", "Exit Application",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				guarde();
				}
	    	}
    	}
    	guardado=true;
    	try {
	    	checkers = new Checkers(size);
	    }catch(Exception e) {}
    	refresque();
    	
    }
    
    private void seleccione(ActionEvent e) {
    	JButton button = (JButton) e.getSource();
    	int i, j;
    	i = Integer.parseInt(button.getName().split(" ")[0]);
    	j = Integer.parseInt(button.getName().split(" ")[1]);
    	cambieColor();
    	if(checkers.seleccione(i,j)) {
    		selectedToken = new int[] {i,j};
	    	for(ArrayList<Integer> pos: checkers.posibleMovesAndJumps(i-1, j-1)) {
	    		boardSquares[pos.get(1)][pos.get(0)].setBackground(mark);
	    	}
	    }
    	else if(selectedToken!=null) {
	    	mueva(i,j);
	    }
    	refresque();
    }
    
    private void mueva(int i, int j) {
    	try {
    		if(checkers.move(selectedToken[0], selectedToken[1], i, j)) {
    			endGame(checkers.winner());
    		}
    		guardado = false;
    	}catch(Exception exc) {
    		Toolkit.getDefaultToolkit().beep();
    	}
    	selectedToken = null;
    }
    
    private void actualiceStats(int[] tokens, int[] eatenTokens, int[] moves) {
		/***/
		/*Player 2*/
		player1Tokens.setText(""+tokens[0]);
	    player1eatenTokens.setText(""+eatenTokens[0]);
	    player1Move.setText(""+moves[0]);
	    /*Player 2*/
	    player2Tokens.setText(""+tokens[1]);
	    player2eatenTokens.setText(""+eatenTokens[1]);
	    player2Move.setText(""+moves[1]);
    }
    
    private void cargueGamePanel() {
    	saveItem.setEnabled(true);
    	saveAsItem.setEnabled(true);
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	setSize(new Dimension((int)dim.getWidth()/2,(int)dim.getHeight()/2));
    	setLocationRelativeTo(null);
    	prepareGamePanel();
    	carguePanel(startPanel,gamePanel);
    	barraMenu.remove(help);
    	barraMenu.add(opciones);
    	barraMenu.add(help);
    	
    }
    private void cargueNewGamePanel() {
    	setResizable(true);
    	try {
    		checkers = new Checkers(size);
    	}catch(Exception e) {}
    	cargueGamePanel();
    }
    
    private void cargueConfigPanel() {
    	colorSelect1.setBackground(color1);
    	colorSelect2.setBackground(color2);
    	widthTextField.setEditable(true);
    	carguePanel(startPanel,configPanel);
    }
    
    private void carguePanel(JPanel ini, JPanel fin) {
    	barraMenu.remove(opciones);
    	ini.setVisible(false);
    	remove(ini);
    	add(fin);
    	fin.setVisible(true);
    }
    
    public static void main(String[] args) {
    	CheckersGUI gui = new CheckersGUI();
    	gui.setVisible(true);
    }
    
    private void raiseError(String message) {
    	Toolkit.getDefaultToolkit().beep();
    	JOptionPane.showMessageDialog(null, message,"Error",JOptionPane.ERROR_MESSAGE);
    }

    private void cambieTexto() {
    	heightTextField.setText(widthTextField.getText());
    }
    
    private void cargueInfo(){
    	File loadFile = new File("resources\\About.txt");
    	about = "";
    	for(String i: leaArchivo(loadFile)) about += i+"\n";
    	loadFile = new File("resources\\Rules.txt");
    	howTo = "";
    	for(String i: leaArchivo(loadFile)) howTo += i+"\n";
    }
    
    private void muestreInfo(String info, String title) {
    	JTextArea textInfo = new JTextArea(info);
    	textInfo.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textInfo);
		add(scrollPane,BorderLayout.CENTER);
		if(scrollPane.getPreferredSize().height>this.getMinimumSize().height ||
				scrollPane.getPreferredSize().width>this.getMinimumSize().width) {
			scrollPane.setPreferredSize(this.getMinimumSize());
		}
		JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.NO_OPTION);
    }
    
    private String prepareSave() {
    	String save = ""+size+"\n"; 
    	save += ""+color1.getRGB()+" "+color2.getRGB()+"\n";
    	for(int i: checkers.consultMoves()) {
    		save += i+" ";
    	}save += "\n";
    	for(int i: checkers.tokens()) {
    		save += i+" ";
    	}save += "\n";
    	for(char[] i: checkers.consult()) {
    		for(char j: i) save += j;
    		save += "\n";
    	}
    	return save;
    }
    
    private ArrayList<String> leaArchivo(File loadFile) {
    	ArrayList<String> save = new ArrayList<String>();
    	try {
    		Scanner scan = new Scanner(loadFile);
    		while(scan.hasNextLine()) {
    			save.add(scan.nextLine());
    		}
    		scan.close();
    	}catch(Exception e) {}	
    	return save;
    }
    
    private void endGame(int winner) {
    	String message = "You lose";
    	if(winner == 0) {
    		message = "You win";
    	}
    	JOptionPane.showMessageDialog(this, message, "End Game", JOptionPane.NO_OPTION);
    	int result = JOptionPane.showConfirmDialog(this, "Play again?", "Play again",JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			reinicie();
			}
		else {
    		System.exit(0);
    	}
    }
    
    public class JBackground extends JPanel{
    	private ImageIcon background;
    	
    	public JBackground(ImageIcon image) {
    		background = image;
    	}
    	
    	public void paintComponent(Graphics g) {
    		g.drawImage(background.getImage(), 0, 0, 550, 550, null);
    		setOpaque(false);
    		super.paintComponent(g);
    	}
    }
    
    @SuppressWarnings("rawtypes")
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
		java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value instanceof javax.swing.plaf.FontUIResource)
        	  UIManager.put (key, f);
          }
        } 
    public void addFont() {
    	try {
    	     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font));
    	} catch (Exception e) {
    	     raiseError(e.getMessage());
    	}
    }
    
}