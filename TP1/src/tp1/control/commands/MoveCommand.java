package tp1.control.commands;

import tp1.control.ExecutionResult;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.logic.Move;
import tp1.view.Messages;

public class MoveCommand extends Command {

	private Move move;

	public MoveCommand() {}

	protected MoveCommand(Move move) {
		this.move = move;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_MOVE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_MOVE_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_MOVE_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_MOVE_HELP;
	}

	
	@Override
	public ExecutionResult execute(GameModel game) throws CommandExecuteException, NotAllowedMoveException{
	    try {
	        game.movePlayer(move);
	    } catch (NotAllowedMoveException | OffWorldException e) {
	        throw new CommandExecuteException(e.getMessage());
	    }

	    return new ExecutionResult(true);
	}



	@Override
	public Command parse(String[] commandWords) throws CommandParseException, NotAllowedMoveException {
	    if (commandWords.length == 2) {
	        switch (commandWords[1].toLowerCase()) {
	            case "left":
	                return new MoveCommand(Move.LEFT);
	            case "right":
	                return new MoveCommand(Move.RIGHT);
	            case "lleft":
	                return new MoveCommand(Move.LLEFT);
	            case "rright":
	                return new MoveCommand(Move.RRIGHT);
	        }
	    } else if (commandWords.length < 2) {
	        throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
	    }

	  
	    return new MoveCommand() {
	        @Override
	        public ExecutionResult execute(GameModel game) throws CommandExecuteException, NotAllowedMoveException {
	        	String mensaje = Messages.DIRECTION_ERROR + commandWords[1] + "\n" + "Allowed directions: <left|lleft|right|rright>"; 
	            throw new CommandExecuteException(mensaje);
	        }
	    };
	}

}
