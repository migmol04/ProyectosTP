package tp1.control.commands;

public class OffWorldException extends GameModelException {

	private static final long serialVersionUID = -5704642792198318775L;

	public OffWorldException() {
	}

	public OffWorldException(String message) {
		super(message);
	}

	public OffWorldException(Throwable cause) {
		super(cause);
	}

	public OffWorldException(String message, Throwable cause) {
		super(message, cause);
	}

	public OffWorldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}