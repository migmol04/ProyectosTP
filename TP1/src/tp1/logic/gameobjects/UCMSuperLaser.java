package tp1.logic.gameobjects;

import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class UCMSuperLaser extends UCMLaser{

	
	private static final int DMG = 2;

	public UCMSuperLaser(GameWorld game, Position pos) {
		super(game, pos);		
	}
	
	@Override
	public String getSymbol() {
		return Messages.SUPERLASER_SYMBOL;
	}

	@Override
	protected int getDamage() {
		return UCMSuperLaser.DMG;
	}
	
	
	@Override
	public String status() {
		return Messages.SUPERLASER_SYMBOL;
	}
}
