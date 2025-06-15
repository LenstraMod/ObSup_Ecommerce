package Product;

public class Product {
	private String productID,productName,productSize,productColor,productCategory,productDescription;
	private int stock;
	private double price;
	
	public Product(String productID, String productName, String productSize, String productColor,
			String productCategory, String productDescription, int stock, double price) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.productSize = productSize;
		this.productColor = productColor;
		this.productCategory = productCategory;
		this.productDescription = productDescription;
		this.stock = stock;
		this.price = price;
	}
	
	public String getProductID() {
		return productID;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getStock() {
		return this.stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String getProductCategory() {
		return this.productCategory;
	}
	
	public boolean isAvailable() {
		return true;
	}
	
	//Return Format table dari product
	public String toString() {
		
		return "Product ID : " + this.productID + "\nProduct Name : " + this.productName 
						+ "\nProduct Stock : " + this.stock 
						+ "\nProduct Price : " + this.price
						+ "\nProduct Size : " + this.productSize
						+ "\nProduct Color : " + this.productColor
						+ "\nProduct Description : " + this.productDescription
						+ "\nProduct Category : " + this.productCategory;
	}
	
	
	
}
