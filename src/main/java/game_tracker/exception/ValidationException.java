package game_tracker.exception;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ValidationException() {
		super("Required fields cannot be null");
	}
	
	public ValidationException(String msg) {
		super(msg);
	}
}
