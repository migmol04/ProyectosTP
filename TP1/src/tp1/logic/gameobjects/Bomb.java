package tp1.logic.gameobjects;
import tp1.logic.Game;
import tp1.logic.Move;
import tp1.logic.Position;
import tp1.view.Messages;

public class Bomb extends EnemyWeapon {
	
	private static final int ARMOR = 1;
	private static final int DMG = 1;
	private DestroyerAlien owner;

	public Bomb(GameWorld game, Position pos, int life, DestroyerAlien owner) {
		super(game, pos, ARMOR);
		this.owner = owner;
		dir = Move.DOWN;
	}

	@Override
	public Position getPosition() {
		return pos;
	}

	@Override
	public String getSymbol() {
		return Messages.BOMB_SYMBOL;
	}

	@Override
	protected int getDamage() {
		return Bomb.DMG;
	}

	@Override
	protected int getArmour() {
		return Bomb.ARMOR;
	}

	@Override
	public void onDelete() {	
		this.life = 0;
		this.game.removeObject(this);
		
	}


	@Override
	public boolean onBorder() {
		boolean enBorde = false;
		if(pos.getY() >= Game.DIM_Y) {
			enBorde = true;
		}
		return enBorde;
	}

	@Override
	public void computerAction() {
		if(onBorder() || !isAlive()) {
			onDelete();
			owner.enableBomb();
		}
		
	}

	@Override
	public String status() {
		return Messages.BOMB_SYMBOL;
	}


}
