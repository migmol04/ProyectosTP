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

	//TODO fill your code

	public Game(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		end = false;
		
		ucmShip = new UCMShip(this);
	}
	

	public String stateToString() {
		
		return "";
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
