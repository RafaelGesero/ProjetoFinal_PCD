package gui;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

import game.*;

import javax.swing.*;

import static java.lang.Thread.sleep;


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


	private void buildGui() {
		boardGui = new BoardJComponent(game, true);
		frame.add(boardGui);
		frame.setSize(800,800);
		frame.setLocation(500, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	/*Cria o servidor e fica á espera da ligação dos clientes, atraves do game
	também é criada a barreira para o final do jogo
	apos os jogadores automaticos serem criados a thred principal é colocada num sleep*/
	public void init() throws IOException, InterruptedException {
		Server server = new Server(this);
		server.start();
		frame.setSize(800,800);
		frame.setVisible(true);
		finalJogo= game.createBarreira();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sleep(game.INITIAL_WAITING_TIME);
		System.out.println("O JOGO VAI COMEÇAR");
		game.createPhoneyHumanPlayers();
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