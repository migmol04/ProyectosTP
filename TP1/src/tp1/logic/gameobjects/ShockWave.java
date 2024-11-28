package tp1.logic.gameobjects;

import java.util.Random;

import tp1.logic.Level;
import tp1.logic.Position;
import tp1.view.Messages;

public class ShockWave extends UCMWeapon {
	
	private static final int DMG = 1;
	public ShockWave(GameWorld game, Position pos, int life) {
		super(game, pos, life);
	}

	@Override
	public Position getPosition() {
		return pos;
	}

	@Override
	public String getSymbol() {
		return Messages.SHOCKWAVE_SYMBOL;
	}

	@Override
	protected int getDamage() {
		return ShockWave.DMG;
	}

	@Override
	protected int getArmour() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean weaponAttack(GameItem other) {
		boolean wAttack = true;
	    this.getShockWaveDamage();
		return wAttack;
		
	}
	
	

	@Override
	public boolean onBorder() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void computerAction() {
		// TODO Auto-generated method stub
	}

	@Override
	public String status() {
		return Messages.SHOCKWAVE_SYMBOL;
	}
}
