package game;

import gui.BoardJComponent;

public class HumanPlayer extends Player {

    private String name;
    BoardJComponent boardGui;

    public HumanPlayer(int id, Game game, Barreira barreira) {
        super(id, game, barreira);
        currentStrength = 5;

    }



    @Override
    public boolean isHumanPlayer() {
        return true;
    }

}
