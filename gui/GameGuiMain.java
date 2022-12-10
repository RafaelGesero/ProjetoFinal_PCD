package gui;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import game.*;

import javax.swing.*;


public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("jogador 1");
	private BoardJComponent boardGui;
	private Game game;
	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);
		buildGui();
	}

	public JFrame getFrame(){
		return frame;
	}

	private void buildGui() {
		boardGui = new BoardJComponent(game, true);
		frame.add(boardGui);
		frame.setSize(800,800);
		frame.setLocation(500, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init() throws IOException, InterruptedException {
		Server server = new Server();
		server.doConnections();
		//server.start();
		frame.setSize(800,800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Barreira finalJogo = new Barreira(3);
		//Thread.sleep(game.INITIAL_WAITING_TIME);
		for(int i = 0 ; i < 100 ; i++){
			Player p = new PhoneyHumanPlayer(i, game, finalJogo);
			Thread t = new Thread(p);
			t.start();
		}
		server.sendObjects(boardGui);
		finalJogo.await();
	}
	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		GameGuiMain game = new GameGuiMain();
		try {
			game.init();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}

class Server extends Thread{

	private int numPlayers = 0;
	private int maxPlayers = 2;
	private ServerSocket ss ;
	DataInputStream in;
	ObjectOutputStream out;
	Socket s;

	public Server() throws IOException {
		ss = new ServerSocket(GameServer.PORTO);
	}

	public void  doConnections() throws IOException {
		System.out.println("espera de ligações");
		while (numPlayers < maxPlayers) {
			Socket s = ss.accept();
			System.out.println("Nova ligação " + numPlayers);
			 in = new DataInputStream(s.getInputStream());
			 out = new ObjectOutputStream(s.getOutputStream());
			numPlayers++;
			out.writeObject(numPlayers);
		}
		System.out.println("O servidor ja nao aceita mais ligações");
	}

	public void sendObjects(BoardJComponent boardGui) throws IOException {
		System.out.println("teste1");
		out.writeObject(boardGui);
	}

	public void run(){

		//acho q a parte do doConnections vai ter de ser aqui,

	}
}

