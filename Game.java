package tp1.logic;
import java.util.Scanner;

import java.util.Random;
import tp1.control.*;

import tp1.logic.gameobjects.*;
import tp1.logic.lists.*;


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
	private DestroyerAlienList Dlist;
    private Ufo ufo;

	//TODO fill your code

	public Game(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		ucmShip = new UCMShip(this);
		this.laser = new UCMLaser(this, ucmShip.getPosition());
		this.laser.die();
		this.ufo = new Ufo(this);
	    manager = new AlienManager(this, level);
	    list = manager.initializeRegularAliens();
	    Dlist = manager.initializeDestroyerAliens();
}
	

	public String stateToString() {
		return "Hola";
	}


	public int getCycle() {
		return getLevel().getSpeed();
	}

	public int getRemainingAliens() {
		return manager.getRemainingAliens();
	}

	public String positionToString(int col, int row) {
		
	    Position pos = new Position(col, row);
	    if (ucmShip.isOnPosition(pos)) {
	        return ucmShip.getSymbol();
	    } else if (laser.isOnPosition(pos)) {
	        return laser.getSymbol();
	    } 

	    else if(ufo.isOnPosition(pos)) {
	    	return ufo.getSymbol();
	    }
	    else  {
	    	for(int i = 0; i < this.list.size(); i++) {
	    		if(this.list.getObjectInPosition(i).isOnPosition(pos)) {
	    			return this.list.getObjectInPosition(i).getSymbol();
	    		}
	    		
	    	for(int j = 0; j < this.Dlist.size(); j++) {
	    		if(this.Dlist.getObjectInPosition(j).isOnPosition(pos)) {
	    			return this.Dlist.getObjectInPosition(j).getSymbol();
	    		}
	    	}
	    	}
	   }
	 
	    return "";
	}


	public boolean playerWin() {
		boolean win = false;
       if(list.size() == 0) {
    	   win = true;
		}
		return win;
	}

	public boolean aliensWin() {
		boolean win = false;
		if(ucmShip.getLife() == 0) {
			win = true;
			
		}
		
		return win;
	}

	public void enableLaser() {
		ucmShip.enableLaser();		
	}
	

	public Random getRandom() {
		Random r1 = new Random();
		return r1;
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
	        
	       manager.automaticMoves();
	       ufo.computerAction();
	       
		 
		 
		    // Mover el láer
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
	    	RegularAlien alien = list.getObjectInPosition(0);
	    	 System.out.println(alien.getPosition().getX() + " " + alien.getPosition().getY());
	    }
	    
	    public UCMShip getUCMShip() {
	    	return this.ucmShip;
	    }
   
}
