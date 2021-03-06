import java.math.*;
/**
 * The class Piece generates figures formed by two rectangles and
 * provide methods for the transformation and analysis of it.
 * 
 * @author Angie Medina - Jose Perez
 * @version 20/08/2020
 */
public class Piece
{
    // instance variables - replace the example below with your own
    private int x1,x2,y1,y2,x,y,rotationState,rotationSquareSide;
    private boolean inHand;
    private Rectangle rectangle1,rectangle2,vertical,horizontal;
    private String color;

    /**
     * Constructor for objects of class Piece. It works with two rectangles
     */
    public Piece(int x1, int y1, int x2, int y2,String color){
        // initialise instance variables
        inHand = true;
        rotationState = 0;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        x = 0;
        y = 0;
        createShape();
        if(y1>y2) {vertical=rectangle1; horizontal=rectangle2;}
        else {horizontal=rectangle1; vertical=rectangle2;}
        if(vertical.getHeight()>=horizontal.getWidth()){
            rotationSquareSide=vertical.getHeight();
        }else rotationSquareSide=horizontal.getWidth();
    }
    
    /**
     * Draws the shape of the current piece.
       */
    public void createShape(){
        rectangle1 = new Rectangle(x1,y1,color);
        rectangle2 = new Rectangle(x2,y2,color);
        rectangle1.makeVisible();
        rectangle2.makeVisible();
    }
    
    /**
     * Changes the inHand signal to True.
     */
    public void take(){
       String[] pieceColor = this.getColor().split(" ");
       this.changeColor(pieceColor[0] + " " + pieceColor[1] + " " + pieceColor[2] + " 50");
       inHand = true;
        
    }
    
    /**
     * Changes the inHand signal to False.
     */
    public void put(){
        String[] pieceColor = this.getColor().split(" ");
        this.changeColor(pieceColor[0] + " " + pieceColor[1] + " " + pieceColor[2] + " 255");
        inHand = false;
    }
    
    /**
     * Move the piece horizontally and vertically.
     * @param dx the desired x distance in pixels.
     * @param dy the desired y distance in pixels.
     */
    public void translate(int dx, int dy){
        x += dx;
        y -= dy;
        rectangle1.moveHorizontal(dx);
        rectangle2.moveHorizontal(dx);
        rectangle1.moveVertical(-1*dy);
        rectangle2.moveVertical(-1*dy);
    }
    
    /**
     * Increase or decrease the area of the rectangles in the current piece.
     * @param p percentage to increase or decrease the area in the current piece.
       */
    public void resize(int p){
        double newRectangle1Height = y1*((100+p)/100.0),newRectangle1Width = x1*((100+p)/100.0);
        double newRectangle2Height = y2*((100+p)/100.0),newRectangle2Width = x2*((100+p)/100.0);
        rectangle1.changeSize((int) newRectangle1Height, (int) newRectangle1Width);
        rectangle2.changeSize((int) newRectangle2Height, (int) newRectangle2Width);
        y1=(int) newRectangle1Height;
        x1=(int) newRectangle1Width;
        y2=(int) newRectangle2Height;
        x2=(int) newRectangle2Width;       
    }
    
    /**
     * Changes the width and height values of the rectangles in the current piece. 
     */
    public void swap(){
        if(rotationState%2==0){
            int tempx=vertical.getWidth(),tempy=vertical.getHeight();
            vertical.changeSize(horizontal.getWidth(),horizontal.getHeight());
            horizontal.changeSize(tempx,tempy);
        }else{
            int tempx=horizontal.getWidth(),tempy=horizontal.getHeight();
            horizontal.changeSize(vertical.getWidth(),vertical.getHeight());
            vertical.changeSize(tempx,tempy);
        }
    }
    
    /**
      * Turn left the current piece 90°.
      */
    public void rotate(){
        if(rotationState==0){
            swap();
            x += rotationSquareSide;
            vertical.goTo(x-vertical.getWidth(),y);
            horizontal.goTo(x-horizontal.getWidth(),y);
            rotationState++;
        }
        else if(rotationState==1){
            swap();
            y += rotationSquareSide;
            vertical.goTo(x-vertical.getWidth(),y-vertical.getHeight());
            horizontal.goTo(x-horizontal.getWidth(),y-horizontal.getHeight());
            rotationState++;
        }
        else if(rotationState==2){
            swap();
            x -= rotationSquareSide;
            vertical.goTo(x,y-vertical.getHeight());
            horizontal.goTo(x,y-horizontal.getHeight());
            rotationState++;
        }
        else{
            swap();
            y -= rotationSquareSide;
            vertical.goTo(x,y);
            horizontal.goTo(x,y);
            rotationState=0;
        }
    }
    
    /**
     * Rotates 16 times the current piece.
     */
    public void spin(){
        for(int i=0; i<64; i++){
            rotate();
        }
    }
    
    /**
      * Moves the piece to given coordinates.
      *  @param x new x position.
      *  @param y new y position.
      */
    public void goTo(int x, int y){
        this.x = x;
        this.y = y;
        vertical.goTo(x,y);
        horizontal.goTo(x,y);
    }
    
    /**
      * Flips the piece vertically or horizontally.
      * @param d Choose the way that piece will be flipped. 'v' to flip vertically or 'h' to flip horizontally.
      */
    public void flip(char d){
        if(d=='v'){
            if(rotationState==0||rotationState==1){
                y += (vertical.getHeight()-horizontal.getHeight());
                horizontal.moveVertical(vertical.getHeight()-horizontal.getHeight());
                rotationState=rotationState==0?3:2;
                y += horizontal.getHeight();
            }
            else{
                y -= (vertical.getHeight()-horizontal.getHeight());
                horizontal.moveVertical(horizontal.getHeight()-vertical.getHeight());
                rotationState=rotationState==3?0:1;
                y -= horizontal.getHeight();
            }
        }
        if(d=='h'){
            if(rotationState==0||rotationState==3){
                x += (horizontal.getWidth()-vertical.getWidth());
                vertical.moveHorizontal(horizontal.getWidth()-vertical.getWidth());
                rotationState=rotationState==0?1:2;
                x += vertical.getWidth();
            }
            else{
                x -= (vertical.getHeight()-horizontal.getHeight());
                vertical.moveHorizontal(vertical.getWidth()-horizontal.getWidth());
                rotationState=rotationState==1?0:3;
                x -= vertical.getWidth();
            }
        }
    }
    
    /**
      * Reflect the piece respect to the choosen side
      * @param d The side with which the piece'll be reflected.
      *     n: top side,
      *     s: low side,
      *     e: rigth side,
      *     w: left side
      */
    public void reflect(char d){
        if(d=='n'||d=='s'){
            translate(0,(d=='n'?1:-1)*vertical.getHeight());
            flip('v');
        }
        else if(d=='w'||d=='e'){
            translate((d=='e'?1:-1)*horizontal.getWidth(),0);
            flip('h');
        
        }
    }
    
    /**
     * Returns the color of the piece
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Returns the state of the piece. If its in hand or not
     */
    public boolean getState(){
        return inHand;
    }
    
    /**
     * Returns the position of the piece
     */
    public int[] getPosition(){
        int[] position = {x, y};
        return position;
    }
    
    /**
     * Returns a tuple of tuples with the rectangles's dimension(x,y)  
     */
    public int[][] getRectanglesDimensions(){
        int[][] dimensions = {{x1, y1}, {x2, y2}};
        return dimensions;
    }
    
    /**
     * Returns a tuple of tuples with the rectangles's cartesian position(x,y)  
     */
    public int[][] getRectanglesPosition(){
        int[][] coord = {vertical.getCoord(),horizontal.getCoord()};
        return coord;
    }
    
    /**
     * Returns a tuple of tuples with the rectangles's cartesian position(x,y)  
     */
    public void changeColor(String color){
        vertical.changeColor(color);
        horizontal.changeColor(color);
    }
    
    /**
     * Returns the longest horizontal side of the shape. 
     */
    public int getWidth(){
        return horizontal.getWidth();
    }
    
    /**
     * Returns the longest vertical side of the shape. 
     */
    public int getHeight(){
        return vertical.getHeight();
    }
    
}


