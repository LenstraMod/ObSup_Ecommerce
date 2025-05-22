package Utility;

public class InputEmptyException extends Exception{

	// Custom Message Exception
	public InputEmptyException(String message) {
		super(message);
	}
	
	public InputEmptyException() {
		super("Input cannot be empty");
	}
	
}
