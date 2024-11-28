package tp1.control.commands;

public class GameModelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GameModelException() {
	}

	public GameModelException(String message) {
		super(message);
	}

	public GameModelException(Throwable cause) {
		super(cause);
	}

	public GameModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
