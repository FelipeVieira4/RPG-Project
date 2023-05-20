package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import game.GameComponent;

public class LevelWorld {
	private ArrayList<Rectangle> Blocks = new ArrayList<Rectangle>();
	private BufferedImage tileSheet;
	private BufferedImage levelImage;
	
	
	public LevelWorld() {
		try {
			tileSheet = ImageIO.read(new File("rsc/tileSheet.png"));
			levelImage = ImageIO.read(new File("rsc/level1_1.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		for(byte x=0;x<levelImage.getWidth();x++){
			for(byte y=0;y<levelImage.getHeight();y++){
				
				if(levelImage.getRGB(x, y)==-1) {
					Blocks.add(new Rectangle(x*GameComponent.tileSize,y*GameComponent.tileSize,GameComponent.tileSize,GameComponent.tileSize));
				}
			}
		}
		
	}
	
	public void draw(Graphics g1) {
		for(Rectangle blockTile:Blocks) {
			g1.drawImage(tileSheet, blockTile.x, blockTile.y,
					blockTile.x+GameComponent.tileSize, blockTile.y+GameComponent.tileSize,
					0, 0, 15,15, null);
		}
	}
	
	public boolean checkBlockCollision(int player_x,int player_y,int xChunk,int yChunk) {
		
		return true;
	}
	
	public BufferedImage getLevelImage() {
		return this.levelImage;
	}
	
}
