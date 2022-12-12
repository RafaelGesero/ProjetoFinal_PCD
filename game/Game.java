package game;



import java.io.*;
import java.util.Observable;

import environment.Cell;
import environment.Coordinate;

public class Game extends Observable implements Serializable{

	public static final int DIMY = 30;
	public static final int DIMX = 30;
	private static final int NUM_PLAYERS = 90;
	private static final int NUM_FINISHED_PLAYERS_TO_END_GAME=3;

	public static final long REFRESH_INTERVAL = 400;
	public static final double MAX_INITIAL_STRENGTH = 3;
	public static final long MAX_WAITING_TIME_FOR_MOVE = 2000;
	public static final long INITIAL_WAITING_TIME = 10000;

	public static final double MAX_FINAL_STRENGTH = 10;

	protected Cell[][] board;

	private Barreira finalJogo;

	public Game() {
		board = new Cell[Game.DIMX][Game.DIMY];
		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) 
				board[x][y] = new Cell(new Coordinate(x, y),this);
	}
	/** 
	 * @param player 
	 */

	//quando o player é criado, o mesmo é adicionado a uma célula random
	public void addPlayerToGame(Player player) {
		Cell initialPos=getRandomCell();
		try {
			initialPos.setPlayer(player);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		notifyChange();
	}

	//criar a barreira para qeu seja possivel identificar os tres primeiros a finalizar o jogo
	public Barreira createBarreira(){
		finalJogo = new Barreira(NUM_FINISHED_PLAYERS_TO_END_GAME);
		return finalJogo;
	}
	public Barreira getBarreira(){
		return finalJogo;
	}

	//criar e inicia todos os players nao humanos
	public void createPhoneyHumanPlayers(){
		for(int i = 0; i < NUM_PLAYERS; i++){
			Player p = new PhoneyHumanPlayer(i, this, finalJogo);
			Thread t = new Thread((Runnable) p);
			t.start();
		}
	}
	public Cell getCell(Coordinate at) {
		return board[at.x][at.y];
	}

	/**	
	 * Updates GUI. Should be called anytime the game state changes
	 */
	public void notifyChange() {
		setChanged();
		notifyObservers();
	}
	//retorna uma célula ramdom que se encontra dentro do tabuleiro
	public Cell getRandomCell() {
		Cell newCell=getCell(new Coordinate((int)(Math.random()*Game.DIMX),(int)(Math.random()*Game.DIMY)));
		return newCell;
	}
}