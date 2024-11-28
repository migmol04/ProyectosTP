package tp1.control.commands;

public class NotAllowedMoveException extends GameModelException {

	private static final long serialVersionUID = -5704642792198318775L;

	public NotAllowedMoveException() {
	}

	public NotAllowedMoveException(String message) {
		super(message);
	}

	public NotAllowedMoveException(Throwable cause) {
		super(cause);
	}

	public NotAllowedMoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAllowedMoveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}