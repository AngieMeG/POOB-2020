import java.awt.*;
import java.math.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.util.regex.*;
/**
 * Write a description of class Polygonal_Puzzle here.
 * The class PolygonalPuzzle generates boards of puzzles given its dimensions
 * and provide methods for the transformation and analysis of it.
 * 
 * @author Angie Medina - Jose Perez
 * @version 20/08/2020
 */
public class PolygonalPuzzle
{
    /**
     * Constructor for objects of class Polygonal_Puzzle
     */
    private int width, height;
    private boolean[][] state;
    private ArrayList<Piece> pieces;
    private ArrayList<String> colors;
    /**
     * Function requirement 1 Create a board with given information
     * @param width in pixels
     *        height in pixels
     */
    public PolygonalPuzzle(int width, int height)
    {
        this.width = width;
        this.height = height;
        state = new boolean[height][width];
        for (int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                state[i][j] = true;
            }
        }
        pieces = new ArrayList<Piece>();
        colors = new ArrayList<String>();
        Canvas canvas = Canvas.getCanvas(width, height);
    }

    /**
     * Check if the color is already used for another piece
     * @param String color is written in RGBa format
     * @return flag false there is a piece in the board with such color
     *              true otherwise
     */
    private boolean checkColor(String color){
        boolean flag =  true;
        for (String check : colors){
            if (check.equals(color)){
                flag = false;
                break;
            }
        }
        return flag;
    }
    
    /**
     * Functional requirement 2 a Create a new piece with given information
     * 
     * @param  x1 first Rectangle's x component
     *         y1 first Rectangle's y component
     *         x2 second Rectangle's x component
     *         y2 second Rectangle's y component
     *         color is written in RGB format. color must be unique
     * 
     */
    public void addPieceInfo(int x1, int y1, int x2, int y2, String color)
    {
        String pat = "((([0-1]?[0-9][0-9]?)|([2](([0-5][0-5])|[0-4][0-9])))[ ]){3}";
        if(!(color+" ").matches(pat)){
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "The color is invalid","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(!checkColor(color)){
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "There is already a piece with such color","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if (x1 > width || x2 > width || y1 > height || y2 > height){
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "The piece would be bigger than the board","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if (getPieceHand() != -1){
            Toolkit.getDefaultToolkit().beep(); 
            String text = "You can't create a piece when you have one already in hand.";
            JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            Piece piece = new Piece(x1, y1, x2, y2, color);
            pieces.add(piece);
            colors.add(color);
        }
    }
    
    /**
     * Functional requirement 2 b Create a new piece randomly
     */
    public void addPieceRandom(){
        if (getPieceHand() != -1){
            Toolkit.getDefaultToolkit().beep(); 
            String text = "You can't create a piece when you have one already in hand.";
            JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            int x1 = (int) (Math.random() * width/4) + 1, y1 = (int) (Math.random() * height/4) + 1;
            int x2 = (int) (Math.random() * width/4) + 1, y2 = (int) (Math.random() * height/4) + 1;
            String testcolor = ""+new Random().nextInt(256)+" "+new Random().nextInt(256)+" "+new Random().nextInt(256);
            while (!checkColor(testcolor)){
                testcolor = "" + new Random().nextInt(256)+" "+new Random().nextInt(256)+" "+new Random().nextInt(256);
            }
            Piece piece = new Piece(x1, y1, x2, y2, testcolor);
            pieces.add(piece);
            colors.add(testcolor);
        }
    }
    
    /**
     * Functional requirement 3 Take a piece by its color.
     * @param color is witten in RGBa format. color must be unique
     */
    public void takePieceColor(String color){
        int[][] coord, dim;
        boolean flag = true;
        String pat = "((([0-1]?[0-9][0-9]?)|([2](([0-5][0-5])|[0-4][0-9])))[ ]){3}";
        if((color+" ").matches(pat)){
            for(Piece posiblePiece : pieces){
                if (posiblePiece.getColor().equals(color)){
                    posiblePiece.take();
                    coord = posiblePiece.getRectanglesPosition() ;
                    dim = posiblePiece.getRectanglesDimensions();
                    makeASpot(coord[0][0],coord[0][0]+dim[0][0],coord[0][1],coord[0][1]+dim[0][1],true);
                    makeASpot(coord[1][0],coord[1][0]+dim[1][0],coord[1][1],coord[1][1]+dim[1][1],true);               
                    flag = false;
                    break;
                }
            }
            if (flag){
                    Toolkit.getDefaultToolkit().beep(); 
                    String text = "There's no piece on the board with such color";
                    JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
        else{
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "The color is invalid","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Returns the position of the piece (in the ArrayList of pieces) that is in the hand
     * @return an integer indicating the piece in hand's position in the list of pieces
     */
    private int getPieceHand(){
        int position = -1;
        for (int i = 0; i < pieces.size(); i++){
            if (pieces.get(i).getState()){
                position = i;
                break;
            }
        }
        return position;
    }
    
    /**
     * Functional requirement 4 Rotate the piece in hand 90° clockwise
     */
    public void rotatePiece()
    {
        int posPiece = getPieceHand();
        if(posPiece != -1){
            pieces.get(posPiece).rotate();
        }
        else{
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "There is no piece in hand","Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    /**
     * Functional requirement 4 Reflect the piece in hand on an certain side
     * @param d Valid d are 'n', 's', 'w' and 'e'
     */
    public void reflectPiece(char d)
    {
        int posPiece = getPieceHand();
        if (d != 'n' && d != 's' && d != 'w' && d != 'e'){
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "The side is invalid","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(posPiece == -1){
            Toolkit.getDefaultToolkit().beep(); 
            JOptionPane.showMessageDialog(null, "There is no piece in hand","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            pieces.get(posPiece).reflect(d);
        }
    }
    
    /**
     * Functional requirement 4 Translate the piece in hand by certain pixels horizontally and vertical.
     * @param dx Desire x distance in pixels
     *        dy Desire y distance in pixels
     */
    public void translatePiece(int dx, int dy)
    {
        int posPiece = getPieceHand();
        if (posPiece != -1){
            Piece inHandPiece = pieces.get(posPiece);
            if (inHandPiece.getPosition()[0] + dx > width || inHandPiece.getPosition()[1] - dy > height){
                Toolkit.getDefaultToolkit().beep();
                String text = "The displacement exceeds the dimensions of the board";
                JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
            }
            else if (inHandPiece.getPosition()[0] + dx < 0 || inHandPiece.getPosition()[1] - dy < 0){
                Toolkit.getDefaultToolkit().beep();
                String text = "The displacement exceeds the dimensions of the board";
                JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
            }
            else{
                inHandPiece.translate(dx, dy);
            }
        }
        else{
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "There is no piece in hand","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Functional requirement 4 Change the size of the piece in hand by percentage
     * @param percentage
     */
    public void changeSize(int percentage)
    {
        int posPiece = getPieceHand();
        if (posPiece != -1){
            Piece inHandPiece = pieces.get(posPiece);  
            int[][] dimensions = inHandPiece.getRectanglesDimensions();
            double resize = 1 + percentage/100.0;
            String text = "The change in the size exceeds the dimensions of the board";
            if (dimensions[0][0] * resize > width || dimensions[1][0] * resize > width){
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
            }
            else if(dimensions[0][1] * resize > height || dimensions[1][1] * resize > height){
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, text,"Error",JOptionPane.ERROR_MESSAGE);
            }
            else{
                inHandPiece.resize(percentage);
            }
        }
        else{
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "There is no piece in hand","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Functional requirement 5 Put the piece in hand on the board
     */
    public void putPiece(){
        int posPiece = getPieceHand();
        if(posPiece!=-1){
            Piece inHandPiece = pieces.get(posPiece);
            int[][] coord = inHandPiece.getRectanglesPosition(), dim = inHandPiece.getRectanglesDimensions();
            boolean freeSpot = checkAFreeSpot(coord[0][0],coord[0][0]+dim[0][0],coord[0][1],coord[0][1]+dim[0][1]);
            freeSpot = freeSpot && checkAFreeSpot(coord[1][0],coord[1][0]+dim[1][0],coord[1][1],coord[1][1]+dim[1][1]);
            if(freeSpot){
                inHandPiece.put();
                makeASpot(coord[0][0],coord[0][0]+dim[0][0],coord[0][1],coord[0][1]+dim[0][1],false);
                makeASpot(coord[1][0],coord[1][0]+dim[1][0],coord[1][1],coord[1][1]+dim[1][1],false);
                checkState(true);
            }
            else{
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "You can't put the current piece in this place.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "There is no piece in hand","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Checks if the entered position is free
     * @param (xi,yi) initial coordinate.
     * @param (xf,yf) final coordinate.
     */
    private boolean checkAFreeSpot(int xi, int xf, int yi, int yf){
        for(int i=xi; i<xf; i++){
            for(int j=yi; j<yf; j++){
                if(!state[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Occupy or release the entered position
     * @param (xi,yi) initial coordinate.
     * @param (xf,yf) final coordinate.
     * free true for release or false for occupy.
     */
    private void makeASpot(int xi, int xf, int yi, int yf, boolean free){
        for(int i=xi; i<xf; i++){
            for(int j=yi; j<yf; j++){
                state[i][j]=free;
            }
        }
    }
    
    /**  Functional requirement 6 Check the state of the board to see if the player won.
     * @return a message indicating if the player already won or not
     */
    public String checkState(){
        for (boolean[] row : state){
            for (boolean cell : row){
                if (cell){
                    return "The game is not over";
                }
            }
        }
        return "You win";
    
    }
    
    /**
     * Functional requirement 6 Check the state of the board, this time the method putPiece
     * is the only one who uses this
     * @param flag if the flag is true it means that the board is full
     */
    private void checkState(boolean flag){
        for (boolean[] row : state){
            for(boolean cell : row){
                if (cell){
                    flag = false;
                    break;
                }
            }
            if (!flag){break;}
        }
        
        if (flag){
            JOptionPane.showMessageDialog(null, "Winner, winner, chicken dinner!!!");
        }
    }
    
    /**
     * Functional requirement 8 The machine put the piece in the hand on a free spot
     * @return True if the machine put the piece on the board, false otherwise.
     */
    public boolean putPieceAI(){
        int pieceWidth=0,pieceHeight=0;
        int posPiece = getPieceHand();
        if(posPiece!=-1){
            Piece inHandPiece = pieces.get(posPiece);
            pieceWidth=inHandPiece.getWidth();
            pieceHeight=inHandPiece.getHeight();
            boolean freeSpot;
            for(int i=0; i<height-pieceHeight; i++){
                for(int j=0; j<width-pieceWidth; j++){
                    for(int k=0; k<4; k++){
                        int[][] coord = inHandPiece.getRectanglesPosition(), dim = inHandPiece.getRectanglesDimensions();
                        freeSpot = checkAFreeSpot(coord[0][0],coord[0][0]+dim[0][0],coord[0][1],coord[0][1]+dim[0][1]);
                        freeSpot = freeSpot && checkAFreeSpot(coord[1][0],coord[1][0]+dim[1][0],coord[1][1],coord[1][1]+dim[1][1]);
                        if(freeSpot){
                            putPiece();
                            return true;
                        }
                        rotatePiece();
                    }
                    translatePiece(1,0);
                }
                translatePiece(0,1);
            }
        }else{
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "There is no piece in hand","Error",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    /**
     * Functional requirement 7 The machine create and put a new piece with a given area on a free spot
     * @param area Int > 0.
     * @return True if the machine put the piece on the board, false otherwise.
     */
    public boolean generatePieceAI(int area){
        if(getPieceHand()==-1){
            String testcolor = ""+new Random().nextInt(256)+" "+new Random().nextInt(256)+" "+new Random().nextInt(256);
            while (!checkColor(testcolor)) {testcolor = "" + new Random().nextInt(256)+" "+new Random().nextInt(256)+" "+new Random().nextInt(256);}
            for(int i=(int) Math.sqrt(area); i>2;i--){
                int j = area/i;
                if(j*i!=area){
                   for(int n = (int) Math.sqrt(area-i*j); n>2; n--){
                        int m = (area-i*j)/n;
                        if(area==(i*j+n*m)) {
                            addPieceInfo(i, j, i+n, m, testcolor);
                            if(!putPieceAI())pieces.remove(pieces.size()-1); else {return true;}
                        }
                   }
                }else if(area==(i*j)) {
                    addPieceInfo(i, j, i, j, testcolor);
                    if(!putPieceAI())pieces.remove(pieces.size()-1); else {return true;}
                }
            }
        }else{
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "There is a piece in hand already","Error",JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
