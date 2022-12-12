package game;

import gui.BoardJComponent;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private Socket socket;
    private int humanPlayerId;

   private BoardJComponent boardGui;
   private Game game;

   private JFrame frame;
    private ObjectInputStream in;
    private PrintWriter out;


    //faz a conecção inicial com o servidor bem como a atribuição do id ao jogador
    public void runClient() throws InterruptedException, ClassNotFoundException, IOException {
        try{
            connectToServer();
            humanPlayerId = (int) in.readObject();
            System.out.println("entrou no jogo,o teu id é : " + humanPlayerId);
        } catch (IOException e) {
            socket.close();
        }
    }

    //cria a coneção e os canais(in e out) que farão a troca de dados com o servidor
    private void connectToServer() throws IOException {
            socket = new Socket("localhost", Server.PORTO);
             in = new ObjectInputStream(socket.getInputStream());
             out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

    }

    //cria a frame do cliente para que o mesmo possa ver o jogo
    private void criarFrame(){
        frame = new JFrame("jogador " + humanPlayerId);
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500, 350);
        frame.setFocusable(true);
    }

    public void run(){
        try {
            runClient();
            criarFrame();
            game = null;
            if(humanPlayerId == 1){
                boardGui = new BoardJComponent(game, true);
                System.out.println("Para realizar movimentos utiliza o teclado (WASD)");
            }else{
                boardGui = new BoardJComponent(game, false );
                System.out.println("Para realizar movimentos utiliza as setas");
            }
            while(true){
                game = (Game) in.readObject();
                boardGui.refresh(game);
                boardGui.repaint();
                frame.getContentPane().removeAll();
                frame.add(boardGui);
                frame.setVisible(true);
                frame.addKeyListener(boardGui);

                if (boardGui.getLastPressedDirection() != null){
                    out.println(boardGui.getLastPressedDirection().toString());
                    boardGui.clearLastPressedDirection();
                }

            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            try {
                socket.close();
                System.exit(0);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    public static void main(String[] args) {
        Client c1 = new Client();

        c1.start();

    }
}
