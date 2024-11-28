package tp1.logic.gameobjects;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExplosiveAlien extends RegularAlien{

	private static final int ARMOR = 2;
	private static final int POINTS = 12;
	private static final int DMG = 1;

	public ExplosiveAlien(GameWorld game, Position pos, AlienManager alienManager) {
		super(game, pos, alienManager);
		this.manager = alienManager;
	}
	
	public ExplosiveAlien() {}
	
	@Override
	 protected AlienShip copy(GameWorld game, Position pos,
	 AlienManager am) {
	 return new ExplosiveAlien(game, pos, am);
	 }

	@Override
	 public String getSymbol() {          
			return Messages.EXPLOSIVE_ALIEN_SYMBOL;
	}
	@Override
	public String status() {          
		return " " + Messages.EXPLOSIVE_ALIEN_SYMBOL+ "[" + "0" + getLife() + "]" ;
	}
	public int getDamage() {          
		return DMG;
	}
	public static String infoToString() {
    	return Messages.alienDescription(Messages.EXPLOSIVE_ALIEN_SYMBOL, ExplosiveAlien.POINTS, 0, ExplosiveAlien.ARMOR);
    }
	
	public static String getInfoToString() {
    	return Messages.EXPLOSIVE_ALIEN_DESCRIPTION;
    }

	@Override
	public boolean receiveAttack(UCMWeapon weapon) {
		boolean hasAttacked = false;
		if(this.isOnPosition(weapon.getPosition())) {
			recieveDamage(weapon.getDamage());
			if(!this.isAlive()) game.triggerExplosionDamage(this);

			hasAttacked = true; 
		}
		return hasAttacked;
	}	 
	 
	
}