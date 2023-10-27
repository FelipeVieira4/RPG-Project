package world;

import entidy.Entidy;

public class Tile extends Entidy{

	public int Xid,Yid;
	
	public Tile( int posX, int posY,int xid, int yid) {
		super(posX,posY);
		
		this.Xid = xid;
		this.Yid = yid;
	}
}
