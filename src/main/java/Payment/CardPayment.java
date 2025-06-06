package Payment;

import java.util.Date;

public class CardPayment extends Payment{
	private String cardNumber;
	 private String cardName;
	 private String cardExpiryDate;
	 private String cvv;
	
	public CardPayment(String paymentID, String paymentDate, String paymentStatus, int productAmount, 
			String cardNumber, String cardName, String cardExpiryDate, String cvv) {
		super(paymentID, paymentDate, paymentStatus, productAmount);
		this.cardNumber = cardNumber;
		this.cardName = cardName;
		this.cardExpiryDate = cardExpiryDate;
		this.cvv = cvv;
	}
	
	public CardPayment( String cardNumber,String cardName, String cardExpiryDate, String cvv) {
		super("","","",0);
		this.cardNumber = cardNumber;
		this.cardName = cardName;
		this.cardExpiryDate = cardExpiryDate;
		this.cvv = cvv;
	}



	public String getCardNumber() {
		return cardNumber;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public String getCardExpiryDate() {
		return cardExpiryDate;
	}
	
	
	public String getCvv() {
		return cvv;
	}
	public void paymentDetail(String productID, String userid) {
		System.out.println("Metode Pemabayaran: Kartu Kredit");
		System.out.println("Nama Pemilik Kartu: " + cardName );
		System.out.println("Nomor kartu: " + cardNumber);
		System.out.println("Tanggal kadaluarsa: " + cardExpiryDate);
	}
}
