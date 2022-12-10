package game;

import gui.GameGuiMain;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    private ServerSocket ss;
    private int numPlayers = 0;
    private int maxPlayers = 2;

    private Socket sp1;
    private Socket sp2;
    private ReadFromClient inp1;
    private ReadFromClient inp2;
    private WriteToClient outp1;
    private WriteToClient outp2;
    public ArrayList<Player> listaPlayers;

    public static final int PORTO = 8980;

    public GameServer(ArrayList<Player> listaPlayers) {
        try {
            ss = new ServerSocket(PORTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void doConnections() throws IOException {
        while (numPlayers < maxPlayers) {
            System.out.println("espera ligações");
            Socket s = ss.accept();
            System.out.println("Nova ligação " + numPlayers);
            DataInputStream in = new DataInputStream(s.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            numPlayers++;
            out.writeInt(numPlayers);

            ReadFromClient rfc = new ReadFromClient(numPlayers, in);
            WriteToClient wtf = new WriteToClient(numPlayers, out);

            if (numPlayers == 1) {
                sp1 = s;
                inp1 = rfc;
                outp1 = wtf;
                outp1.start();

            } else {
                sp2 = s;
                inp2 = rfc;
                outp2 = wtf;
            }

        }

    }

    private class ReadFromClient extends Thread {

        private int playerId;
        private DataInputStream in;

        public ReadFromClient(int playerId, DataInputStream in) {
            this.playerId = playerId;
            this.in = in;
        }

        public void run() {

        }

    }

    class WriteToClient extends Thread {

        private int playerId;
        private ObjectOutputStream out;

        public WriteToClient(int playerId, ObjectOutputStream out) {
            this.playerId = playerId;
            this.out = out;
        }

        public void run() {
            System.out.println("teste");
            while(true){
                try {
                    out.writeObject(listaPlayers);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    sleep(Game.REFRESH_INTERVAL);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}






