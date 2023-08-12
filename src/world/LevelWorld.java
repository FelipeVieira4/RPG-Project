package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.GameComponent;
import item.HealthITem;
import item.Item;
import player.Player;

public class LevelWorld {
	
	public static final int tileFree=-16777216;//Color ID for tile free
	public static final int ColorFree=0;//The RED color of pixel
	
	private ArrayList<Item> Items = new ArrayList<Item>();
	private ArrayList<Tile> tileArray = new ArrayList<Tile>();
	
	private BufferedImage tileSheet;
	private BufferedImage levelImage;

	public LevelWorld() {
		
		Items.add(new HealthITem(2,2));
		
		try {
			tileSheet = ImageIO.read(new File("rsc/tileSheet.png"));
			levelImage = ImageIO.read(new File("rsc/level1_1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		
		for(byte x=0;x<levelImage.getWidth();x++){
			for(byte y=0;y<levelImage.getHeight();y++){
				
				if(levelImage.getRGB(x, y)!=tileFree) {
					
					tileArray.add(new Tile(x,y,((levelImage.getRGB(x, y) & 0xff00) >> 8),0));
				}
								
			}
		}
		
		
	}
	
	public void draw(Graphics g1) {
		
		for(Tile blockTile:tileArray) {
			g1.drawImage(tileSheet, blockTile.getX(), blockTile.getY(),
					blockTile.getX()+GameComponent.tileSize, blockTile.getY()+GameComponent.tileSize,
					blockTile.Xid*16, 0,(blockTile.Xid*16)+16,16, null);
		}
		
		for(Item item: Items) {
			item.draw(g1);
		}
		
	}
	
	public void update(Player p) {
		
		for(int i=Items.size()-1 ; i>=0; i--) {
			
			Item item = Items.get(i);
			
			if(item.update(p)) {
				Items.remove(i);
			}
		}
			
	}
	
	public BufferedImage getLevelImage() {
		return this.levelImage;
	}
	
}