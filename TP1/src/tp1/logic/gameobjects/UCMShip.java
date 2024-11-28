package tp1.logic.gameobjects;

import java.util.Random;

import tp1.logic.Game;
import tp1.logic.Level;
import tp1.logic.Position;
import tp1.view.Messages;
import tp1.logic.Move;

public class UCMShip extends Ship {

	private static final int ARMOR = 3;
	private static final int DMG = 1;
	private boolean canShoot;
	private int points;
	private boolean hasShockWave;
	
    public UCMShip(GameWorld game, Position position) {
        super(game, position, ARMOR);  
        this.canShoot = false;
        this.hasShockWave = false;

    }

    @Override
    public void move(Move move) {
    	int newX = pos.getX() + move.getX();
	    int newY = pos.getY() + move.getY();
        pos = new Position(newX, newY);
    }

    @Override
	public Position getPosition() {
        return pos;
    }

    
    public void receivePoints(int points) {
    	this.points += points;
    }
    
    public static String infoToString() {
    	return Messages.ucmShipDescription(Messages.UCMSHIP_DESCRIPTION, UCMShip.DMG, UCMShip.ARMOR);
    }
	
	
	@Override
	public String getSymbol() {
		return Messages.UCMSHIP_SYMBOL;
		
	}

	@Override
	protected int getDamage() {
		return UCMShip.DMG;
	}
   

	@Override
	protected int getArmour() {
		return UCMShip.ARMOR;
	}

	public void enableLaser() {
        this.canShoot = !this.canShoot;
    }
	
	public boolean shootLaser() {
        boolean shooted = false;
        
        if (!this.canShoot) {
            enableLaser();
            shooted = true;
        }
        
        return shooted;
    }
	

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		
	}
	public void activateShockwave() {
		this.hasShockWave= true;
	}
	
	public void deactivateShockwave() {
		this.hasShockWave= false;
	}
	
	
	public boolean hasShockwave() {
		return hasShockWave;
	}
	
	
	@Override
	public boolean onBorder() {
		boolean enBorde = false;
		if(pos.getX() >= Game.DIM_X || pos.getX() < 0) {
			enBorde = true;
		}
		return enBorde;
	}

	@Override
	public void automaticMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void computerAction() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean receiveAttack(EnemyWeapon weapon) {
		boolean hasAttacked = false;
		if(this.isOnPosition(weapon.getPosition())) {
			recieveDamage(weapon.getDamage());
			hasAttacked = true; 
		}
		return hasAttacked;
	}
	
	public int getPlayerPoints() {
		return this.points;
	}

	public void restarPuntos() {
		
		this.points -= 5;

	}

	@Override
	public String status() {
		if(isAlive()) {
			return Messages.UCMSHIP_SYMBOL;
			}
			else
			return Messages.UCMSHIP_DEAD_SYMBOL;
	}

}
