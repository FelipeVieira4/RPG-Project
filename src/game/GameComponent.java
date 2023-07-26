package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import world.LevelWorld;
import javax.swing.JPanel;

import player.Player;
import sound.Sound;

public class GameComponent extends JPanel implements Runnable{
	
	public static final int screenCOL=16;		//Number of Collumns on screen
	public static final int screenROW=24;		//Number of Rows on screen
	public static final int tileSize=32;		//Size of tiles
	public static final String version = "0.1V";//Version of game
	
	private boolean debugMode=false;
	private boolean paused=false;
	
	private static final long serialVersionUID = 1L;
	private Thread gameThread;
	
	
	private Player player = new Player(3,3);
	private LevelWorld Map = new LevelWorld();
	private Sound music = new Sound("rsc/orchestral_orchestral.wav"); //Music of background
	
	public GameComponent() {
		this.setBackground(new Color(127,148,41));

		
		this.setFocusable(true);
		this.addKeyListener(player);
		this.addKeyListener(new KeyBoardSystem());
		
		gameThread=new Thread(this);
		gameThread.start();
		
	}

	//Função Paint do JPanel responsavel pela parte grafica do jogo
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		
		Map.draw(graphic);
		player.draw(graphic);
		
		if(debugMode) {
			graphic.setColor(Color.BLUE);
			player.getChunk().drawRects(graphic);
			graphic.setColor(Color.RED);
			graphic.drawRect(player.getChunkAsPoint().getX()*tileSize, player.getChunkAsPoint().getY()*tileSize, tileSize, tileSize);
		}
		
		
		super.setDoubleBuffered(true);
		
		graphic.dispose();
	}
	
	//Thread do jogo
	public void run() {
		
		while(true) {
			repaint();	

			//music.play();
			music.loop();
			
			if(!paused) {
							
				player.update(Map);
				Map.update(player);
				
			}
			
			try {
				Thread.sleep(60);
			}catch(Exception io) {
				io.printStackTrace();
			}
		}
		
		
	}
	
	
	private class KeyBoardSystem extends KeyAdapter {
		
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_1)paused = !paused;	
		}
		
	}
	
}
