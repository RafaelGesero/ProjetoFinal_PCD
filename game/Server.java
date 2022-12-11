package game;

import gui.GameGuiMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private int numPlayers = 0;
    private ServerSocket ss ;
    protected GameGuiMain gui;

    public static final int PORTO = 8090;

    public Server(GameGuiMain gui) throws IOException {
        this.gui = gui;
        ss = new ServerSocket(Server.PORTO);
    }

    private void  doConnections() throws IOException {
        Socket s = ss.accept();
        BufferedReader in  = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        numPlayers++;
        out.writeObject(numPlayers);
        out.reset();
        new InfoToClient( out).start();
        new InfoFromClient(in).start();
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
    class InfoToClient extends Thread{
        private ObjectOutputStream out;

        public InfoToClient( ObjectOutputStream out){


            this.out = out;
        }

        public void run(){
            while(true){
                try {
                    out.reset();
                    out.writeObject(gui.getGame());
                    sleep(Game.REFRESH_INTERVAL);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    class InfoFromClient extends Thread{

        private BufferedReader in;

        public InfoFromClient(BufferedReader in){
            this.in = in;
        }

        public void run(){
            while (true){
                try {
                    System.out.println("espera pela direção");
                    String str = in.readLine();
                    System.out.println("nova direção: " + str);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            }


    }
}