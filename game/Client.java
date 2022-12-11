package game;

import environment.Direction;
import gui.BoardJComponent;
import gui.GameGuiMain;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {
    private Socket socket;
    private int humanPlayerId;

   private BoardJComponent boardGui;
   private Game game;

    private ObjectInputStream in;
    private PrintWriter out;

    public void runClient() throws InterruptedException, ClassNotFoundException, IOException {
            connectToServer();
             humanPlayerId = (int) in.readObject();
            System.out.println("entrou no jogo,o teu id é : " + humanPlayerId);

    }

    private void connectToServer() throws IOException {
            socket = new Socket("localhost", Server.PORTO);
             in = new ObjectInputStream(socket.getInputStream());
             out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

    }

    public void run(){
        try {
            runClient();
            JFrame frame = new JFrame("jogador " + humanPlayerId);
            frame.setSize(800,800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(500, 350);
            frame.setFocusable(true);
            game = null;
            boardGui = new BoardJComponent(game);
            while(true){
                game = (Game) in.readObject();
                boardGui.refresh(game);
                boardGui.repaint();
                frame.getContentPane().removeAll();
                frame.add(boardGui);
                frame.setVisible(true);
                frame.addKeyListener(boardGui);

                if (boardGui.getLastPressedDirection() != null){
                    System.out.println(boardGui.getLastPressedDirection().toString());
                    out.println(boardGui.getLastPressedDirection().toString());
                    System.out.println("enviei");
                    boardGui.clearLastPressedDirection();
                }

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Client c1 = new Client();

        c1.start();

    }
}
