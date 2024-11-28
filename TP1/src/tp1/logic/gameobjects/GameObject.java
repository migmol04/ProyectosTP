package tp1.logic.gameobjects;

import java.util.Random;

import tp1.logic.Game;
import tp1.logic.Level;
import tp1.logic.Move;
import tp1.logic.Position;

public abstract class GameObject implements GameItem, GameWorld {

	protected Position pos;
	protected int life;
	protected GameWorld game;
	
	public GameObject(GameWorld game, Position pos, int life) {	
		this.pos = pos;
		this.game = game;
		this.life = life;
	}
	
	public GameObject() {}
	
	
	@Override
	public boolean isAlive() {
		return this.life > 0;
	}

	public int getLife() {
		return this.life;
	}
	
	//TODO fill with your code
	
    public abstract Position getPosition();
	public abstract String getSymbol(); 
	protected abstract int getDamage();
	protected abstract int getArmour();
	public abstract String status();
	
			
	public abstract void onDelete();
	public abstract void automaticMove();
	public abstract  void computerAction();
	
	@Override
	public boolean isOnPosition(Position pos) {
		return (this.pos.getX() == pos.getX()) && (this.pos.getY() == pos.getY());
		
	};
	
	@Override
	public boolean receiveAttack(EnemyWeapon weapon) {
		return false;
	}
	@Override
	public boolean receiveAttack(UCMWeapon weapon) {
		return false;
	}
	@Override
	public boolean performAttack(GameItem other) {
		return false;
	}
	
	public void recieveDamage(int dmg) {
		this.life -= dmg;
	}
	public abstract boolean onBorder();
	
	protected void performMovement(Move dir) {
        pos = new Position(pos.getX() + dir.getX(), pos.getY() + dir.getY());
	}

	public int getSpeed() {
		return 0;
	};
	
	 public void receivePoints(int points) {
			
		}
	public void getShockWaveDamage() {};
	public void addObject(GameObject object) {};
    public void removeObject(GameObject object) {}; 
	public Random getRandom() {
		return null;
	};
	public Level getLevel() {
		return null;
	};
	public void activateShockwave() {};
	public void enableLaser() {};
	public void triggerExplosionDamage(ExplosiveAlien explosiveAlien) {};
	

}
