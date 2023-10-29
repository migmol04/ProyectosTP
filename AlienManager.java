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
	
	private boolean squadInFinalRow = false;
	private int shipsOnBorder = 0;
	private boolean onBorder = false;
	private RegularAlienList list;
	
	public AlienManager(Game game, Level level, RegularAlienList list) {
		this.level = level;
		this.game = game;
		this.remainingAliens = 5;
		this.list = list;
	}
		
	// INITIALIZER METHODS
	
	/**
	 * Initializes the list of regular aliens
	 * @return the initial list of regular aliens according to the current level
	 */
	//protected RegularAlienList initializeRegularAliens() {
		//list = new RegularAlienList(level.getNumRegularAliens());
	    //for (int i = 0; i < list.size(); i++) {
	    	//Position alienPosition = new Position(i + 2, 3);
	        //RegularAlien alien = new RegularAlien(game, this, alienPosition);
	        //list.add(alien);}
		//return list;
	//}


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
			if(onBorder) {
				decreaseOnBorder();
				desc = true;
				onBorder = false;
			}
			else
			{
				onBorder = !onBorder;
			}
		}
		return desc;
	}
	
	
	public void decreaseOnBorder() {
		for(int i = 0; i<list.size(); i++) {
			 RegularAlien alien = list.getObjectInPosition(i);
			 alien.descent();
			
		}
		onBorder = true;
	}
	
	// CONTROL METHODS
		
	public boolean shipOnBorder() {
		boolean esBor = false;
	    for (int i = 0; i < list.size() && !esBor; i++) {
	    	 RegularAlien alien = list.getObjectInPosition(i);
	    	 Position position = alien.getPosition();
	        if (position.getX() == 0 || position.getX() == Game.DIM_X-1) {
	           esBor = true;
	        }
	    }
	    return esBor;
	}

	public boolean isInBorder() {
		boolean esBorde = false;
		if(shipOnBorder()) {
			esBorde = true;
			onBorder = true;
		}
		return esBorde;
	}

}
