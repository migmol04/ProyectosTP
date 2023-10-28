package tp1.logic;
import java.util.Scanner;
import java.util.Random;
import tp1.control.*;

import tp1.logic.gameobjects.*;
import tp1.logic.lists.*;

// TODO implementarlo
public class Game {

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	private Level level;
	private long seed;
	private UCMShip ucmShip;
	private UCMLaser laser;
	private RegularAlien alien;
	private AlienManager manager;
	private RegularAlienList list;
	private Game game;


	//TODO fill your code

	public Game(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		game = this;
		ucmShip = new UCMShip(this);
		this.laser = new UCMLaser(this, ucmShip.getPosition());
		this.laser.die();
		manager = new AlienManager(this, level);
		list = new RegularAlienList(5);
	    for (int i = 0; i < 5; i++) {
	    	 Position alienPosition = new Position(i + 2, 3);
	        alien = new RegularAlien(this, manager, alienPosition);
	        list.add(alien);}
}
	

	public String stateToString() {
		return "Hola";
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
	    Position pos = new Position(col, row);
	    if (ucmShip.isOnPosition(pos)) {
	        return ucmShip.getSymbol();
	    } else if (laser.isOnPosition(pos)) {
	        return laser.getSymbol();
	    } else if (alien.isOnPosition(pos)) {
	        return alien.getSymbol();
	    } else {
	    	for (int i = 0; i < list.size(); i++) {
	            if (list.getObjectInPosition(i).isOnPosition(pos)) {
	                return list.getObjectInPosition(i).getSymbol();
	            }
	        }
	    }
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
		ucmShip.enableLaser();		
	}
	

	public Random getRandom() {
		//TODO fill your code
		return null;
	}

	public Level getLevel() {
		return this.level;
	}
	
	 private void laserAutomaticMoves() {
	        laser.performMovement();
	    }
	    
	 public void update() {
		 if (laser.isAlive()) {
	            laserAutomaticMoves();
	        } else {
	            this.ucmShip.enableLaser();
	        }
	        
	        if(alien.isAlive()) {
	        	alien.automaticMove();
	        }
		 
		 
		    // Mover el láser
		 /*   if (laser.isAlive()) {
		        laser.performMovement();
		        // Comprobar colisiones con aliens
		        for (int i = 0; i < list.size(); i++) {
		            RegularAlien alien = list.getObjectInPosition(i);
		            if (alien != null && laser.getPosition().equals(alien.getPosition())) {
		                alien.receiveDamage();
		                laser.die();  // El láser desaparece después de golpear a un alien
		                break;
		            }
		        }
		    } else {
		        this.ucmShip.enableLaser();
		    }

		    // Mover los aliens
		    if(alien.isAlive()) {
		        list.automaticMove();
		    }

		    // Eliminar aliens muertos
		    list.removeDead();

		    // Comprobar condiciones de victoria/derrota
		    if (list.size() == 0) {
		        System.out.println("¡Has ganado el juego!");
		    } else if (ucmShip.isAlive() == false) {
		        System.out.println("Has perdido el juego.");
		    }
		    */
		}

	    
	    public boolean shootLaser() {
	        boolean shooted = false;
	        
	        if (ucmShip.shootLaser()) {
	            this.laser = new UCMLaser(this, ucmShip.getPosition());          // creates new laser
	            shooted = true;
	        }
	        
	        return shooted;
	    }
	    
	    public void debugInfo() {
	        System.out.println("Nave en: (" + this.ucmShip.getPosition().getX() + ","  + this.ucmShip.getPosition().getY() + ")");
	        if (this.laser.isAlive()) System.out.println("Laser en: (" + this.laser.getPosition().getX() + ","  + this.laser.getPosition().getY() + ")");
	    }
	    
	    public UCMShip getUCMShip() {
	    	return this.ucmShip;
	    }
   
}
