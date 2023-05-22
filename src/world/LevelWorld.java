package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.GameComponent;

public class LevelWorld {
	
	public static final int tileFree=-16777216;//Color id for tile id
	
	private ArrayList<Vector2D> PointBlocks = new ArrayList<Vector2D>();
	private ArrayList<Object> Itens = new ArrayList<Object>();
	
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
		
		//System.out.println(levelImage.getRGB(1, 1));//Print the id of tile free
		
		for(byte x=0;x<levelImage.getWidth();x++){
			for(byte y=0;y<levelImage.getHeight();y++){
				
				if(levelImage.getRGB(x, y)!=tileFree) {
					PointBlocks.add(new Vector2D(x*GameComponent.tileSize,y*GameComponent.tileSize));
				}
			}
		}
		
	}
	
	public void draw(Graphics g1) {
		for(Vector2D blockTile:PointBlocks) {
			g1.drawImage(tileSheet, blockTile.getX(), blockTile.getY(),
					blockTile.getX()+GameComponent.tileSize, blockTile.getY()+GameComponent.tileSize,
					0, 0, 15,15, null);
		}
	}
	
	public BufferedImage getLevelImage() {
		return this.levelImage;
	}
	
}
