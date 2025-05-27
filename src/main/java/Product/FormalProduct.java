
package Product;

public class FormalProduct extends Product{
	
	private String dresscode,material;
	
	public FormalProduct(String productID, String productName, String productSize, String productColor,
			String productDescription, int stock, double price, String dresscode, String material ) {
		super(productID, productName, productSize, productColor, "Non-Formal Product", productDescription, stock, price);
		this.dresscode = dresscode;
		this.material = material;
	}
}
