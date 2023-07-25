package item;

import java.awt.Graphics;

import animation.Animation;
import entidy.Entidy;
import player.Player;

public class Item extends Entidy{
	
	private Animation itenAnim=null;
	
	public Item(int x,int y,String patchSheet,Integer animX,Integer animY,int lastFrame) {
		super(x,y);

		itenAnim = new Animation(patchSheet,animX,animY);
		itenAnim.addAnimation("anim", 0, 1, 0);
		itenAnim.setAnimation("anim");
		
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(itenAnim.getImage(),this.getX(),this.getY(), this.getX()+itenAnim.getImage().getWidth(), this.getY()+itenAnim.getImage().getHeight(),0,0, itenAnim.getImage().getWidth(), itenAnim.getImage().getHeight(), null);
	}
	
	public void updateFrame(int time) {
		itenAnim.TimerFrame(time);
	}
	
	public boolean update(Player p) {return true;}
}
