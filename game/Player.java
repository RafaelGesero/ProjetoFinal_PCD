package game;

import environment.Cell;


/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player implements Runnable{

	protected  Game game;
	private final int id;
	private Cell currentCell;
	protected byte currentStrength;
	protected final  byte originalStrength;
	protected Estado estadoAtual;

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
		estadoAtual = Estado.ESPERA;
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

	public int getEstadoAtual(){
		return estadoAtual.getEstadoAtual();
	}

	public void setCurrentStrength(byte currentStrength){
		this.currentStrength = currentStrength;
	}


	public int getIdentification() {
		return id;
	}

	public byte sumStrength(Player losePlayer){
		byte loseStrPlayer = losePlayer.getCurrentStrength();
		return (byte) (currentStrength + loseStrPlayer);
	}

	public void returnPos(Cell currentCell){
		this.currentCell = currentCell;
	}

	public void addPlayerToGame(){
		currentCell = game.getRandomCell();
		try {
			currentCell.setPlayer(this);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		game.notifyChange();
	}

	private byte inicialStrenght (){
		return (byte) ((Math.random() * game.MAX_INITIAL_STRENGTH) + 1);
	}



}


