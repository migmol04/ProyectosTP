package tp1.logic.gameobjects;

import java.util.Random;

import tp1.logic.Game;
import tp1.logic.Level;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class UCMLaser extends UCMWeapon {
	
	private static final int ARMOR = 1;
	private static final int DMG = 1;
	
	public UCMLaser(GameWorld game, Position pos) {
		super(game, pos, ARMOR);
		dir = Move.UP;
	}

	@Override
	public Position getPosition() {
		return pos;
	}

	@Override
	public String getSymbol() {
		return Messages.LASER_SYMBOL;
	}

	@Override
	protected int getDamage() {
		return UCMLaser.DMG;
	}

	@Override
	protected int getArmour() {
		return UCMLaser.ARMOR;
	}

	@Override
	public void onDelete() {
		this.game.removeObject(this);
	    game.enableLaser();
		
	}
	
	@Override
	public boolean onBorder() {
		boolean enBorde = false;
		if(pos.getY() < 0 ) {
			enBorde = true;
		}
		return enBorde;
	}
	
	public void setPosition(Position position) {
		this.pos = position;
	}

	@Override
	public void computerAction() {
		if(!isAlive()|| onBorder()) {
			onDelete();
		}
		
	}
	
	public void setLife() {
		this.life=UCMLaser.ARMOR;
	}

	@Override
	public String status() {
		return Messages.LASER_SYMBOL;
	}
}
