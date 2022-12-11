package game;

import environment.Direction;
import gui.BoardJComponent;
import gui.GameGuiMain;

import javax.swing.*;
import javax.swing.text.Style;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {
    private Direction direction;
    private Socket socket;
    private int humanPlayerId;

   private BoardJComponent boardGui;

    ObjectInputStream in;
    DataOutputStream out;

    public Client() {
    }

    public Direction getDirection() {
        return direction;
    }

    public void runClient() throws InterruptedException, ClassNotFoundException, IOException {
            connectToServer();
             humanPlayerId = (int) in.readObject();
            System.out.println("entrou no jogo,o teu id é : " + humanPlayerId);

    }

    private void connectToServer() throws IOException {
            socket = new Socket("localhost", GameServer.PORTO);
             in = new ObjectInputStream(socket.getInputStream());
             out = new DataOutputStream(socket.getOutputStream());
    }

    public void run(){
        try {
            runClient();
            JFrame frame = new JFrame("jogador " + humanPlayerId);
            frame.setSize(800,800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(500, 350);
            while(true){
                boardGui = (BoardJComponent) in.readObject();
                frame.getContentPane().removeAll();
                frame.add(boardGui);
                frame.setVisible(true);
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
