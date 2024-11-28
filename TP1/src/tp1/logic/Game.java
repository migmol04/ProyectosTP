package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.control.commands.InitializationException;
import tp1.control.commands.LaserInFlightException;
import tp1.control.commands.NoShockWaveException;
import tp1.control.commands.NotAllowedMoveException;
import tp1.control.commands.NotEnoughPointsException;
import tp1.control.commands.OffWorldException;
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.ExplosiveAlien;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameWorld;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.UCMLaser;
import tp1.logic.gameobjects.UCMShip;
import tp1.logic.gameobjects.UCMSuperLaser;
import tp1.logic.gameobjects.Ufo;
import tp1.view.Messages;

import java.util.Random;

public class Game implements GameStatus, GameModel, GameWorld {

	//TODO fill with your code

	public static final int DIM_X = 9;
	public static final int DIM_Y = 8;
	
	private GameObjectContainer container;
	private UCMShip player;
	private AlienManager alienManager;
	private Level level;
	private int currentCycle;
	private Random rand;
	private UCMLaser laser;
	private UCMSuperLaser superLaser;
	private long seed;
	private boolean exit;
    private InitialConfiguration configuration;

	//TODO fill with your code

	public Game (Level level, long seed) throws InitializationException{
		this.level = level;
		this.seed = seed;
		initGame();
	}
		
	private void initGame () throws InitializationException {
		this.exit = false;
		this.currentCycle = 0;
		this.rand = new Random(seed);
		this.alienManager = new AlienManager(this, this.level);
	    this.container = new GameObjectContainer();
		this.container = alienManager.initialize(configuration);
		this.player = new UCMShip(this, new Position(DIM_X / 2, DIM_Y - 1));
		laser = new UCMLaser(this,player.getPosition());
		superLaser = new UCMSuperLaser(this,player.getPosition());
		container.add(player);
		
	}

	public void movePlayer(Move move) throws NotAllowedMoveException, OffWorldException {
	    if (canMove(move)) {
	        player.move(move);
	    } else {
	        throw new NotAllowedMoveException(Messages.DIRECTION_ERROR);
	    }
	}

    public boolean canMove(Move move) throws OffWorldException {
    	boolean posValida = false;
    	int newX = player.getPosition().getX() + move.getX();
	    int newY = player.getPosition().getY() + move.getY();
        Position newpos = new Position(newX, newY);
        if(newpos.getX() < 0 || newpos.getX() >= DIM_X ) {
        	String mensaje = Messages.MOVEMENT_ERROR + "\n" + "Cannot move in direction " + move +  " from position: "  + "( " + 
        	 player.getPosition().getX() + "," + player.getPosition().getY()  + ")";
        	throw new OffWorldException(mensaje);
        	
        }
        return newpos.getX() >= 0 && newpos.getX() < DIM_X;
        
    }
	
	//CONTROL METHODS
	
	public boolean isFinished() {
		boolean finished = false;
	if(aliensWin() || this.exit || playerWin()) {
		finished = true;
	}
		return finished;
	}

	public void exit() {
	  this.exit = true;
	}
	
	public void reset(InitialConfiguration selectedConfiguration) throws InitializationException {
		this.configuration=selectedConfiguration;
		initGame();
	}
	
	public void shockwave() throws NoShockWaveException {
		boolean w = false;
		if (this.player.hasShockwave()) {
			container.shockwaveAttack();
			this.player.deactivateShockwave();
			w = true;
		}
		else if(!this.player.hasShockwave()) {
			String mensaje = Messages.SHOCKWAVE_ERROR + "\n" + "ShockWave is not enabled";
			throw new NoShockWaveException(mensaje);
		}
	}

	public void update() {
	    this.currentCycle++;
	    this.container.onBorder();
	    this.container.automaticMoves();
	    this.container.checkAttack();
	    this.container.computerActions();
	}
	
	public void enableLaser() {
		this.player.enableLaser();
	}
	
	public void shootLaser() throws LaserInFlightException {
		boolean disparado = false;
		if(this.player.shootLaser()) {
			this.laser.setPosition(this.player.getPosition());
			this.laser.setLife();
			container.add(laser);
			disparado = true;
		}
		
		else if(!this.player.shootLaser()) {
			String mensaje = Messages.LASER_ERROR + "\n" +"Laser already in flight";
			throw new LaserInFlightException(mensaje);
			
		}
	}

	public void shootSuperLaser() throws NotEnoughPointsException, LaserInFlightException {
		if(this.player.getPlayerPoints() >= 5 && this.player.shootLaser() ) {
			
			this.superLaser.setPosition(this.player.getPosition());
			this.superLaser.setLife();
			
			container.add(superLaser);
			
			this.player.restarPuntos();
		}
		
		else if(this.player.getPlayerPoints() < 5) {
			String mensaje = Messages.SUPERLASER_ERROR + "\n" +  "Not enough points: only " + this.player.getPlayerPoints() + " points, 5 points required.";

			throw new NotEnoughPointsException(mensaje);
		}
		
		else if(!this.player.shootLaser()) {
			String mensaje = Messages.SUPERLASER_ERROR + "\n" +"Laser already in flight";
			throw new LaserInFlightException(mensaje);
			
		}
		
	}
	
	//CALLBACK METHODS
	
	@Override
	public void addObject(GameObject object) {
	    this.container.add(object);
	}
	
	@Override
	public void removeObject(GameObject object) {
	    this.container.remove(object);
	}


	//VIEW METHODS
	
	public String positionToString(int col, int row) {
		return container.toString(new Position(col, row));
	}
	
	

	public  String infoToString() {
	    String list = new String (UCMShip.infoToString() + System.lineSeparator() +
	                  DestroyerAlien.infoToString() + System.lineSeparator() +
	                  RegularAlien.infoToString()+ System.lineSeparator() +
	                  Ufo.infoToString());
	    return list;
	}

	@Override
	public String stateToString() {
		int life = this.player.getLife();
		int points = this.player.getPlayerPoints();
		String shockwave = "OFF";
		if (this.player.hasShockwave())shockwave = "ON";
		return String.format("Life: %d%nPoints: %d%nShockwave: %s ", life, points, shockwave);
	}
	
	public void activateShockwave() {
		this.player.activateShockwave();
	}
	
	 public void receivePoints(int points) {
			this.player.receivePoints(points);
		}

	@Override
	public boolean playerWin() {
		boolean win = false;
		if(this.alienManager.allAlienDead()) {
			win = true;
		}
		return win;
	}
	
	
	 @Override
	public Random getRandom() {
		 return this.rand;
	}

	@Override
	public boolean aliensWin() {
		boolean aliensWin = false;
		if(this.alienManager.getSquadInFinalRow() || !this.player.isAlive()) {
			aliensWin = true;
		}
		return aliensWin;
	}

	@Override
	public int getCycle() {
		return this.currentCycle;
	}

	@Override
	public int getRemainingAliens() {
		return this.alienManager.getRemainingAliens();
	}
	
	@Override
	public int getSpeed() {
		return this.level.getSpeed();
	}
	
	@Override
	public Level getLevel() {
		return this.level;
	}

	@Override
	public void triggerExplosionDamage(ExplosiveAlien explosiveAlien) {
		Position explosivePosition = explosiveAlien.getPosition();

	    for (int deltaX = -1; deltaX <= 1; deltaX++) {
	        for (int deltaY = -1; deltaY <= 1; deltaY++) {
	            Position adjacentPosition = new Position(explosivePosition.getX() + deltaX, explosivePosition.getY() + deltaY);
	            
	            container.explosion(explosiveAlien,adjacentPosition);
	            }
	        }
	    }		
	
}
