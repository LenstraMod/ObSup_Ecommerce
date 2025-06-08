package Rating;

import java.util.Date;

public class Rating {
	private Date ratingDate;
	private int ratingValue;
	
	public Rating(String productName, double price, String productSize, String productColor, String userID, Date ratingDate, int ratingValue) {
		super();
		this.ratingDate = ratingDate;
		this.ratingValue = ratingValue;
	}
	
	public String getRatingByProduct(String productId) {
		return "Rating berhasil ditambahkan ke produk dengan ID: " + productId;
    }
	
	public int ratingCalculation() {
		return this.ratingValue;
	}

	@Override
	public String toString() {
		return "Rating [ratingDate=" + ratingDate + ", ratingValue=" + ratingValue + "]";
	}
	
	
}
