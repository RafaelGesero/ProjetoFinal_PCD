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

    public void runClient() throws InterruptedException, ClassNotFoundException {
        try {
            connectToServer();
             humanPlayerId = (int) in.readObject();
            System.out.println("entras-te no jogoo teu id é : " + humanPlayerId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectToServer() throws IOException {
            socket = new Socket("localhost", GameServer.PORTO);
             in = new ObjectInputStream(socket.getInputStream());
             out = new DataOutputStream(socket.getOutputStream());
    }

    public void run(){
        try {
            runClient();
            while(true){
                boardGui = (BoardJComponent) in.readObject();
                JFrame frame = new JFrame("jogador " + humanPlayerId);
                frame.add(boardGui);
                frame.setSize(800,800);
                frame.setLocation(500, 150);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("erro1 no runclient");
            }
        }
    }

    private class ReadFromServer extends Thread {

        private ObjectInputStream in;

        public ReadFromServer(ObjectInputStream in) {
            this.in = in;
        }

        public void run() {
            while (true) {
                try {
                    Status s = (Status) in.readObject();
                    s.getBoard().setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class WriteToServer extends Thread {

        private DataOutputStream out;

        public WriteToServer(DataOutputStream out) {
            this.out = out;
        }

        public void run() {

        }
    }


    public static void main(String[] args) {
        Client c1 = new Client();
        c1.start();
    }
}
