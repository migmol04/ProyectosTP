package tp1.control.commands;

import tp1.view.Messages;

public abstract class NoParamsCommand extends Command {


	 public Command parse(String[] commandWords) throws CommandParseException {
		 	if (matchCommandName(commandWords[0])) {
		 		if(commandWords.length == 1)
		 			return this;
		 		else
		 			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		 	}
		 	else
		 		return null;	
		 }
}
	
	
