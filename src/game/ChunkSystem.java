package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import world.LevelWorld;

public class ChunkSystem {
	ArrayList<Point> rectsPosition = new ArrayList<Point>();
	
	
	public void getChunk(int posX,int posY,LevelWorld level) {
		rectsPosition.clear();
		
		for(int xChunck = posX-1;xChunck<=posX+1;xChunck++) {
			for(int yChunck = posY-1;yChunck <=posY+1;yChunck++) {
				if(level.getLevelImage().getRGB(xChunck, yChunck)==-1) {
					rectsPosition.add(new Point(xChunck,yChunck));
				}
			}
		}
	}
	
	public void print() {
		for (Point i : rectsPosition) {
			System.out.println("X:"+i.getX()+" Y:"+i.getY());
		}
	}
	
	public boolean hasCollision(int posx,int posy) {
		
		for(Point i : rectsPosition) {
			if(new Rectangle(i.getX()*GameComponent.tileSize,i.getY()*GameComponent.tileSize,GameComponent.tileSize,GameComponent.tileSize).intersects
					(new Rectangle(posx,posy,GameComponent.tileSize,GameComponent.tileSize))) {
				return true;
			}
		}
		
		return false;
	}
	
	public void drawRects(Graphics g1) {
		for(Point x : rectsPosition) {
			g1.drawRect(x.getX()*GameComponent.tileSize, x.getY()*GameComponent.tileSize,GameComponent.tileSize,GameComponent.tileSize);
		}
		
	}
	
	private class Point{
		int x,y;
		
		public Point(int posx,int posy) {
			this.x=posx;
			this.y=posy;
		}
		
		public int getX() {
			return this.x;
		}
		public int getY() {
			return this.y;
		}
	}
}
