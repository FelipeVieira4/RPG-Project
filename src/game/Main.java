package game;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L;
	private static final int screenWidth = GameComponent.screenROW*GameComponent.tileSize;
	private static final int screenHeight = GameComponent.screenCOL*GameComponent.tileSize;

	public Main() {
		this.setTitle("Miguel’s Adventures -"+GameComponent.version);

		this.setSize(screenWidth,screenHeight);
		this.setLocation(300,100);
		this.setResizable(false);

		GameComponent gameComponent = new GameComponent(this);
		this.add(gameComponent);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
            public void run() {
            	new Main();
            }
        });
	}

}
