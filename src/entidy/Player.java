package entidy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import game.ChunkSystem;
import game.GameComponent;
import world.LevelWorld;

public class Player extends KeyAdapter{
	
	private BufferedImage linkSheet;		//Tile of player
	private BufferedImage linkTexture;	//The texture of player that will be drawed
	
	private byte speed=6;		//variable of velocity
	private int x,y;			//Position X and Y of player on screen
	private int xChunk,yChunk;	//Position of player on the chuck
	
	private ChunkSystem chunk = new ChunkSystem();
	
	//movement variables
	private boolean left,right,up,down;
	
	
	public Player(byte x,byte y) {
		
		this.x=x*GameComponent.tileSize;
		this.y=y*GameComponent.tileSize;
		
		try {
			linkSheet=ImageIO.read(new File("rsc/link_sheet.png"));
			linkTexture=linkSheet.getSubimage(1, 1, 15, 16);
		}catch(Exception io) {
			return ;
		}
		
	}
	public void draw(Graphics g) {
		g.drawImage(linkTexture, x, y, x+GameComponent.tileSize, y+GameComponent.tileSize, 0, 0, linkTexture.getWidth(), linkTexture.getHeight(), null);
		
		g.setColor(Color.RED);
		
		g.drawRect(x, y, GameComponent.tileSize, GameComponent.tileSize);
		g.setColor(Color.BLUE);
		chunk.drawRects(g);
	}
	
	public void update(LevelWorld map) {
		this.movement(map);
	}

	
	private void movement(LevelWorld map) {
		
		xChunk=Math.round((x+(16))/GameComponent.tileSize);
		yChunk=Math.round((y+(16))/GameComponent.tileSize);
		
		chunk.getChunk(xChunk, yChunk, map);
		
		if(up && !chunk.hasCollision(x, y-5)) {
			this.y-=speed;
		}
		else if(down && !chunk.hasCollision(x, y+speed))this.y+=speed;
		
		if(left && !chunk.hasCollision(x-3, y))this.x-=4;
		else if(right && !chunk.hasCollision(x+speed, y))this.x+=speed;
	}
	
	
	public void keyPressed(KeyEvent key) {
		switch(key.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				left=true;
				break;
			case KeyEvent.VK_RIGHT:
				right=true;
				break;
			case KeyEvent.VK_UP:
				up=true;
				break;
			case KeyEvent.VK_DOWN:
				down=true;
				break;
		}

	}
	
	public void keyReleased(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left=false;
			break;
		case KeyEvent.VK_RIGHT:
			right=false;
			break;
		case KeyEvent.VK_UP:
			up=false;
			break;
		case KeyEvent.VK_DOWN:
			down=false;
			break;
	}
	}


}
