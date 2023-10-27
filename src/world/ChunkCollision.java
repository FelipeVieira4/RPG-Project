package world;

import java.awt.Rectangle;
import java.util.ArrayList;

import entidy.Entidy;
import game.GameComponent;

public class ChunkCollision {
	
	private ArrayList<Entidy> collisionPositions = new ArrayList<Entidy>();
	private int depth=0;
	
	public ChunkCollision(int depth) {
		this.depth=depth;
	}
	
	public ChunkCollision() {}
	
	public void getChunk(int posX,int posY,LevelWorld level) {
		
		collisionPositions.clear();//Clear the stack
		
		for(int xChunck = posX-1;xChunck<=posX+1;xChunck++) {
			for(int yChunck = posY-1;yChunck <=posY+1;yChunck++) {
				
				if((level.getLevelImage().getRGB(xChunck, yChunck) & 0xff) != LevelWorld.colorTileFree) {
					collisionPositions.add(new Entidy(xChunck,yChunck));
				}
				
			}
		}
	}
	
	//Check if the rectangle on position(posx,posy) has collision with some rectangle of "rectsPosition"
	public boolean hasCollision(int posx,int posy) {
		
		Rectangle entidyRec=new Rectangle(posx,posy+depth,GameComponent.tileSize,GameComponent.tileSize-depth);//Create a rectangle on posx,posy
		
		
		Rectangle recPos;
		for(Entidy vector : collisionPositions) {
			recPos=new Rectangle(vector.getX(),vector.getY(),GameComponent.tileSize,GameComponent.tileSize);
			
			if(recPos.intersects(entidyRec))return true;//Check if have collision between recs
		}
		
		return false;
	}
	
}
