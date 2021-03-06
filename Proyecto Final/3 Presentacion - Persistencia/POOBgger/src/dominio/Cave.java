package dominio;

import java.awt.Rectangle;

public class Cave extends Fixed{
	boolean occupied;
	final int points = 50;
	public Cave(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = "Cave";
		occupied = false;
	}
	
	private double collisionPercentage(Element e) {
		Rectangle player = ((Player) e).getBounds(((Player) e).getDimensions());
		Rectangle cave = new Rectangle(x,y,width,height);
		Rectangle intersection = (Rectangle) cave.createIntersection(player);
		double areaCave = cave.getWidth()*cave.getHeight();
		double areaIntersection = intersection.getWidth()*intersection.getHeight();
		System.out.println(areaIntersection/areaCave*100);
		return areaIntersection/areaCave*100;
	}

	private void occupied(Element e) {
		((Playable) e).changeSprite();
		occupied = !(collisionPercentage(e)<20);
		if(occupied) sprite = "CaveOccupied";
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = occupied;
		if(e.isPlayable() && e.isPushable() && !occupied) {
			if(!((Playable) e).isInAir())
			{
				((Player) e).changePoints(points);
				occupied(e);
				isDead = !occupied;
			}
		}
		return isDead;
	}

}
