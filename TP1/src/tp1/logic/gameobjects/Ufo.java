package tp1.logic.gameobjects;

import java.util.Random;

import tp1.logic.Level;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class Ufo extends EnemyShip {
	
	private static final int ARMOR = 1;
	private static final int POINTS = 25;
	private static final int DMG = 1;
	private boolean enabled;
	
	public Ufo(GameWorld game, Position pos, int life) {
        super(game, pos, life);
        this.enabled = false;
        dir = Move.LEFT;
    }

	@Override
	public Position getPosition() {
		return this.pos;
	}

	@Override
	public String getSymbol() {
	   return Messages.UFO_SYMBOL;
		
	}

	@Override
	protected int getDamage() {
		return Ufo.DMG;
	}

	@Override
	protected int getArmour() {
		return Ufo.ARMOR;
	}

	@Override
	public void onDelete() {
		this.enabled = false;
	}
	@Override
	public void automaticMove() {
		if(canGenerateRandomUfo() && !this.enabled) {
			enable();
		}
		if (this.enabled) {
		performMovement(Move.LEFT);
		}
	}
		

	@Override
	protected int getPoints() {
		return Ufo.POINTS;
	}
	
	public void enable() {
	  this.enabled = true;
	  this.life= getArmour();
	  pos = new Position(9, 0);
	}
	
	@Override
	public void computerAction() {
		if(onBorder()|| !isAlive()) {
			if (!isAlive() && this.enabled) {
				this.game.receivePoints(getPoints());
				this.game.activateShockwave();
				
				
			
		}
			onDelete();
			
		}
		
	}
	
	public static String infoToString() {
    	return Messages.alienDescription(Messages.UFO_DESCRIPTION,Ufo.POINTS, Ufo.DMG, Ufo.ARMOR);
    }
		

	@Override
	public boolean onBorder() {
		return pos.getX() < 0;
	}
	
	private boolean canGenerateRandomUfo() {
		boolean generated = false;
		if(game.getRandom().nextDouble() < game.getLevel().getUfoFrequency()) {
			generated = true;
		}
		return generated;
	}

	@Override
	public String status() {
		String sb = "";
		if(isAlive())
		sb =  Messages.UFO_SYMBOL + "[" + "0" + getLife() + "]" ;
		return sb;
	}
}
