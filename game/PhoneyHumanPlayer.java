package game;

import environment.Coordinate;
import environment.Direction;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */



public class PhoneyHumanPlayer extends Player  {

	private int countMove;
	private CyclicBarrier barreira;
	private static int lugar=0;

	public PhoneyHumanPlayer(int id, Game game, CyclicBarrier barreira) {
		super(id, game);
		countMove = originalStrength;
		this.barreira = barreira;

	}

	public boolean isHumanPlayer() {
		return false;
	}

	public static int cheguei() {
		lugar++;
		return lugar;
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

	public void fight(Player p) throws InterruptedException {
		if(p.getEstadoAtual() == 1 || p.getEstadoAtual() == 3){
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
			Thread.sleep(game.MAX_WAITING_TIME_FOR_MOVE);
		}
	}

	public  void  move() throws InterruptedException {
		if(countMove == 1 && estadoAtual == Estado.VIVO){
			Direction goTo = moveTo();
			Coordinate currentCor = getCurrentCell().getPosition();
			Coordinate newCoor = currentCor.translate(goTo.getVector());
			if(!(newCoor.x < 0 || newCoor.y < 0 || newCoor.x >= game.DIMX ||newCoor.y >= game.DIMY)){
				if(game.getCell(newCoor).isOcupied()){
					fight(game.getCell(newCoor).getPlayer());
					return;
				}
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
		}


	@Override
	public void run() {
		addPlayerToGame();
		try {
			Thread.sleep(game.MAX_WAITING_TIME_FOR_MOVE);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		while(estadoAtual == Estado.VIVO || estadoAtual == Estado.ESPERA){
			try {
				move();
				Thread.sleep(game.REFRESH_INTERVAL);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			game.notifyChange();
		}
		if(estadoAtual == Estado.TERMINAL){
			try {

			int lugar = cheguei();

			System.out.println("chegou " + getIdentification());

				//System.out.println(getIdentification() + " : " + lugar + "o lugar");
				barreira.await();

				//mal chega ao 3o fecha logo e nao chega aqui , salta logo p run do exit0
				System.out.println(getIdentification() + " : " + lugar + "o lugar");

			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (BrokenBarrierException e) {
				throw new RuntimeException(e);
			}
			System.out.println("terminei " + getIdentification());
		}else{
			System.out.println("morri " + getIdentification());
		}


	}
}
