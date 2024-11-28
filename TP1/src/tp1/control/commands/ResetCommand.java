package tp1.control.commands;

import java.io.File;
import java.io.IOException;

import tp1.control.ExecutionResult;
import tp1.control.InitialConfiguration;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.logic.Move;
import tp1.view.Messages;

public class ResetCommand extends Command {
	
    private InitialConfiguration selectedConfiguration;
    private String filename;  // Nuevo campo para almacenar el nombre del archivo
    
	public ResetCommand() {}

    public ResetCommand(InitialConfiguration selectedConfiguration) {
        this.selectedConfiguration = selectedConfiguration;
    }
    public ResetCommand(String filename) {
        this.filename = filename;
    }

    
	@Override
	protected String getName() {
		return Messages.COMMAND_RESET_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_RESET_SHORTCUT;
	}

	@Override
	protected String getDetails() {
		return Messages.COMMAND_RESET_DETAILS;
	}

	@Override
	protected String getHelp() {
		return Messages.COMMAND_RESET_HELP;
	}

	@Override
    public ExecutionResult execute(GameModel game) throws CommandExecuteException {
		 try {
	            // Si se proporciona un nombre de archivo, intenta cargar la configuración desde el archivo
	            if (filename != null) {
	                File file = new File(filename);
	                if (file.exists() && file.isFile()) {
	                    try {
	                        InitialConfiguration conf = InitialConfiguration.readFromFile(filename);
	                        game.reset(conf);
	                        return new ExecutionResult(true, false, "");
	                    } 
	                    catch (IOException e) {
	                        // Capturamos la excepción de lectura del archivo y la manejamos
	                    	throw new CommandExecuteException("Error reading configuration file: " + e.getMessage());
	                    }
	                } 
	                
	                else {
	                    // Si el archivo no existe o no es un archivo, proporciona un mensaje de error
	        	        throw new CommandExecuteException("File not found: " + filename);
	                }
	            } 
	            
	            else {
	                // Si no se proporciona un nombre de archivo, utiliza la configuración seleccionada
	                game.reset(selectedConfiguration);
	                return new ExecutionResult(true, false, "");
	            }
	        } 
		 
		 catch (InitializationException e) {
	            // Capturamos la excepción de inicialización y la manejamos
	        	throw new CommandExecuteException(e.getMessage());
	        }
	}
	
	@Override
    public Command parse(String[] commandWords) {
        if (commandWords.length == 1 || (commandWords[1].equalsIgnoreCase("none"))) {
            return new ResetCommand(InitialConfiguration.NONE);
        } else if (commandWords.length == 2) {
            return new ResetCommand(commandWords[1]);
        }

        return null;
    }

}