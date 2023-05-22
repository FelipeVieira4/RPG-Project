package world;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import game.GameComponent;

public class ChunkCollision {
	
	private ArrayList<Vector2D> collisionPositions = new ArrayList<Vector2D>();
	
	public void getChunk(int posX,int posY,LevelWorld level) {
		
			collisionPositions.clear();//Clear the stack
			
			for(int xChunck = posX-1;xChunck<=posX+1;xChunck++) {
				for(int yChunck = posY-1;yChunck <=posY+1;yChunck++) {
					
					if(level.getLevelImage().getRGB(xChunck, yChunck)!=LevelWorld.tileFree) {
						collisionPositions.add(new Vector2D(xChunck,yChunck));
					}
					
				}
		}
	}
	
	//Check if the rectangle on position(posx,posy) has collision with some rectangle of "rectsPosition"
	public boolean hasCollision(int posx,int posy) {
		
		Rectangle entidyRec=new Rectangle(posx,posy,GameComponent.tileSize,GameComponent.tileSize);//Create a rectangle on posx,posy
		
		
		Rectangle recPos;
		for(Vector2D vector : collisionPositions) {
			
			recPos=new Rectangle(vector.getX()*GameComponent.tileSize,vector.getY()*GameComponent.tileSize,
								GameComponent.tileSize,GameComponent.tileSize);
			
			if(recPos.intersects(entidyRec))return true;//Check if have collision between recs
		}
		
		return false;
	}
	
	//Method for debug mode
	public void drawRects(Graphics g1) {
		for(Vector2D x : collisionPositions) {
			g1.drawRect(x.getX()*GameComponent.tileSize, x.getY()*GameComponent.tileSize,GameComponent.tileSize,GameComponent.tileSize);
		}
		
	}
}
