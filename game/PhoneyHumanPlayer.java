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

		System.out.println(p.getCurrentStrength());
		System.out.println("--------");
		System.out.println(getCurrentStrength());

		if(p.getCurrentStrength() > getCurrentStrength()){
			//tu estas a colocar o força de um no outro, tens de somar as duas e eleminar o masi fraco, eu fiz a função que soma o svalores na classe player
			//p.setstrenght = originalStrength;
			this.getCurrentCell().setPlayerToNull();
			System.out.println("o player morreu " + this.getIdentification() + this);
		} else{
			System.out.println("deste lado");
			//this.setstrenght = originalStrength;
			this.getCurrentCell().setPlayerToNull();
			System.out.println("o player morreu deste lado " + this.getIdentification() + this);
		}
	}


	public  void  move() throws InterruptedException {
		Direction goTo = moveTo();
		Coordinate currentCor = getCurrentCell().getPosition();
		Coordinate newCoor = currentCor.translate(goTo.getVector());

		if(countMove == 1){
			//o if que tu fazes no final esta mal, o que tu esta a fazer é mover o player e dps é que vais ver se esta ocupado, tens de fazer esta confirmação antes do movimento
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
		//nao funciona, ver o com que esta a cima, quando invocas a função fight(this) esta a fazer com que o player p1 lute com ele mesmo, antes do mov tens de ver a se a ceel ja esta acupada e se estiver vais buscar esse player e fazes fight("esse player")
		if(getCurrentCell().isOcupied()){
			fight(this);
		}
		}


	@Override
	public void run() {
		addPlayerToGame();
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
