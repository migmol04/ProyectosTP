package tp1.logic.gameobjects;

import java.util.Random;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Level;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class RegularAlien extends AlienShip {
	
	private static final int ARMOR = 2;
	private static final int POINTS = 5;

	public RegularAlien(GameWorld game, Position pos, AlienManager alienManager) {
		super(game, pos, ARMOR);
		this.manager = alienManager;
	}
	
	public RegularAlien() {
	}
	
	 @Override
	 protected AlienShip copy(GameWorld game, Position pos,
	 AlienManager am) {
	 return new RegularAlien(game, pos, am);
	 }

	 @Override
	 public String getSymbol() {          
			return Messages.REGULAR_ALIEN_SYMBOL;
	}
	 
	@Override
	public String status() {          
		return " " + Messages.REGULAR_ALIEN_SYMBOL+ "[" + "0" + getLife() + "]" ;
	}
	
	public static String infoToString() {
    	return Messages.alienDescription(Messages.REGULAR_ALIEN_DESCRIPTION, RegularAlien.POINTS, 0, RegularAlien.ARMOR);
    }

	@Override
	protected int getDamage() {
		return 0;
	}

	@Override
	protected int getArmour() {
		return RegularAlien.ARMOR;
	}
	

	@Override
	public Position getPosition() {
		return this.pos;
	}
	
	@Override
	protected int getPoints() {
		return RegularAlien.POINTS;
	}


	@Override
	public void computerAction() {
		if(!this.isAlive()) {
			onDelete();
			 this.manager.alienDead();
		}
		
	}
	

    public static String getInfoToString() {
    	return Messages.REGULAR_ALIEN_DESCRIPTION;
    }

	
}
