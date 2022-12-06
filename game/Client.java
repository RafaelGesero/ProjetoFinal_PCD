package game;

import environment.Direction;
import gui.BoardJComponent;
import gui.GameGuiMain;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {

    BoardJComponent boardGui;
    private Direction direction;
    private Socket socket;
    private PrintWriter out;
    private ObjectInputStream in;

    public Client (){
    }

    public Direction getDirection(){
        return direction;
    }

    public void runClient() throws InterruptedException{
        try {
            connectToServer();
            sendMessages();
        } catch (IOException e) {
            System.out.println("erro1 runclient");
        } finally {
            try {
                    socket.close();
            } catch (IOException e) {
                System.out.println("erro2 no runclient");
            }
        }
    }

    private void sendMessages() throws IOException, InterruptedException{
        String teste = Direction.UP.toString();
        System.out.println(teste);
        out.println(teste);
    }
    private void connectToServer() throws IOException {
        InetAddress endereco = InetAddress.getByName("192.168.1.1");
        socket = new Socket("localhost", GameGuiMain.PORTO);
         in = new ObjectInputStream(socket.getInputStream());
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }



    public static void main(String[] args) {

        try {
            new Client().runClient();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
