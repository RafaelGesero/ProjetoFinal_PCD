package game;

import environment.Direction;
import gui.BoardJComponent;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HumanPlayer extends Player {

    private String name;
    BoardJComponent boardGui;
    public HumanPlayer(int id, Game game, Barreira barreira, BoardJComponent boardGui) {
        super(id, game, barreira);
        currentStrength = 5;
        this.boardGui = boardGui;
    }

    public void tecla(){
        System.out.println(boardGui.getLastPressedDirection());
    }

    @Override
    public boolean isHumanPlayer() {
        return true;
    }

    @Override
    public void run() {
       while(true){

       }
    }

    public static void main(String[] args) throws IOException {
        InformationToServer its = new  InformationToServer((byte) 5, Direction.UP, Estado.VIVO);
        Socket socket = new Socket("localhost", Game.PORTO);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(its);
        socket.close();
    }



}
