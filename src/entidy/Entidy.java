package entidy;

import java.awt.Rectangle;

import game.GameComponent;

public class Entidy {
	private int x,y;
	
	public Entidy(byte posx,byte posy) {
		this.x=posx*GameComponent.tileSize;
		this.y=posy*GameComponent.tileSize;
	}
	
	public boolean hasCollision(Entidy e2) {
		if(new Rectangle(this.x,this.y,GameComponent.tileSize,GameComponent.tileSize).intersects
				(new Rectangle(e2.getX(),e2.getY(),GameComponent.tileSize,GameComponent.tileSize)))return true;
		return false;
	}
	
	//Set position of Entidy on posX
	public void setX(int posx) {
		this.x=posx;
	}
	
	//Set position of Entidy on posY
	public void setY(int posy) {
		this.y=posy;
	}
	
	//Add force on entidy on X axis
	public void addForceX(int force) {
		this.x+=force;
	}
	
	//Add force on entidy on Y axis
	public void addForceY(int force) {
		this.y+=force;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}
