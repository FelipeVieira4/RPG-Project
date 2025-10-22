package world;

import entity.Entity;

public class Tile extends Entity {

	public int Xid,Yid;
	
	public Tile( int posX, int posY,int xid, int yid) {
		super(posX,posY);
		
		this.Xid = xid;
		this.Yid = yid;
	}
}
