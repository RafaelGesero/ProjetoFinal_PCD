package game;

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

	public void move(){
		int min = 1;
		int max = 4;
		int range =  max - min + 1;
		int rand = (int) ((Math.random() * range) + min);
	}


	@Override
	public void run() {

	}
}
