package gui;

import environment.Coordinate;
import environment.Direction;
import game.Game;
import game.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.stream.StreamSupport;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * Creates a JComponent to display the game state.
 * At the same time, this is also a KeyListener for itself: when a key is pressed,
 * attribute lastPressedDirection is updated accordingly. This feature is a demo to
 * better understand how to deal with keys pressed, useful for the remote client.
 * This feature is not helpful for the main application and should be ignored.
 * This class does not need to be edited.
 * @author luismota
 *
 */
public class BoardJComponent extends JComponent implements KeyListener, Serializable {
	private Game game;
	private transient Image obstacleImage = new ImageIcon("obstacle.png").getImage();
	private transient Image humanPlayerImage= new ImageIcon("abstract-user-flat.png").getImage();
	private Direction lastPressedDirection=null;
	private final boolean alternativeKeys = false;

	public BoardJComponent(Game game) {
		this.game = game;
		setFocusable(true);
		addKeyListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double cellHeight=getHeight()/(double)Game.DIMY;
		double cellWidth=getWidth()/(double)Game.DIMX;

		for (int y = 1; y < Game.DIMY; y++)
			g.drawLine(0, (int)(y * cellHeight), getWidth(), (int)(y* cellHeight));

		for (int x = 1; x < Game.DIMX; x++)
			g.drawLine( (int)(x * cellWidth),0, (int)(x* cellWidth), getHeight());

		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) {
				Coordinate p = new Coordinate(x, y);
				Player player = game.getCell(p).getPlayer();
				if(player!=null) {
					if(player.getEstadoAtual() == 2) {
						g.setColor(Color.BLACK);
						g.fillRect((int) (p.x * cellWidth),
								(int) (p.y * cellHeight),
								(int) (cellWidth), (int) (cellHeight));
						g.drawImage(obstacleImage, (int) (p.x * cellWidth), (int) (p.y * cellHeight),
								(int) (cellWidth), (int) (cellHeight), null);
						continue;
					}

					if(player.getEstadoAtual() == 3){
						g.drawString("X",
								(int) ((p.x + .2) * cellWidth),
								(int) ((p.y + .9) * cellHeight));
					}

					if(player.isHumanPlayer() && player.getEstadoAtual() == 1) {
						g.setColor(Color.GREEN);
						g.fillRect((int)(p.x* cellWidth), 
								(int)(p.y * cellHeight),
								(int)(cellWidth),(int)(cellHeight));
						g.drawImage(humanPlayerImage, (int)(p.x * cellWidth), (int)(p.y*cellHeight), 
								(int)(cellWidth),(int)(cellHeight), null);
					}else if (!(player.isHumanPlayer()) && player.getEstadoAtual() == 1){
						g.setColor(Color.RED);
						g.fillRect((int)(p.x* cellWidth),
								(int)(p.y * cellHeight),
								(int)(cellWidth),(int)(cellHeight));
						g.drawImage(null, (int)(p.x * cellWidth), (int)(p.y*cellHeight),
								(int)(cellWidth),(int)(cellHeight), null);
					}

					if(player.getEstadoAtual() == 1){
						g.setColor(new Color(player.getIdentification() * 1000));
						((Graphics2D) g).setStroke(new BasicStroke(5));
						Font font = g.getFont().deriveFont( (float)cellHeight);
						g.setFont( font );
						String strengthMarking= "" + player.getCurrentStrength();
						g.drawString(strengthMarking,
								(int) ((p.x + .2) * cellWidth),
								(int) ((p.y + .9) * cellHeight));
					}
				}

			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(alternativeKeys) {
			switch(e.getKeyCode()){
				case  KeyEvent.VK_A:
					lastPressedDirection=environment.Direction.LEFT;
					break;
				case  KeyEvent.VK_D:
					lastPressedDirection=environment.Direction.RIGHT;
					break;
				case KeyEvent.VK_W:
					lastPressedDirection=environment.Direction.UP;
					break;
				case KeyEvent.VK_S:
					lastPressedDirection=environment.Direction.DOWN;
					break;
			}
		}else {
			switch(e.getKeyCode()){
				case  KeyEvent.VK_LEFT:
					lastPressedDirection=environment.Direction.LEFT;
					break;
				case  KeyEvent.VK_RIGHT:
					lastPressedDirection=environment.Direction.RIGHT;
					break;
				case KeyEvent.VK_UP:
					lastPressedDirection=environment.Direction.UP;
					break;
				case KeyEvent.VK_DOWN:
					lastPressedDirection=environment.Direction.DOWN;
					break;
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		//ignore
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Ignored...
	}

	public Direction getLastPressedDirection() {
		return lastPressedDirection;
	}

	public void clearLastPressedDirection() {
		lastPressedDirection=null;
	}

	public void refresh(Game game){
		this.game = game;
	}
}
