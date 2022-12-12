package game;

import environment.Coordinate;
import environment.Direction;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */



public class PhoneyHumanPlayer extends Player implements Runnable{

	private int countMove;
	private final byte originalStrength;

	public PhoneyHumanPlayer(int id, Game game, Barreira barreira) {
		super(id, game, barreira);
		originalStrength = inicialStrenght();
		currentStrength = originalStrength;
		countMove = originalStrength;

	}

	public boolean isHumanPlayer() {
		return false;
	}

	//funçao que cria a energia inicial random, para o jogador automatico, dentro dos valores determinados
	private byte inicialStrenght (){
		return (byte) ((Math.random() * game.MAX_INITIAL_STRENGTH) + 1);
	}


	//funçao que define para onde se vai mover aleatoriamente o player automatico entre as 4 direçoes possiveis
	private Direction moveTo(){
		int rand = (int) ((Math.random() * 4) + 1);
		switch (rand) {
			case 1:
				return Direction.UP;
			case 2:
				return Direction.DOWN;
			case 3:
				return Direction.LEFT;
			case 4:
				return Direction.RIGHT;
		}
		return null;
	}

	//funçao que cria o movimento aleatorio dos players automaticos
	public  void  move() {
		if(countMove == 1 && estadoAtual == Estado.VIVO){
			Direction goTo = moveTo();
			Coordinate currentCor = getCurrentCell().getPosition();
			Coordinate newCoor = currentCor.translate(goTo.getVector());

			if(!(newCoor.x < 0 || newCoor.y < 0 || newCoor.x >= game.DIMX ||newCoor.y >= game.DIMY)){
				try {
				if(game.getCell(newCoor).isOcupied()){
					fight(game.getCell(newCoor).getPlayer());
					return;
				}
				getCurrentCell().setPlayerToNull();
				game.getCell(newCoor).setPlayer(this);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			countMove = originalStrength;
		}else{
			countMove--;
		}
		}


	@Override
	public void run() {
		game.addPlayerToGame(this);
		try {
			Thread.sleep(game.MAX_WAITING_TIME_FOR_MOVE);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		while (estadoAtual == Estado.VIVO) {
			try {
				move();
				game.notifyChange();
				Thread.sleep(game.REFRESH_INTERVAL);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		if (estadoAtual == Estado.TERMINAL)
			barreira.countDown(this);

	}
}
