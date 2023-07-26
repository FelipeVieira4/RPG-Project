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
	
	private ArrayList<Vector2D> PointBlocks = new ArrayList<Vector2D>();
	private ArrayList<Item> Items = new ArrayList<Item>();
	
	private BufferedImage tileSheet;
	private BufferedImage levelImage;
	
	public LevelWorld() {
		
		Items.add(new HealthITem(2,2));
		
		try {
			tileSheet = ImageIO.read(new File("rsc/tileSheet.png"));
			levelImage = ImageIO.read(new File("rsc/level1_1.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		//System.out.println(levelImage.getRGB(1, 1));//Print the id of tile free
		levelImage.setRGB(0, 2, -1);
		
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
		
		for(Item item: Items) {
			item.draw(g1);
		}
	}
	
	public void update(Player p) {
		
		for(int i=Items.size()-1 ; i>=0; i--) {
			
			Item item = Items.get(i);
			
			//Search for what kind of object it is
			if(item instanceof HealthITem){
				
				//Update the Object and check if it can be delete
				if(((HealthITem) item).update(p)) {
					Items.remove(i);
				}
				
				
			}
		}
			
	}
	
	public BufferedImage getLevelImage() {
		return this.levelImage;
	}
	
}
