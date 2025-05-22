package Utility;

public class PasswordTooShortException extends Exception{
	
	public PasswordTooShortException(String message) {
		super(message);
	}
	
	public PasswordTooShortException() {
		super("Password minimal 6 karakter");
	}
	
}
