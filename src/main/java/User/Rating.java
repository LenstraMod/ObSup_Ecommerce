package User;

import java.util.Date;

public class Rating {
	private String ratingId;
	private String ratingDate;
	private int ratingValue;
	private String productId;
	
	public Rating( String ratingId, String ratingDate, int ratingValue, String productId) {
		super();
		this.ratingId = ratingId;
		this.ratingDate = ratingDate;
		this.ratingValue = ratingValue;
		this.productId = productId;
	}
	
	public String getRatingByProduct(String productId) {
		return "Rating berhasil ditambahkan ke produk dengan ID: " + productId;
    }
	
	public int ratingCalculation() {
		return this.ratingValue;
	}
	
	

	public int getRatingValue() {
		return ratingValue;
	}

	public String getProductId() {
		return productId;
	}

	@Override
	public String toString() {
		return "Rating [ratingDate=" + ratingDate + ", ratingValue=" + ratingValue + "]";
	}
	
	
}
