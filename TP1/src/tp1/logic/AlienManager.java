package tp1.logic;

import tp1.control.InitialConfiguration;
import tp1.control.commands.InitializationException;
import tp1.logic.gameobjects.AlienShip;
import tp1.logic.gameobjects.DestroyerAlien;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.RegularAlien;
import tp1.logic.gameobjects.ShipFactory;
import tp1.logic.gameobjects.Ufo;

public class AlienManager  {
	
	private Game game;
	private int remainingAliens;
	private Level level;
	private boolean onBorder;
	private boolean squadInFinalRow;
	private int numAliensEnBorde;
	
	
	public AlienManager(Game game, Level level) {
		this.game = game;
		this.level = level;
		this.onBorder = false;
		this.remainingAliens = 0;
	}

	public  GameObjectContainer initialize(InitialConfiguration selectedConfiguration) throws InitializationException {
		this.remainingAliens = this.game.getLevel().getNumDestroyerAliens() + this.game.getLevel().getNumRegularAliens();
		GameObjectContainer container = new GameObjectContainer();
		if(selectedConfiguration== InitialConfiguration.NONE || selectedConfiguration == null ) {
		initializeRegularAliens(container);
		initializeDestroyerAliens(container);
		initializeOvni(container);
		}
		else costumedInitialization(container,selectedConfiguration);
		return container;

	}
	
	public void costumedInitialization(GameObjectContainer container, InitialConfiguration conf) throws InitializationException {
		int x = 0;
        int y = 0;
        String string_x = null;
        String string_y = null;
        this.remainingAliens = 0;
	    try {
	        for (String shipDescription : conf.getShipDescription()) {
	            String[] words = shipDescription.trim().split("\\s+");

	            if (words.length != 3) {
	                throw new InitializationException("Invalid initial configuration.\n"  + "Incorrect entry: " + "\"" + shipDescription + "\"" + ". Insufficient parameters.");
	            }
	            
	            string_x=words[1];
	            string_y=words[2];
	            
	            // Intentar convertir las palabras a números
	            x = Integer.parseInt(string_x);
	            y = Integer.parseInt(string_y);
	            String shipType = words[0];
	          
	            if (!isValidPosition(x, y)) {
	                throw new InitializationException("Invalid initial configuration.\n"  + "Position (" + x + ", " + y + ") is off board.");
	            }
	            
	            AlienShip ship = ShipFactory.spawnAlienShip(shipType, game, new Position(x, y), this);
	            container.add(ship);
	    		this.remainingAliens++;
	        }
	        initializeOvni(container);
	    } 
	    
	    catch (NumberFormatException e) {
            throw new InitializationException("Invalid initial configuration.\n"  + "Invalid position: " + "(" + string_x + ", " + string_y + ")");
	    }
	    
	}
	
	private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < Game.DIM_X && y >= 0 && y < Game.DIM_Y;
	}
	
	
	private void initializeOvni(GameObjectContainer container) {
		container.add(new Ufo(game, new Position(9, 0), 1));
	}
	
	private void initializeRegularAliens (GameObjectContainer container) {
	
		container.add(new RegularAlien(this.game, new Position(2,1), this));
		container.add(new RegularAlien(this.game, new Position(3,1), this));
		container.add(new RegularAlien(this.game, new Position(4,1), this));
		container.add(new RegularAlien(this.game, new Position(5,1), this));
		if(this.level != Level.EASY) {
			container.add(new RegularAlien(this.game, new Position(2,2), this));
			container.add(new RegularAlien(this.game, new Position(3,2), this));
			container.add(new RegularAlien(this.game, new Position(4,2), this));
			container.add(new RegularAlien(this.game, new Position(5,2), this));
			
		}

	}
	
	private void initializeDestroyerAliens(GameObjectContainer container) {
		if(this.level == Level.EASY) {
			container.add(new DestroyerAlien(this.game, new Position(3,2), this));
			container.add(new DestroyerAlien(this.game, new Position(4,2), this));
			
		}
		
		else if(this.level == Level.HARD) {
			container.add(new DestroyerAlien(this.game, new Position(3,3), this));
			container.add(new DestroyerAlien(this.game, new Position(4,3), this));
		}
		
		else {
			container.add(new DestroyerAlien(this.game, new Position(3,3), this));
			container.add(new DestroyerAlien(this.game, new Position(4,3), this));
			container.add(new DestroyerAlien(this.game,  new Position(2,3), this));
			container.add(new DestroyerAlien(this.game, new Position(5,3), this));
			
			
		}
	}
	
	public boolean getOnBorder() {
		return this.onBorder;
	}

	
	public void setOnBorder() {
		this.onBorder = true;
	}
	
	public void setOnBorderFalse() {
		this.onBorder = false;
	}
	
	public int getRemainingAliens() {
	    return this.remainingAliens;
	}
	
	public void alienDead() {
		this.remainingAliens--;
	}
	
	public boolean allAlienDead() {
		return this.remainingAliens == 0;
	}
	
	public void setAlienEnBorde() {
		this.numAliensEnBorde = this.remainingAliens;
	}
	
	public void setHaveLanded() {
		this.squadInFinalRow = true;
	}
	
	public int getAliensEnBorde() {
		return this.numAliensEnBorde;
	}
	
	public void DecreaseOnBorder() {
		this.numAliensEnBorde--;
	}
	
	public boolean getSquadInFinalRow() {
	  return this.squadInFinalRow;
	}
	
}
