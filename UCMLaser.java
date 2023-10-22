package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

/**
 
Class that represents the laser fired by UCMShip.*/
public class UCMLaser {

    public static final int ARMOR = 10;
    public static final int DMG = 10;

    private Move dir;
    private Game game;
    public Position pos;
    private int life;
    private UCMLaser laser;

    public UCMLaser(Game game, Position pos) {
        this.game = game;
        this.pos = pos;
        this.life = 1;
        this.dir = Move.UP;
    }
    

    public void onDelete() {
        game.enableLaser();
    }

    public void automaticMove() {
        pos = new Position(pos.getX(), pos.getY() + dir.getY());
    }

    // PERFORM ATTACK METHODS
    

    public boolean isAlive() {
        boolean alive = true;
        
        if (this.life == 0) {
           alive = false; 
        }
        
        return alive;
    }
    
	
	public int getLife() {
		return this.life;
	}
	
    public boolean isOnPosition(Position p) {
		
		return (this.pos.getX() == p.getX()) && (this.pos.getY() == p.getY())&& (isAlive());
		
	}
	
	public Position getPosition() {
		 return this.pos;
	}
	

	public void die() {
        this.life = 0;
    }

    public boolean isOut() {
        return pos.getY() < 0 || pos.getY() >= Game.DIM_Y;
    }

    public void performMovement() {
        automaticMove();
        
        if (isOut()) {
            die();
        }
    }
    
    
    public String getSymbol() {
    	return Messages.LASER_SYMBOL;
    }
    
	
	
    /*public boolean performAttack(RegularAlien other) {
        //no sé
    }


    public boolean performAttack(DestroyerAlien other) {
        // Implementa la lógica para el ataque del láser a un alien destructor
        //no sé

        return false;
    }

*/
    // ACTUAL ATTACK METHODS

  /*  private boolean weaponAttack(RegularAlien other) {
        //no sé

        return other.receiveAttack(this);
    }
*/
    // RECEIVE ATTACK METHODS

/*
    public boolean receiveAttack(Bomb weapon) {
        // Implementa la lógica para el efecto de un ataque de bomba en el láser receiveDamage(weapon.getDamage());
        //no sé

        return true;
    }

*/
}