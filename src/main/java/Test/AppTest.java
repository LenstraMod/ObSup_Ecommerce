package Test;

import User.*;

public class AppTest {
	
	public static void main(String[] args) {
		UserHandlingTest.UserHandling();
		System.out.println("Selamat datang : " + UserManager.getThisSessionUser().getUsername());
		ProductHandlingTest.ProductHandling();
		
		
	} 
}
