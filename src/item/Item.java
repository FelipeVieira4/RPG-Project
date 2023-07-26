package item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animation.Animation;
import entidy.Entidy;
import player.Player;

public class Item extends Entidy{
	
	private Animation itemAnim=null;
	
	public Item(int x,int y,String patchSheet,Integer animX,Integer animY,int lastFrame) {
		super(x,y);

		itemAnim = new Animation(patchSheet,animX,animY);
		itemAnim.addAnimation("anim", 0, 1, 0);
		itemAnim.setAnimation("anim");		
	}
	
	public void draw(Graphics g) {
		
		BufferedImage itemImage = itemAnim.getImage();
		g.drawImage(itemImage,this.getX(),this.getY(), this.getX()+itemImage.getWidth(), this.getY()+itemImage.getHeight(),0,0, itemImage.getWidth(), itemImage.getHeight(), null);
	}
	
	public void updateFrame(int time) {
		itemAnim.TimerFrame(time);
	}
	
	public boolean update(Player p) {return true;}
}
