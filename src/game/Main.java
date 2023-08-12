package game;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public Main() {
		this.setTitle("CLONE ZELDA -"+GameComponent.version);
		
		
		
		int screenWidht = GameComponent.screenROW*GameComponent.tileSize;
		int screenHeight = GameComponent.screenCOL*GameComponent.tileSize;
		
		this.setSize(screenWidht,screenHeight);
		this.setLocation(300,100);
		this.setResizable(false);
		
		
		
		
		GameComponent gameComponent = new GameComponent();
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
