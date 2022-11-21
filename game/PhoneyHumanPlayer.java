package game;

import com.sun.source.tree.NewClassTree;
import environment.Cell;
import environment.Coordinate;
import environment.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */



public class PhoneyHumanPlayer extends Player  {

	private int countMove;

	public PhoneyHumanPlayer(int id, Game game) {
		super(id, game);
		countMove = originalStrength;

	}

	public boolean isHumanPlayer() {
		return false;
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
	//alterei algumas coisa, a função sumStrenght so faz a soma dps tens de colocar esse cvalor
	// como currentStrenght, mas isso ja fiz, falta so fazer o random quando eelas sao iguais
	public void fight(Player p){
		if(p.getCurrentStrength() > getCurrentStrength()){
			byte newStrenght = p.sumStrenght(this);
			p.setCurrentStrength(newStrenght);
			getCurrentCell().setPlayerToNull();
		} else if(p.getCurrentStrength() < getCurrentStrength()){
			byte newStrenght = sumStrenght(p);
			setCurrentStrength(newStrenght);
			p.getCurrentCell().setPlayerToNull();
		} else {
		}


		}

		//nao precisas de fazer assim, ve como fizemos o random das direções, deve ser mais facil
	public void removeRandom(Player p){
		List<Player> list = new ArrayList<>();
		list.add(p);
		list.add(this);

	}

	public  void  move() throws InterruptedException {
		if(countMove == 1){
			Direction goTo = moveTo();
			Coordinate currentCor = getCurrentCell().getPosition();
			Coordinate newCoor = currentCor.translate(goTo.getVector());
			System.out.println(newCoor);
			//esta cell c1 esta com alguns probelmas, se a newCoor estiver fora do tabuleiro
			// ele cria a c1 com essas cordenadas mas como nao é uma cordenada dentro do tabuleio
			// ela dá erro, ainda tou a ver se descubro, se quiseres fazer teste do fight fas no
			// centro do tabuleiro ou asssim, para nao datr probelmas
			Cell c1 = game.getCell(newCoor);

			if(c1.isOcupied()){
				fight(c1.getPlayer());
				newCoor = new Coordinate(game.DIMX + 1, game.DIMY +1);
			}
			if(!(newCoor.x < 0 || newCoor.y < 0 || newCoor.x >= game.DIMX ||newCoor.y >= game.DIMY)){
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
		while(true){
			try {
				Thread.sleep(game.REFRESH_INTERVAL);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			try {
				move();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			game.notifyChange();
		}

	}
}
