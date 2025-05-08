package User;

import java.util.ArrayList;

public class RegisteredUsers extends User{
	
	private String address;
	private String PaymentMethod;
	
	private ArrayList<User> users;
	
	public RegisteredUsers(String userID, String username, String password, String email, String address, String PaymentMethod) {
		super(userID, username, password, email);
		this.address = address;
		this.PaymentMethod = PaymentMethod;
		this.users = new ArrayList<>();
	}
	
	@Override
	public void login(String email,String password) {
		for(User user: users) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				System.out.println("Login Success");
			}
			else {}
			System.out.println("Email or Password wrong");
		}
	}
	
	@Override
	public void register(String email, String username, String password) {
		if(username.length() < 3) {
			System.out.println("Minimal username 3 huruf");
		}
		else {
			if(password.length() < 6) {
				System.out.println("Minimal password 6 huruf");
			}
			else {
				User newUSer = new RegisteredUsers("1",email,username,password,"","");
				users.add(newUSer);
			}
		}
		
		
	}
	
	

}
