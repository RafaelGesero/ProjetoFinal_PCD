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
    private InputStream in;

    public Client (BoardJComponent boardGui, Direction direction){
        this.boardGui=boardGui;
        this.direction=direction;
    }

    public Direction getDirection(){
        return direction;
    }


    public static void main(String[] args) {

        try {
            runClient();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void runClient() throws InterruptedException{
        try {
           connectToServer();
          //  sendMessages();
        } catch (IOException e) {
            System.out.println("erro1 runclient");
        } finally { // a fechar
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("erro2 no runclient");
            }
        }
    }






    private void connectToServer() throws IOException {
        InetAddress endereco = InetAddress.getByName("192.168.1.1");
        socket = new Socket("localhost", GameGuiMain.PORTO);

        in = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

     private void sendMessages() throws IOException, InterruptedException{
        for(int i = 0; i < NUMERO_MENSAGENS; i++) {
            out.println("Ola " + i);
            String str = in.readLine();
            System.out.println(str);
            sleep(1000);
        }
        out.println("FIM");
    }









}
