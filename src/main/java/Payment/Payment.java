package Payment;

public class Payment {
  private String paymentID;
	private String paymentDate;
	protected String paymentStatus;
	protected int productAmount;
	
	public Payment(String paymentID, String paymentDate, String paymentStatus, int productAmount) {
		this.paymentID = paymentID;
		this.paymentDate = paymentDate;
		this.paymentStatus = paymentStatus;
		this.productAmount = productAmount;
	}
	
	public double calculatedPrice(double productPrice) {
		System.out.println("Jumlah: " + productAmount);
		return productPrice * productAmount;
	}
	
	public void paymentDetail(String productID, String userId) {
		System.out.println("ID Pembayaran: " + paymentID);
		System.out.println("Tanggal Pembayaran: " + paymentDate);
		System.out.println("Status Pembayaran: " + paymentStatus);
		System.out.println("Jumlah Produk: " + productAmount);
	}
}
