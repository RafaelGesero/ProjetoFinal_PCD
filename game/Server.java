package game;

import gui.GameGuiMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int numPlayers = 0;
    private int maxPlayers=2;
    private ServerSocket ss ;
    protected GameGuiMain gui;

    public static final int PORTO = 8090;

    public Server(GameGuiMain gui) throws IOException {
        this.gui = gui;
        ss = new ServerSocket(Server.PORTO);
    }

    public void  doConnections() throws IOException {

        while (numPlayers < maxPlayers ){
            Socket s = ss.accept();
            BufferedReader in  = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            numPlayers++;
            out.writeObject(numPlayers);
            out.reset();
            new InfoToClient( out).start();
            new InfoFromClient(in).start();
            HumanPlayer hp = new HumanPlayer(numPlayers, gui.getGame(), gui.getBarreira());
            gui.getGame().addPlayerToGame(hp);
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
                    String str = in.readLine();
                    System.out.println("nova direção: " + str);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            }


    }
}