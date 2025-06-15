 package Test;

import java.util.*;
import User.*;
import Utility.*;

public class UserHandlingTest {
	
	
	public static void UserHandling(){
		
		UserManager UserBiz = new UserManager();
		
		int menu = 0;
		
		while(UserBiz.getIsLogin() == false) {
			UserMenu();
			String menuPrompt = "Pilih Menu : ";
			try {
				menu = MissionUtil.getIntInput(menuPrompt);
				if(menu < 0) {
					menu *= -1;
				}
			} catch(ArithmeticException e) {
				System.out.println("Masukkan angka positif");
			}
			
			switch(menu) {	
				case 1:
					try {
						System.out.println("Register Page");
						System.out.print("Masukkan Email : ");
						String email = MissionUtil.getStringInput();
						
						System.out.print("Masukkan Username : ");
						String username = MissionUtil.getStringInput();
						
						
						System.out.print("Masukkan Password : ");
						String password = MissionUtil.getStringInput();
						
						if(username.length() < 3) {
							throw new UsernameTooShortException();
						}
						
						if(password.length() < 6) {
							throw new PasswordTooShortException();
						}
						
						UserBiz.register(email,username,password);
						System.out.println();
						continue;
						
					} catch (UsernameTooShortException e) {
						System.out.println(e.getMessage());
					} catch (PasswordTooShortException e) {
						System.out.println(e.getMessage());
					} catch (InputEmptyException e) {
						System.out.println(e.getMessage());
					}
					break;
					
					
					
				case 2:
					System.out.println("Login Page");
					try {
						System.out.print("Masukkan Email : ");
						String email = MissionUtil.getStringInput();
						
						
						System.out.print("Masukkan Password : ");
						String password = MissionUtil.getStringInput();
						
						if(password.length() < 6) {
							throw new PasswordTooShortException();
						}
						
						UserBiz.login(email,password);
						System.out.println();
					} catch (PasswordTooShortException e) {
						System.out.println(e.getMessage());
					} catch (InputEmptyException e) {
						System.out.println(e.getMessage());
					}
			}
		}
	}
	
	
	static void UserMenu() {
		System.out.println("--------- Login dan Register ---------");
		System.out.println("1.Register");
		System.out.println("2.Login");
	}
}
