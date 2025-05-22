package User;

public class RegisteredUsers extends User{
	
	private String address;
	private String PaymentMethod;
	
	public RegisteredUsers(String userID, String email, String username, String password,  String address, String PaymentMethod) {
		super(userID, email, username, password,"User");
		this.address = address;
		this.PaymentMethod = PaymentMethod;
	}

	@Override
	public String toString() {
		return "User ID : " + this.getUserID() + "\nEmail : " + this.getEmail() 
											   + "\nPassword : " + this.getPassword();
	}
	
	
}
