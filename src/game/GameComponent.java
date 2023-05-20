package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import entidy.Player;
import world.LevelWorld;

import javax.swing.JPanel;

public class GameComponent extends JPanel implements Runnable{
	public static final int screenCOL=16;
	public static final int screenROW=24;
	public static final int tileSize=32;
	
	
	
	private static final long serialVersionUID = 1L;
	private Thread gameThread;
	private Player player = new Player((byte)3,(byte)3);
	private LevelWorld Map= new LevelWorld();
	
	public GameComponent() {
		this.setBackground(Color.GRAY);

		this.setFocusable(true);
		super.setDoubleBuffered(true);
		
		this.addKeyListener(player);
		
		gameThread=new Thread(this);
		gameThread.start();
	}

	//Função Paint do JPanel responsavel pela parte grafica do jogo
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		
		graphic.setColor(Color.BLACK);
		graphic.fillRect(0, 0, 32, 32);
		Map.draw(graphic);
		player.draw(graphic);
		graphic.dispose();
	}
	
	//Thread do jogo
	public void run() {
		while(true) {
			repaint();
			Toolkit.getDefaultToolkit().sync();
			player.update(Map);
			try {
				Thread.sleep(60);
			}catch(Exception io) {
				io.printStackTrace();
			}
		}
	}
}
