package player;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import animation.Animation;
import animation.AnimationStage;
import entidy.Entidy;
import game.GameComponent;
import world.ChunkCollision;
import world.LevelWorld;
import world.Vector2D;

public class Player extends Entidy implements KeyListener{
	
	private BufferedImage healthIcon;	//I need move it to another file
	private BufferedImage linkTexture;	//The texture of player that will be drawed
	
	private byte speed=4;		//variable of velocity
	private int xChunk,yChunk;	//Position of player on the chunk
	private byte lifes=3;		//health of player
	
	private ChunkCollision collisionChunkMap = new ChunkCollision(14);
		
	//movement variables
	private boolean left,right,up,down;
	
	
	private String dir="UP",oldDir = dir;
	private PlayerState state = PlayerState.walk;
	
	private Animation AnimationPlayer = null; 
	
	public Player(int x,int y) {
		super(x, y);
		
		AnimationPlayer = new Animation("rsc/link_sheet.png",15,16);
		
		AnimationPlayer.addAnimation("WalkDOWN",new AnimationStage(0, 1, 0));
		AnimationPlayer.addAnimation("WalkHorizontal",new AnimationStage(0, 1, 1));
		AnimationPlayer.addAnimation("WalkUP",new AnimationStage(0, 1, 2));
		AnimationPlayer.addAnimation("Attack",new AnimationStage(0, 1, 3));
		
		AnimationPlayer.setAnimation("WalkDOWN");
		
		linkTexture=AnimationPlayer.getImage();
		
		try {
			healthIcon=ImageIO.read(new File("rsc/health_icon.png"));
		}catch(Exception io) {
			JOptionPane.showMessageDialog(null,"ERRO TO LOAD SOME SPRITE FROM PLAYER SYSTEM","ERRO 0x01",JOptionPane.CLOSED_OPTION);
			System.exit(-1);
		}
	}
	public void draw(Graphics g) {
		
		//Invert the image of player if dir is left
		if(dir=="LEFT") {
			g.drawImage(linkTexture, this.getX(), this.getY(),this.getX()+GameComponent.tileSize, this.getY()+GameComponent.tileSize,linkTexture.getWidth(), 0,0, linkTexture.getHeight(), null);
		}else {
			g.drawImage(linkTexture, this.getX(), this.getY(),this.getX()+GameComponent.tileSize, this.getY()+GameComponent.tileSize,0, 0,linkTexture.getWidth(), linkTexture.getHeight(), null);
		}
		
		//Draw the lifes icon on screen
		for(int i=0;i<lifes;i++) {

			g.drawImage(healthIcon,(i*healthIcon.getWidth())+(10*i), 450, ((i*healthIcon.getWidth())+(10*i))+healthIcon.getWidth()*2, 450+healthIcon.getHeight()*2,0, 0, healthIcon.getWidth(), healthIcon.getHeight(), null);
		}
		
	}
	
	public void update(LevelWorld map) {

		
		
		if(movement(map)) {//If player moved, update the ChunkCollision system
			int oldxChunk=xChunk,oldyChunk=yChunk;
			
			xChunk=Math.round((this.getX()+(GameComponent.tileSize/2))/GameComponent.tileSize);
			yChunk=Math.round((this.getY()+(GameComponent.tileSize/2))/GameComponent.tileSize);
			
			//if charged the positions of collisionChunk,update the collisionChunk
			if(xChunk!=oldxChunk || yChunk!=oldyChunk)collisionChunkMap.getChunk(xChunk, yChunk, map);
			
			
			if(oldDir != dir) {
				switch (this.dir) {
					case "UP": 
						AnimationPlayer.setAnimation("WalkUP");
						break;
					case "LEFT": case "RIGHT": 
						AnimationPlayer.setAnimation("WalkHorizontal");
						break;
					case "DOWN": 
						AnimationPlayer.setAnimation("WalkDOWN");
						break;
				}	
				//System.out.println("Animation State:"+AnimationPlayer.getState());
				//System.out.println("Player State:"+state);
			}
			AnimationPlayer.TimerFrame(this.speed);
		}
		else if(state == PlayerState.attack) {
			AnimationPlayer.setAnimation("Attack");
			
			state=PlayerState.walk;
			//System.out.println("Animation State:"+AnimationPlayer.getState());
			//System.out.println("Player State:"+state);
		}
		else {
			return;
		}
		
		
		
		linkTexture=AnimationPlayer.getImage();
		
	}
	
	private boolean movement(LevelWorld map) {
		
		int buffX=this.getX(),buffY=this.getY();
		oldDir = dir;
		
		if(state == PlayerState.walk) {
			if(up && !collisionChunkMap.hasCollision(this.getX(), this.getY()-speed)) {
				this.addForceY(-speed);
				dir="UP";
			}
			if(down && !collisionChunkMap.hasCollision(this.getX(), this.getY()+speed)) {
				this.addForceY(speed);
				dir="DOWN";
			}
			
			
			if(left && !collisionChunkMap.hasCollision(this.getX()-speed,this.getY())) {
				this.addForceX(-speed);
				dir="LEFT";
			}
			if(right && !collisionChunkMap.hasCollision(this.getX()+speed,this.getY())) {
				this.addForceX(speed);
				dir="RIGHT";
			}
			
			if(buffX!=this.getX() || buffY!=this.getY()) {
				return true;
			}
		}
		
		
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
			case KeyEvent.VK_C:
				state=PlayerState.attack;
				break;
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void addLife() {
		if(this.lifes < 6)this.lifes++;
	}
	
	public ChunkCollision getChunk() {
		return this.collisionChunkMap;
	}
	
	public Vector2D getChunkAsPoint() {
		return new Vector2D(xChunk,yChunk);
	}
	
}
