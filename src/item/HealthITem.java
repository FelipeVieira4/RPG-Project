package item;

import game.GameComponent;
import player.Player;

public class HealthITem extends Item{
	
	private static final int numberFrames=1;
	private static final int sizeWidth=9;
	private static final int sizeHeight=15;
	
	//Carregar o 
	public HealthITem(int x, int y) {
		super(x,y,"rsc/item_health.png",sizeWidth,sizeHeight,numberFrames,sizeWidth,sizeHeight, GameComponent.tileSize/3,GameComponent.tileSize/2);
	}
	
	
	public boolean PlayerUse(Player p){
		if(this.hasCollision(p)) {
			p.addLife();
			return true;
		}
		return false;
	}
}