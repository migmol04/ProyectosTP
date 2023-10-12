package tp1.logic;

import java.util.Random;
import tp1.logic.gameobjects.*;

// TODO implementarlo
public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	private Level level;
	private long seed;
	private boolean end;
	private UCMShip ucmShip;
	
	private Game game;

	//TODO fill your code

	public Game(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		game = this;
		
		ucmShip = new UCMShip(game);
	}
	

	public String stateToString() {
		int dimX = Game.DIM_X;
		int dimY = Game.DIM_Y;
		String[][] board = new String[dimX][dimY];

		// Inicializa el tablero con caracteres vacíos
		for (int x = 0; x < dimX; x++) {
		    for (int y = 0; y < dimY; y++) {
		        board[x][y] = " ";
		    }
		}

		// Coloca el símbolo de la nave en la posición (4, 7)
		board[4][7] = ucmShip.getSymbol();

		// Construye la representación del tablero como una cadena
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < dimY; y++) {
		    for (int x = 0; x < dimX; x++) {
		        sb.append(board[x][y]);
		    }
		    sb.append('\n');
		}

		return sb.toString();
	}


	public int getCycle() {
		//TODO fill your code
		return 0;
	}

	public int getRemainingAliens() {
		//TODO fill your code
		return 0;
	}

	public String positionToString(int col, int row) {
		//TODO fill your code
		return "";
	}

	public boolean playerWin() {
		//TODO fill your code
		return false;
	}

	public boolean aliensWin() {
		//TODO fill your code
		return false;
	}

	public void enableLaser() {
		//TODO fill your code		
	}

	public Random getRandom() {
		//TODO fill your code
		return null;
	}

	public Level getLevel() {
		//TODO fill your code
		return null;
	}
	
	public UCMShip getUCMShip() {
        return ucmShip;
    }
	
	public void setUCMShip(UCMShip ucmShip) {
        this.ucmShip = ucmShip;
    }


}
