package Product;

import java.util.*;

import Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductHandler {

	 private ArrayList<Product> products;	 
	 
	 //User Function
	 
	 //Untuk mengambil semua produk database
	 public void GetAllProducts() {
		 
		 products = new ArrayList<>();
		 
		 //Koneksi ke database
		 Connection conn = null;
		 //Untuk mengeksekusi sql
		 PreparedStatement pstmt = null;
		 //Mengambil hasil dari eksekusi sql
		 ResultSet rs = null;
		 
		 //query sql untuk mengambil semua produk dari table products
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
					 product = new Product(String.valueOf(id),productName,productSize,productColor,"",productDescription,stock,price);
				 }
				 
				 products.add(product);
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
			 System.out.println("Error while fetching products : " + e.getMessage());
		 }	 
	 }
	 
	 //Mengambil semua produk dari arraylist  products
	 public void ShowProduct() {
		 for(Product prd: products) {
			 System.out.println(prd.toString());
			 System.out.println();
		 }
	 }
	 
	 //Mengambil semua produk dari arraylist yang category formal products
	 public ArrayList<Product> ShowFormalProduct(){
		 
		 ArrayList<Product> FP = new ArrayList<>();
		 
		 for(Product prd: products) {
			 if(prd instanceof FormalProduct) {
				 FP.add(prd);
			 }
		 }
		 
		 return FP;
	}
	 
	 //Mengambil semua produk dari arraylist yang category non formal products
	 public ArrayList<Product> ShowNonFormalProduct(){
		 ArrayList<Product> NFP = new ArrayList<>();
		 
		 for(Product prd: products) {
			 if(prd instanceof NonFormalProduct) {
				 NFP.add(prd);
			 }
		 }
		 
		 return NFP;
	 }
	 
	 //Jika terjadi pembelian maka stok berkurang sesuai jumlah produk yang beli oleh user
	 public void updateStock(String productId, int productAmount) {
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 
		 String sql = "UPDATE products SET stock = ? WHERE productId = ?";
		 
		 try {
			 conn = DBConnection.connect();
			 
			 if(conn == null) {
				 System.out.println("Failed to connect to database");
			 }
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, productAmount);
			 pstmt.setString(2, productId);
			 
			 int executePstmt = pstmt.executeUpdate();
			 
			 if(executePstmt > 0) {
				 
				 for(Product prd : products) {
					 if(prd.getProductID().equals(productId)) {
						 prd.setStock(productAmount);
					 }
				 }
				 System.out.println("Berhasil Update");
			 }
			 else {
				 System.out.println("Gagal Update");
			 }
		 } catch(SQLException e) {
			 e.printStackTrace();
			 System.out.println("Update Stock Error : "  + e.getMessage());
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.out.println("Update Stock Error : "  + e.getMessage());
		 }
	 }
	 
	 //Mengambil detail produk untuk halaman produk sesuai id produk
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
	 
	 //Admin Function
	 
	 //overloading
	 //Memasukkan data ke database untuk formal product
	 public boolean InsertProduct(String productName, String productSize, String productColor, String productCategory, String productDescription, int stock, double price, String dresscode, String material) {
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 boolean isSuccess = false;;
		 
		 String sql = "INSERT INTO products (productName,productSize,productColor,productCategory,productDescription,stock,price,dresscode,material) VALUES (?,?,?,?,?,?,?,?,?)";
		 
		 try {
			 conn = DBConnection.connect();
			 
			 if(conn == null) {
				 System.out.println("Failed to connect to database");
			 }
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, productName);
			 pstmt.setString(2, productSize);
			 pstmt.setString(3, productColor);
			 pstmt.setString(4, productCategory);
			 pstmt.setString(5, productDescription);
			 pstmt.setInt(6, stock);
			 pstmt.setDouble(7, price);
			 pstmt.setString(8, dresscode);
			 pstmt.setString(9, material);
			 
			 int executePstmt = pstmt.executeUpdate();
			 
			 if(executePstmt > 0) {
				 System.out.println("Berhasil tambah data");
				 isSuccess = true;
			 }
			 else {
				 throw new Exception("Gagal menambah data");
			 }
		 } catch(SQLException e) {
			 e.printStackTrace();
			 System.out.println("Add Stock Error : "  + e.getMessage());
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.out.println("Add Stock Error : "  + e.getMessage());
		 }
		 
		 return isSuccess;
	 }
	 
	//Memasukkan data ke database untuk non formal product
	 public boolean InsertProduct(String productName, String productSize, String productColor, String productCategory, String productDescription, int stock, double price, String ocassion) {
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 boolean isSuccess = false;;
		 
		 String sql = "INSERT INTO products (productName,productSize,productColor,productCategory,productDescription,stock,price,ocassion) VALUES (?,?,?,?,?,?,?,?)";
		 
		 try {
			 conn = DBConnection.connect();
			 
			 if(conn == null) {
				 System.out.println("Failed to connect to database");
			 }
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, productName);
			 pstmt.setString(2, productSize);
			 pstmt.setString(3, productColor);
			 pstmt.setString(4, productCategory);
			 pstmt.setString(5, productDescription);
			 pstmt.setInt(6, stock);
			 pstmt.setDouble(7, price);
			 pstmt.setString(8, ocassion);
			 
			 int executePstmt = pstmt.executeUpdate();
			 
			 if(executePstmt > 0) {
				 System.out.println("Berhasil Tambah Data");
				 isSuccess = true;
			 }
			 else {
				 System.out.println("Gagal Tambah Data");
			 }
		 } catch(SQLException e) {
			 e.printStackTrace();
			 System.out.println("Add product Error : "  + e.getMessage());
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.out.println("Add product Error : "  + e.getMessage());
		 }
		 
		 return isSuccess;
	 }
	 
	 //Edit produk sesuai id produk, property product, value yang baru
	 public boolean UpdateProduct(String productId, String productProperty, String newValue) {
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 boolean isSuccess = false;;
		 
		 String sql = "UPDATE products SET " + productProperty + " = ? WHERE productId = ?";
		 
		 try {
			 conn = DBConnection.connect();
			 
			 if(conn == null) {
				 System.out.println("Failed to connect to database");
			 }
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, newValue);
			 pstmt.setString(2, productId);
			 
			 int executePstmt = pstmt.executeUpdate();
			 
			 if(executePstmt > 0) {
				 System.out.println("Berhasil update data");
				 isSuccess = true;
			 }
			 else {
				 throw new Exception("Gagal update data");
			 }
		 } catch(SQLException e) {
			 e.printStackTrace();
			 System.out.println("Update product Error : "  + e.getMessage());
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.out.println("Update product Error : "  + e.getMessage());
		 }
		 
		 return isSuccess;
	 }
	 
	 //Delete produk sesuai id produk
	 public boolean DeleteProduct(String productId){
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 boolean isDelete = false;
		 
		 String SQL = "DELETE FROM products WHERE productId = ?";
		 
		 try {
			 conn = DBConnection.connect();
			 
			 if(conn == null) {
				 System.err.println("Failed to connect to database");
			 }
			 
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, productId);
			 
			 int executePstmt = pstmt.executeUpdate();
			 
			 if(executePstmt > 0) {
				 System.out.println("Delete data berhasil");
				 isDelete = true;
			 }
			 else {
				 throw new Exception("Delete data gagal");
			 }
			 
		 } catch(SQLException e) {
			 e.printStackTrace();
			 System.out.println("Delete Error : " + e.getMessage());
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.out.println("Delete Error :  " + e.getMessage());
		 }
		 return isDelete;	 
	 }
}
