package tp1.logic.gameobjects;


import tp1.logic.Position;

public abstract class UCMWeapon extends Weapon {

	public UCMWeapon(GameWorld game, Position pos, int life) {
		super(game, pos, life);
		
	}
	
	@Override
	public boolean receiveAttack(UCMWeapon weapon) {
		boolean hasAttacked = false;
		if(this.isOnPosition(weapon.getPosition())) {
			hasAttacked = true; 
		}
		return hasAttacked;
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
		Position posSiguiente = new Position (weapon.getPosition().getX(),weapon.getPosition().getY()-1);
		if(this.isOnPosition(weapon.getPosition())||this.isOnPosition(posSiguiente)) {
			this.recieveDamage(this.getDamage());
			hasAttacked = true; 
		}
		return hasAttacked;
	}
}

