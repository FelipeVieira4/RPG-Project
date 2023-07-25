package item;

import player.Player;

public class HealthITem extends Item{
	
	public HealthITem(int x, int y) {
		super(x,y,"rsc/item_health.png",9,15,1);
	}
	
	//This method is type boolean because if it returns true, it will be deleted
	public boolean update(Player p) {
		super.updateFrame(10);
		
		if(this.hasCollision(p)) {
			p.addLife();
			return true;
		}
		
		return false;
	}
	
}