package game_tracker.exception;

public class UsernameNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UsernameNotFoundException(String username) {
		super("Username of " + username + " was not found");
	}
}
