package gui;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

	public BoardJComponent getBoardGui(){
		return boardGui;
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
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finalJogo = new Barreira(3);
		Thread.sleep(game.INITIAL_WAITING_TIME);
		for(int i = 0 ; i < 100 ; i++){
			Player p = new PhoneyHumanPlayer(i, game, finalJogo);
			Thread t = new Thread((Runnable) p);
			t.start();
		}
		finalJogo.await();
	}

	public void addHumanPlayer(int humanId){
		HumanPlayer hp = new HumanPlayer(humanId, game, finalJogo, true);
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
class Server extends Thread{

	private int numPlayers = 0;
	private ServerSocket ss ;
	protected GameGuiMain gui;

	public Server(GameGuiMain gui) throws IOException {
		this.gui = gui;
		ss = new ServerSocket(GameServer.PORTO);
	}

	private void  doConnections() throws IOException {
			Socket s = ss.accept();
			 DataInputStream in  = new DataInputStream(s.getInputStream());
			 ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			numPlayers++;
			out.writeObject(numPlayers);
			out.reset();
			new ClientHandler(s, in, out).start();
	}

	public void run() {
		while(true){
			try {
				System.out.println("waiting for connection....");
				doConnections();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}
	class ClientHandler extends Thread{

private Socket clientSocker;
private DataInputStream in;
private ObjectOutputStream out;

		public ClientHandler(Socket clientSocker, DataInputStream in, ObjectOutputStream out){
			this.clientSocker = clientSocker;
			this.in = in;
			this.out = out;
		}

		public void run(){
			while(true){
				try {
					out.reset();
					out.writeObject(gui.getBoardGui());
					sleep(Game.REFRESH_INTERVAL);
				} catch (IOException | InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}
}