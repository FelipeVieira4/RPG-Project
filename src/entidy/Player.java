package entidy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import game.GameComponent;
import world.ChunkCollision;
import world.LevelWorld;
import world.Vector2D;

public class Player extends Entidy implements KeyListener{
	
	private BufferedImage linkSheet;		//Tile of player
	private BufferedImage healthIcon;	//I need move it to another file
	private BufferedImage linkTexture;	//The texture of player that will be drawed
	
	private byte speed=4;		//variable of velocity
	private int xChunk,yChunk;	//Position of player on the chuck
	private byte lifes=5;		//health of player
	
	private ChunkCollision collisionChunk = new ChunkCollision();
	
	//movement variables
	private boolean left,right,up,down;
	
	
	public Player(byte x,byte y) {
		
		super(x, y);
		
		try {
			linkSheet=ImageIO.read(new File("rsc/link_sheet.png"));
			linkTexture=linkSheet.getSubimage(1, 1, 15, 16);
			healthIcon=ImageIO.read(new File("rsc/health_icon.png"));
		}catch(Exception io) {
			JOptionPane.showMessageDialog(null,"ERRO TO LOAD SOME SPRITE FROM PLAYER SYSTEM","ERRO 0x01",JOptionPane.CLOSED_OPTION);
			System.exit(-1);
		}
	}
	public void draw(Graphics g) {
		g.drawImage(linkTexture, this.getX(), this.getY(),this.getX()+GameComponent.tileSize, this.getY()+GameComponent.tileSize, 0, 0, linkTexture.getWidth(), linkTexture.getHeight(), null);
		
		//Draw the lifes icon on screen
		for(int i=0;i<lifes;i++) {

			g.drawImage(healthIcon,(i*healthIcon.getWidth())+(10*i), 10, ((i*healthIcon.getWidth())+(10*i))+healthIcon.getWidth()*2, 10+healthIcon.getHeight()*2,
									0, 0, healthIcon.getWidth(), healthIcon.getHeight(), null);
		}
		
		
		g.setColor(Color.RED);
		g.drawRect(this.getX(), this.getY(), GameComponent.tileSize, GameComponent.tileSize);
	}
	
	public void update(LevelWorld map) {

		
		
		if(movement(map)) {//If player moved, update the ChunkCollision system
			int oldxChunk=xChunk,oldyChunk=yChunk;
			
			xChunk=Math.round((this.getX()+(GameComponent.tileSize/2))/GameComponent.tileSize);
			yChunk=Math.round((this.getY()+(GameComponent.tileSize/2))/GameComponent.tileSize);
			
			//if charged the positions of collisionChunk,update the collisionChunk
			if(xChunk!=oldxChunk || yChunk!=oldyChunk)collisionChunk.getChunk(xChunk, yChunk, map);
		}
		
		
	}

	
	private boolean movement(LevelWorld map) {
				
		int buffX=this.getX(),buffY=this.getY();
		
		if(up && !collisionChunk.hasCollision(this.getX(), this.getY()-speed)) {
			this.addForceY(-speed);
		}
		else if(down && !collisionChunk.hasCollision(this.getX(), this.getY()+speed)) {
			this.addForceY(speed);
		}
		
		if(left && !collisionChunk.hasCollision(this.getX()-5,this.getY())) {
			this.addForceX(-speed);
		}
		else if(right && !collisionChunk.hasCollision(this.getX()+speed,this.getY())) {
			this.addForceX(speed);
		}
		
		if(buffX!=this.getX() || buffY!=this.getY())return true;
		
		return false;
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

	public ChunkCollision getChunk() {
		return this.collisionChunk;
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Vector2D getChunkAsPoint() {
		return new Vector2D(xChunk,yChunk);
	}
	
}
