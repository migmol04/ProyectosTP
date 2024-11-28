package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;

import tp1.control.commands.InitializationException;
import tp1.logic.AlienManager;
import tp1.logic.Position;

public abstract class ShipFactory {
	
	private static final List<AlienShip> AVAILABLE_ALIEN_SHIPS = Arrays.asList(
			new RegularAlien(),
			new DestroyerAlien(),
			new ExplosiveAlien()

		);
	
	protected abstract AlienShip copy(GameWorld game, Position pos, AlienManager am);
	
	
	public static AlienShip spawnAlienShip(String input, GameWorld game, Position pos, AlienManager am) throws InitializationException {
	    for (AlienShip ship : AVAILABLE_ALIEN_SHIPS) {
	        if (ship.getSymbol().equals(input)) {
	            return ship.copy(game, pos, am);
	        }

	    }
	    throw new InitializationException("Invalid initial configuration.\n" + "Unknown ship: \"" + input + "\"");
	}

}