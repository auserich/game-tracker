package game_tracker.exception;

public class UsernameTakenException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameTakenException(String username) {
		super("Username " + username + " already exists");
	}
}
