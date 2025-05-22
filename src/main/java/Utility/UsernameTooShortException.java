package Utility;

public class UsernameTooShortException extends Exception {

	public UsernameTooShortException(String message) {
		super(message);
	}
	
	public UsernameTooShortException() {
		super("Minimal username 3 karakter");
	}
	
}
