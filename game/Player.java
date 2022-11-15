package game;



import environment.Cell;
import environment.Coordinate;

/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player implements Runnable{


	protected  Game game;
	private int id;

	private Cell currentCell;

	private byte currentStrength;
	protected byte originalStrength;

	// TODO: get player position from data in game
	public Cell getCurrentCell() {
		return currentCell;
	}


	public Player(int id, Game game) {
		super();
		this.id = id;
		this.game=game;
		originalStrength = inicialStrenght();
		currentStrength = originalStrength;
	}

	public abstract boolean isHumanPlayer();
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", currentStrength=" + currentStrength + ", getCurrentCell()=" + getCurrentCell()
		+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public byte getCurrentStrength() {
		return currentStrength;
	}


	public int getIdentification() {
		return id;
	}

	public  byte inicialStrenght (){
		return (byte) ((Math.random() * game.MAX_INITIAL_STRENGTH) + 1);
	}

	public void returnPos(Cell currentCell){
		this.currentCell = currentCell;
	}
}


