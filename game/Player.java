package game;

import environment.Cell;

import java.io.Serializable;
import java.util.concurrent.BrokenBarrierException;


/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player implements  Serializable {

	protected final Game game;
	private final int id;
	private Cell currentCell;
	protected byte currentStrength;

	protected Estado estadoAtual;
	protected Barreira barreira;

	// TODO: get player position from data in game
	public Cell getCurrentCell() {
		return currentCell;
	}


	public Player(int id, Game game, Barreira barreira) {
		super();
		this.id = id;
		this.game=game;
		this.barreira = barreira;
	}

	public abstract boolean isHumanPlayer();

	public abstract void move();
	
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

	public void setEstadoAtual(Estado estadoAtual){
		this.estadoAtual = estadoAtual;
	}

	public void setCurrentStrength(byte currentStrength){
		this.currentStrength = currentStrength;
	}


	public int getIdentification() {
		return id;
	}

	//funcao que verifica se a soma das energias ja ultrapassoo o valor maximo de energia (10), e caso passe altera o estado do jogador
	private byte sumStrength(Player losePlayer){
		byte loseStrPlayer = losePlayer.getCurrentStrength();

		if((currentStrength + loseStrPlayer) >= game.MAX_FINAL_STRENGTH){
			estadoAtual = Estado.TERMINAL;
			return (byte) game.MAX_FINAL_STRENGTH;
		}else
			return (byte) (currentStrength + loseStrPlayer);
	}

	//funçao que define o vencedor entre 2 players, vencedor este que "absorve" a energia do player que perdeu a batalha e altera o estado do mesmo
	public void fight(Player p)  {
		if(p.getEstadoAtual() == 1){
			//System.out.println("A posição " + p.getCurrentCell().getPosition() + " está ocupada pelo player " + p.getIdentification() + " e o player " + this.getIdentification() + " deseja mover-se para célula, o que dará um confronto");
			if (p.getCurrentStrength() > getCurrentStrength()) {
				byte newStrength = p.sumStrength(this);
				p.setCurrentStrength(newStrength);
				estadoAtual = Estado.MORTO;
			} else if (p.getCurrentStrength() < getCurrentStrength()) {
				byte newStrength = sumStrength(p);
				setCurrentStrength(newStrength);
				p.estadoAtual = Estado.MORTO;
			} else {
				byte newStrength = p.sumStrength(this);
				Player[] names = {this, p};
				Player name = names[(int) (Math.random() * (double) names.length)];
				if (p.getIdentification() == name.getIdentification()) {
					p.setCurrentStrength(newStrength);
					estadoAtual = Estado.MORTO;
				} else{
					setCurrentStrength(newStrength);
					p.estadoAtual = Estado.MORTO;
				}
			}
		}else {
			if(!isHumanPlayer()) {
				try {
					Thread.sleep(game.MAX_WAITING_TIME_FOR_MOVE);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void returnPos(Cell currentCell){
		this.currentCell = currentCell;
	}

}


