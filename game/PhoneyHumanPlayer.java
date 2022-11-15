package game;

import com.sun.source.tree.NewClassTree;
import environment.Cell;
import environment.Coordinate;
import environment.Direction;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */
public class PhoneyHumanPlayer extends Player  {

	private int countMove;
	public PhoneyHumanPlayer(int id, Game game) {
		super(id, game);
		countMove = originalStrength;
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

	public  void  move() {
		if(countMove == 1){
			Direction goTo = moveTo();
			Coordinate currentCor = getCurrentCell().getPosition();
			Coordinate newCoor = currentCor.translate(goTo.getVector());
			if(!(newCoor.x < 0 || newCoor.y < 0 || newCoor.x >= game.DIMX ||newCoor.y >= game.DIMY)){
				getCurrentCell().setPlayerToNull();
				try {
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
		while(true){
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
