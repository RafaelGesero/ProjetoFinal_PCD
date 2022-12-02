package environment;

import game.Estado;
import game.Game;
import game.Lock;
import game.Player;



public class Cell {
	private Coordinate position;
	private Game game;
	private Player player=null;

	private Lock l = new Lock();

	
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

	public void setPlayerToNull() throws InterruptedException {
		l.lock();
		try{
			player = null;
		}finally {
			l.unlock();
		}
	}


	public Player getPlayer() {
		return player;
	}

	// Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
	public synchronized void setPlayer(Player player) throws InterruptedException {
		l.lock();
		try{
			while(isOcupied()){

			}
			this.player = player;
			player.returnPos(this);
			player.setEstadoAtual(Estado.VIVO);
		}finally {
			l.unlock();
		}


	}
	
	

}
