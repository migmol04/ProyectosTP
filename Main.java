package tp1;
import tp1.view.GamePrinter;
import tp1.logic.gameobjects.*;

import java.util.Locale;
import java.util.Scanner;

import tp1.control.Controller;
import tp1.logic.Game;
import tp1.logic.Level;
import tp1.view.Messages;
import static tp1.view.Messages.error;


public class Main {

	/**
	 * Show application help message
	 */
	private static void usage() {
		System.out.println(Messages.USAGE);
		System.out.println(Messages.USAGE_LEVEL_PARAM);
		System.out.println(Messages.USAGE_SEED_PARAM);
	}

	/**
	 * SpaceInvaders entry point
	 * 
	 * @param args Arguments for the game.
	 */
	public static void main(String[] args) {
	    Locale.setDefault(new Locale("es", "ES"));

	    if (args.length < 1 || args.length > 2) {
	        usage();
	    } else {
	        Level level = Level.valueOfIgnoreCase(args[0]);
	        if (level == null) {
	            System.out.println(Messages.ALLOWED_LEVELS);
	            usage();
	        } else {
	            long seed = System.currentTimeMillis() % 1000;
	            String seedParam = "";
	            try {
	                if (args.length == 2) {
	                    seedParam = args[1];
	                    seed = Long.parseLong(seedParam);
	                }

	                System.out.println(Messages.WELCOME);

	                System.out.println(String.format(Messages.CONFIGURED_LEVEL, level.name()));
	                System.out.println(String.format(Messages.CONFIGURED_SEED, seed));

	                Game game = new Game(level, seed);
	                Scanner scanner = new Scanner(System.in);
	                Controller controller = new Controller(game, scanner);

	                // Crear una instancia de UCMShip y agregarla al juego
	                UCMShip ucmShip = game.getUCMShip();
	                // Configura la instancia de UCMShip como sea necesario, por ejemplo, estableciendo su posición

	                // Crear una instancia de GamePrinter
	                GamePrinter gamePrinter = new GamePrinter(game);

	                // Inicia el juego y el bucle principal
	                controller.run(); // Este es el bucle principal

	                // Después del bucle principal, imprime el tablero final
	                String finalGameBoard = gamePrinter.toString();
	                System.out.println(finalGameBoard);

	            } catch (NumberFormatException nfe) {
	                System.out.println(String.format(Messages.SEED_NOT_A_NUMBER_ERROR, seedParam));
	                usage();
	            } catch (Exception e) {
	                System.out.println(error(e.getMessage()));
	            }
	        }
	    }
	}

}

