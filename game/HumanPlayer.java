package game;

import environment.Coordinate;
import environment.Direction;

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
                break;
            case "DOWN":
                goTo = Direction.DOWN;
                break;
            case "LEFT":
                goTo = Direction.LEFT;
                break;
            case "RIGHT":
                goTo = Direction.RIGHT;
                break;
        }
    }

    @Override
    public boolean isHumanPlayer() {
        return true;
    }

    @Override
    public void move() {
        if(estadoAtual == Estado.VIVO){
            Coordinate currentCoor = getCurrentCell().getPosition();
            Coordinate newCoor = currentCoor.translate(goTo.getVector());
            if(!(newCoor.x < 0 || newCoor.y < 0 || newCoor.x >= game.DIMX ||newCoor.y >= game.DIMY)){
                try {
                    if(game.getCell(newCoor).isOcupied()){
                        fight(game.getCell(newCoor).getPlayer());
                        if (estadoAtual == Estado.TERMINAL)
                            barreira.countDown(getIdentification());
                        return;
                    }
                    getCurrentCell().setPlayerToNull();
                    game.getCell(newCoor).setPlayer(this);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
