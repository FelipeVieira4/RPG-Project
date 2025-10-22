package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ia.Npc;
import item.TuxCollect;
import sound.Sound;
import world.LevelWorld;

import javax.swing.*;

import item.HealthITem;
import item.Item;
import player.Player;

public class GameComponent extends JPanel implements Runnable{
	
	public static final int screenCOL=16;		//Number of Collumns on screen
	public static final int screenROW=24;		//Number of Rows on screen
	public static final int tileSize=32;		//Size of tiles
	
	public static final String version = "0.3V IN DEV";//Version of game
	
	private volatile boolean paused = false;
    private boolean keepRunning=true;

	private static final long serialVersionUID = 1L;
	private Thread gameThread = new Thread(this);

    private boolean debugMode=true;
	
	private Player player = new Player(3,3);
	private LevelWorld Map = new LevelWorld();
	private ArrayList<Item> ItemList = new ArrayList<Item>();

    private ArrayList<Npc> npcList = new ArrayList<>();

	private Sound music = new Sound("rsc/orchestral_orchestral.wav"); //Music of background

    private JFrame jFrame;

	public GameComponent(JFrame jFrame) {
		Color backgroundColor = new Color(127,148,41);
		this.setBackground(backgroundColor);
		ItemList.add(new HealthITem(2, 2));
        ItemList.add(new TuxCollect(7, 3));

        Npc npc = new Npc(9, 6, this.Map);
        npc.setTarget(player);
        npcList.add(npc);

		this.setFocusable(true);
		this.addKeyListener(player);
		this.addKeyListener(new KeyBoardSystem());
		
		super.setDoubleBuffered(true);
		this.jFrame=jFrame;
		
		gameThread.start();
		
	}

	//Função Paint do JPanel responsavel pela parte grafica do jogo
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		
		
		Map.draw(graphic,this.debugMode);

		for(Item item: ItemList){
            if (this.debugMode) {
                graphic.setColor(Color.BLUE);
                graphic.drawRect(item.getX(), item.getY(), item.getWidht(), item.getHeight());
            }
            item.draw(graphic);
        }

		player.draw(graphic);
        if (this.debugMode) {
            graphic.setColor(Color.RED);
            graphic.drawRect(player.getX(), player.getY(), player.getWidht(), player.getHeight());

            player.getChunk().drawDebugCollsion(graphic);
        }
		for (Npc npc:npcList){
            graphic.setColor(Color.BLUE);
            graphic.drawRect(npc.getX(),npc.getY(),npc.getWidht(),npc.getHeight());
        }


		graphic.dispose();
	}
	
	//Thread do jogo
	public void run() {
		
		while(keepRunning) {
			

			//music.play();
			//music.loop();
			
			
			if(!paused) {

				player.update(Map);
				
				for(int i=ItemList.size()-1 ; i>=0; i--){
					if(ItemList.get(i).PlayerUse(player)){
						ItemList.remove(i);
					}else{
						ItemList.get(i).updateFrame(10);
					}
				}

                for(Npc npc: npcList){
                    npc.update();
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

    public void shouldClose(){
        this.keepRunning=!this.keepRunning;
        this.jFrame.dispose();
    }
}
