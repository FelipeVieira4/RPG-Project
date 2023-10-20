package entidy;

import java.awt.Graphics;

public abstract class GameObject extends Entidy{
  
  public GameObject( int posx, int posy) {
		super(posx, posy);
	}

  public void update(){return;}
  public void draw(Graphics g){return;}
  public boolean remove(){return false;}
}