package gui;

import java.util.Observable;
import java.util.Observer;

import game.Barreira;
import game.Game;
import game.PhoneyHumanPlayer;
import game.Player;

import javax.swing.JFrame;


public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Game game;
	private static int lugar=0;

	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);

		buildGui();

	}

	public static int cheguei() {
		lugar++;
		return lugar;
	}

	private void buildGui() {
		boardGui = new BoardJComponent(game);
		frame.add(boardGui);


		frame.setSize(800,800);
		frame.setLocation(500, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init()  {
		frame.setVisible(true);


		Barreira finalJogo = new Barreira(3);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0 ; i < 100 ; i++){
			Player p = new PhoneyHumanPlayer(i, game, finalJogo);
			Thread t = new Thread(p);
			t.start();
		}
		try {
			finalJogo.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

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
