package Test;

import User.*;

public class AppTest {
	
	public static void main(String[] args) {
		while(true) {
			UserHandlingTest.UserHandling();
			if(UserManager.getThisSessionUser() instanceof RegisteredUsers) {
				System.out.println("Selamat datang : " + UserManager.getThisSessionUser().getUsername());
				ProductHandlingTest.ProductHandling();	
			}
			else if(UserManager.getThisSessionUser() instanceof Admin) {
				System.out.println("Welcome Admin, " + UserManager.getThisSessionUser().getUsername());
				ProductHandlingTest.AdminProductHandling();
			}
		}
		
	} 
}
