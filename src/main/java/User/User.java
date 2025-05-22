package User;

import Utility.*;

public class User {
	
	private String userID;
	private String username;
	private String password;
	private String email;
	private String role;
	
	
	public User(String userID, String email, String username, String password, String role) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws UsernameTooShortException {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getUserID() {
		return userID;
	}

	public String getPassword() {
		return password;
	}
	
}
