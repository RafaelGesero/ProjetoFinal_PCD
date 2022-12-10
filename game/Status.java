package game;

import javax.swing.*;
import java.io.Serializable;

public class Status implements Serializable {

    private JFrame board;


    public Status(JFrame board){
        this.board = board;

    }

    public JFrame getBoard(){
        return board;
    }

}
