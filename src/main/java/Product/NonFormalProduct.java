package Product;

public class NonFormalProduct extends Product{

	private String ocassion;

	public NonFormalProduct(String productID, String productName, String productSize, String productColor,
			String productDescription, int stock, double price, String ocassion) {
		super(productID, productName, productSize, productColor, "Non-Formal Product", productDescription, stock, price);
		this.ocassion = ocassion;
	}
	
	
}
