package User;

import java.util.ArrayList;

public class UserManager {
	
	private ArrayList<RegisteredUsers> users;
	private boolean isLogin = false;
	
	public UserManager() {
		this.users = new ArrayList<>();
	}
	
	public void register(String email, String username,String password) {	
		RegisteredUsers newUser = new RegisteredUsers(Integer.toString(IDGenerator()),email,username,password,"","");
		users.add(newUser);
		System.out.println("Register Berhasil");
	}
	
	public void login(String email, String password) {
		for(User user: users) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				isLogin = true;
				System.out.println("Login Berhasil");
			}
			else {
				System.out.println("Email atau Password salah");
			}
		}
	}
	
	public boolean getIsLogin() {
		return isLogin;
	}
	
	public void seeDetails() {
		for(RegisteredUsers user: users) {
			System.out.println(user.toString());
		}
	}
	
	int IDGenerator() {
	    return users.size() + 1;
	}

}
