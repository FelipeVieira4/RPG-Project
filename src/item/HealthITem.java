package item;

import player.Player;

public class HealthITem extends Item{
	
	private static final int numberFrames=1;
	private static final int sizeWidht=9;
	private static final int sizeHeight=15;
	
	public HealthITem(int x, int y) {
		super(x,y,"rsc/item_health.png",sizeWidht,sizeHeight,numberFrames);
	}
	
	
	public boolean CanPlayerUse(Player p){
		if(this.hasCollision(p)) {
			p.addLife();
			return true;
		}
		return false;
	}
}