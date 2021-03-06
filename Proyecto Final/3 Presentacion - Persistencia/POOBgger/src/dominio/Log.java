package dominio;

import java.util.ArrayList;

/**
 * Pretends be a Frogger's log
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public class Log extends Carrier {
	/**
	 * Log class constructor
	 * @param x Log's x position
	 * @param y Log's y position
	 * @param speed Log's speed
	 * @param sprite Log's sprite name
	 * */
	public Log(int x, int y, int speed, String sprite) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.carried = new ArrayList<Pushable>();
		this.maxCarryNumber = Integer.MAX_VALUE;
	}
	
	public void move() {
		super.move();
	}
	
	@Override
	public boolean inCollision(Element e) {
		return super.inCollision(e);
	}
	
}
