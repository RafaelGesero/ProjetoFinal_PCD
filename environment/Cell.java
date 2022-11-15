package environment;

import game.Game;
import game.Player;

public class Cell {
	private Coordinate position;
	private Game game;
	private Player player=null;
	
	public Cell(Coordinate position,Game g) {
		super();
		this.position = position;
		this.game=g;
	}

	public String toString(){
		String str = position.toString();
		return str;

	}

	public Coordinate getPosition() {
		return position;
	}

	public boolean isOcupied() {
		return player!=null;
	}

	public synchronized void setPlayerToNull(){
		player = null;
		notifyAll();
	}


	public Player getPlayer() {
		return player;
	}

	// Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
	public  synchronized void setPlayer(Player player) throws InterruptedException {
		while(isOcupied()){
			wait();
		}
		this.player = player;
		player.returnPos(this);
	}
	
	

}
