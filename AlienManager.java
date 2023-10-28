package tp1.logic;

import tp1.logic.gameobjects.RegularAlien;

//import tp1.logic.gameobjects.DestroyerAlien;

//import tp1.logic.lists.DestroyerAlienList;
import tp1.logic.lists.RegularAlienList;

/**
 * 
 * Manages alien initialization and
 * used by aliens to coordinate movement
 *
 */
public class AlienManager {
	
	private Level level;
	private Game game;
	private int remainingAliens;
	
	private boolean squadInFinalRow;
	private int shipsOnBorder;
	private boolean onBorder;
	private RegularAlienList list;

	public AlienManager(Game game, Level level) {
		this.level = level;
		this.game = game;
		this.remainingAliens = 5;
		list = new RegularAlienList(5);
	}
		
	// INITIALIZER METHODS
	
	/**
	 * Initializes the list of regular aliens
	 * @return the initial list of regular aliens according to the current level
	 */
	protected RegularAlienList initializeRegularAliens() {
		//TODO fill your code
		return null;
	}

	/**
	 * Initializes the list of destroyer aliens
	 * @return the initial list of destroyer aliens according to the current level
	 */
//	protected  DestroyerAlienList initializeDestroyerAliens() {
//   for (int i = 0; i < level.getDestroyerAlienCount(); i++) {
//       DestroyerAlien destroyerAlien = new DestroyerAlien(/* pass the appropriate arguments */);
//       destroyerAlienList.add(destroyerAlien);
//	}
	
	public int getRemainingAliens() {
		return this.remainingAliens;
	}
	
	public boolean allAlienDead() {
		return getRemainingAliens() == 0;
	}
	
	public boolean alienDead() {
		boolean dead = false;
		for (int i = 0; i < list.size(); i++) {
			 RegularAlien alien = list.getObjectInPosition(i);
			 if(!alien.isAlive()) {
				 this.remainingAliens--;
				 dead = true;
			 }
		}
		return dead;
	}
	
	public boolean haveLanded() {
		return true;
	}
	
	public boolean finalRowReached() {
		boolean finalRow = false;
		for(int i = 0; i < list.size(); i++) {
			 RegularAlien alien = list.getObjectInPosition(i);
			 if(alien.isinFinalRow()) {
				 finalRow = true;
			 }	
		}
		return finalRow;
	}
	
	public boolean readyToDescent() {
		boolean desc = false;
		if(isInBorder()) {
			decreaseOnBorder();
			desc = true;
		}
		return desc;
	}
	
	public void decreaseOnBorder() {
		for(int i = 0; i<list.size(); i++) {
			 RegularAlien alien = list.getObjectInPosition(i);
			 alien.descent();
		}
	}
	
	
	// CONTROL METHODS
		
	public boolean shipOnBorder() {
		boolean esBor = false;
	    for (int i = 0; i < list.size(); i++) {
	    	 RegularAlien alien = list.getObjectInPosition(i);
	    	 Position position = alien.getPosition();

	        if (position.getX() == 0 || position.getX() == game.DIM_X - 1) {
	           esBor = true;;
	        }
	    }

	    return esBor;
		
	}

	public boolean isInBorder() {
		boolean esBorde = false;
		if(shipOnBorder()) {
			esBorde = true;
		}
		return esBorde;
	}

}
