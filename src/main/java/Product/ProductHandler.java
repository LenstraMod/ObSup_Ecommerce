package Product;

import java.util.*;

import Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductHandler {

	 private ArrayList<Product> products;
	 
//	 public void InitializeProduct() {
//		 
//		 products = new ArrayList<>();
//		 
//		 products.add(new FormalProduct("","a","a","a","a",1,1.0,"a","a"));
//		 products.add(new NonFormalProduct("","a","a","a","a",1,1.0,"a"));
//		 products.add(new NonFormalProduct("","a","a","a","a",1,1.0,"a"));
//		 
//		String sql = "INSERT INTO products VALUES(?,?,?,?,?,?,?,?)";
//
//		try(Connection conn = DBConnection.connect(); PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(sql) : null){
//			if(conn == null) {
//				System.err.println("Cannot connect to database , failed to initialize product");
//			}
//			
//			if(pstmt == null) {
//				System.err.println("Cannot make the SQL statement , failed to initialize product");
//			}
//			
//			pstmt.setString(2, products[1]);
//			
//		}
//		
//	 }
	 
	 public void GetAllProducts() {
		 
		 products = new ArrayList<>();
		 
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 
		 String SQL = "SELECT * FROM Products";
		 
		 try {
			 conn = DBConnection.connect();
			 
			 if(conn == null) {
				 System.err.println("Failed to connect dotabase");
				 return;
			 }
			 
			 pstmt = conn.prepareStatement(SQL);
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()) {
				 int id =  rs.getInt("productId");
				 String productName = rs.getString("productName");
				 String productSize = rs.getString("productSize");
				 String productColor = rs.getString("productColor");
				 String productCategory = rs.getString("productCategory");
				 String productDescription = rs.getString("productDescription");
				 int stock = rs.getInt("stock");
				 double price = rs.getDouble("price");
				 
				 Product product = null;
				 
				 if("Formal Product".equalsIgnoreCase(productCategory)) {
					 String dresscode = rs.getString("dresscode");
					 String material = rs.getString("material");
					 product = new FormalProduct(String.valueOf(id),productName,productSize,productColor,productDescription,stock,price,dresscode,material);
				 }
				 else if("Non-Formal Product".equalsIgnoreCase(productCategory)) {
					 String ocassion = rs.getString("ocassion");
					 product = new NonFormalProduct(String.valueOf(id),productName,productSize,productColor,productDescription,stock,price,ocassion);
				 }
				 else {
					 System.out.println("Product wihtout category");
					 product = new Product(String.valueOf(id),productName,productSize,productColor,"",productDescription,stock,price);
				 }
				 
				 products.add(product);
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
			 System.out.println("Error while fetching products : " + e.getMessage());
		 }	 
	 }
	 
	 public void ShowProduct() {
		 for(Product prd: products) {
			 System.out.println(prd.toString());
			 System.out.println();
		 }
	 }
	 
	 public ArrayList<Product> ShowFormalProduct(){
		 
		 ArrayList<Product> FP = new ArrayList<>();
		 
		 for(Product prd: products) {
			 if(prd instanceof FormalProduct) {
				 FP.add(prd);
			 }
		 }
		 
		 return FP;
	}
	 
	 public ArrayList<Product> ShowNonFormalProduct(){
		 ArrayList<Product> NFP = new ArrayList<>();
		 
		 for(Product prd: products) {
			 if(prd instanceof NonFormalProduct) {
				 NFP.add(prd);
			 }
		 }
		 
		 return NFP;
	 }
	 
	 public Product ShowProductDetail(String productId) {
		 Product product = null;
		 boolean isProductGet = false;
		 
		 for(Product prd: products) {
			 if(prd.getProductID().equals(productId)) {
				 product = prd;
				 isProductGet = true;
			 }
		 }
		 
		 if(isProductGet = false) {
			 return null;
		 }
		 
		 return product;
	 }
}
