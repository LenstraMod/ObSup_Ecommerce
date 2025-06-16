package User;

import Product.*;
import java.util.*;
	//Class user untuk admin 
	public class Admin extends User{
	
	
	public Admin(String userId, String username, String password, String email) {
		super(userId,username,password,email,"Admin");
	}

}
