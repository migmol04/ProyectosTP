package tp1.logic.gameobjects;

import java.util.Random;

import tp1.logic.AlienManager;
import tp1.logic.Game;
import tp1.logic.Level;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class DestroyerAlien extends AlienShip {
	
	
	private static final int ARMOR = 1;
	private static final int POINTS = 10;
	private static final int DMG = 1;
	private static final int BOMB_LIFE = 1;
	private boolean canShootBomb;
	private boolean bombReadyToFire;
	private Bomb bomb;

	public DestroyerAlien(GameWorld game, Position pos, AlienManager alienManager) {
		super(game, pos, ARMOR);
		this.manager = alienManager;
		this.canShootBomb = false;
		this.bombReadyToFire = true;
		
	}
	
	public DestroyerAlien() {}
	
	@Override
	protected AlienShip copy(GameWorld game, Position pos,
	AlienManager am) {
	return new DestroyerAlien(game, pos, am);
	}


	@Override
	 public String getSymbol() {          
			return Messages.DESTROYER_ALIEN_SYMBOL;
	}
	@Override
	public String status() {           
		return " " + Messages.DESTROYER_ALIEN_SYMBOL + "[" + "0" + getLife() + "]"    ;
	}

	@Override
	protected int getDamage() {
		return this.bomb.getDamage();
	}
	

	public static String infoToString() {
    	return Messages.alienDescription(Messages.DESTROYER_ALIEN_DESCRIPTION, DestroyerAlien.POINTS, DestroyerAlien.DMG, DestroyerAlien.ARMOR);
    }
	

	@Override
	protected int getArmour() {
		return DestroyerAlien.ARMOR;
	}
	
	@Override
	protected int getPoints() {
		return DestroyerAlien.POINTS;
	}
	
	private Position getBombPosition() {
		return new Position(getPosition().getX(), getPosition().getY() + 1);
	}
	
	


	@Override
	public Position getPosition() {
		return this.pos;
	}
	
	@Override
	public void computerAction() {
	    if (!isAlive()) {
	        onDelete();
	        this.manager.alienDead();
	    } else if (this.bombReadyToFire && canGenerateRandomBomb() && this.cyclesToMove != 1) {
	        bomb = new Bomb(game, getBombPosition(), DestroyerAlien.BOMB_LIFE, this);
	        this.game.addObject(bomb);
	        enableBomb();
	    }
	}
	public boolean canGenerateRandomBomb() {
	    if(game.getRandom().nextDouble() < game.getLevel().getShootFrequency()) {
	        this.canShootBomb = true;
	    } else {
	        this.canShootBomb = false;
	    }
	    return this.canShootBomb;
	}
	
	public void enableBomb() {
		this.bombReadyToFire = !this.bombReadyToFire;
	}
}