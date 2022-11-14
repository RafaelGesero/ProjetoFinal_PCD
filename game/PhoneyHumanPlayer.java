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
		int rand = (int) ((Math.random() * max) + min);
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

	public synchronized void  move(){
		Direction goTo = moveTo();
		Coordinate currentCor = getCurrentCell().getPosition();
		Coordinate newCor = currentCor.translate(goTo.getVector());
		if((newCor.x < 0 && newCor.y < 0) || (newCor.x < 0 && newCor.y > game.DIMY)
				|| (newCor.x > game.DIMX && newCor.y < 0) || (newCor.x > game.DIMX && newCor.y > game.DIMY)){
			System.out.println("entou no if ");
			return;
		}else{
			getCurrentCell().setPlayerToNull();
			game.getCell(newCor).setPlayer(this);
		}

	}


	@Override
	public void run() {
		for(int i = 0; i < 100; i++){
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
