package game;

import environment.Coordinate;
import environment.Direction;

public class HumanPlayer extends Player {

    private Direction goTo;


    public HumanPlayer(int id, Game game, Barreira barreira) {
        super(id, game, barreira);
        currentStrength = 5;
    }


    //apos receber a direção pretendida pelo cliente a mesma é transformada para que possa ser feito o movimento
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

    //move o player humano para a direção pretendida, apos ver se todas as condições o permitem
    @Override
    public void move() {
        if(estadoAtual == Estado.VIVO){
            Coordinate currentCoor = getCurrentCell().getPosition();
            Coordinate newCoor = currentCoor.translate(goTo.getVector());
            if(!(newCoor.x < 0 || newCoor.y < 0 || newCoor.x >= game.DIMX ||newCoor.y >= game.DIMY)){
                try{

                    game.getCell(currentCoor).lockCell();
                    game.getCell(newCoor).lockCell();
                    //System.out.println("as celulas: " + currentCoor + " e " + newCoor + " estão bloqueadas");
                    if(game.getCell(newCoor).isOcupied()){
                        fight(game.getCell(newCoor).getPlayer());
                        if (estadoAtual == Estado.TERMINAL)
                            barreira.countDown(this);
                        return;
                    }
                    getCurrentCell().setPlayerToNull();
                    game.getCell(newCoor).setPlayer(this);
                }finally {

                    game.getCell(currentCoor).unlockCell();
                    game.getCell(newCoor).unlockCell();
                }
            }
        }

    }
}
