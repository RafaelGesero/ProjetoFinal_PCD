package game;

import environment.Direction;
import gui.BoardJComponent;

public class HumanPlayer extends Player {

    private Direction goTo;


    public HumanPlayer(int id, Game game, Barreira barreira) {
        super(id, game, barreira);
        currentStrength = 5;

    }


    public void setGoTo(String str){
        switch (str){
            case "UP":
                goTo = Direction.UP;
            case "DOWN":
                goTo = Direction.DOWN;
            case "LEFT":
                goTo = Direction.LEFT;
            case "RIGHT":
                goTo = Direction.RIGHT;
        }
    }


    @Override
    public boolean isHumanPlayer() {
        return true;
    }

    @Override
    public void move() {

    }

}
