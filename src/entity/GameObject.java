package entity;

import java.awt.Graphics;

public abstract class GameObject extends Entity {
  
  public GameObject(int posx, int posy) {
		super(posx, posy);
  }

  public GameObject(int posx, int posy, int width, int height) {
      super(posx, posy, width, height);
  }

  public void update(){return;}
  public abstract void draw(Graphics graphic);
}