package gui;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

import game.*;

import javax.swing.*;


public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Barreira finalJogo;
	private Game game;
	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);
		buildGui();
	}

	public Game getGame(){
		return game;
	}

	public Barreira getBarreira(){
		return finalJogo;
	}

	private void buildGui() {
		boardGui = new BoardJComponent(game, true);
		frame.add(boardGui);
		frame.setSize(800,800);
		frame.setLocation(500, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() throws IOException, InterruptedException {
		Server server = new Server(this);
		server.start();
		frame.setSize(800,800);
		frame.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finalJogo = new Barreira(3);
		Thread.sleep(game.INITIAL_WAITING_TIME);
		System.out.println("o jogo vai começar");
		for(int i = 0 ; i < 90 ; i++){
			Player p = new PhoneyHumanPlayer(i, game, finalJogo);
			Thread t = new Thread((Runnable) p);
			t.start();
		}
		finalJogo.await();
	}


	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public static void main(String[] args) throws IOException {
		GameGuiMain game = new GameGuiMain();
		try {
			game.init();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}