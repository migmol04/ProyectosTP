package tp1.control;

import static tp1.view.Messages.debug;
import tp1.logic.*;
import tp1.logic.gameobjects.*;

import java.util.Scanner;

import tp1.view.GamePrinter;
import tp1.view.Messages;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private Game game;
	private Scanner scanner;
	private GamePrinter printer;
	private RegularAlien alien;
	private Move dir;
	private Position pos;
	private AlienManager manager;
	public boolean laserFired = false;

	public Controller(Game game, Scanner scanner) {
		this.game = game;
		this.scanner = scanner;
		printer = new GamePrinter(game);
		pos = new Position(4, 4);
		manager = new AlienManager(game, game.getLevel());
		dir = Move.RIGHT;
		alien = new RegularAlien(5, pos, 5, game, dir, 1, 100, manager);

	}

	/**
	 * Show prompt and request command.
	 *
	 * @return the player command as words
	 */
	private String[] prompt() {
		System.out.print(Messages.PROMPT);
		String line = scanner.nextLine();
		String[] words = line.toLowerCase().trim().split("\\s+");

		System.out.println(debug(line));

		return words;
	}

	/**
	 * Runs the game logic
	 */
	public void run() {
	    boolean running = true;
	    printGame();

	    while (running) {
	        String[] words = prompt();

	        if (words.length > 0) {
	            String command = words[0];
	            if (command.equals("m")) {
	                if (words.length > 1) {
	                    String direction = words[1];
	                    UCMShip ucmShip = game.getUCMShip();

	                    // Handle movement commands
	                    if (direction.equals("left")) {
	                        ucmShip.move(Move.LEFT);
	                        alien.automaticMove();
	                        printGame();
	                    } else if (direction.equals("right")) {
	                        ucmShip.move(Move.RIGHT);
	                        alien.automaticMove();
	                        printGame();
	                    } else if (direction.equals("rright")) {
	                        ucmShip.move(Move.RRIGHT);
	                        alien.automaticMove();
	                        printGame();
	                    } else if (direction.equals("lleft")) {
	                        ucmShip.move(Move.LLEFT);
	                        alien.automaticMove();
	                        printGame();
	                    } else if (direction.equals("up")) {
	                        ucmShip.move(Move.UP);
	                        alien.automaticMove();
	                        printGame();
	                    } else if (direction.equals("down")) {
	                        ucmShip.move(Move.DOWN);
	                        alien.automaticMove();
	                        printGame();
	                    }
	                }
	            }  else if (command.equals("s")) {
	                // Handle the "s" command to shoot the laser
	                if (!laserFired) {
	                    UCMShip ucmShip = game.getUCMShip();
	                    if (ucmShip.shootLaser()) {
	                        // Create a new UCMLaser and perform its automatic movement
	                        UCMLaser laser = new UCMLaser(Move.UP, game);
	                        laserFired = true;  
	                        printGame();
	                    }
	                }    }
	        }
	    }
	}
		
	/**
	 * Draw / paint the game
	 */
	private void printGame() {
		System.out.println(printer);
	}
	
	/**
	 * Prints the final message once the game is finished
	 */
	public void printEndMessage() {
		System.out.println(printer.endMessage());
	}
	
}
