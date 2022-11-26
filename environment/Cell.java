package environment;

import game.Estado;
import game.Game;
import game.Player;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
	private Coordinate position;
	private Game game;
	private Player player=null;
	private Lock l = new ReentrantLock();
	Condition condition = l.newCondition();

	
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

	public void setPlayerToNull(){
		l.lock();
		try{
			player = null;
			condition.signal();
		}finally {
			l.unlock();
		}
	}


	public Player getPlayer() {
		return player;
	}

	// Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
	public void setPlayer(Player player) throws InterruptedException {
		l.lock();
		try{
			while(isOcupied()){
				condition.await();
			}
			this.player = player;
			player.returnPos(this);
			player.setEstadoAtual(Estado.VIVO);
		}finally {
			l.unlock();
		}


	}
	
	

}
