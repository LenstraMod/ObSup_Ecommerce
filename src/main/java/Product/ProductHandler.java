package Product;

import java.util.*;

public class ProductHandler {

	 private HashMap<String,Product> products;
	 
	 public void InitializeProduct() {
		 products = new HashMap<String,Product>();
		 
		 products.put("P-" + GenerateID(), new NonFormalProduct("P-" + GenerateID(),"Kaos Gorila","XL","White","Keren Nyaman",10,100000.0,"Santai"));
		 products.put("P-" + GenerateID(), new FormalProduct("P-" + GenerateID(),"Texudo Wow Wow","XL","Black","Cool kecek",15,1000000.0,"Acara Penting","Kain berkualitas"));
		
	 }
	 
	 public void GetAllProducts() {
		 for(String key : products.keySet()) {
			 Product product = products.get(key);
			 System.out.println(key + product);
		 }
	 }
	 
	 public int GenerateID() {
		 return products.size() + 1;
	 }
	
}
