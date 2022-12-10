package game;

import gui.BoardJComponent;

public class HumanPlayer extends Player {

    private String name;
    BoardJComponent boardGui;
    private boolean keyTeclas;

    public HumanPlayer(int id, Game game, Barreira barreira, BoardJComponent boardGui, boolean keyTeclas) {
        super(id, game, barreira);
        currentStrength = 5;
        this.boardGui = boardGui;
        this.keyTeclas = keyTeclas;
    }

    public void tecla(){
        System.out.println(boardGui.getLastPressedDirection());
    }

    @Override
    public boolean isHumanPlayer() {
        return true;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public void run() {

    }
}
