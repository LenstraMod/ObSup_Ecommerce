package User;

public abstract class User {
	
	private String userID;
	private String username;
	private String password;
	private String email;
	
	
	public User(String userID, String email, String username, String password) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
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



	public abstract void login(String email, String Password);
	
	public abstract void register(String email, String username, String password);
	
}
