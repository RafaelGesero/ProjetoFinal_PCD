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
		Coordinate newCoor = currentCor.translate(goTo.getVector());
		System.out.println(game.DIMX);
		System.out.println(game.DIMY);
		System.out.println(newCoor);
		if(newCoor.x < 0 && newCoor.y <0 ){

		}else if(newCoor.x <0 && newCoor.y > game.DIMY){
			System.out.println("entrou no 1 if");
		}else if(newCoor.x > game.DIMX && newCoor.y < 0 ){
			System.out.println("entrou no 2 if");
		}else if(newCoor.x > game.DIMX && newCoor.y > game.DIMY){
			System.out.println("entrou no 3 if");
		}else {
			System.out.println("entrou no else ");
			getCurrentCell().setPlayerToNull();
			game.getCell(newCoor).setPlayer(this);
		}

		System.out.println("---------------------------");

	}


	@Override
	public void run() {
		for(int i = 0; i < 100; i++){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			move();
			game.notifyChange();
		}

	}
}
