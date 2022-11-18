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


	public void fight(Player p){

		byte a = p.getCurrentStrength();
		byte b = this.getCurrentStrength();

		int output = Byte.compare(a,b);
		System.out.println("comprar ambos: " + output);

		System.out.println("estou aqui");

		if(p.getCurrentStrength() > this.getCurrentStrength()){
			System.out.print("abc");
			p.setstrenght = originalStrength;
			this.getCurrentCell().setPlayerToNull();
			System.out.println("o player morreu " + this.getIdentification() + this);

		} else{

			System.out.println("deste lado");
			this.setstrenght = originalStrength;
			this.getCurrentCell().setPlayerToNull();
			System.out.println("o player morreu deste lado " + this.getIdentification() + this);

		}




	}

	public  void  move() throws InterruptedException {
		Direction goTo = moveTo();
		Coordinate currentCor = getCurrentCell().getPosition();
		Coordinate newCoor = currentCor.translate(goTo.getVector());

		if(countMove == 1){
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
		if(getCurrentCell().isOcupied()){
			System.out.println("entrei aqui");
			fight(this);
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
			try {
				move();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			game.notifyChange();
		}

	}
}
