package entidy;

import java.awt.Graphics;

public abstract class GameObject extends Entidy{
  
  public GameObject(int posx, int posy) {
		super(posx, posy);
  }

  public GameObject(int posx, int posy, int widht, int height) {
      super(posx, posy, widht, height);
  }

  public void update(){return;}
  public abstract void draw(Graphics graphic);
}