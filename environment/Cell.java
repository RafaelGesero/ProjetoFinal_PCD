package environment;

import game.Game;
import game.Player;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
	private Coordinate position;
	private Game game;
	private Player player=null;
	private Lock l = new ReentrantLock();


	
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

	}


	public Player getPlayer() {
		return player;
	}

	// Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
	public  synchronized void setPlayer(Player player) throws InterruptedException {
			this.player = player;
			player.returnPos(this);


	}
	
	

}
