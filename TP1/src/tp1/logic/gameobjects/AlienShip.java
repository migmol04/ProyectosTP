package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

public abstract class AlienShip extends EnemyShip {
	protected  int cyclesToMove;
	protected  int speed;
	protected AlienManager manager;
	private static final int SHOCKWAVE_DMG = 1;
	
	
	public AlienShip(GameWorld game, Position pos, int life) {
        super(game, pos, life);
        speed = this.game.getSpeed();
        cyclesToMove = speed;
    	dir = Move.LEFT;
  
    }
	
	public AlienShip() {
  
  
    }    

	
	@Override
	public void automaticMove() {
		cyclesToMove--;
	
	        if (this.manager.getAliensEnBorde() > 0 && this.manager.getOnBorder()) {
	        	descent();
	            this.manager.DecreaseOnBorder();
	            dir = dir.changeDir(dir);
	            cyclesToMove = speed;
	        	haveLanded();
	            
	           
	        } else if (cyclesToMove == 0){
	            this.manager.setAlienEnBorde();
	            this.manager.setOnBorderFalse();
	            performMovement(dir);
	            cyclesToMove = speed; 
	        }
	    
	}
	
	protected abstract AlienShip copy(GameWorld game, Position pos, AlienManager am);
	
	
	protected void haveLanded() {
		if(isInFnalRow()) {
			this.manager.setHaveLanded();

		}
	}

	protected void readyToDescent() {
		if(onBorder()) {
			 this.manager.setOnBorder();
		}
	}

	protected boolean isInBorder() {
        return pos.getX() == 0 || pos.getX() == Game.DIM_X - 1;
    }
	
	
	protected boolean isInFnalRow() {
	  return pos.getY() >= Game.DIM_Y -1; 
	}

	public void descent() {
		performMovement(Move.DOWN);
	}
	
	@Override
	public void onDelete() {
		this.game.receivePoints(getPoints());
		 this.game.removeObject(this);	
	}
	
	@Override
	public void getShockWaveDamage() {
		this.life -= AlienShip.SHOCKWAVE_DMG;
	}
	 @Override
	  public boolean onBorder() {
	      boolean onBorder = false;
	      if(isInBorder()) {
	    	 this.manager.setOnBorder();
	        onBorder = true;
	       }
	       return onBorder;
	   }
}
