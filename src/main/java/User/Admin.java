package User;

import Product.*;
import java.util.*;

public class Admin extends User{
	
	private HashMap<String, Object> products;
	
	public Admin(String userId, String username, String password, String email) {
		super(userId,username,password,email,"Admin");
	}
	
	public void addProduct(Product product) {
		
		try {
			products = new HashMap<>();	
			products.put(product.getProductID(), product);	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
