package tp1.logic;

//import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.lists.DestroyerAlienList;
//import tp1.logic.lists.DestroyerAlienList;
import tp1.logic.lists.RegularAlienList;
import tp1.logic.gameobjects.*;

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
	private DestroyerAlienList Destroyerlist;
	
	public AlienManager(Game game, Level level) {
		this.level = level;
		this.game = game;
	}
		
	// INITIALIZER METHODS
	
	/**
	 * Initializes the list of regular aliens
	 * @return the initial list of regular aliens according to the current level
	 */
	protected RegularAlienList initializeRegularAliens() {
		this.remainingAliens = this.level.getNumRegularAliens() + this.level.getNumDestroyerAliens();
		this.list = new RegularAlienList(this.level.getNumRegularAliens());
		
		if(this.level != Level.EASY) {
			for(int j = 0; j < list.size(); j++) {
				Position pos2 = new Position(j + 2, 1);
				RegularAlien alien2= new RegularAlien(game, this, pos2);
				this.list.add(alien2, j);
				if(j > 3) {
					Position pos3 = new Position(j - 2, 2);
					RegularAlien alien3= new RegularAlien(game, this, pos3);
					this.list.add(alien3, j);
				}
			}
		}
		
		else {
			for(int i = 0; i < list.size(); i++) {
				Position pos = new Position(i + 2 , 1);
				RegularAlien alien = new RegularAlien(game, this, pos);
				this.list.add(alien, i);
			}
		}
		return this.list;
	}


	/**
	 * Initializes the list of destroyer aliens
	 * @return the initial list of destroyer aliens according to the current level
	 */
	protected  DestroyerAlienList initializeDestroyerAliens() {
		this.Destroyerlist = new DestroyerAlienList(this.level.getNumDestroyerAliens());
		if(this.level == Level.EASY) {
			for(int i = 0; i < Destroyerlist.size(); i++) {
				Position pos = new Position(i + 3, 2);
				DestroyerAlien Dalien = new DestroyerAlien(game, this, pos);
				this.Destroyerlist.add(Dalien, i);
				
			}
			
		}
		
		else if(this.level == Level.HARD) {
			for(int i = 0; i < Destroyerlist.size(); i++) {
				Position pos = new Position(i + 3, 3);
				DestroyerAlien Dalien = new DestroyerAlien(game, this, pos);
				this.Destroyerlist.add(Dalien, i);	
			}
		}
		
		else if(this.level == Level.INSANE) {
			for(int i = 0; i < Destroyerlist.size(); i++) {
				Position pos = new Position(i + 2, 3);
				DestroyerAlien Dalien = new DestroyerAlien(game, this, pos);
				this.Destroyerlist.add(Dalien, i);	
			}
		}
		
		return this.Destroyerlist;
	}

	
	public int getRemainingAliens() {
	    return remainingAliens;
	}
	
	public boolean allAlienDead() {
	    return remainingAliens == 0;
	}
	
	public void alienDead() {
	        remainingAliens--;
	}
	
	public void automaticMoves() {
		this.list.automaticMoves();
		this.Destroyerlist.automaticMoves();
	}
  
	
	public boolean finalRowReached() {
		squadInFinalRow =false;
	        if (list.isInFinalRow() || Destroyerlist.isInFinalRow()) {
	        	squadInFinalRow = true;
	    }
	    return squadInFinalRow;
	}

	
	
	public void decreaseOnBorder() {
		
		shipOnBorder();
	    
	}

	public boolean readyToDescend() {
	 return (this.list.readyToDescent()) || (this.Destroyerlist.readyToDescent());
	}

	public boolean haveLanded() {
		boolean aterrizaje = false;
		if(finalRowReached()) {
			aterrizaje = true;
		}
	    return aterrizaje;
	}

	
	// CONTROL METHODS
		
	public void shipOnBorder() {
		if(!onBorder) {
			onBorder = true;
			shipsOnBorder = remainingAliens;
		}
	}

	public boolean onBorder() {
		return list.isInBorder();
	}

}
