package game_tracker.exception;

import game_tracker.model.User;

public class UsernameTakenException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameTakenException(User user) {
		super("Username " + user.getUsername() + " already exists");
	}
}
