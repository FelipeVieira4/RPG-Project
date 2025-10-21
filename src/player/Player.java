package player;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import animation.Animation;
import animation.AnimationStage;
import entidy.Entidy;
import game.GameComponent;
import world.ChunkCollision;
import world.LevelWorld;


public class Player extends Entidy implements KeyListener{
	
	private enum Direction {UP,DOWN,LEFT,RIGHT}
	private enum PlayerState {idle,walk,attack,deadAnim,dead;}

    private int tux_collected=0;

	private BufferedImage healthIcon;	//I need move it to another file

	private byte speed=GameComponent.tileSize/8;	//variable of velocity
	private int xChunk,yChunk;	//Position of player on the chunk
	private byte lifes=3;		//health of player
	
	private Direction dir=Direction.DOWN,	oldDir = dir;
	private PlayerState state = PlayerState.walk;
	
	private ChunkCollision collisionChunkMap;
	private Animation AnimationPlayer;	
	

	private Set<Integer> keyPressed = new HashSet<>();	
	
	public Player(int x,int y) {
		super(x, y);
		
		collisionChunkMap = new ChunkCollision(GameComponent.tileSize/2);//The depper of player sprite is the half of an tile
		
		
		AnimationPlayer = new Animation("rsc/link_sheet.png");
		
		//Get the animations on link_sheet.png
		AnimationPlayer.addAnimation("DOWN",new AnimationStage(0, 1, 0));
		AnimationPlayer.addAnimation("LEFT",new AnimationStage(0, 1, 1));
		AnimationPlayer.addAnimation("RIGHT",new AnimationStage(0, 1, 1));
		AnimationPlayer.addAnimation("UP",new AnimationStage(0, 1, 2));
		AnimationPlayer.addAnimation("ATTACK",new AnimationStage(0, 1, 3));
		
		AnimationPlayer.setAnimation(dir.toString());
		
		try {
			healthIcon=ImageIO.read(new File("rsc/health_icon.png"));
		}catch(Exception io) {
			JOptionPane.showMessageDialog(null,"ERRO TO LOAD SPRITE FROM PLAYER SYSTEM","ERRO 0x01",JOptionPane.CLOSED_OPTION);
			System.exit(-1);
		}
	}
	public void draw(Graphics g) {
		BufferedImage linkTexture = AnimationPlayer.getImage(15,16);//Get image from animation system
		
		//Invert the image of player if it is left
		if(dir.equals(Direction.LEFT)) {
			g.drawImage(linkTexture, this.getX(), this.getY(),this.getX()+GameComponent.tileSize, this.getY()+GameComponent.tileSize,linkTexture.getWidth(), 0,0, linkTexture.getHeight(), null);
		}else {
			g.drawImage(linkTexture, this.getX(), this.getY(),this.getX()+GameComponent.tileSize, this.getY()+GameComponent.tileSize,0, 0,linkTexture.getWidth(), linkTexture.getHeight(), null);
		}
		
		//Draw the lifes icon on screen
		for(int i=0;i<lifes;i++) {
			int yPos=GameComponent.tileSize*(GameComponent.screenCOL-2);
			int xPos=(i*GameComponent.tileSize)+(GameComponent.tileSize/3);
			
			g.drawImage(healthIcon,xPos,yPos, xPos+GameComponent.tileSize/2, yPos+GameComponent.tileSize/2,0, 0, healthIcon.getWidth(), healthIcon.getHeight(), null);
		}
		
	}
	
	public void update(LevelWorld map) {

		
		
		if(movement(map)) {//If player moved, update the ChunkCollision system
			int oldxChunk=xChunk,oldyChunk=yChunk;
			
			xChunk=Math.round((this.getX()+(GameComponent.tileSize/2))/GameComponent.tileSize);
			yChunk=Math.round((this.getY()+(GameComponent.tileSize/2))/GameComponent.tileSize);
			
			//if charged the positions of collisionChunk,update the collisionChunk
			if(xChunk!=oldxChunk || yChunk!=oldyChunk) collisionChunkMap.getChunk(xChunk, yChunk, map);
			
			
			if(oldDir != dir) {
				AnimationPlayer.setAnimation(dir.toString());
			}
			AnimationPlayer.TimerFrame(4);
		}
		else if(state == PlayerState.attack) {
			AnimationPlayer.setAnimation("ATTACK");
			
						
			state=PlayerState.walk;
		}

		return;
	}
	
	private boolean movement(LevelWorld map) {
		System.out.println(this.tux_collected);
		int oldX=this.getX(),oldY=this.getY();
		oldDir = dir;
		
		if(state == PlayerState.walk) {
            if (isKeyPressed(KeyEvent.VK_UP) && !collisionChunkMap.hasCollision(this.getX(), this.getY() - speed)) {
                this.addForceY(-speed);
                dir = Direction.UP;
            } else if (isKeyPressed(KeyEvent.VK_DOWN) && !collisionChunkMap.hasCollision(this.getX(), this.getY() + speed)) {
                this.addForceY(speed);
                dir = Direction.DOWN;
            }

            if (isKeyPressed(KeyEvent.VK_LEFT) && !collisionChunkMap.hasCollision(this.getX() - speed, this.getY())) {
                this.addForceX(-speed);
                dir = Direction.LEFT;
            } else if (isKeyPressed(KeyEvent.VK_RIGHT) && !collisionChunkMap.hasCollision(this.getX() + speed, this.getY())) {
                this.addForceX(speed);
                dir = Direction.RIGHT;
            }
			

			return (oldX!=this.getX() || oldY!=this.getY()); //return if player charged of chunck
		}
		
		
		return false;
	}
	
	public boolean isKeyPressed(int KEY) {
		return keyPressed.contains(KEY);
	}
	
	public void keyPressed(KeyEvent key) {
		keyPressed.add(key.getKeyCode());
		return ;
	}
	
	public void keyReleased(KeyEvent key) {
		keyPressed.remove(key.getKeyCode());
		switch(key.getKeyCode()) {
			case KeyEvent.VK_C:
				state=PlayerState.attack;
				break;
		}
		
		return ;
	}

	public void keyTyped(KeyEvent arg0) {
		return ;
	}
	
	public void addLife() {
		if(this.lifes < 6)this.lifes++;
		return ;
	}
	
	public ChunkCollision getChunk() {
		return this.collisionChunkMap;
	}
	
	public Entidy getChunkAsEntidy() {
		return new Entidy(xChunk,yChunk);
	}

    public void addTuxCollected(){
        this.tux_collected++;
    }
    public void resetTuxCollected(){
        this.tux_collected=0;
    }
}