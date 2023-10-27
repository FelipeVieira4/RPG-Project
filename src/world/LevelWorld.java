package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.GameComponent;

public class LevelWorld {
	
	public static final int colorNonTile=-16777216;//Color ID for tile free
	public static final int colorTileFree=0;//The RED color of pixel
	
	private ArrayList<Tile> tileArray = new ArrayList<Tile>();
	
	private BufferedImage tileSheet;	//The texture of tiles
	private BufferedImage levelImage;	//The base of map

	public LevelWorld() {
		try {
			tileSheet = ImageIO.read(new File("rsc/tileSheet.png"));
			levelImage = ImageIO.read(new File("rsc/level1_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
		for(byte x=0;x<levelImage.getWidth();x++){
			for(byte y=0;y<levelImage.getHeight();y++){
				
				if(levelImage.getRGB(x, y)!=colorNonTile) {	
					tileArray.add(new Tile(x,y,((levelImage.getRGB(x, y) & 0xff00) >> 8),0));
				}
								
			}
		}
		
		
	}
	
	public void draw(Graphics g1) {

		//Draw the tiles of map on screen
		for(Tile blockTile:tileArray) {
			g1.drawImage(tileSheet, blockTile.getX(), blockTile.getY(),
				blockTile.getX()+GameComponent.tileSize, blockTile.getY()+GameComponent.tileSize,
				blockTile.Xid*16, 0,(blockTile.Xid*16)+16,16, null);
			}
		}
	
	public BufferedImage getLevelImage() {
		return this.levelImage;
	}
	
}