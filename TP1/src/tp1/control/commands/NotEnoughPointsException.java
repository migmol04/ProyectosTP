package tp1.control.commands;

public class NotEnoughPointsException extends GameModelException {
	public NotEnoughPointsException() {
		super();
	}

	public NotEnoughPointsException(String message) {
		super(message);
	}

	public NotEnoughPointsException(Throwable cause) {
		super(cause);
	}

	public NotEnoughPointsException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughPointsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
