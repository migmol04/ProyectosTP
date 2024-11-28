package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;

public abstract class Weapon extends GameObject {
	
	
	private static final int DMG = 1;
	protected Move dir;

	public Weapon(GameWorld game, Position pos, int life) {
		super(game, pos, life);
	}
	
	@Override
	public void automaticMove() {
		 pos = new Position(pos.getX() + dir.getX(), pos.getY() + dir.getY());
	}
	
	protected abstract boolean weaponAttack(GameItem other); 
	
	@Override
	public boolean performAttack(GameItem other) {
	boolean attack = false;

	if(isAlive() && weaponAttack(other)) {
		attack = true;
	}
	return attack;
	}
	
}
	


