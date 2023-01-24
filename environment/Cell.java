package environment;

import game.Estado;
import game.Game;
import game.Lock;
import game.Player;

import java.io.Serializable;
import java.util.concurrent.BlockingDeque;


public class Cell implements Serializable{
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

	//retorna a coordenada da atual celula
	public Coordinate getPosition() {
		return position;
	}


	//retorna true se a cácula estiver ocupada por algum jogador
	public boolean isOcupied() {
		return player!=null;
	}


	//Antes de colocar o player na proxima célula o mesmo é retirado da célula anterior, com os devidos metodos de sincronização
	public synchronized void setPlayerToNull(){
			player = null;
			notify();
	}
	public Player getPlayer() {
		return player;
	}

	public void lockCell(){
		try {
			l.lock();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void unlockCell(){
		l.unlock();
	}

	public synchronized void setInicialPlayer(Player player) throws InterruptedException {
		while(isOcupied()){
			System.out.println("A celula está ocupada, por isso o player " + player.getIdentification()  + " vai ficar á espera");
			wait();
		}
		setPlayer(player);
	}

	//quando o player necessiat de ir para uma célula o mesmo é colocado com os devidos metodos de sincronização
	public void setPlayer(Player player){
		this.player = player;
		player.returnPos(this);
		player.setEstadoAtual(Estado.VIVO);
	}
}
