package tp1.control.commands;

public class NoShockWaveException extends Exception {
	public NoShockWaveException() {
	}

	public NoShockWaveException(String message) {
		super(message);
	}

	public NoShockWaveException(Throwable cause) {
		super(cause);
	}

	public NoShockWaveException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoShockWaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
