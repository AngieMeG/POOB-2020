package dominio;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * POOgger basic element behavior
 * @version 2.0
 * @author Angie Medina - Jose Perez
 */
public abstract class Element implements Serializable{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected String sprite;
	protected boolean isVisible;
	
	
	/**
	 * Returns a string with the Element's sprite name
	 * @return Element's sprite name
	 */
	public String getSprite() {
		return sprite;
	}
	
	/**
	 * Returns Element's x position
	 * @return Element's x position
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns Element's y position
	 * @return Element's y position
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns if the given element can be destroyed upon collision
	 * @param e desired element to check
	 * @Returns true if the elements are colliding false otherwise
	 */
	abstract public boolean inCollision(Element e);
	
	/**
	 * Returns the collision box for the element
	 * @param size Element's size
	 * @return A Rectangle for the element's collision box
	 */
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
	
	/**
	 * Returns if the element is pushable
	 * @Returns True if the element is pushable false otherwise
	 */
	public boolean isPushable() {
		return false;
	}
	
	/**
	 * Returns if the element is carriable
	 * @Returns True if the element is carriable false otherwise
	 */
	public boolean isCarriable() {
		return false;
	}
	
	/**
	 * Returns if the element is playable
	 * @Returns True if the element is playable false otherwise
	 */
	public boolean isPlayable() {
		return false;
	}
	
	/**
	 * Returns if the element is visible
	 * @return True if the element is visible false otherwise
	 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/**
	 * Returns the element's width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the element's width
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Stops the element's animator
	 */
	protected void stopAnimator() {	
	}
	
	/**
	 * Resume the element's animator
	 */
	protected void resumeAnimator() {
	}
	
	/**
	 * Returns if the element gives bonus to the player when they collide
	 */
	protected boolean givesBonus() {
		return false;
	}
	
	/**
	 * Returns the amount of bonus points the element gives to the player when they collide
	 */
	protected int getPoints() {
		return 0;
	}
	
	/**
	 * Returns if the element is destructible
	 */
	public boolean isDestructible() {
		return true;
	}
	
	/**
	 * Destroy (Make visible) the element and returns the element's power
	 * @return the element's power
	 */
	public Power destroy() {
		isVisible = false;
		return null;
	}
}
