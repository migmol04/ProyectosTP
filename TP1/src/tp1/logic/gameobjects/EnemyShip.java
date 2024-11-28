package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

public abstract class EnemyShip extends Ship {
	protected Move dir;
	public EnemyShip(GameWorld game, Position pos, int life) {
        super(game, pos, life);
    }
	
	public EnemyShip() {
        
    }
	
	protected abstract int getPoints();
	public abstract void onDelete();
	
	  @Override
		public boolean receiveAttack(UCMWeapon weapon) {
			boolean hasAttacked = false;
			if(this.isOnPosition(weapon.getPosition())) {
				recieveDamage(weapon.getDamage());
				hasAttacked = true; 
			}
			return hasAttacked;
		}
	
}
