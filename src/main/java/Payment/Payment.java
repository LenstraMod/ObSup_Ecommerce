package Payment;

public class Payment {
  private String paymentID;
	private String paymentDate;
	protected String paymentStatus;
	protected int productAmount;
	protected String paymentMethod;
	
	public Payment(String paymentID, String paymentDate, String paymentStatus, int productAmount, String paymentMethod) {
		this.paymentID = paymentID;
		this.paymentDate = paymentDate;
		this.paymentStatus = paymentStatus;
		this.productAmount = productAmount;
		this.paymentMethod = paymentMethod;
	}
	
	public double calculatedPrice(double productPrice) {
		System.out.println("Jumlah: " + productAmount);
		return productPrice * productAmount;
	}
	
	public void paymentDetail() {
		System.out.println("ID Pembayaran: " + paymentID);
		System.out.println("Tanggal Pembayaran: " + paymentDate);
		System.out.println("Status Pembayaran: " + paymentStatus);
		System.out.println("Total harga: " + productAmount);
		System.out.println("Payment Method : " + this.paymentMethod);
	}
}
