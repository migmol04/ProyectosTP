package tp1.logic.gameobjects;

import tp1.logic.Position;

public abstract class EnemyWeapon extends Weapon {

	public EnemyWeapon(GameWorld game, Position pos, int life) {
		super(game, pos, life);
	}
	
	@Override
	public boolean weaponAttack(GameItem other) {
		boolean wAttack = false;
		if(other.receiveAttack(this)) {
			this.recieveDamage(this.getDamage());
			wAttack = true;
		}
		return wAttack;
		
	}
	
	@Override
	public boolean receiveAttack(EnemyWeapon weapon) {
		boolean hasAttacked = false;
		if(this.isOnPosition(weapon.getPosition())) {
			hasAttacked = true; 
		}
		return hasAttacked;
	}
	
		
}
