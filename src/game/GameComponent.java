package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import sound.Sound;
import world.LevelWorld;
import javax.swing.JPanel;

import item.HealthITem;
import item.Item;
import player.Player;

public class GameComponent extends JPanel implements Runnable{
	
	public static final int screenCOL=16;		//Number of Collumns on screen
	public static final int screenROW=24;		//Number of Rows on screen
	public static final int tileSize=32;		//Size of tiles
	
	public static final String version = "0.2V";//Version of game
	
	private volatile boolean paused = false;
	
	private static final long serialVersionUID = 1L;
	private Thread gameThread = new Thread(this);
	
	
	private Player player = new Player(3,3);
	private LevelWorld Map = new LevelWorld();
	private ArrayList<Item> ItemList = new ArrayList<Item>();
	
	private Sound music = new Sound("rsc/orchestral_orchestral.wav"); //Music of background
	
	public GameComponent() {
		Color backgroundColor = new Color(127,148,41);
		this.setBackground(backgroundColor);
		ItemList.add(new HealthITem(2, 2));
		
		this.setFocusable(true);
		this.addKeyListener(player);
		this.addKeyListener(new KeyBoardSystem());
		
		super.setDoubleBuffered(true);
		
		
		gameThread.start();
		
	}

	//Função Paint do JPanel responsavel pela parte grafica do jogo
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		
		
		Map.draw(graphic);

		for(Item item: ItemList)item.draw(graphic);

		player.draw(graphic);
		
		
		graphic.dispose();
	}
	
	//Thread do jogo
	public void run() {
		
		while(true) {
			

			music.play();
			music.loop();
			
			
			if(!paused) {
							
				player.update(Map);
				
				for(int i=ItemList.size()-1 ; i>=0; i--){
					if(ItemList.get(i).PlayerUse(player)){
						ItemList.remove(i);

					}else{
						ItemList.get(i).updateFrame(10);
					}
				}

			}
			
			repaint();	
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
