package world;

import entidy.Entidy;

public class Tile extends Entidy{

	public int Xid,Yid;
	
	public Tile( int x, int y,int xid, int yid) {
		super(x,y);
		
		this.Xid = xid;
		this.Yid = yid;
	}
}
