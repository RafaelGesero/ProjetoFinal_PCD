package game;

import gui.GameGuiMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private int numPlayers = 0;
    private int maxPlayers=2;
    private ServerSocket ss ;
    private Socket socket;
    protected GameGuiMain gui;

    public static final int PORTO = 8090;

    public Server(GameGuiMain gui) throws IOException {
        this.gui = gui;
        ss = new ServerSocket(Server.PORTO);
    }

    /*criar conexao e canais que farao a troca de dados entre o cliente e o servidor
    */

    public void  doConnections() throws IOException {

        socket = ss.accept();
        BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        numPlayers++;
        out.writeObject(numPlayers);
        out.reset();
        HumanPlayer hp = new HumanPlayer(numPlayers, gui.getGame(), gui.getGame().getBarreira());
        gui.getGame().addPlayerToGame(hp);
        new InfoToClient(out).start();
        new InfoFromClient(in, hp).start();

    }

    public void run(){
        while(numPlayers<maxPlayers){
            try {
                doConnections();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    //funçao que envia a informçao para o cliente
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
                } catch (IOException e) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

    }

    //funçao que recebe a informaçao do cliente
    class InfoFromClient extends Thread{

        private BufferedReader in;
        private HumanPlayer hp;

        public InfoFromClient(BufferedReader in, HumanPlayer hp){
            this.in = in;
            this.hp = hp;
        }

        public void run(){
                while (true) {
                    try {
                        if (hp.getEstadoAtual() != 3) {
                            String str = in.readLine();
                            hp.setGoTo(str);
                            hp.move();
                        }

                    } catch (IOException e) {
                        try {
                            socket.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
        }


    }
}