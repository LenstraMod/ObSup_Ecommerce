public class FormalProduct extends Product{
	
	private String dressCode;
	private String material;

	public FormalProduct(String productId, String productName, int productStock, double productPrice,
			String productSize, String productColor, String productDescription, String productCategory, String dressCode, String material) {
		
		super(productId, productName, productStock, productPrice, productSize, productColor, productDescription,
				productCategory);
		this.dressCode = dressCode;
		this.material = material;
	}
}
