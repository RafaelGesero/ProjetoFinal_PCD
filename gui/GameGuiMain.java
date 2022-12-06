package gui;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import game.*;

import javax.swing.JFrame;


public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Game game;
	public static final int PORTO = 8980;


	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);
		try {
			startServing();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		buildGui();

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
		HumanPlayer hp = new HumanPlayer(999, game, finalJogo, boardGui, true);
		Thread tp = new Thread(hp);
		tp.start();

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

	public void startServing() throws IOException{
		ServerSocket ss = new ServerSocket(PORTO);
		try {
			System.out.println("espera de ligação");
			while(true) {
				Socket socket = ss.accept();
				new GameServer(socket).start();
			}
		} finally {
			ss.close();
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		GameGuiMain game = new GameGuiMain();
		game.init();
	}

}

class GameServer extends Thread {

	private Socket socket;
	private OutputStream out;
	private InputStream in;

	protected GameServer(Socket socket) {
		this.socket = socket;
	}

	private void doConnections(Socket socket) throws IOException {
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
	}

	public void run() {
		try {
			try {
				doConnections(socket);
			} finally {
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("Erro no run");
		}
	}

}

