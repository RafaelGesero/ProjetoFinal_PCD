package game;

import environment.Direction;
import gui.BoardJComponent;
import gui.GameGuiMain;

import javax.swing.text.Style;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client{
    private Status status;
    private Direction direction;
    private Socket socket;
    private int humanPlayerId;

    public Client (){
    }

    public Direction getDirection(){
        return direction;
    }

    public void runClient() throws InterruptedException, ClassNotFoundException {
        try {
            connectToServer();
        } finally {
            try {
                    socket.close();
            } catch (IOException e) {
                System.out.println("erro1 no runclient");
            }
        }
    }

    public void connectToServer(){
        try {
            socket = new Socket("localhost", GameServer.PORTO);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            humanPlayerId = in.readInt();
            System.out.println(humanPlayerId);

            ReadFromServer rfs = new ReadFromServer(in);
            WriteToServer wft = new WriteToServer(out);
            rfs.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class  ReadFromServer extends Thread{

        private ObjectInputStream in;

        public ReadFromServer(ObjectInputStream in){
            this.in = in;
        }
        public void run(){
            System.out.println("teste");
            while(true){
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

    private class  WriteToServer extends Thread{

        private DataOutputStream out;

        public WriteToServer(DataOutputStream out){
            this.out = out;
        }
        public void run(){

        }
    }




    public static void main(String[] args) {
        try {
            try {
                new Client().runClient();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
