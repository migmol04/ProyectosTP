package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

import tp1.view.Messages;

public class CommandGenerator {

	private static final List<Command> availableCommands = Arrays.asList(
		new MoveCommand(),
		new LaserCommand(),
		new SuperLaserCommand(),
		new ShockwaveCommand(),
		new ListCommand(),
		new ResetCommand(),
		new HelpCommand(),
		new ExitCommand(),
		new NoneCommand()
		//TODO fill with your code
	);

	public static Command parse(String[] commandWords) throws CommandParseException, NotAllowedMoveException{		
		Command command = null;
		if(commandWords[0]== "") {
			commandWords[0] = "none";
		}
		for (Command c: availableCommands) {
			if (c.matchCommandName(commandWords[0])) {
	            command = c.parse(commandWords);
	           
	        }
		}
		if(command == null) {
			throw new CommandParseException(Messages.UNKNOWN_COMMAND);
		}
		return command;
	}
		
	public static String commandHelp() {
	    StringBuilder commands = new StringBuilder();	
	    for (Command c: availableCommands) {
	        commands.append(c.getDetails()).append(": ").append(c.getHelp()).append("\n");
	    }
	    return commands.toString();
	}


}
