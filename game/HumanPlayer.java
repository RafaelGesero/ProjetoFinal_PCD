package game;

import gui.BoardJComponent;

public class HumanPlayer extends Player {

    private String name;
    BoardJComponent boardGui;
    private boolean keyTeclas;

    public HumanPlayer(int id, Game game, Barreira barreira, boolean keyTeclas) {
        super(id, game, barreira);
        currentStrength = 5;

        this.keyTeclas = keyTeclas;
    }



    @Override
    public boolean isHumanPlayer() {
        return true;
    }

}
