package ua.nure.pekariev.exceptions;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 7739765038981046496L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

}
