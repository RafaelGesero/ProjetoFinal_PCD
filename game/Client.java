package game;

import environment.Direction;
import gui.BoardJComponent;

import java.io.InputStream;
import java.io.PrintWriter;
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










}
