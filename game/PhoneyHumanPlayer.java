package game;

import environment.Cell;
import environment.Coordinate;
import environment.Direction;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */
public class PhoneyHumanPlayer extends Player  {
	public PhoneyHumanPlayer(int id, Game game) {
		super(id, game);
	}

	public boolean isHumanPlayer() {
		return false;
	}

	private Direction moveTo(){
		int min = 1;
		int max = 4;
		int range =  max - min + 1;
		int rand = (int) ((Math.random() * range) + min);
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

	public void move(){
		Direction goTo = moveTo();
		getCurrentCell().setPlayerToNull();
		Coordinate cutrrentCor = getCurrentCell().getPosition();
		Coordinate newCor = cutrrentCor.translate(goTo.getVector());
		game.getCell(newCor).setPlayer(this);



	}


	@Override
	public void run() {
		for(int i = 0; i < 10; i++){
			try {
				Thread.sleep(game.REFRESH_INTERVAL);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			move();
			game.notifyChange();
		}

	}
}
