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
    public boolean checkCommand(String[] words) {        //funcion agregada por mi
    	boolean seCumple = false;
    	if(words.length > 0) {
    			seCumple = true;
    		
    	}
    	return seCumple;
    }
    
    
	public void run() {
	    boolean running = true;
	    printGame();
	  
	    UCMShip ucmShip = game.getUCMShip();

	   while (running) {
		   String[] words = prompt();
	    	if(checkCommand(words)){
	    		if(words[0].equals("m")) {
	    			if(words[1].equals("left")) {
	    				 ucmShip.move(Move.LEFT);
	    			}
	    			
	    			else if(words[1].equals("lleft")) {
	    				 ucmShip.move(Move.LLEFT);
	    			}
	    			
                    else if(words[1].equals("right")) {
                    	 ucmShip.move(Move.RIGHT);
	    			}
	    			
                    else if(words[1].equals("rright")) {
                    	 ucmShip.move(Move.RRIGHT);
	    			}
	    			
	    		}
	    		
	    		else if(words[0].equals("s")) {
	    			if(!ucmShip.shootLaser()) {
	    				ucmShip.enableLaser();	
	    			}
	    			
	    		}
	    		game.getLaser().die();
	    		printGame();
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
