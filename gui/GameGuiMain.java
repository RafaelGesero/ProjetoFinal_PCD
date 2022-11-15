package gui;

import java.util.Observable;
import java.util.Observer;
import game.Game;
import game.PhoneyHumanPlayer;
import game.Player;

import javax.swing.JFrame;

public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Game game;

	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);

		buildGui();

	}

	private void buildGui() {
		boardGui = new BoardJComponent(game);
		frame.add(boardGui);


		frame.setSize(800,800);
		frame.setLocation(0, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init()  {
		frame.setVisible(true);

		// Demo players, should be deleted
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Player p1 = new PhoneyHumanPlayer(1, game);
		Player p2 = new PhoneyHumanPlayer(2, game);
		Player p3 = new PhoneyHumanPlayer(3, game);


		game.addPlayerToGame(p1);
		game.addPlayerToGame(p2);
		game.addPlayerToGame(p3);

		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		Thread t3 = new Thread(p3);

		t1.start();
		//t2.start();
		//t3.start();

		//game.addPlayerToGame(new PhoneyHumanPlayer(2, game));
		//game.addPlayerToGame(new PhoneyHumanPlayer(3, game));



	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public static void main(String[] args) {
		GameGuiMain game = new GameGuiMain();
		game.init();
	}

}
