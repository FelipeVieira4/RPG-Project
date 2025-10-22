package entity;

import java.awt.Rectangle;

import game.GameComponent;

public class Entity {
	private int x,y;
    private int width=GameComponent.tileSize;
    private int height=GameComponent.tileSize;
	
	public Entity(int posx, int posy) {
		this.x=posx*GameComponent.tileSize;
		this.y=posy*GameComponent.tileSize;

	}

    public Entity(int posx, int posy, int width, int height) {
        this.x=posx*GameComponent.tileSize;
        this.y=posy*GameComponent.tileSize;

        this.width=width;
        this.height=height;
    }
	
	public boolean hasCollision(Entity e2) {
		if (new Rectangle(this.x,this.y,this.width,this.height).intersects
				(new Rectangle(e2.getX(),e2.getY(),e2.getWidth(),e2.getHeight())))return true;
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

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }
}
