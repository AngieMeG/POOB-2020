package dominio;

/**
 * Pogger's snake implementation
 * @version 3.0
 * @author Angie Medina - Jose Perez
 * */
public class Snake extends Mobile implements Pushable{
	
	private int speed;
	private int state;
	private String orientation;
	private Animator animator;
	private boolean beingCarried;
	private Carrier carrier;	

	/**
	 * Snake class constructor
	 * @param x Snake's x position
	 * @param y Snake's y position
	 * @param speed Snake's speed
	 * @param size Snake's dimensions
	 * @param sprite Snake's sprite name
	 * @param flipped if the Snake can submerge
	 * */
	public Snake(int xPos, int yPos, int speed,int[] size, String sprite, boolean flipped){
		sprite = "Snake1";
		this.x = xPos;
		this.y = yPos;
		this.width = size[0];
		this.height = size[1];
		this.speed = speed;
		this.isVisible = true;
		this.sprite = sprite;
		orientation = "F";
		state = 0;
		animator = new Animator();
		if(flipped) flip();
		animator.animate(100,4,new Runnable() {public void run() {updateSprite();}},false);
	}
	
	/**
	 * Plays snake's move animation;
	 * */
	private void updateSprite(){
		state = (state+1)%4;
		sprite = "Snake"+(state+1)+orientation; 
	}
	
	@Override
	public void move() {
		x += speed;
		if (carrier!=null && (x+width>carrier.getX()+carrier.getWidth() || x<=carrier.getX())) {
			flip();
			if(speed<0) x+=speed;
		}
	}
	
	/**
	 * Flip horizontally the snake's sprite
	 * */
	public void flip() {
		speed *= -1;
		orientation = orientation.equals("F")?"":"F";
		sprite = "Snake"+(state+1)+orientation;
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = true;
		if(e.isPlayable()) {
			isDead = !((Playable) e).isToxic();
			if(!isDead) isVisible = false;
		}
		return isDead;
	}

	@Override
	public void beingCarried(Carrier c) {
		beingCarried = true;
		carrier = c;
	}

	@Override
	public boolean setPosition(int x, int y) {
		return false;
	}

	@Override
	public void addPush(int push, String dir) {
	}

	@Override
	public void setVisible(boolean visible) {
	}

	@Override
	public char getDir() {
		return orientation.charAt(0);
	}

	@Override
	public boolean isBeingCarried() {
		return beingCarried;
	}
	
	@Override
	public boolean isCarriable() {
		return true;
	}
	
	@Override
	protected void stopAnimator() {
		animator.stop();
	}
	
	@Override
	protected void resumeAnimator() {
		animator.resume();
	}
}
